package com.root.graphics;

public class Sprite {
	
	public final int SIZE;
	private int x, y;
	public int[] pixels;
	protected SpriteSheet sheet;
	private int width, height; 
	

	
	public static Sprite[] split(SpriteSheet sheet){
		int amount = (sheet.getHoriz() * sheet.getVert())/ 
				(sheet.WIDTH * sheet.HEIGHT);
		Sprite[] sprites = new Sprite[amount];

		int[] pixels = new int[sheet.getHeight()* sheet.getWidth()];
		
		int current = 0;
		for(int yp = 0; yp < sheet.getVert()/sheet.getHeight(); yp++){
			for(int xp = 0; xp < sheet.getHoriz()/sheet.getWidth(); xp++){
				
				for (int y = 0; y < sheet.getHeight(); y++){
					for (int x = 0; x < sheet.getWidth(); x++){
						pixels [x + y*sheet.getWidth()] = sheet.getPixels()[
						  (x + xp*sheet.getWidth()) + (y + yp*sheet.getHeight())*sheet.getHoriz()];
					}
				}
				
				sprites[current] = new Sprite(pixels, sheet.getWidth(), sheet.getHeight());
				current++;
			}
		}
		
		return sprites;
	}
	
	protected Sprite(SpriteSheet sheet, int width, int height){
		if (width == height){
			SIZE = width;
		} else SIZE = -1;
		
		this.width = width;
		this.height = height;
		this.sheet = sheet;
	}
	
	public Sprite(int size, int x, int y, SpriteSheet sheet){
		this.SIZE = size;
		this.width = SIZE;
		this.height = SIZE;
		this.x = x*SIZE;
		this.y = y*SIZE;
		pixels = new int[SIZE * SIZE];
		this.sheet = sheet;
		load();
	}
	
	public Sprite(int width, int height, int color){
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		setColor(color);
	}
	
	public Sprite(int size, int color){
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		setColor(color);
	}
	
	public Sprite(int[] pixels, int width, int height) {
		if (width == height){
			SIZE = width;
		} else SIZE = -1;
		this.width = width;
		this.height = height;
		this.pixels = new int[pixels.length];
		for (int i =0; i <pixels.length; i++){
			this.pixels[i] = pixels[i];
		}
		
	}

	private void setColor(int color){
		for (int i = 0; i < width * height; i++){
			pixels[i] = color;
		}
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	

	private void load(){
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				pixels[x + y*width] = sheet.pixels[(this.x + x) + (this.y + y)*sheet.getWidth()];
			}
		}
	}
	

	
	//List of Sprites
	public static final int TILE_SIZE = 16;
	
	//particle sprites (just square particles for now)
	public static Sprite particle_normal = new Sprite(3, 0xAAAAAA);
	
	//grass sprites
	public static Sprite grass_1 = new Sprite(16, 0, 0, SpriteSheet.test_sprites_1);
	public static Sprite grass_2 = new Sprite(16, 1, 0, SpriteSheet.test_sprites_1);
	public static Sprite grass_3 = new Sprite(16, 2, 0, SpriteSheet.test_sprites_1);
	public static Sprite grass_4 = new Sprite(16, 3, 0, SpriteSheet.test_sprites_1);
	public static Sprite grass_5 = new Sprite(16, 4, 0, SpriteSheet.test_sprites_1);
	public static Sprite grass_6 = new Sprite(16, 5, 0, SpriteSheet.test_sprites_1);
	public static Sprite grass_7 = new Sprite(16, 6, 0, SpriteSheet.test_sprites_1);
	public static Sprite grass_8 = new Sprite(16, 7, 0, SpriteSheet.test_sprites_1);
	
	public static Sprite brick_1 = new Sprite(16, 0, 1, SpriteSheet.test_sprites_1);
	public static Sprite lava_1 = new Sprite(16, 1, 1, SpriteSheet.test_sprites_1);
	public static Sprite checker_1 = new Sprite(16, 2, 1, SpriteSheet.test_sprites_1);
	
	
	public static Sprite voidTile = new Sprite(16, 15, 15, SpriteSheet.test_sprites_1);
	
	//projectiles
	public static Sprite wizard_projectile = new Sprite(16, 0, 0, SpriteSheet.projectile_sheet);
	
	/*stationary player sprite
	public static Sprite player_up_stat = new Sprite(32, 0, 3, SpriteSheet.player);
	public static Sprite player_down_stat = new Sprite(32, 0, 1, SpriteSheet.player);
	public static Sprite player_left_stat = new Sprite(32, 0, 2, SpriteSheet.player);
	public static Sprite player_right_stat = new Sprite(32, 0, 0, SpriteSheet.player);
	
	//up player movement animations
	public static Sprite player_up_mov_1 = new Sprite(32, 1, 3, SpriteSheet.player);
	public static Sprite player_up_mov_2 = new Sprite(32, 2, 3, SpriteSheet.player);
	public static Sprite player_up_mov_3 = new Sprite(32, 3, 3, SpriteSheet.player);
	
	//down player movement animations
	public static Sprite player_down_mov_1 = new Sprite(32, 1, 1, SpriteSheet.player);
	public static Sprite player_down_mov_2 = new Sprite(32, 2, 1, SpriteSheet.player);
	public static Sprite player_down_mov_3 = new Sprite(32, 3, 1, SpriteSheet.player);
	
	//left player movement animations
	public static Sprite player_left_mov_1 = new Sprite(32, 1, 2, SpriteSheet.player);
	public static Sprite player_left_mov_2 = new Sprite(32, 2, 2, SpriteSheet.player);
	public static Sprite player_left_mov_3 = new Sprite(32, 3, 2, SpriteSheet.player);
	
	//right player movement animations
	public static Sprite player_right_mov_1 = new Sprite(32, 1, 0, SpriteSheet.player);
	public static Sprite player_right_mov_2 = new Sprite(32, 2, 0, SpriteSheet.player);
	public static Sprite player_right_mov_3 = new Sprite(32, 3, 0, SpriteSheet.player);
	*/
}
