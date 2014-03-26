import java.io.*;
import java.util.*;

class GameHelper{
	/* For general-purpose functions. */
	private BufferedReader br;
	GameHelper(){
		br = new BufferedReader(new InputStreamReader(System.in));
	}
	public int get2or4(){
		/* Returns 2 95% of the time, 4 the other 5%. */
		int x = (int)(Math.random()*100);
		if(x < 95)
			return 2;
		return 4;
	}
	
	public boolean diff(int a[], int b[]){
		/* Returns true if a and b have different elements at any corresponding index. */
		for(int i = 0; i < 4; i++)
			if(a[i] != b[i])
				return true;
		return false;
	}

	public int[] reverse(int arr[]){
		/* Returns the array with the order of elements reversed. */
		int x[] = new int[arr.length];
		for(int i = 0; i < arr.length; i++)
			x[arr.length-1-i] = arr[i];
		return x;
	}
	
		
	public char readMove(){
		/* Reads user input. If a string is entered, only the first chatacter is processed. */
		String inp = "";
		char ch = '\0';
		boolean firstmove = true;
		do{
			if(!firstmove)
				System.out.println("Invalid move! Try again.");
			else firstmove = false;
			try{
				inp = br.readLine();
				ch = inp.charAt(0);
			}
			catch(Exception e){
				System.out.println("I/O Error! Game terminating...");
				System.exit(0);
			}
			if(ch > 90)
				ch -= 32;
		}
		while(ch != 'R' && ch != 'L' && ch != 'U' && ch != 'D' && ch != 'X' && ch != 'Z');
		return ch;
	}

	public void dispInst(){
		System.out.println("1. Use L, R, U, D for the respective shifts.\n2. Use X for resetting the game.\n3. Use Z for exiting.\n4. Inputs are case-insensitive.\n");
	}
	public void dispWelcome(){
		System.out.println("Welcome to 2048!\n================");
	}
	
}


