package com.onesoft.monsters;


public class SettingScreen extends Screen {
	private int    m_button_count = 2;

	private Button m_btnBack = null;
	private Button m_buttons[] = new Button[2];
	private String m_buttonTexts[]={"Sound:On","Vibrate:On"};
	
	private float m_buttonX = 0.0f;
	
	private float m_backX ,m_backY;
	
	private boolean m_bStarted = false;
	
	private boolean m_bAnimateFinish = false;
	
	public SettingScreen(Game game) {
		super(game);
		
		m_buttonX = (Settings.SCREEN_WIDTH-Settings.MENU_BUTTON_WIDTH)/2.0f;
		
		float buttonY = (Settings.SCREEN_HEIGHT - m_button_count*Settings.MENU_BUTTON_HEIGHT-40)/2;
		
		for(int i=0;i<m_button_count;++i){
			m_buttons[i] = new Button(m_buttonTexts[i],
					-Settings.MENU_BUTTON_WIDTH,buttonY+i*(Settings.MENU_BUTTON_HEIGHT+10)
					,Settings.MENU_BUTTON_WIDTH,Settings.MENU_BUTTON_HEIGHT);
		}
		
		m_backX = 0.0f;
		m_backY = Settings.SCREEN_HEIGHT;
		
		m_btnBack = new Button("Back", 0, 0, 100, 50);
	}
	
	public void animate_start(){
		if(!m_bStarted){
			if (m_backY < 0) {
				m_backY = 0;
				m_bStarted = true;
			} else {
				m_backY -=  10.0f;
			}
		}
		
		boolean finish = true;
		if(m_bStarted){
			for(int i=0;i<m_button_count;++i){
				if(m_buttons[i].getX() >= m_buttonX){
					m_buttons[i].setX(m_buttonX);
					continue;
				}
				float x=m_buttons[i].getX();
				m_buttons[i].setX(x+5.0f*(i+1));
				finish = false;
			}
			
			m_bAnimateFinish = finish;
		}
	}
	
	@Override
	public void update(float deltaTime) {
		m_btnBack.update(deltaTime);
		if(m_btnBack.getValue()){
			game.setScreen(new MenuScreen(game));
			return;
		}
		
		if(!m_bAnimateFinish){
			animate_start();
		}
		else{
			for (int i = 0; i < m_buttons.length; ++i) {
				m_buttons[i].update(deltaTime);

				if (m_buttons[i].getValue()) {
					switch (i) {
					case 0:
					{
						Settings.bSoundEnabled = !Settings.bSoundEnabled;
						if(Settings.bSoundEnabled){
							m_buttons[0].setText("Sound:On");
						}else{
							m_buttons[0].setText("Sound:Off");
						}
						break;
					}
					case 1:
					{
						Settings.bVibrateEnable = !Settings.bVibrateEnable;
						if(Settings.bVibrateEnable){
							m_buttons[1].setText("Vibrate:On");
						}else{
							m_buttons[1].setText("Vibrate:Off");
						}
						break;
					}
					}
				}
			}
		}

	}

	@Override
	public void present(float deltaTime) {
		Render.draw(Resources.m_background_menu_region, m_backX, m_backY, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
		
		for(int i=0;i<m_button_count;i++){
			m_buttons[i].present(deltaTime);
		}
		
		m_btnBack.present(deltaTime);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		for(Button b : m_buttons){
			b.dispose();
		}
		
		m_btnBack.dispose();
	}
}
