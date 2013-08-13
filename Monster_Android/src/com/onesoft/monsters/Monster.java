package com.onesoft.monsters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Monster extends DynamicObj {
	protected MonsterType       m_theType = null;
	
	protected ScoreScreen       m_lifeRemain = null;
	
	public int                  m_monsterWidth = 50;
	public int                  m_monsterHeight = 50;
	
	protected float             m_life = 1.0f;
	protected float             m_lifeDecrease = 0.0f;
	
	protected float             m_frameDuration = 0.0f;
	protected float             m_theSpeed = 0.0f;
	protected float             m_timeEllapsed = 0.0f;
	protected long              m_time = 0;
	
	protected boolean           m_bEscape = false;
	protected boolean           m_bDestroy = false;
	protected boolean           m_bHitted = false;
	protected boolean           m_bAlive = true;
	protected boolean           m_bUpOrDown = false;
	
	protected float             m_speedUp = 0.0f;
	
	protected Animation         m_theAnimation = null;
	
	protected float             m_theX = 0.0f;
	protected float             m_theY = 0.0f;
	
	public Monster(Simulation s,MonsterType type) {		
		super(s);
		
		m_theType = type;
		
		switch(type){
		case NORMAL_BIRD:
		{
			m_lifeDecrease = 0.06f;
			m_frameDuration = 0.03f;
			m_theAnimation = Resources.m_normal_bird_animation;
			
			m_theSpeed = 20.0f;
			m_speedUp = 15.0f;
			
			m_monsterHeight = 50;
			m_monsterWidth  = 50;
			
			m_theX = Settings.SCREEN_WIDTH;
			m_theY = (Settings.SCREEN_HEIGHT - Settings.GROUND_Y - 
					m_monsterHeight)*Render.randFloat() + Settings.GROUND_Y + m_monsterHeight;
			
			break;
		}
		case GREEN_BIRD:
		{
			m_lifeDecrease = 0.06f;
			m_frameDuration = 0.0005f;
			m_theAnimation = Resources.m_green_bird_animation;
			
			m_theSpeed = 40.0f;
			m_speedUp = 20.0f;
			
			m_monsterWidth = 40;
			m_monsterHeight = 40;
			
			m_theX = Settings.SCREEN_WIDTH;
			m_theY = (Settings.SCREEN_HEIGHT - Settings.GROUND_Y - 
					m_monsterHeight)*Render.randFloat() + Settings.GROUND_Y + m_monsterHeight;
			
			break;
		}
		case DRAGON:
		{
			m_lifeDecrease = 0.03f;
			m_frameDuration = 0.001f;
			m_theAnimation = Resources.m_dragon_animation;
			
			m_theSpeed = 60.0f;
			m_speedUp = 27.0f;
			
			m_monsterWidth = 60;
			m_monsterHeight = 60;
			
			m_theX = Settings.SCREEN_WIDTH;
			m_theY = (Settings.SCREEN_HEIGHT - Settings.GROUND_Y - 
					m_monsterHeight)*Render.randFloat() + Settings.GROUND_Y + m_monsterHeight;
			break;
		}
		case SNAKE:
		{
			m_lifeDecrease = 0.03f;
			m_frameDuration = 0.0005f;
			m_theAnimation = Resources.m_snake_animation;
			
			m_theSpeed = 50.0f;
			m_speedUp =  0.0f;
			
			m_monsterWidth = 70;
			m_monsterHeight = 70;
			
			m_theX = Settings.SCREEN_WIDTH;
			m_theY = Settings.GROUND_Y + m_monsterHeight / 2;
			break;
		}
		}

		BodyDef bd = new BodyDef();
		bd.position.set(m_theX, m_theY);
		bd.type = BodyType.DynamicBody;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(m_monsterWidth/2, m_monsterHeight/2);
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.density = 0.0f;
		fd.filter.categoryBits = Settings.MONSTER_GROUP;
		fd.filter.maskBits = Settings.BULLET_GROUP;
		fd.friction = 0.0f;
		fd.restitution = 0.0f;
		m_theBody = m_theSimulation.addBody(bd, fd);
		
		m_theBody.setUserData(this);
		
		m_lifeRemain = new ScoreScreen(m_theX, m_theY + m_monsterHeight, 
				m_monsterWidth, 10, m_life);
	}

	public boolean canDestroy(){
		return m_bDestroy;
	}
	
	public boolean isEscape(){
		return m_bEscape;
	}
	
	public void hitted(Body theOther){
		Vector2 s = theOther.getLinearVelocity();
		float d = Math.abs(s.x) + Math.abs(s.y);
		
		m_life -= m_lifeDecrease * d * 0.1f;
		
		m_bHitted = true;
		
		m_lifeRemain.setScore(m_life);
		
		if(m_life < 0.0f){
			m_bAlive = false;
			if(m_theType == MonsterType.GREEN_BIRD ||
					m_theType == MonsterType.NORMAL_BIRD){
				Resources.playSound(Resources.m_sound_bird_cry);
			}
		}
	}
	
	@Override
	public void present(float deltatime) {
		float x = m_theBody.getPosition().x;
		float y = m_theBody.getPosition().y;
		
		float angle = m_theBody.getAngle();
		
		TextureRegion temp = Resources.getKeyFrame(m_theAnimation, m_timeEllapsed);
		
		if(m_bAlive){
			Render.draw(temp, x - m_monsterWidth/2, y - m_monsterHeight / 2,
				m_monsterWidth, m_monsterHeight, angle);
			m_lifeRemain.present(deltatime);
		}else{
			Render.draw(temp, x - m_monsterWidth/2, y - m_monsterHeight / 2,
				m_monsterWidth, m_monsterHeight, (float)(angle + Math.PI/2));
		}
	}

	@Override
	public void update(float deltatime) {
		m_timeEllapsed += m_frameDuration;
		
		float x = m_theBody.getPosition().x;
		if(x <= -m_monsterWidth && m_bAlive){
			m_bDestroy = true;
			m_bEscape = true;
		}
		
		float y = m_theBody.getPosition().y;
		
		if(y <= 0.0f ){
			m_bDestroy = true;
		}
		
		if (m_bAlive) {
			m_lifeRemain.setX(x - m_monsterWidth/2);
			m_lifeRemain.setY(y + m_monsterHeight / 2);
			m_lifeRemain.update(deltatime);
			
			if (System.currentTimeMillis() - m_time >= 1000) {
				m_time = System.currentTimeMillis();
				m_bUpOrDown = !m_bUpOrDown;

				if (m_bHitted) {
					m_theBody.setLinearVelocity(new Vector2(-m_theSpeed,
							m_speedUp));
					m_bHitted = false;
				}
				if (m_bUpOrDown) {
					m_theBody.setLinearVelocity(new Vector2(-m_theSpeed, 0.0f));
				}
			}
		} else {
			m_theBody.setAngularVelocity(5f);
			m_theBody.setLinearVelocity(new Vector2(0.0f, -30.0f));
		}
	}

	@Override
	public void dispose() {
		if(m_theBody != null){
			m_theSimulation.removeBody(m_theBody);
			m_theBody = null;
		}
		m_lifeRemain.dispose();
	}
}
