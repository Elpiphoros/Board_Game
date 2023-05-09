# Board Game

This game is played by two players.

The game board consist of 3x3 cells, each of which can contain a piece of stone, and a set of colored (red, yellow, and green) stones. 
The board is initially empty. Players move in turn, they have the following options:

1. placing a red stone into an empty cell,
2. replacing a red stone on the board with a yellow stone,
3. replacing a yellow stone on the board with a green stone.

The goal of the game is to have three stones of the same color in a row, column, or diagonal. The player who achieves this with his or her move wins the game.

When the game is started, the program will ask for the names of the players.

The program also stores the results of the games as follows. For each game, 
the following information will be stored: the date and time when the game was started, 
the name of the players, the number of turns made by the players during the game, 
and the name of the winner. The program is able to display a high score table in which the top 5 players with the most wins are displayed.

The game stores data in a database, in a JSON file.
