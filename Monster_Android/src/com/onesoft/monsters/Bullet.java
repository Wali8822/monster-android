package com.onesoft.monsters;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class Bullet extends DynamicObj {
	protected RevoluteJoint     m_theJoint = null;
	
	public static final int     BULLET_RADIUS = 20;
	public static final float   m_maxMotorSpeed = 5.0f;
	public static final float   m_minMotorSpeed = 0.85f;
	
	protected BulletState       m_theState ;
	protected int               m_theRef = 0;
	protected float             m_currentMotorSpeed = 0.0f;
	
	protected boolean           m_bCurrentBullet= true;
	
	public Bullet(Simulation s,Body anchor) {
		super(s);
		
		BodyDef bd = new BodyDef();
		bd.position.set(Settings.WEAPON_X+Settings.WEAPON_RADIUS, 
				Settings.WEAPON_Y);
		bd.type = BodyType.DynamicBody;
		
		CircleShape cs = new CircleShape();
		cs.setRadius(BULLET_RADIUS / 2);
		FixtureDef fd = new FixtureDef();
		fd.density = 0.0000001f;
		fd.filter.categoryBits = Settings.BULLET_GROUP;
		fd.filter.maskBits = Settings.MONSTER_GROUP;
		fd.shape = cs;
		fd.restitution = 0.85f;
		fd.friction = 0.5f;
		m_theBody = m_theSimulation.addBody(bd, fd);
		m_theBody.setUserData(this);
		
		RevoluteJointDef rjd = new RevoluteJointDef();
		rjd.initialize(anchor, m_theBody, anchor.getWorldCenter());
		rjd.enableLimit = false;
		rjd.enableMotor = true;
		rjd.motorSpeed = 10.0f;
		rjd.maxMotorTorque = 500.0f;
		m_theJoint = (RevoluteJoint)m_theSimulation.addJoint(rjd);
		
		m_currentMotorSpeed = 1.0f;
		
		m_theState = BulletState.NORMAL;
	}

	public void speedUp(){
		m_currentMotorSpeed += 1f;
		
		if(m_currentMotorSpeed >= m_maxMotorSpeed){
			m_currentMotorSpeed = m_maxMotorSpeed;
		}
		
		m_theJoint.setMotorSpeed(m_currentMotorSpeed);
	}
	
	public void normalSpeed(){
		m_currentMotorSpeed = m_minMotorSpeed;
		
		m_theJoint.setMotorSpeed(m_currentMotorSpeed);
	}
	
	public void shoot(){
		if(m_theJoint != null){
			m_theSimulation.removeJoint(m_theJoint);
			m_bCurrentBullet = false;
			m_theJoint = null;
		}
	}
	
	public void addRef(){
		++m_theRef;
	}
	
	public int getRef(){
		return m_theRef;
	}
	
	public void bombed(){
		m_theState = BulletState.BOMBED;
	}
	
	public boolean isBombed(){
		return m_theState == BulletState.BOMBED;
	}
	
	public boolean isCurrentBullet(){
		return m_bCurrentBullet;
	}
	
	@Override
	public void present(float deltatime) {
		float x = m_theBody.getPosition().x;
		float y = m_theBody.getPosition().y;
		
		float angle = m_theBody.getAngle();
		
		Render.draw(Resources.m_bullet_region, x - BULLET_RADIUS / 2, y - BULLET_RADIUS / 2, 
				BULLET_RADIUS, BULLET_RADIUS, angle);
	}

	@Override
	public void update(float deltatime) {
	}

	@Override
	public void dispose() {
		if(m_theBody != null){
			m_theSimulation.removeBody(m_theBody);
			m_theBody = null;
		}
	}
}
