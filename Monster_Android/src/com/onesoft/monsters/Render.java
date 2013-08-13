package com.onesoft.monsters;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Render {
	public static SpriteBatch m_batch = null;
	public static Random m_rand = null;
	
	public static void InitRender(){
		m_batch = new SpriteBatch();
		m_rand = new Random();
	}
	
	public static void dispose(){
		m_batch.dispose();
	}
	
	public static void draw(TextureRegion t,float x,float y,float width,float height,float angle){
		m_batch.begin();
		m_batch.draw(t, x, y, width / 2, height / 2,
				width,height,
				1.0f,1.0f,
				(float)(Math.toDegrees(angle)));
		m_batch.end();
	}
	
	public static void draw(TextureRegion t,float x,float y,float width,float height,float orignX,float orignY,float angle,float scaleX,float scaleY){
		m_batch.begin();
		m_batch.draw(t, x, y, orignX, orignY,
				width,height,
				scaleX,scaleY,
				(float)(Math.toDegrees(angle)));
		m_batch.end();
	}
	
	public static void draw(TextureRegion t,float x,float y,float width,float height){
		m_batch.begin();
		m_batch.draw(t, x, y, width, height);
		m_batch.end();
	}
	
	public static void draw(Texture t,float x, float y){
		m_batch.begin();
		m_batch.draw(t, x, y);
		m_batch.end();
	}
	
	public static void draw(String text,float x,float y){
		m_batch.begin();
		Resources.m_font.draw(m_batch, text, x, y);
		m_batch.end();
	}
	
	public static BitmapFont.TextBounds getTextBounds(String text){
		BitmapFont.TextBounds ret = new BitmapFont.TextBounds();
		
		BitmapFont.TextBounds temp =  Resources.m_font.getBounds(text);
		
		ret.width = temp.width;
		ret.height = temp.height;
		return ret;
	}
	
	public static int randInt(){
		return m_rand.nextInt();
	}
	
	public static float randFloat(){
		return m_rand.nextFloat();
	}
	
	public static boolean proprably(int p)
	{	
		return (Render.randInt() % p == 0);
	}
	
	public static float getAngle(Vector2 v1,Vector2 v2){
		Vector2 temp = v2.cpy();
		
		temp.sub(v1);
		
		return (float)Math.atan2(temp.y, temp.x);
	}
}
