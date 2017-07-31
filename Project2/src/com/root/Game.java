package com.root;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.root.entity.mob.Player;
import com.root.graphics.Font;
import com.root.graphics.Screen;
import com.root.input.Keyboard;
import com.root.input.Mouse;
import com.root.level.Level;
import com.root.level.SpawnLevel;
import com.root.level.TileCoord;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	//window variables
	private static int width = 360;
	private static int height = width/16 * 9;
	private static int scale = 3;

	
	private Thread gameThread;
	private JFrame frame;
	private Keyboard key;
	private Level level;
	private Player player;
	private boolean running = false;
	
	private Screen screen;
	private Font font;
	
	//create an image with accessible buffers
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	//setup the Raster (A data Structure representing a 2D array of pixels) allowing you to draw to image
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game(){
		//window creation
		Dimension size = new Dimension(width*scale, height*scale);
		setPreferredSize(size);
		screen = new Screen(width, height);
		frame = new JFrame();
		level = new SpawnLevel("/levels/spawnLevel.png");
		font = new Font();
		//input initialization
		key = new Keyboard();
		
		Mouse mouse = new Mouse();
		addKeyListener(key);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		
		//create player giving him keyboard control
		TileCoord player_spawn = new TileCoord(16, 40);
		player = new Player(key, player_spawn.getX(), player_spawn.getY());
		level.add(player);
		
		//Player player2 = new Player(key, player_spawn.getX()+ 32, player_spawn.getY()+ 10);
		//level.add(player2);
	}
	
	public synchronized void start(){
		//actions on starting a new thread
		running = true;
		gameThread = new Thread(this, "Display");
		gameThread.start();
	}
	
	public synchronized void stop(){
		//actions on closing a thread
		running = false;
		try{
			gameThread.join();
		} catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public void run(){
		
		//get system time
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0/60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		long now;
		
		requestFocus();
		
		//game loop
		while(running){
			
			//place limiter on update()
			now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime=now;
			while(delta >= 1){
				//handles frame-wise logic
				//update();
				delta--;
				updates++;
			}
			
			
			//handles images, will not be limited
			//render();
			frames++;
			
			//fps counter
			if (System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println(updates + " ups, " +frames + " frames");
				updates = 0;
				frames = 0;
			}
		}
		
		stop();
	}

	
	public void update(){
		key.update();
		level.update();
		
	}
	
	public void render(){
		//BufferStrategy- The image will be ready and stored for when it is time to display
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3); //three allows three images to be stored - triple buffering - no advantage going higher than 3
			return;
		}
		
		//call the screen's render method
		screen.clear();
		double xScroll = player.getX() - screen.width/2;
		double yScroll = player.getY() - screen.height/2;
		level.render((int) xScroll, (int)yScroll, screen);
		font.render(50,50,-3, 0xffff00,"hey guys,\nmy name is Ed.", screen);
		//copy screen.pixels to pixels
		for(int i = 0; i < pixels.length; i++){
			pixels[i] = screen.pixels[i];
		}
		
		//gets a graphics object to draw to the buffer
		Graphics g = bs.getDrawGraphics();

		//graphics logic
		//fill the screen with black for background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		/*for mouse
		g.fillRect(Mouse.getX()-8, Mouse.getY()-8, 16, 16);
		if(Mouse.getButton() != -1){
		g.drawString("Button: " + Mouse.getButton(), 80, 80);
		}
		*/
		
		//destroy the graphics object - releasing memory
		g.dispose();
		
		//make the next available buffer visible
		bs.show();
	}
	
	public static void main(String[] args){
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle("Project2");
		//adds game to Component, possible due to extending Canvas
		game.frame.add(game);
		//causes window to be sized based on preferredSize
		game.frame.pack();
		//have Frame close on exit
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//center frame on screen
		game.frame.setLocationRelativeTo(null);
		//shows Window
		game.frame.setVisible(true);
		//start game
		game.start();
		

		
	}
	
	public static int getWindowWidth(){
		return width * scale;
	}
	
	public static int getWindowHeight(){
		return height * scale;
	}
	
}
