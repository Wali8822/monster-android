package com.onesoft.monsters;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

public abstract class Game implements ApplicationListener{
	Screen screen;
	
	public void setScreen (Screen helpScreen2) {
		screen.pause();
		screen.dispose();
		screen = helpScreen2;
	}
	
	public abstract Screen getStartScreen();
	
	@Override 
	public void create () {
		Settings.load();
		Render.InitRender();
		Resources.load();
		
		screen = getStartScreen();
	}

	@Override public void resume () {
		screen.resume();
	}

	@Override public void render () {
		GL10 gl = Gdx.gl10;
		
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		screen.update(Gdx.graphics.getDeltaTime());
		screen.present(Gdx.graphics.getDeltaTime());
	}

	@Override public void resize (int width, int height) {
	}

	@Override public void pause () {
		screen.pause();
	}

	@Override public void dispose () {
		screen.dispose();
		Settings.dispose();
		Resources.dispose();
		Render.dispose();
	}
}
