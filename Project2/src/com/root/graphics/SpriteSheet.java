package com.root.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	//system path to spritesheet
	private String path;
	
	public final int SIZE;
	public final int WIDTH, HEIGHT;// width and height of each sprite
	public int[] pixels;
	private int horiz, vert; //how large spritesheet is by pixels
	
	
	private Sprite[] sprites;
	
	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize){
		//these are all pixel precision rather than Sprite precision
		int xx = x * spriteSize;
		int yy = y * spriteSize;
		int w = width * spriteSize;
		int h = height * spriteSize;
		
		if (width == height){
			SIZE = width;
		} else SIZE = -1;
		
		WIDTH = w;
		HEIGHT = h;
		
		pixels = new int[w * h];
		for(int a = 0; a < h; a++){
			int yp = yy + a;
			for (int b = 0; b<w; b++){
				int xp = xx + b;
				pixels[b + a * w] = sheet.pixels[xp + yp * sheet.WIDTH]; 
			}
		}
		int frame = 0;
		sprites = new Sprite[width * height];
		for (int ya = 0; ya < height; ya++){
			for (int xa = 0; xa < width; xa++){

				int[] spritePixels = new int[spriteSize * spriteSize];
				for (int a = 0; a < spriteSize; a++){
					for (int b = 0; b < spriteSize; b++){
						spritePixels[b + a * spriteSize] = pixels[((xa * spriteSize) + b) + 
						         ((ya * spriteSize) + a) * WIDTH];
						
						
					}
				}
				Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
				sprites[frame] = sprite;
				frame++;
			}
		}
		
		
	}
	
	public SpriteSheet(String path, int size){
		this.path = path;
		this.SIZE = size;
		WIDTH = size;
		HEIGHT = size;
		pixels = new int[SIZE * SIZE];
		loadImage();
	}
	
	public SpriteSheet(String path, int width, int height){
		this.path = path;
		this.SIZE = -1;
		WIDTH = width;
		HEIGHT = height;
		pixels = new int[WIDTH * HEIGHT];
		loadImage();
	}
	
	private void loadImage(){
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			horiz = image.getWidth(); 
			vert = image.getHeight();
			pixels = new int[horiz * vert];
			//set image to pixel array
			image.getRGB(0, 0, horiz, vert, pixels, 0, horiz);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Sprite[] getSprites(){
		return sprites;
	}
	
	public int getWidth(){
		return WIDTH;
	}
	
	public int getHeight(){
		return HEIGHT;
	}
	
	public int getHoriz(){
		return horiz;
	}
	
	public int getVert(){
		return vert;
	}
	
	public int[] getPixels() {
		return pixels;
	}
	
	
	//list of SpriteSheets
	public static SpriteSheet test_sprites_1 = new SpriteSheet("/textures/test_sprites_1.png", 256);
	public static SpriteSheet player = new SpriteSheet("/player_images/player_walk_cardinal.png", 128);
	public static SpriteSheet projectile_sheet = new SpriteSheet("/textures/projectile_sheet.png", 256);
	public static SpriteSheet zombie_sheet = new SpriteSheet("/enemy_images/zombie/zombiesheet.png", 384, 256);
	
	//creates a subsprite sheet of player starting at sprite x = 0, y = 1, extending 4 sprite widths and 1 sprite height
	public static SpriteSheet player_down = new SpriteSheet(player, 0, 1, 4, 1, 32);
	public static SpriteSheet player_up = new SpriteSheet(player, 0, 3, 4, 1, 32);
	public static SpriteSheet player_left = new SpriteSheet(player, 0, 2, 4, 1, 32);
	public static SpriteSheet player_right = new SpriteSheet(player, 0, 0, 4, 1, 32);
	
	
	//subsheet of zombies
	public static SpriteSheet male_zombie_mattock_down = new SpriteSheet(zombie_sheet, 9, 4, 3, 1, 32);
	public static SpriteSheet male_zombie_mattock_up = new SpriteSheet(zombie_sheet, 9, 7, 3, 1, 32);
	public static SpriteSheet male_zombie_mattock_left = new SpriteSheet(zombie_sheet, 9, 5, 3, 1, 32);
	public static SpriteSheet male_zombie_mattock_right = new SpriteSheet(zombie_sheet, 9, 6, 3, 1, 32);
	
	public static SpriteSheet female_zombie_grey_down = new SpriteSheet(zombie_sheet, 0, 4, 3, 1, 32);
	public static SpriteSheet female_zombie_grey_up = new SpriteSheet(zombie_sheet, 0, 7, 3, 1, 32);
	public static SpriteSheet female_zombie_grey_left = new SpriteSheet(zombie_sheet, 0, 5, 3, 1, 32);
	public static SpriteSheet female_zombie_grey_right = new SpriteSheet(zombie_sheet, 0, 6, 3, 1, 32);

	
}
