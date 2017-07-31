package com.root.entity.mob;

import com.root.graphics.AnimatedSprite;
import com.root.graphics.Screen;
import com.root.graphics.Sprite;
import com.root.graphics.SpriteSheet;
import com.root.util.Debug;

public class Shooter extends Mob{

	private int attackCooldown = 0;
	private boolean walking = false;
	private double xVel = 0;
	private double yVel = 0;
	
	private AnimatedSprite zombie_down_walking = new AnimatedSprite(SpriteSheet.female_zombie_grey_down,32, 32, 3);
	private AnimatedSprite zombie_up_walking = new AnimatedSprite(SpriteSheet.female_zombie_grey_up,32, 32, 3);
	private AnimatedSprite zombie_left_walking = new AnimatedSprite(SpriteSheet.female_zombie_grey_left,32, 32, 3);
	private AnimatedSprite zombie_right_walking = new AnimatedSprite(SpriteSheet.female_zombie_grey_right,32, 32, 3);
	private AnimatedSprite currentSprite = zombie_up_walking;
	
	public Shooter(double x, double y){
	this.x = x*16;
	this.y = y*16;
	currentSprite.setFrame(1);//motionless frame
	sprite = currentSprite.getSprite();
	attackCooldown = 0;
	setHitBox(6,14,11,13);
	}
	
	public double getX(){
		return x;
	}
	
	//to correct the y position spot being in the head of the zombie
	public double getY(){
		return y + 5;
	}
	
	public void update() {
		
		time++; //every time (time % 60 == 0) 1 second has passed
		if (time >= 7201) time = 0;
		
		//handle cooldown for wizard projectile
		if (attackCooldown > 0) attackCooldown--;
		//movement animations
		if (walking) currentSprite.update();
		else currentSprite.setFrame(1);
		if (yVel < 0){ 
			currentSprite = zombie_up_walking;
			dir = 0;
		} else if (yVel > 0){
			currentSprite = zombie_down_walking;
			dir = 2;
		}
		if (xVel > 0){
			currentSprite = zombie_right_walking;
			dir = 1;
		} else if (xVel < 0){
			currentSprite = zombie_left_walking;
			dir = 3;
		}
		
		//shootClosest(this, 40);
		shootRandom(this, 40);
		
	}

	public void render(Screen screen) {
		Debug.drawRect(screen, -40, 40, 100, 40, 0xFF00FF, true);
		screen.renderMob((int)x-16, (int)y-16, this);
	}
	
	public int getAttackCooldown(){
		return attackCooldown;
	}
	
	public void setAttackCooldown(int fireRate){
		this.attackCooldown = fireRate;
	}
	
}
