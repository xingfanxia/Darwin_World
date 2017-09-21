/* Code by Makala Hieshima and Xingfan Xia
   For Darwin Final Project in CS201 (Data Structures)
   Last Modified 9 March 2016
   --------------------------------------------------------------------------------------------------
   The WorldGraph class creates the actual "physical" world as a graph where each cell in the
   theoretical world grid is represented as a Vertex and edges between vertices represent a connected
   path that may be followed by Creatures from one to another.  Creatures can only move in the 4 
   cardinal directions north, south, east, and west (or up, down, left, and right), they may not move
   diagonally.  They also may not move through walls, either the external walls of the grid or the 
   internal walls that are added. Therefore if there is a wall between 2 cells of the grid there should
   NOT be an edge between the 2 corresponding vertices.
   --------------------------------------------------------------------------------------------------*/

import java.util.*;

public class WorldGraph extends BaseWorldGraph {
	Vertex[] myWorld;
	int width, height, totalVertices;

	private class Vertex {
		private BaseCreature critter;
		private Point p;
		private ArrayList<Vertex> edges;

		/* Initalize a Vertex with null as the Creature */
		public Vertex(Point p) {
			this.p = p;
			critter = null;
			edges = new ArrayList<Vertex>();
		}
		
		/* Adds an edge to given Vertex v, returns true if added, returns false if edge already
		   exists */
		public boolean addEdge(Vertex v) {
			if(edges.indexOf(v) != -1) {
				return false;
			}
			edges.add(v);
			return true;
		}
		
		/* Removes an edge from given Vertex v, returns true if removed, returns false if
		   edge doesn't exist */
		public boolean rmvEdge(Vertex v) {
			if(edges.indexOf(v) == -1) {
				return false;
			}
			edges.remove(v);
			return true;
		}
		
		/* Returns true if and edge exists with the given Vertex v, returns false if it 
		   doesn't */
		public boolean getEdge(Vertex v) {
			if(edges.indexOf(v) == -1) {
				return false;
			}
			return true;
		}
		
		public ArrayList<Vertex> getAllEdges() {
			return edges;
		}
		
		public void addCritter(BaseCreature c) {
			critter = c;
		}
		
		public BaseCreature getCritter() {
			return critter;
		}
		
		public Point getPoint() {
			return p;
		}
	}
	
	/* Initialize your array (or whatever data structure) of Vertices. Each cell in the
       world grid is a Vertex, so you will need to create a total of width*height new
       vertices. Add edges between each Vertex and it's 4 neighbors (or less if the Vertex
       is on the edge of the world grid. Be careful not to add duplicate edges. */
	public WorldGraph(int width, int height) {
		totalVertices = width * height;
		this.width = width;
		this.height = height;
		myWorld = new Vertex[totalVertices];
		int index = 0;
		for(int i=0;i<width;i++) {
			for(int j=0;j<height;j++) {
				Point newP = new Point(i,j);
				myWorld[index] = new Vertex(newP);
				index++;
			}
		}
		addEdges();
	}
	
	/* Adds correct edges to all Vertices */
	private void addEdges() {
		int index = 0;
		while(index < totalVertices) {
			if(index >= height) { //if not first column
				myWorld[index].addEdge(myWorld[index - width]); //addEdge to vertex to left
			}
			if(index % height != 0) { //if not first row
				myWorld[index].addEdge(myWorld[index - 1]); //addEdge to vertex above
			}
			if(index < totalVertices - height) { //if not last column
				myWorld[index].addEdge(myWorld[index + width]); //addEdge to vertex to right
			}
			if(index % height != height - 1) { //if not last row
				myWorld[index].addEdge(myWorld[index+1]); //addEdge to vertex below
			}
			index++;
		}
	}
    
    /* returns true or false as to whether the Point p represents a valid Vertex
       in the world grid */
    public boolean inRange(Point p) {
    	for(Vertex v: myWorld) {
    		if(v.getPoint().x == p.x && v.getPoint().y == p.y) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /* returns vertex at point p if it exists, if it doesn't exist, returns null */
    private Vertex getVertex(Point p) {
    	if(inRange(p)) {
    		for(Vertex v: myWorld) {
    			if(v.getPoint().x == p.x && v.getPoint().y == p.y) {
    				return v;
    			}
    		}
    	}
    	return null;
    }

    /* adds the Creature critter to the Vertex at Point p if p is a valid point */
    public void addCreature(Point p, BaseCreature critter) {
    	if(getVertex(p) == null) {
			return;
    	}
    	getVertex(p).addCritter(critter);
    }
    
    /* gets the Creature in the Vertex at Point p if p is a valid point */
    public BaseCreature getCreature(Point p) {
    	if(getVertex(p) != null) {
			return getVertex(p).getCritter();
    	}
    	return null;
    }
    
    /* removes the Creature currently in the Vertex at Point p (sets the value of critter in 
       that Vertex to null) if p is a valid point */
    public void removeCreature(Point p) {
    	if(getVertex(p) == null) {
			return;
    	}
    	getVertex(p).addCritter(null);
    }

    /* adds an edge to the graph from Point p to the point in the direction indicated by
       dir, if that is a valid point */
    public void addEdge(Point p, Direction dir) {
    	if(inRange(p) == false) {
    		return;
    	}
    	int index = 0;
		for(int i=0;i<totalVertices;i++) {
			if(myWorld[i].getPoint().x == p.x && myWorld[i].getPoint().y == p.y) {
				index = i;
				break;
			}
		}
    	if(dir == Direction.getAt(0)) {
    		if(index % height == 0) {
    			return;
    		}
    		myWorld[index].addEdge(myWorld[index - 1]);
    		myWorld[index - 1].addEdge(myWorld[index]);
    	}
    	else if(dir == Direction.getAt(1)) {
    		if(index >= totalVertices - height) {
    			return;
    		}
    		myWorld[index].addEdge(myWorld[index + width]);
    		myWorld[index + width].addEdge(myWorld[index]);
    	}
    	else if(dir == Direction.getAt(2)) {
    		if(index % height == height - 1) {
				return;
			}
			myWorld[index].addEdge(myWorld[index+1]);
			myWorld[index+1].addEdge(myWorld[index]);
    	}
    	else {
    		if(index < height) {
    			return;
    		}
    		myWorld[index].addEdge(myWorld[index - width]);
    		myWorld[index - width].addEdge(myWorld[index]);
    	}
    }

    /* returns true or false as to whether there is an edge between the Vertices at
       Point a and Point b */
    public boolean getEdge(Point a, Point b) {
    	return getVertex(a).getEdge(getVertex(b));
    }

    /* removes the edge in the graph from Point p to the point in the direction indicated by
       dir. Don't forget to remove the edge from both vertices! */
    public void removeEdge(Point p, Direction dir) {
    	if(inRange(p) == false) {
    		return;
    	}
    	int index = 0;
		for(int i=0;i<totalVertices;i++) {
			if(myWorld[i].getPoint().x == p.x && myWorld[i].getPoint().y == p.y) {
				index = i;
				break;
			}
		}
    	if(dir == Direction.getAt(0)) { //if north
    		if(index % height == 0) { //if first row
    			return;
    		}
    		myWorld[index].addEdge(myWorld[index - 1]);
    	}
    	else if(dir == Direction.getAt(1)) { //if east
    		if(index >= totalVertices - height) { //if last column
    			return;
    		}
    		myWorld[index].addEdge(myWorld[index + width]);
    	}
    	else if(dir == Direction.getAt(2)) { //if south
    		if(index % height == height - 1) { //if last row
				return;
			}
			myWorld[index].addEdge(myWorld[index+1]);
    	}
    	else { //if west
    		if(index < height) { //if first row
    			return;
    		}
    		myWorld[index].addEdge(myWorld[index - width]);
    	}
    }
 
    
    /* returns the point adjacent to p in direction d, or null if there is no cell in that direction
       (i.e. the point is off the edge of the world grid). Note that in the graphical world the coordinate
       (0,0) is in the UPPER left-hand corner, meaning incrementing Y makes you move DOWN, or SOUTH,
       and decrementing Y makes you move UP, or NORTH. So adjPoint( (2,2), NORTH) would return (2,1). */
    public  Point adjPoint(Point p, Direction d) {
    	if(inRange(p) == false) {
    		return null;
    	}
    	int index = 0;
		for(int i=0;i<totalVertices;i++) {
			if(myWorld[i].getPoint().x == p.x && myWorld[i].getPoint().y == p.y) {
				index = i;
				break;
			}
		}
    	if(d == Direction.getAt(0)) { //if north
    		if(index % height == 0) { //if first row
    			return null;
    		}
    		return myWorld[index - 1].getPoint();
    	}
    	else if(d == Direction.getAt(1)) { //if east
    		if(index >= totalVertices - height) { //if last column
    			return null;
    		}
    		return myWorld[index + width].getPoint();
    	}
    	else if(d == Direction.getAt(2)) { //if south
    		if(index % height == height - 1) { //if last row
				return null;
			}
			return myWorld[index + 1].getPoint();
    	}
    	else { //if west
    		if(index < height) { //if first row
    			return null;
    		}
    		
    		return myWorld[index - width].getPoint();
    	}
    }
}