/*
	 Copyright 2014, Adarsh Yagnik (adarsh_yagnik@yahoo.com)
	 This file is part of 2048.
	 
	 2048 is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    2048 is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with 2048(file: COPYING).  If not, see <http://www.gnu.org/licenses/>.
	
*/

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
	
	public String pad(int x){
		int l = (""+x).length();
		if(x == 0 || l == 0)
			return "    ";
		if(l == 1)
			return "  "+x+" ";
		if(l == 2)
			return " "+x+" ";
		if(l == 3)
			return " "+x;
		return "XXXX";
	}
}


