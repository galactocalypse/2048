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
		int x = (int)(Math.random()*100);
		if(x < 95)
			return 2;
		return 4;
		
	}
	void init(){
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
	int process(int x1, int y1, int x2, int y2){
		//x1, y1 = dest; x2,y2 = source
		if(board[x2-1][y2-1] == 0)
			return 0;
		if(board[x1-1][y1-1] == 0){
			board[x1-1][y1-1] = board[x2-1][y2-1];
			board[x2-1][y2-1] = 0;
			return 0;
		}
		if(board[x1-1][y1-1] == board[x2-1][y2-1]){
			board[x1-1][y1-1] = 2*board[x1-1][y1-1];
			board[x2-1][y2-1] = 0;
			return board[x1-1][y1-1];
		}
		return 0;
	}
	
	boolean movePossible(){
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
		for(int i = 0; i < 4; i++)
			if(a[i] != b[i])
				return true;
		return false;
	}
	ArrayList<Object> move(int arr[]){
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
			}
		}
		a.add(arr);
		a.add(s);
		a.add(diff(arr, b));
		//System.out.println("Changed:");
		//printarr(b);
		//System.out.println("To:");
		//printarr(arr);
		//System.out.println("Res: "+(Boolean)a.get(2));
		return a;
	}

	void printarr(int arr[]){
		System.out.print("[");
		for(int i = 0; i < arr.length; i++){
			System.out.print(arr[i]);
			if(i < arr.length-1)
				System.out.print(", ");
		}
		System.out.println("]");
	}
	
	int[] reverse(int arr[]){
		int x[] = new int[arr.length];
		for(int i = 0; i < arr.length; i++)
			x[arr.length-1-i] = arr[i];
		return x;
	}
	int[] getRow(int r){
		return board[r];
	}
	int[] getColumn(int c){
		int a[] = new int[4];
		a[0] = board[0][c];
		a[1] = board[1][c];
		a[2] = board[2][c];
		a[3] = board[3][c];
		return a;
	}
	void setRow(int r, int c[]){
		board[r][0] = c[0];
		board[r][1] = c[1];
		board[r][2] = c[2];
		board[r][3] = c[3];
		
	}
	void setColumn(int c, int r[]){
		board[0][c] = r[0];
		board[1][c] = r[1];
		board[2][c] = r[2];
		board[3][c] = r[3];
		
	}
	boolean moveLeft(){
		int s = 0;
		boolean ch = false;
		for(int i = 0; i < 4; i++){
			//s += moveRowLeft(i);
			ArrayList<Object> a = move(getRow(i));
			int[] arr = (int[])a.get(0);
			setRow(i, arr);
			s += (Integer)a.get(1);
			ch = ch || (Boolean)a.get(2);
		}
		score += s;
		return ch;
	}

	boolean moveRight(){
		int s = 0;
		boolean ch = false;
		for(int i = 0; i < 4; i++){
			ArrayList<Object> a = move(reverse(getRow(i)));
			int[] arr = reverse((int[])a.get(0));
			setRow(i, arr);
			s += (Integer)a.get(1);
			ch = ch || (Boolean)a.get(2);
			//s += moveRowRight(i);
		}
		score += s;
		return ch;
	}
	boolean moveUp(){
		int s = 0;
		boolean ch = false;
		for(int i = 0; i < 4; i++){
			ArrayList<Object> a = move(getColumn(i));
			int[] arr = (int[])a.get(0);
			setColumn(i, arr);
			s += (Integer)a.get(1);
			ch = ch || (Boolean)a.get(2);
		}
		score += s;
		return ch;
	}
	boolean moveDown(){
		int s = 0;
		boolean ch = false;
		for(int i = 0; i < 4; i++){
			ArrayList<Object> a = move(reverse(getColumn(i)));
			int[] arr = reverse((int[])a.get(0));
			setColumn(i, arr);
			s += (Integer)a.get(1);
			ch = ch || (Boolean)a.get(2);
		}
		score += s;
		return ch;
	}
	void disp(){
		for(int i = 1; i <= 4; i++){
			for(int j = 1; j <= 4; j++){
				System.out.print(board[i-1][j-1] + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	void makeMove(char ch){
		boolean c = false;
		switch(ch){
			case 'R':
				c = c || moveRight();
				break;
			case 'L':
				c = c || moveLeft();
				break;
			case 'U':
				c = c || moveUp();
				break;
			case 'D':
				c = c || moveDown();
				break;
			default:
				System.out.println("Invalid move!");
		}
		if(c)
			addNew();
	}
	char readMove(){
		String inp = "";
		char ch = '\0';
		boolean firstmove = true;
		do{
			if(!firstmove)
				System.out.println("Invalid move! Try again.");
			else firstmove = false;
			try{
				
				//ch = (char)br.read();
				//br.read();
				inp = br.readLine();
				ch = inp.charAt(0);
				//System.out.println("You entered: '"+ch+"'");
				return ch;
			}
			catch(Exception e){
				System.out.println("I/O Error! Game terminating...");
				System.exit(0);
			}
			if(ch > 90)
				ch -= 32;
		}
		while(ch != 'R' && ch != 'L' && ch != 'U' && ch != 'D');
		return 0;
	}
	void addNew(){
		int ind[] = getRandomPoint();
		if(ind == null)
			return;
		int a = get2or4();
		board[ind[0]-1][ind[1]-1] = a;
	}
	void showScore(){
		System.out.println("Your score is: " + score);
	}
	boolean checkWin(){
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				if(board[i][j] == 2048)
					return true;
			}
		}
		return false;
	}
	void displayWin(){
		System.out.println("You won! :D");
	}
	void displayLoss(){
		System.out.println("You lost! :(");
	}
}
public class G2048{
	public static void main(String args[]) throws Exception{
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
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

