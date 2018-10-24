import java.util.*;

class LabTest {
	static Scanner in;

	static void AddCharList(DblList<Character> ls) { 
		// read a character string

	    // insert each character into a node in a DblList "ls".
		String list;
		list = in.next();

		for(int i = 0; i < list.length(); i++)
			ls.InsertBack(list.charAt(i));
	}

	public static void main(String[] args) {
		in = new Scanner(System.in);

		// a list containing characters in a scrambled order
		DblList<Character> scrambled = new DblList<Character>();	
		// a list containing characters in the original order
		DblList<Character> orig = new DblList<Character>();	

		int cmdcnt = 0;
		while(true) {
			System.err.println("SCR > ");
			String command;
			command = in.next();
			cmdcnt ++;
			if(command.equals("quit"))
				break;	// stop the program
			if(command.equals("add")) {
				AddCharList(orig);
				AddCharList(scrambled);
			} else if(command.equals("cut")) {
				if(scrambled.Cut() == false)
					System.out.println("Cannot Cut");
			} else if(command.equals("putl")) {
				if(scrambled.PutLeft() == false)
					System.out.println("Empty Hand");
			} else if(command.equals("putr")) {
				if(scrambled.PutRight() == false)
					System.out.println("Empty Hand");
			} else if(command.equals("movel")) {
				if(scrambled.MoveLeft() == false)
					System.out.println("Left End Already");
			} else if(command.equals("mover")) {
				if(scrambled.MoveRight() == false)
					System.out.println("Right End Already");
			} else if(command.equals("movee")) {
				scrambled.MoveEnd();
			} else if(command.equals("rev")) {
				scrambled.Reverse();
			}

			// show the current list
			System.out.println("Ori : " + orig);
			System.out.println("Scr : " + scrambled);
			if(orig.equals(scrambled)) {
				System.out.println("You Won by " + cmdcnt + " commands");
				cmdcnt = 0;
			}
		}
	}
}


