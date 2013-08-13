package com.onesoft.monsters;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Weapon extends WeaponCommon {
	public static final int        ANCHOR_WIDTH = 5;
	public static final int        ANCHOR_HEIGHT = 5;
	public static final int        MAX_BULLET_SHOOTED = 3;
	protected Bullet               m_currentBullet = null;
	protected ArrayList<Bullet>    m_bulletsShooted = null;
	
	public Weapon(Simulation s) {
		super(s);
		
		//create the anchor
		BodyDef bd = new BodyDef();
		bd.position.set(Settings.WEAPON_X, Settings.WEAPON_Y);
		bd.type = BodyType.StaticBody;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(ANCHOR_WIDTH / 2, ANCHOR_HEIGHT / 2,
				new Vector2(0,0),0.0f);
		m_theBody = m_theSimulation.addBody(bd, shape, 0.0f);
		m_theBody.setUserData("Anchor");
		
		m_currentBullet = new Bullet(m_theSimulation, m_theBody);
		m_bulletsShooted = new ArrayList<Bullet>(10);
	}

	public void shoot(){
		if(m_currentBullet != null){
			if(m_bulletsShooted.size() > MAX_BULLET_SHOOTED)
			{
				return ;
			}
			
			Resources.playSound(Resources.m_sound_shoot);
			
			m_currentBullet.shoot();
			
			m_bulletsShooted.add(m_currentBullet);
			
			m_currentBullet = new Bullet(m_theSimulation, m_theBody);
		}
	}
	
	public void speedUp(){
		if(m_currentBullet != null){
			m_currentBullet.speedUp();
		}
	}
	
	public void normalSpeed(){
		if(m_currentBullet != null){
			m_currentBullet.normalSpeed();
		}
	}
	
	@Override
	public void present(float deltatime) {
		float x = m_theBody.getPosition().x;
		float y = m_theBody.getPosition().y;
		
		float angle = m_theBody.getAngle();
		
		Render.draw(Resources.m_anchor_region, x - ANCHOR_WIDTH/2, y - ANCHOR_HEIGHT/2,
				ANCHOR_WIDTH , ANCHOR_HEIGHT,angle);
		
		if(m_currentBullet != null){
			m_currentBullet.present(deltatime);
		}
		
		for(Bullet b : m_bulletsShooted){
			if(b != null){
				b.present(deltatime);
			}
		}
	}

	@Override
	public void update(float deltatime) {
		if(m_currentBullet != null){
			if (m_currentBullet.isBombed()) {
				m_currentBullet.dispose();
				m_currentBullet = null;
			} else {
				m_currentBullet.update(deltatime);
			}
		}
		
		ArrayList<Bullet> temp = new ArrayList<Bullet>();
		for(Bullet b:m_bulletsShooted){
			if(b != null){
				if(b.isBombed()){
					b.dispose();
					temp.add(b);
				}
			}
		}
		m_bulletsShooted.removeAll(temp);
	}

	@Override
	public void dispose() {
		if(m_currentBullet != null){
			m_currentBullet.dispose();
		}
		for(Bullet b:m_bulletsShooted){
			if(b != null){
				b.dispose();
			}
		}
		m_bulletsShooted.clear();
	}
}
