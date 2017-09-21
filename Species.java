/* Code by Sherri Goings
   For Darwin Final Project in CS201 (Data Structures)
   Last Modified for Fall 2011 Term
   --------------------------------------------------------------------------------------------------
   The WorldMap class handles all of the graphics in the Darwin simluation. It depends on the 
   Draw class for the actual drawing, and also the Point class and Direction enum for other operations. 
   --------------------------------------------------------------------------------------------------*/

import java.util.*;
import java.io.*;
import java.lang.*;

public class Species extends BaseSpecies {
    private Instruction[] program;
    private int programSize;

    public Species(String filename) {
	readFromFile(filename);
    }
    
    public void print() {
	for (int i=0; i<program.length; i++)
	    System.out.println(program[i]);
    }

    public String getName() {
	return name;
    }

    public Instruction getInst(int k) {
	return program[k];
    }

    public void createProgram(int size) {
        program = new Instruction[size];
        programSize = 0;
    }

    public void  addInstruction(Instruction inst) {
        program[programSize] = inst;
        programSize++;
    }
}
