package com.root.graphics;

public class Font {

	private static SpriteSheet font = new SpriteSheet("/fonts/arial16.png", 16);
	private static Sprite[] font_characters = Sprite.split(font);
	
	private static String charIndex = "ABCDEFGHIJKLM" + //
									"NOPQRSTUVWXYZ" + //
									"abcdefghijklm" + //
									"nopqrstuvwxyz" + //
									"0123456789.,'" + //
									"'\"\";:!@$%()-+" + //
									" ";
	
	public Font(){	
	}
	
	public void render(String text, Screen screen){
		int x = 50;
		int y = 50;
		int xOffset = 0;
		for(int i = 0; i <text.length(); i++){
			int yOffset = 0;
			char currentChar = text.charAt(i);
			//adjust for sinking lowercase
			if(currentChar == 'g'|| currentChar == 'y'|| currentChar == 'q'||currentChar == 'p'|| currentChar == 'j'){
				yOffset = 4;
			}
			if(currentChar == ','|| currentChar == ';'){
				yOffset = 2;
			}
			int index = charIndex.indexOf(currentChar);
			screen.renderSprite(x+(font.WIDTH*(i%16)), 
					(y+(font.HEIGHT*(i/16))) + yOffset,
					font_characters[index], false);
		}
		
	}
	
	public void render(int x, int y, String text, Screen screen){
		render(x,y,0,0,text,screen);
	}
	
	public void render(int x, int y, int color, String text, Screen screen){
		render(x,y,0,color,text,screen);
	}
	
	public void render(int x, int y, int spacing, int color,  String text, Screen screen){
		int xOffset = 0;
		int line = 0;
		for(int i = 0; i <text.length(); i++){
			xOffset += (16+spacing);
			int yOffset = 0;
			char currentChar = text.charAt(i);
			//adjust for sinking lowercase
			if(currentChar == 'g'|| currentChar == 'y'|| currentChar == 'q'||currentChar == 'p'|| currentChar == 'j'){
				yOffset = 4;
			}
			if(currentChar == ','|| currentChar == ';'){
				yOffset = 2;
			}
			if(currentChar == '\n'){
				line++;
				xOffset = 0;
			}
			int index = charIndex.indexOf(currentChar);
			if(index == -1) continue;
			screen.renderTextCharacter(x + xOffset, 
					y + line*20 + yOffset,
					font_characters[index], color, false);
		}
		
	}
	
	
	
	
}
