package com.onesoft.monsters;


import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Resources {
	public   static  Texture             m_background_menu = null;
	public   static  TextureRegion       m_background_menu_region = null;
	
	public   static  Texture             m_button_pressed = null;
	public   static  TextureRegion       m_button_pressed_region = null;
	public   static  Texture             m_button_normal  = null;
	public   static  TextureRegion       m_button_normal_region = null;
	
	public   static  Texture             m_mainloop_top = null;
	public   static  TextureRegion       m_mainloop_top_region = null;
	public   static  Texture             m_mainloop_bottom = null;
	public   static  TextureRegion       m_mainloop_bottom_region = null;
	
	public   static  Texture             m_anchor = null;
	public   static  TextureRegion       m_anchor_region = null;
	public   static  Texture             m_bullet = null;
	public   static  TextureRegion       m_bullet_region = null;
	
	public   static  Texture             m_normal_bird = null;
	public   static  Animation           m_normal_bird_animation = null;
	
	public   static  Texture             m_green_bird = null;
	public   static  Animation           m_green_bird_animation = null;
	
	public   static  Texture             m_dragon = null;
	public   static  Animation           m_dragon_animation = null;
	
	public   static  Texture             m_snake = null;
	public   static  Animation           m_snake_animation = null;
	
	public   static  Texture             m_score_background = null;
	public   static  TextureRegion       m_score_background_region = null;
	
	public   static  Texture             m_score_foreground = null;
	public   static  TextureRegion       m_score_foreground_region = null;
	
	public   static  BitmapFont          m_font = null;
	
	public   static  Sound               m_sound_bird_cry = null;
	public   static  Sound               m_sound_shoot = null;
	public   static  Sound               m_sound_hit = null;
	
	public static void load(){
		m_background_menu = loadTexture("data/menu_background.png");
		m_background_menu_region = new TextureRegion(m_background_menu);
		
		m_mainloop_top = loadTexture("data/top.png");
		m_mainloop_top_region = new TextureRegion(m_mainloop_top);
		m_mainloop_bottom = loadTexture("data/bottom.png");
		m_mainloop_bottom_region = new TextureRegion(m_mainloop_bottom);
		
		m_normal_bird = loadTexture("data/normal_bird.png");
		int perWidth = m_normal_bird.getWidth() / 4;
		
		int height = m_normal_bird.getHeight();
		m_normal_bird_animation = new Animation(Settings.FRAME_DURATION, 
				new TextureRegion(m_normal_bird,0,         0,perWidth,height),
				new TextureRegion(m_normal_bird,perWidth,  0,perWidth,height),
				new TextureRegion(m_normal_bird,perWidth*2,0,perWidth,height),
				new TextureRegion(m_normal_bird,perWidth*3,0,perWidth,height)
				);
		
		m_green_bird = loadTexture("data/green_bird.png");
		perWidth = m_green_bird.getWidth() / 4;
		height = m_green_bird.getHeight();
		
		m_green_bird_animation = new Animation(Settings.FRAME_DURATION, 
				new TextureRegion(m_green_bird,0,         0,perWidth,height),
				new TextureRegion(m_green_bird,perWidth,  0,perWidth,height),
				new TextureRegion(m_green_bird,perWidth*2,0,perWidth,height),
				new TextureRegion(m_green_bird,perWidth*3,0,perWidth,height)
		);
		
		m_dragon = loadTexture("data/dragon.png");
		perWidth = m_dragon.getWidth() / 4;
		height = m_dragon.getHeight();
		
		m_dragon_animation = new Animation(Settings.FRAME_DURATION, 
				new TextureRegion(m_dragon,0,         0,perWidth,height),
				new TextureRegion(m_dragon,perWidth,  0,perWidth,height),
				new TextureRegion(m_dragon,perWidth*2,0,perWidth,height),
				new TextureRegion(m_dragon,perWidth*3,0,perWidth,height)
				);
		
		m_snake = loadTexture("data/monster_snake.png");
		perWidth = m_snake.getWidth() / 4;
		height = m_snake.getHeight();
		
		m_snake_animation = new Animation(Settings.FRAME_DURATION, 
				new TextureRegion(m_snake,0,         0,perWidth,height),
				new TextureRegion(m_snake,perWidth,  0,perWidth,height),
				new TextureRegion(m_snake,perWidth*2,0,perWidth,height),
				new TextureRegion(m_snake,perWidth*3,0,perWidth,height)
		);
		
		m_font = new BitmapFont(Gdx.files.internal("data/courier.fnt"), 
				Gdx.files.internal("data/courier_0.png"),
				false);
	
		m_sound_bird_cry = Gdx.audio.newSound(Gdx.files.internal("data/bird_cry.mp3"));
		m_sound_shoot = Gdx.audio.newSound(Gdx.files.internal("data/shoot.mp3"));
		m_sound_hit = Gdx.audio.newSound(Gdx.files.internal("data/rockbounce.mp3"));
		
		Color colorNormal = new Color(0.5f, 0.5f, 0.5f, 0.9f);
		Color colorPressed = new Color(1.0f, 0.5f, 0.5f, 0.5f);	
		
		Pixmap pmap = new Pixmap(32, 32, Format.RGBA8888);
		pmap.setColor(colorPressed.r,colorPressed.g,colorPressed.b,colorPressed.a);
		pmap.fillRectangle(0, 0, 32, 32);
		m_button_pressed = new Texture(pmap);
		m_button_pressed_region = new TextureRegion(m_button_pressed);
		
		pmap.setColor(colorNormal.r, colorNormal.g, colorNormal.b, colorNormal.a);
		pmap.fillRectangle(0, 0, 32, 32);
		pmap.fillRectangle(0, 0, 32, 32);
		m_button_normal = new Texture(pmap);
		m_button_normal_region = new TextureRegion(m_button_normal);
		
		pmap.setColor(0, 0, 0, 1.0f);
		pmap.fillRectangle(0, 0, 32, 32);
		m_anchor = new Texture(pmap);
		m_anchor_region = new TextureRegion(m_anchor);
		
//		pmap.setColor(1.0f, 1.0f, 1.0f, 1.0f);
//		pmap.fillCircle(16, 16, 16);
//		m_bullet = new Texture(pmap);
//		m_bullet_region = new TextureRegion(m_bullet);
		m_bullet = loadTexture("data/bullet.png");
		m_bullet_region = new TextureRegion(m_bullet);
		
		pmap.fill();
		pmap.setColor(colorNormal.r, colorNormal.g, colorNormal.b, colorNormal.a);
		pmap.fillRectangle(0, 0, 32, 32);
		m_score_background = new Texture(pmap);
		m_score_background_region = new TextureRegion(m_score_background);
		
		pmap.setColor(colorPressed.r, colorPressed.g, colorPressed.b, colorPressed.a);
		pmap.fillRectangle(0, 0, 32, 32);
		m_score_foreground = new Texture(pmap);
		m_score_foreground_region = new TextureRegion(m_score_foreground);
		
		pmap.dispose();
	}
	
	public static void dispose(){
		m_background_menu.dispose();
		
		m_button_normal.dispose();
		m_button_pressed.dispose();
		
		m_mainloop_bottom.dispose();
		m_mainloop_top.dispose();
		
		m_bullet.dispose();
		m_anchor.dispose();
		
		m_normal_bird.dispose();
		
		m_green_bird.dispose();
		
		m_score_background.dispose();
		m_score_foreground.dispose();
		
		m_font.dispose();
		
		m_sound_bird_cry.dispose();
		m_sound_shoot.dispose();
		m_sound_hit.dispose();
	}
	
	public static Texture loadTexture(String filename){
		return new Texture(Gdx.files.internal(filename));
	}
	
	public static TextureRegion getKeyFrame(Animation anim,float statetime){
		return anim.getKeyFrame(statetime, true);
	}
	
	public static void vibrate(int time){	
		if(Settings.bVibrateEnable && Gdx.app.getType() == ApplicationType.Android
				&& Gdx.input.supportsVibrator()){
			Gdx.input.vibrate(time);
		}
	}
	
	public static void playSound(Sound s){
		if(Settings.bSoundEnabled)
			s.play();
	}
}
