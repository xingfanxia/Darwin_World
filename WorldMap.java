/* Code by Sherri Goings
   For Darwin Final Project in CS201 (Data Structures)
   --------------------------------------------------------------------------------------------------
   The WorldMap class handles all of the graphics in the Darwin simluation. It depends on the 
   Draw class for the actual drawing, and also the Point class and Direction enum for other operations. 
   --------------------------------------------------------------------------------------------------*/

public class WorldMap {
    private int w, h;     // width and height of the virtual world
    private static final int size = 30; // number of pixels per side of a grid square
    
    // construct a world map given the width and height of the world
    public WorldMap(int width, int height) {
	w = width;
	h = height;
	init();	
    }

    // draw the empty grid of the correct height and width and the text instructions
    private void init() {
	Draw.initGraphicsWindow(w*size+250, h*size+60, "WorldMap" );
	Draw.setPenRadius(.002);
	for (int i=1; i<w+1; i++) 
	    for (int j=1; j<h+1; j++) 
		Draw.square(size*i+size/2,size*j+size/2, size/2);

        int textCenter = Draw.width()-100;
	Draw.rectangle(textCenter, 62, 65, 40);
	Draw.text(textCenter, 50,"Click anywhere");
	Draw.text(textCenter, 75,"to pause");

	Draw.rectangle(textCenter, 225, 90, 95);
	Draw.text(textCenter, 150,"While paused:");
	Draw.text(textCenter, 175,"---------------");
	Draw.text(textCenter, 200,"type 'c' to view");
	Draw.text(textCenter, 225,"next creature step");
	Draw.text(textCenter, 250,"------------------");
	Draw.text(textCenter, 275,"type 'p' to view");
	Draw.text(textCenter, 300,"next population step");
    }

    /* update the generation count displayed in the simulation window
       arguments: int gen - generation number to display 
    */
    public void updateGen(int gen) {
	Draw.setPenColor(Draw.WHITE);
	Draw.filledRectangle( size+w*(size/2), size/2, w*(size/2), (size/2)-2);
	Draw.setPenColor(Draw.BLUE);
	Draw.text(size+w*(size/2), size/2, "generation: "+ Integer.toString(gen) );
    }

    /* draw visual representation of wall between 2 cells as a red line. 
       Arguments: Point p is the cell that will have a wall on the Direction dir side.
    */
    public void addWall(Point p, Direction dir) {
 	Draw.setPenRadius(.004);
	Draw.setPenColor(Draw.RED);
	switch(dir) {
	case SOUTH: 
 	    Draw.line(size*(p.x+1), size*(p.y+2), size*(p.x+2), size*(p.y+2));
	    break;
	case WEST:
	    Draw.line(size*(p.x+1), size*(p.y+1), size*(p.x+1), size*(p.y+2));
	    break;
	case NORTH:
	    Draw.line(size*(p.x+1), size*(p.y+1), size*(p.x+2), size*(p.y+1));
	    break;
	case EAST:
	    Draw.line(size*(p.x+2), size*(p.y+1), size*(p.x+2), size*(p.y+2));
	    break;
	}
    }
 
    /* Draw over a square in the worldmap with the new information or blank it out if requested.
       Arguments: Point p is the square to be redrawn, String speciesKey is the single-letter
       key for the species of the creature to be placed in the square, a key of " " means to blank 
       the square out. Direction dir is the direction the creature will face. 
    */
    public void updateSquare(Point p, String speciesKey, Direction dir) {
	if (speciesKey.equals(" ")) {
	    Draw.setPenColor(Draw.WHITE);
	    Draw.filledSquare(size*(p.x+1)+size/2, size*(p.y+1)+size/2, (size/2)-2);
	}
	else {
	    Draw.setPenColor(Draw.MAGENTA);
	    Draw.text(size*(p.x+1)+size/2,size*(p.y+1)+(size/2)+2,speciesKey);
	    Draw.setPenColor(Draw.BLUE);
	    drawArrow(p, dir);
	}    
    }
    
    /* Draw the arrow showing which direction a creature is facing.
       Arguments: Point p to draw the arrow in, pointing in Direction dir. 
    */
    private void drawArrow(Point p, Direction dir) {
	int xPix = size*(p.x+1);
	int yPix = size*(p.y+1);
	if (dir==Direction.SOUTH) { 
	    double[] Xs = { xPix+4, xPix+4, xPix+26, xPix+26, xPix+15 };
	    double[] Ys = { yPix+20, yPix+4, yPix+4, yPix+20, yPix+26 };
	    Draw.polygon(Xs, Ys);
	}
	else if (dir==Direction.WEST) {
	     double[] Xs = { xPix+4, xPix+10, xPix+26, xPix+26, xPix+10 };
	     double[] Ys = { yPix+15, yPix+4, yPix+4, yPix+26, yPix+26 };
	    Draw.polygon(Xs, Ys);
	}
	else if (dir==Direction.NORTH) {
	    double[] Xs = { xPix+4, xPix+4, xPix+26, xPix+26, xPix+15 };
	    double[] Ys = { yPix+10, yPix+26, yPix+26, yPix+10, yPix+4 };
	    Draw.polygon(Xs, Ys);
	}
	else if (dir==Direction.EAST) {
	    double[] Xs = { xPix+4, xPix+4, xPix+20, xPix+26, xPix+20 };
	    double[] Ys = { yPix+4, yPix+26, yPix+26, yPix+15, yPix+4 };
	    Draw.polygon(Xs, Ys);
	}
    }
}