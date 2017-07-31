package com.root.entity.spawner;

import com.root.entity.particle.Particle;
import com.root.entity.spawner.Spawner.Type;
import com.root.level.Level;

public class ParticleSpawner extends Spawner {

	private int life;
	
	public ParticleSpawner(int x, int y, int life, int amount, Level level){
		super(x, y, Type.PARTICLE, amount, level);
		this.life = life;
		for(int i = 0; i < amount; i++){
				level.add(new Particle(x, y, life));
			}
		}

	public void update(){
		remove();//only for temporary spawners
	}
	
	
}
	

