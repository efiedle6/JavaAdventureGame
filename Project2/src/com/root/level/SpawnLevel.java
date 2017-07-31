package com.root.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.root.entity.mob.Player;
import com.root.entity.mob.Shooter;
import com.root.entity.mob.ZombieChaser;
import com.root.entity.mob.ZombieStar;

public class SpawnLevel extends Level {


	
	public SpawnLevel(String path) {
		super(path);
	}
	
	protected void loadLevel(String path){
		try{
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			//width and height refer to Level.width/height
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			levelTiles = new int[w * h];
			image.getRGB(0, 0, w, h, levelTiles, 0, w);
		
		} catch (IOException e){
			e.printStackTrace();
			System.out.println("Exception in Spawn Level! Could not load level file!");
		}
		
		//for (int ii = 0; ii < 11; ii ++){
			add(new Shooter(20, 55)); //TODO remove
			add(new ZombieChaser(20, 20));
			add(new ZombieStar(20, 20));
		//}
	}
	
	
	// Grass = 0xFF00FF00, Flower = 0xFFFFFF00, Rock = 0xFF7F7F00
	protected void generateLevel(){
		
		
	}
	
}
