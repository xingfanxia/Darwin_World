/* Code by Sherri Goings
   For Darwin Final Project in CS201 (Data Structures)
   Last Modified for Fall 2011 Term
   --------------------------------------------------------------------------------------------------
   The WorldMap class handles all of the graphics in the Darwin simluation. It depends on the 
   Draw class for the actual drawing, and also the Point class and Direction enum for other operations. 
   --------------------------------------------------------------------------------------------------*/

public class Creature extends BaseCreature {

    public Creature(BaseSpecies spec, Point p, Direction d, BaseWorld w) {
	species = spec;
	point = p;
	dir = d;
	world = w;
	curK = 0;
    }
    
    public String toString() {
	String ret = "";
	ret += "Creature of species "+species.getName()+" at point "+point+" with direction "+dir;
	return ret;
    }

    public BaseSpecies getSpecies() { return species; }

    public void setSpecies(BaseSpecies spec) { species = spec; }
    
    public Point getPoint() { return point; }

    public Direction getDir() { return dir; }

    public void setDir(Direction d) { dir = d; }

    public void setK(int k) { curK = k; }

    public void takeOneTurn() {
	Instruction curInst = species.getInst(curK);
	Operation op = curInst.op;
	int arg = curInst.argument;
	Point facePoint = world.worldGraph().adjPoint(point, dir);
        if (facePoint!=null && !world.worldGraph().getEdge(point, facePoint))
            facePoint = null;

	switch(op) {
	case HOP:
	    if (facePoint != null && world.worldGraph().getCreature(facePoint)==null) {
		world.worldGraph().addCreature(facePoint, this);	    
		world.worldGraph().removeCreature(point);
		this.point = facePoint;
	    }
	    curK++;
	    break;

	case LEFT:
	    dir = dir.getLeft();
	    curK++;
	    break;

	case RIGHT:
	    dir = dir.getRight();
	    curK++;
	    break;

	case INFECT:
	    if (facePoint != null) {
		BaseCreature infected = world.worldGraph().getCreature(facePoint);
		if (infected!=null && !infected.getSpecies().getName().equals(species.getName())) {
                    infected.getSpecies().decAlive();
                    species.addAlive();
       		    infected.setSpecies(species);
		    infected.setDir(Direction.getAt(world.rgen.nextInt(3)));
		    infected.setK(0);
                    world.worldMap().updateSquare(infected.point, " ", infected.dir, -1);
                    world.worldMap().updateSquare(infected.point, infected.getSpecies().getName().substring(0,1), infected.dir, infected.getSpecies().getColor());
		}	    
	    }
	    curK++;
	    break;

	case IFEMPTY:
	    if (facePoint!=null && world.worldGraph().getCreature(facePoint)==null)
		curK = arg;
	    else
		curK++;
	    takeOneTurn();
	    break;

	case IFWALL:
	    if (facePoint==null)
		curK = arg;
	    else
		curK++;
	    takeOneTurn();
	    break;

	case IFSAME:
	    if (facePoint != null) {
		BaseCreature facing = world.worldGraph().getCreature(facePoint);
		if (facing != null && facing.getSpecies().getName().equals(species.getName()))
		    curK = arg;
		else
		    curK++;
	    }
	    else
		curK++;
	    takeOneTurn();
	    break;

	case IFENEMY:
	    if (facePoint != null) {
		BaseCreature facing = world.worldGraph().getCreature(facePoint);
		if (facing != null && !facing.getSpecies().getName().equals(species.getName()))
		    curK = arg;
		else
		    curK++;
	    }
	    else
		curK++;
	    takeOneTurn();
	    break;

	case IFRANDOM:
	    if (world.rgen.nextDouble()<.5) 
		curK = arg;
	    else
		curK++;
	    takeOneTurn();
	    break;

	case GOTO:
	    curK = arg;
	    takeOneTurn();
	    break;
	}

    }
}