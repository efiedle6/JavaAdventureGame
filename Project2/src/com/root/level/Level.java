package com.root.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.root.entity.Entity;
import com.root.entity.mob.Player;
import com.root.entity.particle.Particle;
import com.root.entity.projectile.Projectile;
import com.root.graphics.Screen;
import com.root.level.tile.Tile;
import com.root.util.Vector2i;

public abstract class Level {
	
	//this array contains all the levels tiles, contains tiles of level currently loaded
	protected int[] levelTiles; //tiles
	protected int width, height;
	//fill this to generate tiles on screen
	protected int[] tiles;//tilesInt
	
	private Comparator<Node> nodeSorter = new Comparator<Node>(){
		public int compare(Node n0, Node n1){
			if (n1.fCost < n0.fCost) return 1;
			if (n1.fCost > n0.fCost) return -1;
			return 0;
		}
	};
	
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();
	private List<Player> players = new ArrayList<Player>();
	
	public Level(int width, int height){
		this.width = width;
		this.height = height;
		tiles = new int[width*height];
		generateLevel();
	}
	
	public Level(String path){
		loadLevel(path);
		generateLevel();
	}
	
	protected void loadLevel(String path){
		
	}
	
	
	protected void generateLevel(){
		
	}
	
	//manage time for day night cycle
	private void time(){
		
	}
	
	public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset){
		boolean solid = false;
		for(int c = 0; c < 4; c++){
			//shift right by 4 to divide by TILE_SIZE (16)
			int xt = (x - c % 2 * size + xOffset)>>4;
			int yt = (y - c / 2 * size + yOffset)>>4;
			
			if (getTile(xt,yt).solid()){
				solid = true;
			}
			
		}
		return solid;
	}
	
	//handle logic, updates and removes
	public void update(){
		for (int i = 0; i < entities.size(); i++){
			if(entities.get(i).isRemoved()== true){
				entities.remove(i);
			}else entities.get(i).update();
		}
		for (int i = 0; i < projectiles.size(); i++){
			if(projectiles.get(i).isRemoved()== true){
				projectiles.remove(i);
			} else projectiles.get(i).update();
		}
		for (int i = 0; i < particles.size(); i++){
			if(particles.get(i).isRemoved()== true){
				particles.remove(i);
			}else  particles.get(i).update();
		}
		for (int i = 0; i < players.size(); i++){
			if(players.get(i).isRemoved()== true){
				players.remove(i);
			}else  players.get(i).update();
		}
		
	}
	
	public void add(Entity e){
		e.init(this);
		if (e instanceof Particle){
			particles.add((Particle)e);
		} else if(e instanceof Projectile){
			projectiles.add((Projectile)e);
		} else if(e instanceof Player){
			players.add((Player)e);
		}
		else{
			entities.add(e);
		}
	}
	
	public List<Projectile> getProjectiles(){
		return projectiles;
	}
	

	
	public void removeProjectile(Projectile p){
		projectiles.remove(p);
	}
	
	
	//handle rendering
	public void render(int xScroll, int yScroll, Screen screen){
		//set the offset based on the location of the player
		screen.setOffSet(xScroll, yScroll);
		
		//create variables for top left and bottom right corners
		//use screen.dimension because you only want to render what's on screen
		int x0 = xScroll >> 4, x1 = (xScroll + screen.width) >> 4;
		int y0 = yScroll >> 4, y1 = (yScroll + screen.height) >> 4;	
		
		//
		for (int y = y0; y < y1 + 1; y++){
			for (int x = x0; x < x1 + 1; x++){
				getTile(x, y).render(x, y, screen);
				
			}
		}
		
		renderEntities(screen);
		renderProjectiles(screen);
		renderParticles(screen);
		renderPlayers(screen);
		
	}
	
	private void renderEntities(Screen screen){
		for (int i = 0; i <entities.size(); i++){
			entities.get(i).render(screen);
		}
	}
	private void renderProjectiles(Screen screen){
		for (int i = 0; i <projectiles.size(); i++){
			projectiles.get(i).render(screen);
		}
	}
	private void renderParticles(Screen screen){
		for (int i = 0; i < particles.size(); i++){
			particles.get(i).render(screen);
		}
	}
	private void renderPlayers(Screen screen){
		for (int i = 0; i < players.size(); i++){
			players.get(i).render(screen);
		}
	}
	
	public List<Player> getPlayers(){
			return players;	
	}
	
	public Player getPlayerAt(int index){
		return players.get(index);
	}
	
	public Player getClientPlayer(){
		return players.get(0);
	}
	
	public List<Node> findPath(Vector2i start, Vector2i dest){//A* Search
		List<Node> openList = new ArrayList<Node>();//list of tiles to consider
		List<Node> closedList = new ArrayList<Node>();//list of tiles already considered
		Node current = new Node(start, null, 0, getDistance(start, dest));
		openList.add(current);
		
		while(openList.size() > 0){
			Collections.sort(openList, nodeSorter);
			current = openList.get(0);
			if (current.tile.equals(dest)){//found the destination!
				List<Node> path = new ArrayList<Node>();//backtrack to parents until start is reached
				while(current.parent != null){
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(current);
			closedList.add(current);
			for (int i = 0; i<9; i++){
				if (i == 4) continue;
				int x = current.tile.getX();
				int y = current.tile.getY();
				int xi = (i % 3) - 1; // -1, 0, 1 depending on if x is <, =, > starting x.
				int yi = (i / 3) - 1;// -1, 0, 1 depending on if y is <, =, > starting y.
				Tile at = getTile(x + xi, y + yi);
				if (at == null) continue;
				if (at.solid()) continue;
				Vector2i a = new Vector2i (x + xi, y + yi);
				double gCost = current.gCost;//prioritize diagnals
				if (getDistance(current.tile, a) > 1.4 && getDistance(current.tile, a) < 1.42) gCost += 0.95;
				else gCost += 1;
				double hCost = getDistance(a, dest);
				Node node = new Node(a, current, gCost, hCost);
				if(vecInList(closedList, a) && gCost >= node.gCost) continue; //Skips if node encountered before
				if(!vecInList(openList, a) || gCost< node.gCost) openList.add(node); //if node unencountered add it to list for future checking
			}
		}
		closedList.clear();
		return null;
	}
	
	private boolean vecInList(List<Node> list, Vector2i vector){
		for (Node n : list){
			if (n.tile.equals(vector)) return true;
		}
		return false;
	}
	
	private double getDistance(Vector2i start, Vector2i dest){
		double dx = (int)start.getX() - (int)dest.getX();
		double dy = (int)start.getY() - (int)dest.getY();
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	//Enter the entity that is searching and a radius
	public List<Entity> getEntities(Entity e, int radius){ 
		List<Entity> result = new ArrayList<Entity>();
		int eX = (int)e.getX();
		int eY = (int)e.getY();
		for (int i = 0; i < entities.size(); i++){
			Entity currentEnt = entities.get(i);
			int x = (int)currentEnt.getX();
			int y = (int)currentEnt.getY();
			
			int dx = Math.abs(x - eX);
			int dy = Math.abs(y - eY);
			
			double distance = Math.sqrt(dx*dx + dy*dy);
			if (distance <= radius) result.add(currentEnt);
		}
		return result;
	}
	
	//Enter the entity that is searching and a radius
		public List<Player> getPlayers(Entity e, int radius){ 
			List<Player> result = new ArrayList<Player>();
			int eX = (int)e.getX();
			int eY = (int)e.getY();
			
			for (int i = 0; i < players.size(); i++){
					Player currentPla = players.get(i);
					int x = (int)currentPla.getX();
					int y = (int)currentPla.getY();
					
					int dx = Math.abs(x - eX);
					int dy = Math.abs(y - eY);
					
					double distance = Math.sqrt(dx*dx + dy*dy);
					if (distance <= radius) result.add(currentPla);
			}
			return result;
		}
	
	// Grass = 0xFF00FF00, Flower = 0xFFFFFF00, Rock = 0xFF7F7F00
	public Tile getTile(int x, int y){
		//handle out of map bounds
		if (x < 0 || x >= width) return Tile.voidTile;
		else if (y < 0 || y >= height) return Tile.voidTile;
		
		//use a switch statement to get the correct tile based on int in tiles[]
		int tileValue = levelTiles[x + y*width];
		Tile tile;

		switch(tileValue){
			case 0xFF404040:
				tile = Tile.brick_1;
				break;
			case 0: tile = Tile.grass_1;
				break;
			case 1: tile = Tile.grass_2;
				break;
			case 2: tile = Tile.grass_3;
				break;
			case 3:
			case 0xFF7F7F00: tile = Tile.grass_4;
				break;
			// grass_5 is basic grass
			case 4:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
			case 15:
			case 0xFF00FF00:
				tile = Tile.grass_5;
				break;
			case 5: tile = Tile.grass_6;
				break;
			case 6:
			case 0xFFFFFF00: tile = Tile.grass_7;
				break;
			case 7: tile = Tile.grass_8;
				break;
			
			default: tile = Tile.voidTile;
				break;
		}
		return tile;
	}
	
}
