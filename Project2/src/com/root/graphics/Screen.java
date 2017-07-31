package com.root.graphics;

import java.util.Random;

import com.root.entity.mob.Mob;
import com.root.entity.projectile.Projectile;
import com.root.level.tile.Tile;

public class Screen {
	
	public int width, height;
	public int[] pixels;
	
	public int xOffSet, yOffSet;
	private final int ALPHA_COL = 0xFFFF00FF;
	
	private Random random = new Random();
	
	public Screen(int width, int height){
		this.width = width;
		this.height = height;
		pixels = new int[width*height];
		
	}
	
	public void clear(){
		for(int i = 0; i <pixels.length; i++){
			pixels[i] = 0;
		}
	}
	
	public void renderSheet(int xp, int yp, SpriteSheet sheet, boolean fixed){
		//render the sprite so that it covers then entire screen
		if(fixed){
			xp -= xOffSet;
			yp -= yOffSet;
		}
		
		for (int y = 0; y < sheet.HEIGHT; y ++){
			int ya = y + yp;
			for(int x = 0; x < sheet.WIDTH; x++){
				int xa = x + xp;
				if(xa<0 || xa >= width || ya < 0 || ya >= height) continue;
				pixels[xa + ya * width] = sheet.pixels[x + y * sheet.WIDTH];
			}
		}
		
	}
	
	
	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed){
		//if fixed is true it will have a specific position, if false it will follow players movement(useful for UI)
		if(fixed){
			xp -= xOffSet;
			yp -= yOffSet;
		}
		
		for (int y = 0; y < sprite.getHeight(); y ++){
			int ya = y + yp;
			for(int x = 0; x < sprite.getWidth(); x++){
				int xa = x + xp;
				if(xa<0 || xa >= width || ya < 0 || ya >= height) continue;
				
				int col = sprite.pixels[x + y * sprite.getWidth()];
				if (col != ALPHA_COL){
				pixels[xa + ya * width] = col;
				}
			}
		}
		
	}
	
	public void renderTextCharacter(int xp, int yp, Sprite sprite, int color, boolean fixed){
		//if fixed is true it will have a specific position, if false it will follow players movement(useful for UI)
		if(fixed){
			xp -= xOffSet;
			yp -= yOffSet;
		}
		
		for (int y = 0; y < sprite.getHeight(); y ++){
			int ya = y + yp;
			for(int x = 0; x < sprite.getWidth(); x++){
				int xa = x + xp;
				if(xa<0 || xa >= width || ya < 0 || ya >= height) continue;
				
				int col = sprite.pixels[x + y * sprite.getWidth()];
				if (col != ALPHA_COL){
				pixels[xa + ya * width] = color;
				}
			}
		}
		
	}
	
	
	public void renderTile(int xp, int yp, Tile tile){
		
		xp -= xOffSet;
		yp -= yOffSet;
		
		for (int y = 0; y < tile.sprite.SIZE; y++){
			//get the absolute position by factoring in offset
			int ya = y + yp;
			for (int x = 0; x < tile.sprite.SIZE; x++){
				//get the absolute position by factoring in offset
				int xa = x + xp;
				//ensure you are only rendering what is visible on screen
				if (xa < -tile.sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				//render the tile onto screen
				pixels[xa + ya * width] = tile.sprite.pixels[x+ y * tile.sprite.SIZE];
			}
		}
	}
	
public void renderProjectile(int xp, int yp, Projectile p){
		
		xp -= xOffSet;
		yp -= yOffSet;
		
		for (int y = 0; y < p.getSprite().SIZE; y++){
			//get the absolute position by factoring in offset
			int ya = y + yp;
			for (int x = 0; x < p.getSprite().SIZE; x++){
				//get the absolute position by factoring in offset
				int xa = x + xp;
				//ensure you are only rendering what is visible on screen
				if (xa < -p.getSprite().SIZE || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				//render the tile onto screen
				int col = p.getSprite().pixels[x + y * p.getSprite().SIZE];
				if (col != ALPHA_COL){
					pixels[xa + ya * width] = col;
				}
			}
		}
	}
	
	public void renderMob(int xp, int yp, Sprite sprite){
		xp -= xOffSet;
		yp -= yOffSet;
		
		for (int y = 0; y < sprite.SIZE; y++){
			//get the absolute position by factoring in offset
			int ya = y + yp;
			for (int x = 0; x < sprite.SIZE; x++){
				//get the absolute position by factoring in offset
				int xa = x + xp;
				//ensure you are only rendering what is visible on screen
				if (xa < - sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				//render the tile onto screen
				int col = sprite.pixels[x + y * sprite.SIZE];
				if (col != ALPHA_COL){
					pixels[xa + ya * width] = col;
				}
			}
		}
	}
	
	public void renderMob(int xp, int yp, Mob mob){
		xp -= xOffSet;
		yp -= yOffSet;
		
		for (int y = 0; y < mob.getSprite().SIZE; y++){
			//get the absolute position by factoring in offset
			int ya = y + yp;
			for (int x = 0; x < mob.getSprite().SIZE; x++){
				//get the absolute position by factoring in offset
				int xa = x + xp;
				//ensure you are only rendering what is visible on screen
				if (xa < - mob.getSprite().SIZE || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				//render the tile onto screen
				int col = mob.getSprite().pixels[x + y * mob.getSprite().SIZE];
				if (col != ALPHA_COL){
					pixels[xa + ya * width] = col;
				}
			}
		}
	}
	
	public void setOffSet(int xOffSet, int yOffSet){
		this.xOffSet = xOffSet;
		this.yOffSet = yOffSet;
	}

	public void drawRect(int xp, int yp, int width, int height, int color, boolean fixed) {
		if(fixed){
			xp -= xOffSet;
			yp -= yOffSet;
		}
		
		for (int x = xp; x < xp + width; x++){
			if (yp >= this.width || x < 0 || x>this.height) continue;
			if (yp > 0) pixels[x + yp * this.width] = color;
			if (yp + height >= this.height) continue;
			if (yp + height> 0) pixels[x + (yp + height) * this.width] = color;
		}
		
		for (int y = yp; y <= yp + height; y++){
			if (xp >= this.width || y < 0 || y>this.height) continue;
			if (xp > 0) pixels[xp + y * this.width] = color;
			if (xp + width >= this.width) continue;
			if (xp + width > 0) pixels[(xp + width) + y * this.width] = color;
		}
	
		
	}
	
	
	
}
