package com.onesoft.monsters;


public class MenuScreen extends Screen {
	private final int  m_button_count = 5;
	
	private Button m_buttons[] = new Button[5];
	private String m_buttonTexts[]={"Exit","About","Help","Settings","Play Game"};
	
	private float m_buttonX = 0.0f;
	
	private float m_backX ,m_backY;
	
	private boolean m_bStarted = false;
	
	private boolean m_bFinished = false;
	private boolean m_bMenuFinshed = false;
	
	private boolean m_bAnimateFinish = false;
	
	private int     m_nButtonIndex = -1;
	
	public MenuScreen(Game game) {
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
	}

	public void OnMenuItem(int nItem){
			switch(nItem){
			case 0:
				System.exit(0);
				break;
//			case 1:
//				game.setScreen(new AboutScreen(game));
//				break;
//			case 2:
//				game.setScreen(new HelpScreen(game));
//				break;
			case 3:
				game.setScreen(new SettingScreen(game));
				break;
			case 4:
				game.setScreen(new LevelChooseScreen(game));
				break;
			}
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
	
	public void animate_end(){
		boolean finish = true;
		if(!m_bFinished){
			for(int i=0;i<m_button_count;++i){
				if(m_buttons[i].getX() <= -Settings.MENU_BUTTON_WIDTH){
					continue;
				}
				float x=m_buttons[i].getX();
				m_buttons[i].setX(x-5.0f*((4-i)+1));
				finish = false;
			}
			m_bFinished = finish;
		}
		
		if(m_bFinished ){
			if (m_backY > Settings.SCREEN_HEIGHT) {
				m_bAnimateFinish = true;
			} else {
				m_backY +=  10.0f;
			}
		}
	}
	
	@Override
	public void update(float deltaTime) {
		if(!m_bAnimateFinish){
			if(m_bMenuFinshed){
				animate_end();
			}
			else{
				animate_start();
			}
		}
		else{
			if(m_bMenuFinshed){
				OnMenuItem(m_nButtonIndex);
			}
		}
		
		if(m_bAnimateFinish){
			for (int i = 0; i < m_buttons.length; ++i) {
				m_buttons[i].update(deltaTime);

				if (m_buttons[i].getValue()) {
					switch (i) {
					case 0:
						m_nButtonIndex = 0;
						break;
					case 1:
						m_nButtonIndex = 1;
						break;
					case 2:
						m_nButtonIndex = 2;
						break;
					case 3:
						m_nButtonIndex = 3;
						break;
					case 4:
						m_nButtonIndex = 4;
						break;
					}
					m_bMenuFinshed = true;
					m_bAnimateFinish = false;
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
	}

}
