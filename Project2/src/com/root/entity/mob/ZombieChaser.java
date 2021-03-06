package com.root.entity.mob;

import java.util.List;

import com.root.graphics.AnimatedSprite;
import com.root.graphics.Screen;
import com.root.graphics.SpriteSheet;

public class ZombieChaser extends Mob{
	
	private double zombieWalkSpeed = .5;
	private double zombieChaseSpeed = 1.5;
	private double zombieRushSpeed = 2.5;
	private int attackCooldown = 0;
	private boolean walking = false;
	private double xVel = 0;
	private double yVel = 0;
	private int time = 0;
	private boolean randomly = false;
	
	private AnimatedSprite zombie_down_walking = new AnimatedSprite(SpriteSheet.female_zombie_grey_down,32, 32, 3);
	private AnimatedSprite zombie_up_walking = new AnimatedSprite(SpriteSheet.female_zombie_grey_up,32, 32, 3);
	private AnimatedSprite zombie_left_walking = new AnimatedSprite(SpriteSheet.female_zombie_grey_left,32, 32, 3);
	private AnimatedSprite zombie_right_walking = new AnimatedSprite(SpriteSheet.female_zombie_grey_right,32, 32, 3);
	private AnimatedSprite currentSprite = zombie_up_walking;
	
	public ZombieChaser(double x, double y){
		this.x = (int)(x*16);
		this.y = (int)(y*16);
		currentSprite.setFrame(1);//motionless frame
		sprite = currentSprite.getSprite();
		attackCooldown = 0;
		setHitBox(6,14,13,17);
	}
	
	public void update() {
		move();
		time++; //every time (time % 60 == 0) 1 second has passed
		if (time >= 7201) time = 0;
		
		//if not walking set to first frame
		if (walking) currentSprite.update();
		else currentSprite.setFrame(1);
		
		//handle cooldown for attacks
		if (attackCooldown > 0) attackCooldown--;
		//because of move() inherited by mob, this also sets dir, which is used for anim
		
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
	}

	public void render(Screen screen) {
		//grab the current sprite with dimensions
				sprite = currentSprite.getSprite();
				int xx = (int)(x - sprite.SIZE/2);
				int yy = (int)(y - sprite.SIZE/2);
				screen.renderMob(xx, yy, this);
	}
	
	public void move(){
		if(!randomly) xVel = 0.0;
		if(!randomly) yVel = 0.0;
		List<Player> players = level.getPlayers(this, 150);
		 //TODO ensure 0 is always client player, not sure if this is true
		if(players.size() > 0){
			randomly = false;
			Player player = players.get(0);
			if(x < player.getX()) xVel += zombieChaseSpeed;//TODO set walking = true
			if(x > player.getX()) xVel += -zombieChaseSpeed;
			if(y < player.getY()) yVel += zombieChaseSpeed;
			if(y > player.getY()) yVel += -zombieChaseSpeed;
		} else if (players==null || players.size()==0){ //include random movement when player is not close
			if (time % (random.nextInt(91)+ 30) == 0){
				randomly = true;
				xVel = ((double)(random.nextInt(3) - 1)) * zombieWalkSpeed;
				yVel = ((double)(random.nextInt(3) - 1)) * zombieWalkSpeed;
				if (random.nextInt(4) == 0){
					xVel = 0.0;
					yVel = 0.0;
				}
			}
			
			if(collisionWithTile(xVel, yVel)){
				xVel = ((double)(random.nextInt(3) - 1)) * zombieWalkSpeed;
				yVel = ((double)(random.nextInt(3) - 1)) * zombieWalkSpeed;
			}
		}
		
		if (xVel != 0 || yVel != 0){walking = true; move (xVel, yVel);}
		else{walking = false;}
	}
	
}
