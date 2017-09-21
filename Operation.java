/* Code by Sherri Goings
   For Darwin Final Project in CS201 (Data Structures)
   --------------------------------------------------------------------------------------------------
   The enum Operation stores the valid operations a Creature can perform. It also includes a 
   function to find a specific operation given a String that is the operation's name.
   --------------------------------------------------------------------------------------------------*/

public enum Operation {
    HOP, LEFT, RIGHT, INFECT, IFEMPTY, IFWALL, IFSAME, IFENEMY, IFRANDOM, GOTO; 
    
    // returns the operation that matches a String of the same name, or null if no match found
    public static Operation findOpFromString(String testString) {
	for (Operation op : Operation.values()) {
	    if (op.name().equals(testString)) 
		return op;
	}
	return null;
    }

    /* shows example use of the Operation enum type. See Direction.java for more specifics
       on enumeration types in general. */
    public static void main(String[] args) {

        String opString = "INFECT";
        Operation op = Operation.findOpFromString(opString);

        if (op == Operation.INFECT)
            System.out.println("String "+opString+" matches operation INFECT");
        else
            System.out.println("String "+opString+" does not match operation INFECT");

        if (op == Operation.IFENEMY)
            System.out.println("String "+opString+" matches operation IFENEMY");
        else
            System.out.println("String "+opString+" does not match operation IFENEMY");
        
    }
}

   
    