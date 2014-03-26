public class G2048{
	/*
		Driver class for the game.
	*/
	public static void main(String args[]) throws Exception{
		Game g = new Game();
		g.dispWI();
		g.init();
		g.disp();
		g.showScore();
		do{
			g.performAction();
			g.disp();
			g.showScore();
			if(g.checkWin()){
				System.out.println("You won! :D");
			}
		}
		while(g.movePossible());
		if(!g.checkWin()){
			System.out.println("You lost! :(");
		}
	}
}

