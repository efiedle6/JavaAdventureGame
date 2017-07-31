package com.root.graphics;

public class AnimatedSprite extends Sprite {

	private int frame = 0;
	private Sprite sprite;
	private int rate = 5;
	private int animationSize = 0;
	private int time = 0;
	private int length = -1; //the number of animations states  for a sprite
	
	public AnimatedSprite(SpriteSheet sprite, int width, int height, int length){
		super(sprite, width, height);
		this.length = length;
		this.sprite = sheet.getSprites()[0];
		if( length > sheet.getSprites().length){
			System.err.println("Error, length of animation is too long");
		}
	}
	
	public void update(){
		time++;
		if (time % rate == 0){
			time = 0;
			//advance our animation frame
			if(frame >= (length- 1)) frame = 0;
			else frame++;
			
			sprite = sheet.getSprites()[frame];
		}
		//System.out.println(sprite + ", Frame: " + frame); //for testing
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public void setFrameRate(int frames){
		rate = frames;
	}

	public void setFrame(int i) {
		if (i > sheet.getSprites().length - 1) {
			System.err.println("Index out of bounds in Animated Sprite" + this);
			return;
		}
		sprite = sheet.getSprites()[i];
	}
	
}
