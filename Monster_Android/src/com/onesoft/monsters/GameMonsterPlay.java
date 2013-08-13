package com.onesoft.monsters;

public class GameMonsterPlay extends Game {

	@Override
	public Screen getStartScreen() {
		return new MenuScreen(this);
	}
}
