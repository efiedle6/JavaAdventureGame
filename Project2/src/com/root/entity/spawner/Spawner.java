package com.root.entity.spawner;

import com.root.entity.Entity;
import com.root.level.Level;

public class Spawner extends Entity{

	
	public enum Type{
		PARTICLE,
		MOB;
	}
	
	private Type type;
	
	public Spawner(int x, int y, Type type, int amount, Level level){
		init(level);
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
	
	
}
