/* Code by Sherri Goings
   For Darwin Final Project in CS201 (Data Structures)
   --------------------------------------------------------------------------------------------------
   The WorldMap class handles all of the graphics in the Darwin simluation. It depends on the 
   Draw class for the actual drawing, and also the Point class and Direction enum for other operations. 
  --------------------------------------------------------------------------------------------------*/

import java.util.*;
import java.io.*;
import java.lang.*;

public class Darwin {
    public static int speed = 3;
    public static int pauseTime = 100;
    public static int randSeed = (int)System.currentTimeMillis();
    public static String configFile = "configs/config.txt";
    public static int runSteps = 100;
    public static LinkedList<BaseCreature> pop = new LinkedList<BaseCreature>();
    public static BaseWorld world;
    public static int curGen=0;
    public static boolean graphics=true;
    public static boolean quiet=false;

    // Java won't automatically compile/recompile WorldGraph.java when compile
    // Darwin unless there's a reference to a WorldGraph object in this class.
    public static WorldGraph forCompilingPurposesNeverActuallyUsed;
    
    // get a random Point from the world grid and add the creature at that point	
    private static void addCreature(BaseSpecies spec) {
        Point p = world.getOpenPoint();
        addCreature(spec, p);
    }

    // add a creature of the given species to the world at the given point
    private static void addCreature(BaseSpecies spec, Point p) {
        Direction d = Direction.getAt(world.rgen.nextInt(4));
	BaseCreature c = new Creature(spec, p, d, world);
	pop.add(c);
	if (graphics)
	    world.worldMap().updateSquare(p, spec.getName().substring(0,1), d);
	world.worldGraph().addCreature(p, c);
    }
    
    private static boolean processConfigFile() {
	try {
	    Scanner scan = new Scanner(new File(configFile));
	    
	    while (scan.hasNext()) {
		String s = scan.next();
		if (s.equals("WORLD_XY")) {
		    int gridX=0, gridY=0;
		    if (scan.hasNextInt())
			gridX = scan.nextInt();
		    else {
			System.out.println("error in config file, WORLD_XY must be followed by 2 integers");
			return false;
		    }
		    if (scan.hasNextInt())
			gridY = scan.nextInt();
		    else {
			System.out.println("error in config file, WORLD_XY must be followed by 2 integers");
			return false;
		    }
		    world = new World(gridX, gridY, randSeed);
		}
		else if (s.equals("RUNSTEPS")) {
		    if (scan.hasNextInt())
			runSteps = scan.nextInt();
		    else {
			System.out.println("error in config file, RUNSTEPS must be followed by an integer");
			return false;
		    }
		}
		else if (s.equals("SPECIES_FILE")) {
		    BaseSpecies curSpec = null;
		    int numCreatures = 0;
		    String placeMode = "";
		    int numToSetPlace = 0;

		    // checking if SPECIES_FILE has proper filename argument
		    if (scan.hasNext())
			curSpec = new Species(scan.next());
		    else {
			System.out.println("error in config file, SPECIES_FILE must be followed by a filename");
			return false;
		    }
		    // if here, have now successfully created new species

		    // checking if next config item is the number of creatures for this species
		    if (scan.hasNext() && scan.next().equals("NUM_CREATURES")) {

			// checking if NUM_CREATURES has proper int argument
			if (scan.hasNextInt()) 
			    numCreatures = scan.nextInt();
			else {
			    System.out.println("error in config file, NUM_CREATURES must be followed by an integer");
			    return false;
			}
		    }
		    else {
			System.out.println("error in config file, SPECIES_FILE line must be followed by NUM_CREATURES line");
			return false;
		    }
		    // if here, have now successfully set the number of creatures for this species
		    
		    // checking if next config item is the placement mode for these creatures
		    if (scan.hasNext() && scan.next().equals("PLACEMENT_MODE")) {

			// checking if PLACEMENT_MODE has proper String argument
			if (scan.hasNext())
			    placeMode = scan.next();
			else {
			    System.out.println("error in config file, PLACEMENT_MODE must be followed by RANDOM or SET");
			    return false;
			}
		    }
		    else {
			System.out.println("error in config file, NUM_CREATURES line must be followed by PLACEMENT_MODE line");
			return false;
		    }
		    // if here, have set the placement mode for this species (though don't know if it's valid yet)
		    
		    // checking if PLACEMENT_MODE is either SET or RANDOM 
		    if (placeMode.equals("SET")) {
			// checking if PLACEMENT_MODE SET has extra int argument
			if (scan.hasNextInt())
			    numToSetPlace = scan.nextInt();
			else {
			    System.out.println("error in config file, PLACEMENT_MODE SET must be followed by an integer");
			    return false;
			}
			// if here, have valid placement mode of set and the number of creatures to add this way

			for (int i=0; i<numToSetPlace; i++) {
			    int x=-1, y=-1;
			    Point pt=null;

			    // checking that next line is a PLACE_CELL line
			    if (scan.hasNext() && scan.next().equals("PLACE_CELL")) {			

				// checking that PLACE_CELL line has 2 int arguments in grid range
				if (scan.hasNextInt()) {
				    x = scan.nextInt();
				    if (scan.hasNextInt()) {
					y = scan.nextInt();
				    }
				    else {
					System.out.println("error in config file, PLACE_CELL must be followed by 2 integers");
					return false;
				    }
				}
				else {
				    System.out.println("error in config file, PLACE_CELL must be followed by 2 integers");
				    return false;
				}
				pt = new Point(x,y);
				if (!world.worldGraph().inRange(pt)) {
				    System.out.println("error in config file, PLACE_CELL point is not in grid range");
				}
				// if here, have valid x,y Point in grid
				
			    }
			    else {
				System.out.println("error in config file, need N PLACE_CELL lines after PLACEMENT_MODE SET N");
				return false;
			    }
			    // if here, have valid PLACE_CELL line, so can add Creature!
			    addCreature(curSpec, pt);
			}
		    }
		    else if (!placeMode.equals("RANDOM")) {
			System.out.println("error in config file, PLACEMENT_MODE must be either RANDOM or SET");
			return false;
		    }
		    // if here, have successfully set a valid placement mode for this species, and added any setPlacement Creatures
		    
		    // Now add the rest of the creatures with random placement
		    for (int i=numToSetPlace; i<numCreatures; i++)
			addCreature(curSpec);
		   			    
		}
		else {
		    System.out.println("error in config file, invalid config option or invalid ordering");
		    return false;
		}
	    }
	    // if here, have successfully read the entire config file, whew...
	}catch (IOException e){
	    System.err.println("Error: " + e.getMessage());
	}
	return true;
    }

    private static boolean setPauseTime() {
	switch(speed) {
	case 5:
	    pauseTime = 0;
	    break;
	case 4:
	    pauseTime = 5;
	    break;
	case 3:
	    pauseTime = 10;
	    break;
	case 2:
	    pauseTime = 30;
	    break;
	case 1:
	    pauseTime = 100;
	    break;
	default:
	    System.out.println("invalid speed option, requires int (1-5)");
	    return false;
	}
	return true;
    }	

    private static boolean processArgs(String[] args) {
	int i=0;

	while (i<args.length) {
	    if (args[i].equals("-s")) {
		if (i+1>args.length) {
		    System.out.println("option -s requires parameter (1-5)");
		    return false;
		}
		else 
		    speed = Integer.parseInt(args[i+1]);
		if (!setPauseTime())
		    return false;
	    }
	    else if (args[i].equals("-c")) {
		if (i+1>args.length) {
		    System.out.println("option -c requires parameter (filename)");
		    return false;
		}
		else
		    configFile = args[i+1];
	    }
	    else if (args[i].equals("-r")) {
		if (i+1>args.length) {
		    System.out.println("option -r requires parameter (int random number seed)");
		    return false;
		}
		else
		    randSeed = Integer.parseInt(args[i+1]);
	    }
	    else if (args[i].equals("-g")) {
		if (i+1>args.length) {
		    System.out.println("option -g requires parameter (true or false)");
		    return false;
		}
		else {
		    if (args[i+1].equals("true"))
			graphics = true;
		    else if (args[i+1].equals("false"))
			graphics = false;
		    else
			System.out.println("invalid parameter for option -g (requires true or false)");
		}
	    }
	     else if (args[i].equals("-q")) {
		 quiet = true;
		 i -= 1;
	    }
	    else {
		System.out.println("invalid option, try -h for list of available options");
		return false;
	    }
	    i += 2;
	}
	return true;
    }
    
    private static ListIterator<BaseCreature> oneCreatureStep(ListIterator<BaseCreature> it) {
	if (!it.hasNext()) {
	    endOfGeneration();
	    it = pop.listIterator();
	}   
	BaseCreature c = it.next();
	if (graphics)
	    world.worldMap().updateSquare(c.getPoint(), " ", c.getDir());
	c.takeOneTurn();
	if (graphics)
	    world.worldMap().updateSquare(c.getPoint(), c.getSpecies().getName().substring(0,1), c.getDir());
	return it;
    }

    private static boolean gameOver() {
        String sp = pop.getFirst().getSpecies().getName();
        for (BaseCreature c : pop) {
            if (!c.getSpecies().getName().equals(sp))
                return false;
        }
        return true;
    }

    private static void endOfGeneration() {
	if (graphics)
	    world.worldMap().updateGen(curGen);

	if (gameOver()) {
	    System.out.println("Game over at generation "+curGen+", species "+pop.getFirst().getSpecies().getName()+" wins!");
	    if (!graphics)
		System.exit(1);
	    System.out.println("Type q to quit");
	    char key = ' ';
	    while (key!='q') {
		try { Thread.currentThread().sleep(10); }
		catch (InterruptedException e) { System.out.println("Error sleeping"); }
		if (Draw.hasNextKeyTyped()) 
		    key = Draw.nextKeyTyped();
	    }
	    System.exit(1);
	}
	curGen++;
    }

    public static void main(String[] args) {

	if (args.length>0 && args[0].equals("-h")) {
	    System.out.println("usage: java WorldMap [option <parameter>] ...");
	    System.out.println("-h           :  lists usage and options");
	    System.out.println("-g graphics  :  turns graphics on/off, graphics is either true or false. default=true");
	    System.out.println("-s speed     :  sets speed of simulation to int <speed>, valid range is [1-5]. default=3");
	    System.out.println("-c configFile: sets file to read configuration from to String <configFile>. default='config.txt'");
	    System.out.println("-r randSeed  :  sets random number seed for simluation to int <randSeed>. default uses system time ");
            System.out.println("-q : quiet mode, won't print values of config options");
	    return;
	}
	System.out.println("\n*** Note - to see command line options type 'java Darwin -h' in terminal\n");

	setPauseTime();
	if (!processArgs(args))
	    return;
	if (!processConfigFile())
	    return;

	if (!quiet) {
	    System.out.println("running program with settings:");
	    System.out.println("graphics on? "+graphics);
	    System.out.println("simulation speed: "+speed);
	    System.out.println("random number seed: "+randSeed);
	    System.out.println("config file: "+configFile);
	    System.out.println("population steps to run simulation: "+runSteps);
	}

	curGen = 0;
	ListIterator<BaseCreature> it = pop.listIterator();
	while (curGen<runSteps) {	  
  
	    it = oneCreatureStep(it);

	    if (!graphics)
		continue;

	    try { Thread.currentThread().sleep(pauseTime); }
	    catch (InterruptedException e) { System.out.println("Error sleeping"); }

	    if (Draw.mouseClicked()) {
		while (!Draw.mouseClicked()) {
		    try { Thread.currentThread().sleep(10); }
		    catch (InterruptedException e) { System.out.println("Error sleeping"); }
		    if (Draw.hasNextKeyTyped()) {
			char key = Draw.nextKeyTyped();
			if (key == 'p') {
			    for (int i=0; i<10; i++)
				it = oneCreatureStep(it);
			}
			else if (key=='c') 
			    it = oneCreatureStep(it);
			else
			    System.out.println("invalid key pressed, ignoring...");
		    }
		}
	    }
	   
	}		   
    }
}
