/* Code by Makala Hieshima and Xingfan Xia
   For Darwin Final Project in CS201 (Data Structures)
   Last Modified 9 March 2016
   --------------------------------------------------------------------------------------------------
   The Creature class represents an individual Creature in the Darwin world. It stores the Creature's 
   Species, it's current location on the world grid, the direction it's currently facing, 
   the instruction from its Species program it should execute next, and the World object it lives
   in so it can interact with and affect that world. Implements takeOneTurn() function to perform one
   full turn of the Creature. Depends on Species, Point, and World classes, and Direction enum.
   --------------------------------------------------------------------------------------------------*/
   
import java.util.Random; //use for rgen

public class Creature extends BaseCreature {
  public Creature(BaseSpecies species, Point point, Direction direction, BaseWorld world) {
    //init Creature class
    this.species = species;
    this.point = point;
    this.dir = direction;
    this.world = world;
    this.curK = 0;
  }
  
  public String toString(){
    //concert it to String
    String str = "";
    str = str + "Creature of species " + this.species.getName() + " at point " + this.point + " with direction " + this.dir;
    return str;
  }
  
  // gets this Creature's current species
  public BaseSpecies getSpecies(){
    //return specie
    return this.species;
  }
  
  // changes this Creature's current species
  public void setSpecies(BaseSpecies species){
    //set to target specie
    this.species = species;
  }
  
  // gets this Creature's current location in the world grid
  public Point getPoint(){
    //return the point
    return this.point;
  }
  
  // gets this Creature's current facing direction
  public Direction getDir(){
    return this.dir;
  }
  
  // sets this Creature's current facing direction
  public void setDir(Direction direction){
    this.dir = direction;
  }
  
  // sets this Creature's current instruction to the given newK argument
  public void setK(int cur){
    this.curK = cur;
  }
  
  /* Performs one turn of a Creature.  A turn goes until one of the 4 actions is taken (left, right,
     hop, or infect) and then immediately ends. The creature can execute as many if statements as it
     wants in one turn to decide on an action, but can only perform one action. */
  public void takeOneTurn(){
    Instruction inst = this.species.getInst(this.curK); //get instruction
    Operation op = inst.op; //get op code for the instruction
    int i = inst.argument; //get the argument if there is any
    BaseCreature creatureA; //temp creature
    Point curPoint = this.world.worldGraph().adjPoint(this.point, this.dir); //set cur point
    if ((curPoint != null) && (!this.world.worldGraph().getEdge(this.point, curPoint))) { //if same point
      curPoint = null; 
    }
    
    switch (op) //do the corresponding instruction to specific op code
    {
      case HOP:  //if hop
        if ((curPoint != null) && (this.world.worldGraph().getCreature(curPoint) == null)){
          this.world.worldGraph().addCreature(curPoint, this);
          this.world.worldGraph().removeCreature(this.point);
          this.point = curPoint; //go ahead
        }
        this.curK++; //increment curK
        break;
      case LEFT: 
        this.dir = this.dir.getLeft(); 
        this.curK++;
        break;
      case RIGHT: 
        this.dir = this.dir.getRight();
        this.curK++;
        break;
      case INFECT: 
        if (curPoint != null) {
          creatureA = this.world.worldGraph().getCreature(curPoint); //set creature
          if ((creatureA != null) && (!creatureA.getSpecies().getName().equals(this.species.getName()))){ //if not ally
            creatureA.getSpecies().decAlive();
            this.species.addAlive();
            creatureA.setSpecies(this.species);
            creatureA.setDir(Direction.getAt(BaseWorld.rgen.nextInt(3)));
            creatureA.setK(0);
            this.world.worldMap().updateSquare(creatureA.point, " ", creatureA.dir);
            this.world.worldMap().updateSquare(creatureA.point, creatureA.getSpecies().getName().substring(0, 1), creatureA.dir);
          }
        }
        this.curK++; //increment curK
        break;
      case IFEMPTY: 
        if ((curPoint != null) && (this.world.worldGraph().getCreature(curPoint) == null)) {
          this.curK = i;
        } else {
          this.curK++;
        }
        takeOneTurn(); //recursive call
        break; //and break
      case IFWALL: 
        if (curPoint == null) {
          this.curK = i;
        } else {
          this.curK++;
        }
        takeOneTurn();
        break;
      case IFSAME: 
        if (curPoint != null)
        {
          creatureA = this.world.worldGraph().getCreature(curPoint);
          if ((creatureA != null) && (creatureA.getSpecies().getName().equals(this.species.getName()))) {//if same specie
            this.curK = i;
          } else {
            this.curK++;
          }
        }
        else
        {
          this.curK++;
        }
        takeOneTurn();
        break;
      case IFENEMY: 
        if (curPoint != null)
        {
          creatureA = this.world.worldGraph().getCreature(curPoint);
          if ((creatureA != null) && (!creatureA.getSpecies().getName().equals(this.species.getName()))) {//if enemy
            this.curK = i;
          } else {
            this.curK++;
          }
        }
        else
        {
          this.curK++;
        }
        takeOneTurn();
        break;
      case IFRANDOM: 
        if (BaseWorld.rgen.nextDouble() < 0.5D) {//do random stuff
          this.curK = i;
        } else {
          this.curK++;
        }
        takeOneTurn();
        break;
      case GOTO: 
        this.curK = i;
        takeOneTurn();
    }
  }
}
