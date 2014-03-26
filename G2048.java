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

public class G2048{
	/*
		Driver class for the game.
	*/
	public static void main(String args[]) throws Exception{
		System.out.println("Copyright 2014, Adarsh Yagnik (adarsh_yagnik@yahoo.com)\n"+
			"2048 is free software, and you are welcome to "+
			"redistribute it within the framework of the GNU Public Licence.\nRead the file COPYING for details.\n\n");
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

