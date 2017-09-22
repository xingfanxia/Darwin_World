/* Code by Sherri Goings
   For Darwin Final Project in CS201 (Data Structures)
   Last Modified for Fall 2011 Term
   --------------------------------------------------------------------------------------------------
   The WorldMap class handles all of the graphics in the Darwin simluation. It depends on the 
   Draw class for the actual drawing, and also the Point class and Direction enum for other operations. 
   --------------------------------------------------------------------------------------------------*/

import java.util.*;

public class World extends BaseWorld{

      public World(int w, int h, int seed) {
	rgen = new Random(seed);
	worldGraph = new WorldGraph(w, h);
        worldMap = new WorldMap(w,h);
	addWalls(w,h);
        width = w;
        height = h;
     }   

    public BaseWorldGraph worldGraph() { return worldGraph; }
    public WorldMap worldMap() { return worldMap; }

    /* adds one random wall to certain internal cells in the world grid 
       Arguments: width and height of the world. */
    public void addWalls(int width, int height) {
	for (int i=1; i<width-1; i+=3) {
	    for (int k=1; k<height-1; k+=3) {
		Point curP = new Point(i,k);
                addWall(curP);
            }
        }
    }

    public Point getOpenPoint() {
        Point p = new Point(rgen.nextInt(width), rgen.nextInt(height));
        while (worldGraph.getCreature(p) != null)
            p = new Point(rgen.nextInt(width), rgen.nextInt(height));
        return p;
    }
    

    public void addWall(Point curP) {
        int wallDirection = rgen.nextInt(4);
        // 0: North, 1: East, 2: South, 3: West
        Direction dir = Direction.values()[rgen.nextInt(4)];
        worldMap.addWall(curP, dir);
        worldGraph.removeEdge(curP, dir);
    }
}