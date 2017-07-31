package com.root.entity.mob;

import com.root.Game;
import com.root.entity.projectile.Projectile;
import com.root.entity.projectile.WizardProjectile;
import com.root.graphics.AnimatedSprite;
import com.root.graphics.Screen;
import com.root.graphics.SpriteSheet;
import com.root.input.Keyboard;
import com.root.input.Mouse;

public class Player extends Mob {
	
	/* Inherited variables
	public int x, y;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	protected Sprite sprite;
	protected int dir = random.nextInt(4);
	protected boolean moving = false;
	protected int anim = 0;
	*/
	
	private Keyboard input;
	public double playerSpeed = 2;
	private int wizProjCooldown = 0;
	private boolean walking = false;
	private double xVel = 0, yVel = 0;

	
	private AnimatedSprite player_down_walking = new AnimatedSprite(SpriteSheet.player_down,32, 32, 4);
	private AnimatedSprite player_up_walking = new AnimatedSprite(SpriteSheet.player_up,32, 32, 4);
	private AnimatedSprite player_left_walking = new AnimatedSprite(SpriteSheet.player_left,32, 32, 4);
	private AnimatedSprite player_right_walking = new AnimatedSprite(SpriteSheet.player_right,32, 32, 4);
	private AnimatedSprite currentSprite = player_up_walking;
	
	public Player(Keyboard input){
		this.sprite = currentSprite;
		this.input = input;
		wizProjCooldown = 0;
		setHitBox(2,13,7,11);
	}
	
	public Player(Keyboard input, double x, double y){
		this.x = x;
		this.y = y;
		this.input = input;
		this.sprite = currentSprite;
		wizProjCooldown = 0;
		setHitBox(2,13,7,14);//make sure center pixel is included in your hitbox
	}
	
	public double getX(){
		return x;
	}
	
	//to correct the y position spot being in the head of the player
	public double getY(){
		return y + 5;
	}
	
	public void update(){
		/*List<Entity> es = level.getEntities(this , 50); //test TODO remove these lines 
		System.out.println(es.size());
		for (Entity e : es){
			System.out.println(e.toString());
		}*/ //end test
		
		//if not walking set to first frame
		if (walking) currentSprite.update();
		else currentSprite.setFrame(0);
		
		//handle cooldown for wizard projectile
		if (wizProjCooldown > 0) wizProjCooldown--;
		
		
		//adjust velocity based on player input.
		//because of move() inherited by mob, this also sets dir, which is used for anim
		xVel = 0; yVel = 0;
		if (input.up){ 
			currentSprite = player_up_walking;
			yVel -= playerSpeed;
			dir = 0;
		} else if (input.down){
			currentSprite = player_down_walking;
			yVel+= playerSpeed;
			dir = 2;
		}
		
		if (input.right){
			currentSprite = player_right_walking;
			xVel += playerSpeed;
			dir = 1;
		} else if (input.left){
			currentSprite = player_left_walking;
			xVel -= playerSpeed;
			dir = 3;
		}
		
		
		if (xVel != 0 || yVel != 0){walking = true; move (xVel, yVel);}
		else{walking = false;}
		//for shooting
		clear();
		updateShooting(x, y);
	}
	
	private void clear() {
		for(int i = 0; i < level.getProjectiles().size(); i++){
			Projectile p = level.getProjectiles().get(i);
			if(p.isRemoved()){
				level.removeProjectile(p);
			}
		}
		
	}

	private void updateShooting(double xp, double yp){
		
		if (Mouse.getButton() == 1 && wizProjCooldown <= 0){
			//determine projectile angle
			double dx = (Mouse.getX() - (Game.getWindowWidth())/2);
			double dy = (Mouse.getY() - Game.getWindowHeight()/2);
			double mouseDirec = Math.atan2(dy, dx);
			shoot(xp, yp, mouseDirec);
			wizProjCooldown = WizardProjectile.FIRE_RATE;
		}
	}
	

	
	public void render(Screen screen){
		//grab the current sprite with dimensions
		sprite = currentSprite.getSprite();
		int xx = (int)(x - sprite.SIZE/2);
		int yy = (int)(y - sprite.SIZE/2);
		screen.renderMob(xx, yy, sprite);
	}
	
	public void move(){
		
	}
	
}
