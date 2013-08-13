package com.onesoft.monsters;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class GameFinishScreen extends Screen {

	private Button m_buttons[] = new Button[2];
	private String m_buttonTexts[]={"Play Again","Main Menu"};
	
	private String m_textToSay = "Thanks For Playing!";
	
	private BitmapFont.TextBounds m_bounds ;
	
	protected boolean       m_isGameOver = false;
	
	private float m_destX = 0.0f;
	
	public GameFinishScreen(Game game,String text,String btnText) {
		super(game);
		
		m_textToSay = text;
		
		float buttonX = (Settings.SCREEN_WIDTH-Settings.MENU_BUTTON_WIDTH)/2.0f;
		float buttonY = (Settings.SCREEN_HEIGHT - 2*Settings.MENU_BUTTON_HEIGHT-40)/2;
		
		m_destX = buttonX;
		for(int i=0;i<2;++i){
			m_buttons[i] = new Button(m_buttonTexts[i],
					-200,buttonY+i*(Settings.MENU_BUTTON_HEIGHT+10)
					,Settings.MENU_BUTTON_WIDTH,Settings.MENU_BUTTON_HEIGHT);
		}
		
		m_buttons[0].setText(btnText);
		
		m_bounds = Render.getTextBounds(m_textToSay);
	}

	@Override
	public void update(float deltaTime) {
		for(int i=0;i<m_buttons.length;++i){
			if(m_buttons[i].getX() >= m_destX){
				m_buttons[i].setX(m_destX);
				continue;
			}
			float x=m_buttons[i].getX();
			m_buttons[i].setX(x+5.0f*(i+1));
		}
		
		for(int i=0;i<m_buttons.length;++i){
			m_buttons[i].update(deltaTime);
			
			if(m_buttons[i].getValue()){
				switch(i){
				case 0:
				{
					if(m_isGameOver){
						game.setScreen(new GameLoopScreen(game));
					}
					else{
						Settings.LEVEL ++;
						game.setScreen(new GameLoopScreen(game));
					}
					break;
				}
				case 1:
					game.setScreen(new MenuScreen(game));
					break;
				}
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		for(int i=0;i<2;i++){
			m_buttons[i].present(deltaTime);
		}
		
		Render.draw(m_textToSay, (Settings.SCREEN_WIDTH - m_bounds.width)/2 , Settings.SCREEN_HEIGHT-20);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		for(int i=0;i<2;i++){
			m_buttons[i].dispose();
		}
	}
}
