package com.onesoft.monsters;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class DynamicObj {
	protected Body           m_theBody = null;
	protected Simulation     m_theSimulation = null;
	
	public DynamicObj(Simulation s){
		this.m_theSimulation = s;
		this.m_theBody = null;
	}
	
	public abstract void present(float deltatime);
	public abstract void update(float deltatime);
	public abstract void dispose();
	
	public float getAngle(){
		return m_theBody.getAngle();
	}
	
	public Vector2 getPosition(){
		return m_theBody.getPosition();
	}
}
