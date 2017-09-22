/* Code by Sherri Goings
   For Darwin Final Project in CS201 (Data Structures)
   Last Modified for Fall 2011 Term
   --------------------------------------------------------------------------------------------------
   The Species class defines a specific species in the Darwin world. It constructs a Species by
   reading a Species file and stores the Species name and the actual "program" it runs as a list
   of Instructions.  Depends on the Instruction class and Operation enum.
   --------------------------------------------------------------------------------------------------*/

import java.util.*;
import java.io.*;
import java.lang.*;

public abstract class BaseSpecies {
    protected String name;
    protected int color;
    protected int numAlive;
    /* CONSTRUCTOR: public Species(String filename) 
       calls readFromFile() to read a Species from the given file 
    */

    public void addAlive() { numAlive++; }
    public void decAlive() 
    { 
        numAlive--; 
        if (numAlive == 0)
            System.out.println("Species "+name+" has been eliminated!");
    }
    public int getAlive() { return numAlive; }
    public int getColor() { return color; }

    public void readFromFile(String filename) {
	try{
            // create the file reader
      	    Scanner scan = new Scanner(new File(filename));
            
            // get the Species name and the total number of Instructions in its program
	    name = scan.next();
            scan.nextLine();
            int numInsts = scan.nextInt();           
            createProgram(numInsts);

            scan.nextLine();
	   	    
            // go through each line of the Species file, skipping blank lines
	    while (scan.hasNextLine()) {
		String s = scan.nextLine();
		if (s.length()<1)
		    break;

                // create a new reader to parse just this individual line
		Scanner sScan = new Scanner(s);

                // read the String representation of the Operation and search the Operation
                // enum to find the matching Operation object
		String opString = sScan.next();
		Operation curOp = Operation.findOpFromString(opString);

                // if this Operation has a line number argument, read and save it too
		int argument = 0;
		if (sScan.hasNextInt())
		    argument = sScan.nextInt();
		addInstruction(new Instruction(curOp, argument));
	    }

	}catch (IOException e){
	    System.err.println("Error: " + e.getMessage());
	}
    }
    
    // returns the kth instruction from your Instruction list
    public abstract Instruction getInst(int k);

    // returns the name of the species
    public abstract String getName();

    /* initialize your program class variable to be some sort of list of instructions 
       (may or may not need size depending on your list) */
    public abstract void createProgram(int size);

    // add Instruction inst to your list of instructions
    public abstract void addInstruction(Instruction inst);
    
}