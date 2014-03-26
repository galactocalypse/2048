import java.util.*;
class Game{

	private int board[][];
	private int score;
	private GameHelper gh;
	
	Game(){
		init();
	}
	
	private int[] getRandomPoint(){
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
	
	public void init(){
		/* Initializes the board. */
		gh = new GameHelper();
		board = new int[4][4];
		int a = gh.get2or4();
		int b = gh.get2or4();
		
		int p1[] = getRandomPoint();
		board[p1[0] - 1][p1[1] - 1] = a;
		int p2[] = getRandomPoint();
		board[p2[0] - 1][p2[1] - 1] = b;
		
		score = 0;
	}
	
	public boolean movePossible(){
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

	private ArrayList<Object> move(int arr[]){
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
		a.add(gh.diff(arr, b));
		return a;
	}

	
	private int[] getRow(int r){
		/* Returns the r-th row. */
		return board[r];
	}
	
	private int[] getColumn(int c){
		/* Returns the c-th column. */
		int a[] = new int[4];
		a[0] = board[0][c];
		a[1] = board[1][c];
		a[2] = board[2][c];
		a[3] = board[3][c];
		return a;
	}
	
	private void setRow(int r, int c[]){
		/* Copies c to the r-th row. */
		board[r][0] = c[0];
		board[r][1] = c[1];
		board[r][2] = c[2];
		board[r][3] = c[3];
	}
	
	private void setColumn(int c, int r[]){
		/* Copies r to the c-th column. */
		board[0][c] = r[0];
		board[1][c] = r[1];
		board[2][c] = r[2];
		board[3][c] = r[3];
	}
	
	private boolean makeMove(char dir){
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
				a = move(gh.reverse(getRow(i)));
				arr = gh.reverse((int[])a.get(0));
			}
			else if(dir == 'U'){
				a = move(getColumn(i));
				arr = (int[])a.get(0);
			}
			else if(dir == 'D'){
				a = move(gh.reverse(getColumn(i)));
				arr = gh.reverse((int[])a.get(0));
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

	public void disp(){
		/* Displays the board. */
		for(int i = 1; i <= 4; i++){
			for(int j = 1; j <= 4; j++){
				System.out.print(board[i-1][j-1] + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void performAction(){
		/* Caller for makeMove. Also adds new cell if required. */
		char ch = gh.readMove();
		if(ch == 'X'){
			init();
		}
		else if(ch == 'Z'){
			System.out.println("Exiting...");
			System.exit(0);
		}
		else if((ch == 'L' || ch == 'R' || ch == 'U' || ch == 'D') && makeMove(ch))
			addNew();
	}
	
	private void addNew(){
		/* Add a new cell. */
		int ind[] = getRandomPoint();
		if(ind == null)
			return;
		int a = gh.get2or4();
		board[ind[0]-1][ind[1]-1] = a;
	}

	public void showScore(){
		/* Display the player's score. */
		System.out.println("Your score is: " + score);
	}
	
	public boolean checkWin(){
		/* See if the player has won. */
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				if(board[i][j] == 2048)
					return true;
			}
		}
		return false;
	}
	public void dispWI(){
		gh.dispWelcome();
		gh.dispInst();
		
	}
}


