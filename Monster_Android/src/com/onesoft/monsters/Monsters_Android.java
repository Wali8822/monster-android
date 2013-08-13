package com.onesoft.monsters;

import com.badlogic.gdx.backends.android.AndroidApplication;

import android.os.Bundle;

public class Monsters_Android extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(new GameMonsterPlay(), false);
    }
}