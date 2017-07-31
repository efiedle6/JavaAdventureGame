package com.root.entity.particle;

import com.root.entity.Entity;
import com.root.graphics.Screen;
import com.root.graphics.Sprite;

public class Particle extends Entity {
	
	private Sprite sprite;
	private int life;//lifetime of particles
	private int time = 0;
	
	//the amount of pixels moved in x and y axis
	private double xa, ya, za;
	//the amount of pixels moved in x and y axis
	private double xx, yy, zz;
	
	
	public Particle(int x, int y, int life){

		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.zz = random.nextFloat() + 2.0;
		this.life = life + (random.nextInt(20) - 10);
		
		sprite = Sprite.particle_normal;
		this.xa = random.nextGaussian();//gives bell curve between 0 - 1
		this.ya = random.nextGaussian();
	}
	
	
	public void update(){
		if (time >= 7400) time = 0;
		time++;
		if(time > life){
				remove();
			}
		//za causes the particles to drop more each update
		za -= 0.1;
		
		//establishes a 'floor' that particles bounce off of
		if(zz < 0){
			//once particles hit floor, have them switch directions but reduce speed
			//the lower the values the quicker the speed dampens
			zz = 0;
			za *= -0.5;
			xa *= 0.2;
			ya *= 0.2;
		}
		
		move(xx + xa, (yy + ya) + (zz + za));		
	}
	
	private void move(double x, double y) {
		if(collision(x, y)){
			//reverse direction of particles if collision
			this.xa *= -0.5;
			this.ya *= -0.5;
			this.za *= -0.5;
		}
		this.xx += xa;
		this.yy += ya;
		this.zz += za;

	}

	public boolean collision(double x, double y){
		boolean solid = false;
		for(int c = 0; c < 4; c++){
			//shift right by 4 to divide by TILE_SIZE (16)
			double xt = (x - c % 2 * Sprite.TILE_SIZE)/Sprite.TILE_SIZE;
			double yt = (y - c / 2 * Sprite.TILE_SIZE)/Sprite.TILE_SIZE;
			//handles collision on the right and bottom of tiles
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			
			//handles collision on the left and top of tiles
			if (c%2 == 0) ix = (int) Math.floor(xt);
			if (c/2 == 0) iy = (int) Math.floor(yt);
			
			if (level.getTile(ix,iy).solid()){
				solid = true;
			}
			
		}
		return solid;
	}

	public void render(Screen screen){
		screen.renderSprite((int)xx - 1, (int)yy -(int)zz - 1, sprite, true);
	}
	
	
}
