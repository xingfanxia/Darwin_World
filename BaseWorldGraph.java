/* Code by Sherri Goings
   For Darwin Final Project in CS201 (Data Structures)
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

public abstract class BaseWorldGraph {

    /* Part of the Vertex class. Start by cutting and pasting this into your WorldGraph
       class. Depending on what graph implementation you use it will look different, 
       but definitely a Vertex should be able to store a Creature if one is at that cell 
       in the grid. 

       private class Vertex {
           private BaseCreature critter;

           // Initalize a Vertex with null as the Creature
           public Vertex(Point p) {
               critter = null;
           }
       } 
    */

    /* CONSTRUCTOR: public WorldGraph(int width, int height)
       Initialize your array (or whatever data structure) of Vertices. Each cell in the
       world grid is a Vertex, so you will need to create a total of width*height new
       vertices. Add edges between each Vertex and it's 4 neighbors (or less if the Vertex
       is on the edge of the world grid. Be careful not to add duplicate edges. */
     

    /* returns true or false as to whether the Point p represents a valid Vertex
       in the world grid */
    public abstract boolean inRange(Point p);
    

    /* adds the Creature critter to the Vertex at Point p if p is a valid point */
    public abstract void addCreature(Point p, BaseCreature critter);
    
    /* gets the Creature in the Vertex at Point p if p is a valid point */
    public abstract BaseCreature getCreature(Point p);
    
    /* removes the Creature currently in the Vertex at Point p (sets the value of critter in 
       that Vertex to null) if p is a valid point */
    public abstract void removeCreature(Point p);

    /* adds an edge to the graph from Point p to the point in the direction indicated by
       dir, if that is a valid point */
    public abstract void addEdge(Point p, Direction dir);

    /* returns true or false as to whether there is an edge between the Vertices at
       Point a and Point b */
    public abstract boolean getEdge(Point a, Point b);

    /* removes the edge in the graph from Point p to the point in the direction indicated by
       dir. Don't forget to remove the edge from both vertices! */
    public abstract void removeEdge(Point p, Direction dir);
 
    
    /* returns the point adjacent to p in direction d, or null if there is no cell in that direction
       (i.e. the point is off the edge of the world grid). Note that in the graphical world the coordinate
       (0,0) is in the UPPER left-hand corner, meaning incrementing Y makes you move DOWN, or SOUTH,
       and decrementing Y makes you move UP, or NORTH. So adjPoint( (2,2), NORTH) would return (2,1). */
    public abstract Point adjPoint(Point p, Direction d);
}