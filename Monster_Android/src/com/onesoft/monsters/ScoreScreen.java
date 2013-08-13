package com.onesoft.monsters;

import com.badlogic.gdx.math.Rectangle;

public class ScoreScreen extends Screen {
	protected float        m_scoreValue ; 
	protected Rectangle    m_rectBackground;
	protected Rectangle    m_rectForeground;
	
	protected float        m_valueChangeTo;
	protected boolean      m_bValueChange ;
	
	public ScoreScreen(float x,float y,float width ,float height,float value) {
		super(null);
		
		m_rectBackground = new Rectangle(x, y, width, height);
		
		m_rectForeground  = new Rectangle(x + 2, y + 2, 0 , height -4);
		
		m_rectForeground.setWidth((width-4) * value);
		
		m_scoreValue = value;
		
		m_bValueChange = false;
	}

	public void increase(float v){
		m_scoreValue += v;
	}
	
	public void setX(float x){
		m_rectBackground.setX(x);
		m_rectForeground.setX(x);
	}
	
	public void setY(float y){
		m_rectBackground.setY(y);
		m_rectForeground.setY(y);
	}
	
	public void setScore(float value){
		m_valueChangeTo = value;
		m_bValueChange = true;
	}
	
	public float getScore(){
		if(m_bValueChange){
			return m_valueChangeTo;
		}
		else{
			return m_scoreValue;
		}
	}
	
	@Override
	public void update(float deltaTime) {
		if(m_bValueChange){
			if(m_valueChangeTo > m_scoreValue){
				m_scoreValue += 0.0005f;
			}
			else
			{
				m_scoreValue -= 0.0005f;
			}
			
			if(m_scoreValue >= m_valueChangeTo){
					m_bValueChange = false;
					m_scoreValue = m_valueChangeTo;
			}
		}
		
		if(m_scoreValue > 1.0f){
			m_scoreValue = 1.0f;
		}
		if(m_scoreValue < 0){
			m_scoreValue = 0;
		}
		
		m_rectForeground.setWidth(m_scoreValue * (m_rectBackground.getWidth() -4)) ;
	}

	@Override
	public void present(float deltaTime) {
		Render.draw(Resources.m_score_background_region, 
				m_rectBackground.x, m_rectBackground.y, 
				m_rectBackground.width, m_rectBackground.height);
		
		Render.draw(Resources.m_score_foreground_region, 
				m_rectForeground.x, m_rectForeground.y, 
				m_rectForeground.width, m_rectForeground.height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}
