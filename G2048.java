import java.io.*;
import java.util.*;

class Game{

	BufferedReader br;
	int board[][];
	int score;

	Game(){
		init();
	}
	
	int[] getRandomPoint(){
		/* Returns the 1-based co-ordinates of a random empty cell */
		ArrayList<int[]> a = new ArrayList<int[]>();
		for(int i = 1; i <= 4; i++){
			for(int j = 1; j <= 4; j++){
				if(board[i-1][j-1] == 0)
					a.add(new int[]{i, j});
			}
		}
		int r = (int)(Math.random()*a.size());
		return (r < a.size())?a.get(r):null;
	}
	
	int get2or4(){
		/* Returns 2 95% of the time, 4 the other 5%. */
		int x = (int)(Math.random()*100);
		if(x < 95)
			return 2;
		return 4;
	}
	
	void init(){
		/* Initializes the board. */
		br = new BufferedReader(new InputStreamReader(System.in));
		board = new int[4][4];
		int a = get2or4();
		int b = get2or4();
		
		int p1[] = getRandomPoint();
		board[p1[0] - 1][p1[1] - 1] = a;
		int p2[] = getRandomPoint();
		board[p2[0] - 1][p2[1] - 1] = b;
		
		score = 0;
	}
	
	boolean movePossible(){
		/* Returns true if a move is possible on the board. */
		for(int i = 1; i <= 4; i++){
			for(int j = 1; j <= 4; j++){
				if(board[i-1][j-1] == 0)
					return true;
				if(i > 1){
					if(board[i-1][j-1] == board[i-2][j-1])
						return true;
				}
				if(j > 1){
					if(board[i-1][j-1] == board[i-1][j-2])
						return true;
				}
				if(i < 4){
					if(board[i-1][j-1] == board[i][j-1])
						return true;
				}
				if(j < 4){
					if(board[i-1][j-1] == board[i-1][j])
						return true;
				}
			}
		}
		return false;
	}

	boolean diff(int a[], int b[]){
		/* Returns true if a and b have different elements at any corresponding index. */
		for(int i = 0; i < 4; i++)
			if(a[i] != b[i])
				return true;
		return false;
	}
	
	ArrayList<Object> move(int arr[]){
		/* Moves arr to the left. Returns the array after processing, the increase in score and a boolean value denoting whether there has been any change at all. This function is used for all directions by generating the relevant arrays. */
		int s = 0;
		int b[] = arr.clone();
		ArrayList<Object> a = new ArrayList<Object>();
		boolean ch[] = new boolean[4];
		for(int i = 0; i < 4; i++)
			ch[i] = false;
		for(int c = 0; c < 4; c++){
			int ind = -1;
			for(int i = c; i < 4; i++)
				if(arr[i] != 0){
					ind = i;
					break;
				}
			if(ind == -1)
				break;
			int t = arr[ind];
			arr[ind] = 0;
			arr[c] = t;
			
			if(c > 0 && !ch[c-1] && arr[c-1] == arr[c]){
				arr[c-1] = 2*arr[c];
				arr[c] = 0;
				s += arr[c-1];
				ch[c-1] = true;
				arr[c] = 0;
				c--;
			}
		}
		a.add(arr);
		a.add(s);
		a.add(diff(arr, b));
		return a;
	}

	int[] reverse(int arr[]){
		/* Returns the array with the order of elements reversed. */
		int x[] = new int[arr.length];
		for(int i = 0; i < arr.length; i++)
			x[arr.length-1-i] = arr[i];
		return x;
	}
	
	int[] getRow(int r){
		/* Returns the r-th row. */
		return board[r];
	}
	
	int[] getColumn(int c){
		/* Returns the c-th column. */
		int a[] = new int[4];
		a[0] = board[0][c];
		a[1] = board[1][c];
		a[2] = board[2][c];
		a[3] = board[3][c];
		return a;
	}
	
	void setRow(int r, int c[]){
		/* Copies c to the r-th row. */
		board[r][0] = c[0];
		board[r][1] = c[1];
		board[r][2] = c[2];
		board[r][3] = c[3];
	}
	
	void setColumn(int c, int r[]){
		/* Copies r to the c-th column. */
		board[0][c] = r[0];
		board[1][c] = r[1];
		board[2][c] = r[2];
		board[3][c] = r[3];
	}
	
	boolean hvMove(char dir){
		/* Make a shift in the given direction. */
		int s = 0;
		boolean ch = false;
		for(int i = 0; i < 4; i++){
			ArrayList<Object> a = null;
			int[] arr = null;
			if(dir == 'L'){
				a = move(getRow(i));
				arr = (int[])a.get(0);
			}
			else if(dir == 'R'){
				a = move(reverse(getRow(i)));
				arr = reverse((int[])a.get(0));
			}
			else if(dir == 'U'){
				a = move(getColumn(i));
				arr = (int[])a.get(0);
			}
			else if(dir == 'D'){
				a = move(reverse(getColumn(i)));
				arr = reverse((int[])a.get(0));
			}
			if(dir == 'L' || dir == 'R')
				setRow(i, arr);
			else if(dir == 'U' || dir == 'D')
				setColumn(i, arr);
			s += (Integer)a.get(1);
			ch = ch || (Boolean)a.get(2);
		}
		score += s;
		return ch;
	}

	void disp(){
		/* Displays the board. */
		for(int i = 1; i <= 4; i++){
			for(int j = 1; j <= 4; j++){
				System.out.print(board[i-1][j-1] + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	void makeMove(char ch){
		/* Caller fir hvMove. Also adds new cell if required. */
		if(ch == 'x'){
			init();
		}
		else if((ch == 'L' || ch == 'R' || ch == 'U' || ch == 'D') && hvMove(ch))
			addNew();
	}
	
	char readMove(){
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
		while(ch != 'R' && ch != 'L' && ch != 'U' && ch != 'D');
		return ch;
	}
	
	void addNew(){
		/* Add a new cell. */
		int ind[] = getRandomPoint();
		if(ind == null)
			return;
		int a = get2or4();
		board[ind[0]-1][ind[1]-1] = a;
	}
	
	void showScore(){
		/* Display the player's score. */
		System.out.println("Your score is: " + score);
	}
	
	boolean checkWin(){
		/* See if the player has won. */
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				if(board[i][j] == 2048)
					return true;
			}
		}
		return false;
	}
	
	void displayWin(){
		/* Display a congratulatory message for the player. */
		System.out.println("You won! :D");
	}
	
	void displayLoss(){
		/* Display sad smiley for player. */
		System.out.println("You lost! :(");
	}
}

public class G2048{
	/*
		Driver class for the Game.
	*/
	public static void main(String args[]) throws Exception{
		Game g = new Game();
		g.init();
		g.disp();
		g.showScore();
		do{
			g.makeMove(g.readMove());
			g.disp();
			g.showScore();
			if(g.checkWin()){
				g.displayWin();
			}
		}
		while(g.movePossible());
		if(!g.checkWin()){
			g.displayLoss();
		}
	}
}

