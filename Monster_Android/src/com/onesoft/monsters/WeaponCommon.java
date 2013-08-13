package com.onesoft.monsters;

public abstract class WeaponCommon extends DynamicObj{
	public WeaponCommon(Simulation s){
		super(s);
	}
	
	public abstract void shoot();
	
	public abstract void speedUp();
	public abstract void normalSpeed();
}
