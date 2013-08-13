package com.onesoft.monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


public class Button extends Screen {
	private RectangleEx                 m_rectangle = null;
	private String                      m_btnText = null;
	private boolean                     m_bPressed = false;
	private BitmapFont.TextBounds       m_textBounds= null;
	private boolean                     m_bValue = false;
	
	public Button(String text,float x,float y,float width,float height){
		super(null);
		
		m_btnText=text;
		m_rectangle=new RectangleEx(x,y,width,height);

		m_bPressed=false;

		m_textBounds = Render.getTextBounds(m_btnText);
	}

	@Override
	public void present(float deltaTime){
		float fx,fy;
		fx=m_rectangle.getX()+m_rectangle.getWidth()/2-m_textBounds.width/2;
		fy=m_rectangle.getY()+m_rectangle.getHeight()/2+m_textBounds.height/2;
		
		if(m_bPressed)
		{
			Render.draw(Resources.m_button_pressed_region, m_rectangle.getX(),
					m_rectangle.getY(),
					m_rectangle.getWidth(),
					m_rectangle.getHeight());
			Render.draw(m_btnText, fx, fy);
		}
		else
		{
			Render.draw(Resources.m_button_normal_region, m_rectangle.getX(),
					m_rectangle.getY(),
					m_rectangle.getWidth(),
					m_rectangle.getHeight());
			Render.draw(m_btnText, fx, fy);
		}
	}

	public void setPosition(float x,float y){
		m_rectangle.setX(x);
		m_rectangle.setY(y);
	}
	
	public boolean hit(){
		return m_bPressed;
	}
	
	public void setText(String text){
		m_btnText = text;
	}
	
	public void setX(float x){
		m_rectangle.setX(x);
	}
	
	public void setY(float y){
		m_rectangle.setY(y);
	}
	
	public float getX(){
		return m_rectangle.getX();
	}
	
	public float getY(){
		return m_rectangle.getY();
	}
	
	public boolean getValue(){
		return m_bValue;
	}
	
	@Override
	public void update(float deltaTime) {
		//The button is down
		if(Gdx.input.isTouched()){
			float x = Gdx.input.getX();
			float y = Settings.SCREEN_HEIGHT-Gdx.input.getY();
			m_bPressed = m_rectangle.contains(x, y);
		}
		else{
			m_bValue = m_bPressed;
			m_bPressed = false;
		}
	}

	@Override
	public void dispose() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
