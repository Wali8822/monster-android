package com.onesoft.monsters;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

public class GameLoopScreen extends Screen {
	protected Button                m_btnBack = null;
	protected Button                m_btnShoot = null;
	
	protected ScoreScreen           m_scrLife = null;
	protected ScoreScreen           m_scrScore = null;
	
	protected Simulation            m_theSimulation = null;
	protected WeaponCommon          m_theWeapon = null;
	protected float                 m_theLife = 1.0f;
	
	protected float                 m_score = 0.0f;
	
	protected ArrayList<Monster>    m_monsters = null;
	
	public GameLoopScreen(Game game) {
		super(game);
		
		m_theSimulation = new Simulation();
		m_theWeapon = new Weapon(m_theSimulation);
		
		m_btnBack = new Button("Back", 0, 0, 100, Settings.GROUND_Y / 2);
		m_btnShoot = new Button("Shoot", Settings.SCREEN_WIDTH - 100, 0.0f, 100, Settings.GROUND_Y / 2);
		
		m_scrLife = new ScoreScreen(Settings.SCREEN_WIDTH/2+10,
				0.0f, Settings.SCREEN_WIDTH / 2-120,
				Settings.GROUND_Y/2, m_theLife);
		
		m_scrScore = new ScoreScreen(110, 0, Settings.SCREEN_WIDTH / 2-120,
				Settings.GROUND_Y/2, m_score);
		
		m_monsters = new ArrayList<Monster>(8);
		
		switch (Settings.LEVEL) {
		case 1:
		{
			m_monsters.add(new Monster(m_theSimulation, MonsterType.NORMAL_BIRD));
			m_monsters.add(new Monster(m_theSimulation, MonsterType.GREEN_BIRD));
			m_monsters.add(new Monster(m_theSimulation, MonsterType.DRAGON));
			break;
		}
		case 2:
		{
			m_monsters.add(new Monster(m_theSimulation, MonsterType.NORMAL_BIRD));
			m_monsters.add(new Monster(m_theSimulation, MonsterType.GREEN_BIRD));
			m_monsters.add(new Monster(m_theSimulation, MonsterType.SNAKE));
			m_monsters.add(new Monster(m_theSimulation, MonsterType.DRAGON));
			break;
		}
		}
	}

	@Override
	public void update(float deltaTime) {
		m_btnShoot.update(deltaTime);
		if(m_btnShoot.hit()){
			m_theWeapon.speedUp();
		}
		else{
			m_theWeapon.normalSpeed();
		}
		
		if(m_btnShoot.getValue()){
			m_theWeapon.shoot();
		}
		
		ArrayList<Monster> temp = new ArrayList<Monster>();
		for(Monster m : m_monsters){
			m.update(deltaTime);
			if(m.isEscape()){
				m_theLife -= 0.4f;
				m_scrLife.setScore(m_theLife);
			}
			if(m.canDestroy()){
				m.dispose();
				temp.add(m);
				if(!m.isEscape()){
					m_score += 0.1f;
					m_scrScore.setScore(m_score);
				}
			}
		}
		m_monsters.removeAll(temp);
		
		if(m_monsters.size() < 4){
			int w = Render.randInt()%5;
			MonsterType type = MonsterType.NORMAL_BIRD;
			switch (w) {
			case 1:
				type = MonsterType.NORMAL_BIRD;
				break;
			case 2:
				type = MonsterType.GREEN_BIRD;
				break;
			case 3:
				type = MonsterType.DRAGON;
				break;
			case 4:
				type = MonsterType.SNAKE;
				break;
			default:
				type = MonsterType.NORMAL_BIRD;
				break;
			}
			m_monsters.add(new Monster(m_theSimulation, type));
		}
		
		m_theSimulation.update();
		m_theWeapon.update(deltaTime);
		m_scrLife.update(deltaTime);
		m_scrScore.update(deltaTime);
		
		if(m_theLife <= 0.0f){
			game.setScreen(new GameFinishScreen(game, "Game Over","Try Again"));
		}
		
		if(m_score >= 1.0f){
			game.setScreen(new GameFinishScreen(game, "Finish", "Next Level"));
		}
		
		m_btnBack.update(deltaTime);
		if(m_btnBack.getValue()){
			game.setScreen(new MenuScreen(game));
		}
	}

	@Override
	public void present(float deltaTime) {
		Render.draw(Resources.m_mainloop_bottom_region, 0, 0, 
				Settings.SCREEN_WIDTH, Settings.GROUND_Y);
		Render.draw(Resources.m_mainloop_top_region, 0, Settings.GROUND_Y, 
				Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT - Settings.GROUND_Y);
		
		for(Monster m:m_monsters){
			m.present(deltaTime);
		}
		
		m_theWeapon.present(deltaTime);
		m_theSimulation.present();
		
		m_btnShoot.present(deltaTime);
		m_btnBack.present(deltaTime);
		
		m_scrScore.present(deltaTime);
		m_scrLife.present(deltaTime);
		
		String fps = "FPS:"+Gdx.graphics.getFramesPerSecond();
		Render.draw(fps, 100, 100);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		m_btnBack.dispose();
		m_btnShoot.dispose();
		
		m_scrScore.dispose();
		m_scrLife.dispose();
		
		m_theWeapon.dispose();

		for(Monster m:m_monsters){
			if(!m.canDestroy()){
				m.dispose();
			}
		}
		m_monsters.clear();	
		
		m_theSimulation.dispose();
	}
}
