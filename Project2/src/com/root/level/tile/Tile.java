package com.root.level.tile;

import com.root.graphics.Screen;
import com.root.graphics.Sprite;

public abstract class Tile {
	
	public int x, y;
	public Sprite sprite;
	

	
	public Tile(Sprite sprite){
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen){
	}
	
	public boolean solid(){
		return false;
	}
	
	//list of Tiles
	//grass biome tiles
	public static Tile grass_1 = new GrassTile(Sprite.grass_1);
	public static Tile grass_2 = new GrassTile(Sprite.grass_2);
	public static Tile grass_3 = new GrassTile(Sprite.grass_3);
	public static Tile grass_4 = new GrassTile(Sprite.grass_4);
	public static Tile grass_5 = new GrassTile(Sprite.grass_5);
	public static Tile grass_6 = new GrassTile(Sprite.grass_6);
	public static Tile grass_7 = new GrassTile(Sprite.grass_7);
	public static Tile grass_8 = new GrassTile(Sprite.grass_8);
	
	public static Tile brick_1 = new WallTile(Sprite.brick_1);
	public static Tile lava_1 = new GrassTile(Sprite.lava_1);
	public static Tile checker_1 = new GrassTile(Sprite.checker_1);
	
	
	public static Tile voidTile = new VoidTile(Sprite.voidTile);
}
