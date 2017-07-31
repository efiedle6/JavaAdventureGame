package com.root.entity;

import java.util.Random;

import com.root.graphics.Screen;
import com.root.graphics.Sprite;
import com.root.level.Level;

public class Entity {

	protected double x, y;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	protected Sprite sprite;
	
	public void update() {
	}
	
	public void render(Screen screen){	
		if(sprite != null) screen.renderSprite((int)x,(int)y, sprite, true);
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public void remove(){
		//remove from level
		removed = true;
	}
	
	public boolean isRemoved(){
		return removed;
	}
	
	public void init(Level level){
		this.level = level;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	
}
