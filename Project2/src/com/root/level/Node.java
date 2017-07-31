package com.root.level;

import com.root.util.Vector2i;

public class Node {

	public Vector2i tile; //vector location of the node
	public Node parent; //the node you are coming from, start has a null parent
	public double fCost, gCost, hCost; //the costs of traveling through this tile
	//gCost is the total node to node cost distance-wise along path to dest
	//hCost (heuristic cost) is the direct distance between dest and origin
	//fCost is  gCost + hCost
	
	public Node(Vector2i tile, Node parent, double gCost, double hCost){
		this.tile = tile;
		this.parent = parent;
		this.gCost = gCost;
		this.hCost = hCost;
		this.fCost = this.gCost + this.hCost;
	}
	
	
}
