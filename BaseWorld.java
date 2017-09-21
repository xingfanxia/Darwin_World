/* Code by Sherri Goings
   For Darwin Final Project in CS201 (Data Structures)
   Last Modified for Fall 2011 Term
   --------------------------------------------------------------------------------------------------
   The World class represents the actual Darwin world. It holds the worldGraph and worldMap instances,
   a random number generator, and potentially other objects. It depends on class Point and enum Direction.
   --------------------------------------------------------------------------------------------------*/

import java.util.*;

public abstract class BaseWorld {
    public static Random rgen;
    protected WorldMap worldMap;
    protected BaseWorldGraph worldGraph;
    protected int width;
    protected int height;

    
   
    /* CONSTRUCTOR: public World(int width, int height, int seed);
       World constructor should create worldGraph and worldMap instances, and
       initialize the random number generator, like: rgen = new Random(seed);
       Then it should call addWalls() with the given width and height to add the random walls.
       Arguments: width of world, height of world, seed for random number generator */
    
    // returns worldGraph
    public abstract BaseWorldGraph worldGraph();

    // returns worldMap
    public abstract WorldMap worldMap();

    /* adds one random wall to certain internal cells in the world grid 
       Arguments: width and height of the world. */
    public void addWalls(int width, int height) {
	for (int i=1; i<width-1; i+=2) {
	    for (int k=1; k<height-1; k+=2) {
		Point curP = new Point(i,k);
                addWall(curP);
            }
        }
    }

    public abstract Point getOpenPoint();
    
    /* adds one wall to Point p by choosing a random direction and removing the edge
       between the the Vertex at Point p and the adjacent Vertex in the direction chosen. 
       Also should call draw the wall by calling the worldMap function addWall with the 
       given Point and chosen Direction */
    public abstract void addWall(Point p);


}
