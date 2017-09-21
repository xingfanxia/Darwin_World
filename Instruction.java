/* Code by Sherri Goings
   For Darwin Final Project in CS201 (Data Structures)
   --------------------------------------------------------------------------------------------------
   The Instruction class stores one full instruction of a Creature, including the Operation 
   and the associated line number if there is one for that operation. Also a toString() to 
   get a String representation of an Instruction
   --------------------------------------------------------------------------------------------------*/

public class Instruction {
    public Operation op;
    public int argument=-1;
    
    public Instruction(Operation o, int arg) {
	op = o;
	argument = arg;
    }
    
    // returns a string with the Operation name and the associated line number if one exists.
    public String toString() {
        String ret = op.name();
        if (argument>=0)
            ret += " "+argument;
	return ret;
    }
}