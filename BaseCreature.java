/* Code by Sherri Goings
   For Darwin Final Project in CS201 (Data Structures)
   Last Modified for Fall 2011 Term
   --------------------------------------------------------------------------------------------------
   The Creature class represents an individual Creature in the Darwin world. It stores the Creature's 
   Species, it's current location on the world grid, the direction it's currently facing, 
   the instruction from its Species program it should execute next, and the World object it lives
   in so it can interact with and affect that world. Implements takeOneTurn() function to perform one
   full turn of the Creature. Depends on Species, Point, and World classes, and Direction enum.
   --------------------------------------------------------------------------------------------------*/

public abstract class BaseCreature {
    protected BaseSpecies species;  // species of this creature
    protected Point point;      // current location on world grid
    protected Direction dir;    // current direction facing
    protected BaseWorld world;      // world living in
    protected int curK;         // index of next instruction to execute from program located in Species class

    /* CONSTRUCTOR: public Creature(BaseSpecies spec, Point p, Direction d, BaseWorld w) 
       sets the class variables appropriately */
        
    // gets this Creature's current location in the world grid
    public Point getPoint()  { return point; }

    // gets this Creature's current facing direction
    public Direction getDir()  { return dir; }

    // gets this Creature's current species
    public BaseSpecies getSpecies() { return species; }

    // sets this Creature's current species to newSpec argument
    public void setSpecies(BaseSpecies newSpec)  { species = newSpec; }

    // sets this Creature's current direction to Direction argument d
    public void setDir(Direction d)  { dir = d; }

    // sets this Creature's current instruction to the given newK argument
    public void setK(int newK) { curK = newK; }

    /* Performs one turn of a Creature.  A turn goes until one of the 4 actions is taken (left, right,
       hop, or infect) and then immediately ends. The creature can execute as many if statements as it
       wants in one turn to decide on an action, but can only perform one action. */
    public abstract void takeOneTurn();
}
