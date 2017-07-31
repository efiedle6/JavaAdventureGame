package com.root.entity.projectile;

import com.root.entity.Entity;
import com.root.graphics.Sprite;

public abstract class Projectile extends Entity {
	
	//it should own a mob that shoots it. then use rateOfFire here
	//and add itself to the mob's projectile array
	
	
	protected final double xOrigin, yOrigin;
	protected double angle;
	protected Sprite sprite;
	protected double x, y;
	protected double nx, ny;
	
	//parameters
	protected double speed, range, damage, distance;
	
	public Projectile(double x, double y, double dir){
		this.xOrigin = x;
		this.yOrigin = y;
		this.angle = dir;
		this.x = x;
		this.y = y;
		
	}
	
	public Sprite getSprite(){
		return this.sprite;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public double getSpeed(){
		return speed;
	}
	
}
