package com.root.level.tile;

import com.root.graphics.Screen;
import com.root.graphics.Sprite;

//works with all tiles from grass biome
public class GrassTile extends Tile {

	public GrassTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen){
		//convert x and y from tile precision into pixel precision
		screen.renderTile(x << 4, y << 4, this);
	}
	
}
