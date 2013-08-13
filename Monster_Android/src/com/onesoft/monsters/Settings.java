package com.onesoft.monsters;

import com.badlogic.gdx.Gdx;

public class Settings {
	public static  int             SCREEN_WIDTH = 0;
	public static  int             SCREEN_HEIGHT = 0;
	
	public static  int             MENU_BUTTON_WIDTH = 0;
	public static  int             MENU_BUTTON_HEIGHT = 0;
	
	public static  int             GROUND_X = 0;
	public static  int             GROUND_Y = 0;
	
	public static  int             BULLET_RADIUS = 0;
	public static  int             WEAPON_RADIUS = 0;
	public static  int             WEAPON_X = 0;
	public static  int             WEAPON_Y = 0;
	
	public static  int             MAX_BULLET_LIFE = 10;
	
	public static  short           BULLET_GROUP = 0x0002;
	public static  short           MONSTER_GROUP = 0x0004;
	public static  short           WALL_GROUP = 0x0004;
	
	public static  float           FRAME_DURATION = 0.008f;
	
	public static  int             LEVEL = 1;
	
	public static  boolean         bVibrateEnable = true;
	public static  boolean         bSoundEnabled = true;
	
	public static void load(){
		SCREEN_WIDTH = Gdx.graphics.getWidth();
		SCREEN_HEIGHT = Gdx.graphics.getHeight();
		
		MENU_BUTTON_WIDTH = 260;
		MENU_BUTTON_HEIGHT = 40;
		
		GROUND_X = 0;
		GROUND_Y = SCREEN_HEIGHT / 4;
		
		WEAPON_RADIUS = 50;
		WEAPON_X = WEAPON_RADIUS + 20;
		WEAPON_Y = WEAPON_RADIUS + 20 + GROUND_Y;
		
		bVibrateEnable = true;
		bSoundEnabled = true;
	}
	
	public static void dispose(){
		
	}
}
