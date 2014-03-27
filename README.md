2048
====

Description
-----------
My implementation of 2048 in Java. Made over a couple of nights because I was missing Java. Released under the GNU Public Licence.
![2048](snap.png 2048 - Sample Run)

Inspiration
-----------
The original (which I am yet to crack!):
http://gabrielecirulli.github.io/2048/

Usage
-----
javac G2048.java  
java G2048  

Use L, R, D and U for the respective shifts.

Structure
---------
1. Game: Contains all game info and dependent methods.
2. GameHelper: Contains game-state-independent methods required by the Game class. Separate class introduced to retain simplicity.
3. G2048: Contains actual game loop.

Contact
-------
For bugs, suggestions, or other queries, get in touch at adarsh_yagnik@yahoo.com
