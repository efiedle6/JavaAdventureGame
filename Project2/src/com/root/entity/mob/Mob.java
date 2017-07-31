package com.root.entity.mob;

import java.util.List;

import com.root.entity.Entity;
import com.root.entity.projectile.Projectile;
import com.root.entity.projectile.WizardProjectile;
import com.root.graphics.Screen;
import com.root.graphics.Sprite;
import com.root.util.Vector2i;

public abstract class Mob extends Entity {
	
	/*Inherited
	 *	public int x, y;
	 *	private boolean removed = false;
	 *	protected Level level;
	 *	protected final Random random = new Random();
	 *	
	 */
	

	protected int time = 0;
	protected Player target = null;
	protected int dir = random.nextInt(4);
	protected boolean moving = false;
	protected Sprite sprite;
	//protected int anim = 0;//animation frame?
	protected int hbXmod, hbYmod; //for offsets off x,y position of hitbox sqaure
	protected int hbXmul, hbYmul; //for the width and height of hitbox
	
	public int getHbXmod() {
		return hbXmod;
	}

	public void setHbXmod(int hbXmod) {
		this.hbXmod = hbXmod;
	}

	public int getHbYmod() {
		return hbYmod;
	}

	public void setHbYmod(int hbYmod) {
		this.hbYmod = hbYmod;
	}

	public int getHbXmul() {
		return hbXmul;
	}

	public void setHbXmul(int hbXmul) {
		this.hbXmul = hbXmul;
	}

	public int getHbYmul() {
		return hbYmul;
	}

	public void setHbYmul(int hbYmul) {
		this.hbYmul = hbYmul;
	}
	
	protected void setHitBox(int x,int y,int w,int l){
		setHbXmod(x);
		setHbXmul(w);
		setHbYmod(y);
		setHbYmul(l);
	}


	
	
	public void move(double xa, double ya){
		if (xa!=0 && ya!=0){
			move(xa, 0);
			move(0, ya);
			return;
		}
		
		while (xa != 0){
			if(Math.abs(xa) > 1){ //accounts for doubles between 0 and 1 ex: 0.4
				if(!collisionWithTile(sign(xa), ya)){
					this.x += sign(xa);
					
				}
				xa -= sign(xa);
			}else{
				if(!collisionWithTile(sign(xa), ya)){
					this.x += xa;
				}
				xa = 0;
			}
		}
		
		while (ya != 0){
			if(Math.abs(ya) > 1){ //accounts for doubles between 0 and 1 ex: 0.4
				if(!collisionWithTile(xa, sign(ya))){
					this.y += sign(ya);
				}
				ya -= sign(ya);
			}else{
				if(!collisionWithTile(xa, sign(ya))){
					this.y += ya;
				}
				ya = 0;
			}
		}
		
	}
	
	private int sign(double value){
		if (value < 0) return -1;
		else if (value == 0) return 0;
		else return 1;
	}
	
	public abstract void update();
	
	public abstract void render(Screen screen);
	
	protected boolean collisionWithTile(double xa, double ya){
		boolean solid = false;
		for(int c = 0; c < 4; c++){
			int xt = (int)((x + xa) - (c % 2) * hbXmul + hbXmod)/16; //should be Sprite.TILE_SIZE
			int yt = (int)((y + ya) - (c / 2) * hbYmul + hbYmod)/16;
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c%2 == 0) ix = (int) Math.floor(xt);
			if (c/2 == 0) iy = (int) Math.floor(yt);
			
			if (level.getTile(ix,iy).solid()){
				solid = true;
			}
			
		}
	
		return solid;
	}
	
	protected void shoot(double x, double y, double mouseDir){
		Projectile p = new WizardProjectile(x, y, mouseDir);
		level.add(p);
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public void shootClosest(Shooter mob, int fireRate){ //shoot closest method input the mob itself to get its variables
		List<Player> players = level.getPlayers(this, 80);//TODO allow for followers as well
		double min = 0; //set minimum distance
		Player closest = null;
		for (int i = 0; i < players.size(); i++){//attack closest Player
			Player p = players.get(i);
			double distance = Vector2i.getDistance(new Vector2i ((int)x,(int)y),
					new Vector2i ((int)p.getX(),(int)p.getY()));
			if(i == 0 || distance < min) {
				min = distance;
				closest = p;
			}
		}
		if (mob.getAttackCooldown() <= 0 && closest != null){
		double px = closest.getX();
		double dx = (px - mob.getX());
		double py = closest.getY();
		double dy = (py - mob.getY());
		double dir = Math.atan2(dy, dx);
		shoot(getX(), getY(), dir);
		mob.setAttackCooldown(fireRate);
		}//TODO fix super types next project
	}
	
	public void shootRandom(Shooter mob, int fireRate){ //shoot closest method input the mob itself to get its variables
		if (time % 60 == 0){
			List<Player> players = level.getPlayers(this, 200);//TODO allow for followers as well
			int index = players.size();
			//System.out.println(index);
			if(players != null && players.size() != 0){//TODO attempted fix for crash
				int ranNum = random.nextInt(index);
				target = players.get(ranNum);
			} else target = null;
		}

		if (mob.getAttackCooldown() <= 0 && target != null){
			double px = target.getX();
			double dx = (px - mob.getX());
			double py = target.getY();
			double dy = (py - mob.getY());
			double dir = Math.atan2(dy, dx);
			shoot(getX(), getY(), dir);
			mob.setAttackCooldown(fireRate);
		}
	}//TODO fix super types next project
	
}
