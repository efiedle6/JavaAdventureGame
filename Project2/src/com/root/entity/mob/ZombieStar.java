package com.root.entity.mob;

import java.util.List;

import com.root.graphics.AnimatedSprite;
import com.root.graphics.Screen;
import com.root.graphics.Sprite;
import com.root.graphics.SpriteSheet;
import com.root.level.Node;
import com.root.util.Vector2i;

public class ZombieStar extends Mob{
	
	private double zombieWalkSpeed = 1;
	private double zombieChaseSpeed = 1;
	private double zombieRushSpeed = 2;
	private int attackCooldown = 0;
	private boolean walking = false;
	private double xVel = 0;
	private double yVel = 0;
	private int time = 0;
	private List<Node> path;
	private boolean randomly = false;//for if the mob is wandering randomly or not
	
	private AnimatedSprite zombie_down_walking = new AnimatedSprite(SpriteSheet.female_zombie_grey_down,32, 32, 3);
	private AnimatedSprite zombie_up_walking = new AnimatedSprite(SpriteSheet.female_zombie_grey_up,32, 32, 3);
	private AnimatedSprite zombie_left_walking = new AnimatedSprite(SpriteSheet.female_zombie_grey_left,32, 32, 3);
	private AnimatedSprite zombie_right_walking = new AnimatedSprite(SpriteSheet.female_zombie_grey_right,32, 32, 3);
	private AnimatedSprite currentSprite = zombie_up_walking;
	
	public ZombieStar(double x, double y){
		this.x = (int)(x*16);
		this.y = (int)(y*16);
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
		List<Player> players = level.getPlayers(this, Sprite.TILE_SIZE*16);
		 //TODO ensure 0 is always client player, not sure if this is true
		if(players.size() > 0){
			int px = (int)level.getPlayerAt(0).getX(); //this is pixel precision
			int py = (int)level.getPlayerAt(0).getY();
			Vector2i start = new Vector2i((int)(getX()/16), (int)(getY()/16));
			Vector2i dest = new Vector2i(px/Sprite.TILE_SIZE, py/Sprite.TILE_SIZE);
			
			//if (time % 3 == 0) 
			path = level.findPath(start, dest);
			if(path != null){//path found, pursue
				randomly = false;
				if (path.size() > 0){//still have tiles to traverse
					Vector2i vec = path.get(path.size() - 1).tile; //traverse path from the last index
					if((int)x < (int)vec.getX()*16) xVel += zombieChaseSpeed;
					if((int)x > (int)vec.getX()*16) xVel -= zombieChaseSpeed;
					if((int)y < (int)vec.getY()*16) yVel += zombieChaseSpeed;
					if((int)y > (int)vec.getY()*16) yVel -= zombieChaseSpeed;
				}
			}//path not found so do random action
			else{
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