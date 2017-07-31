package com.root.entity.projectile;

import com.root.entity.spawner.ParticleSpawner;
import com.root.graphics.Screen;
import com.root.graphics.Sprite;

public class WizardProjectile extends Projectile {
	
	public static final int FIRE_RATE = 15; //# of updates between shots
	
	public WizardProjectile(double x, double y, double dir) {
		super(x, y, dir);
		range = 150;
		speed = 4;
		damage = 20;
		sprite = Sprite.wizard_projectile;
		
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}
	
	protected void move(){
			this.x += nx;
			this.y += ny;
		if(getDist() > range){
			this.remove();
		}
	}
	
	public void update(){
		//8 is the height and width of projectile. 4 is offset from topleft of spritebox
		//TODO find way to not hardcode offset
		if(level.tileCollision((int)(x + nx) ,(int)( y + ny), 8, 4, 4)){
			level.add(new ParticleSpawner((int)x, (int)y, 60, 20, level));
			remove();
		}
		move();
	}
	
	public void render(Screen screen){
		screen.renderProjectile((int)x - 8, (int) y - 4, this);
	}
	
	public double getDist(){
		//sqrt x^2 + y^2
		double dist = Math.sqrt(Math.abs((xOrigin - x)*(xOrigin - x)) + Math.abs((yOrigin - y)*(yOrigin - y)));
		return dist;
	}
	
}
