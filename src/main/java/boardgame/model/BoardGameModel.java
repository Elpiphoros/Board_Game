package boardgame.model;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

/**
 * Class defining the model of the game.
 */
public class BoardGameModel {

    private String current_player, player1, player2, winner;

    private int operationsOfPlayer1 = 0, operationsOfPlayer2 = 0;

    /**
     * Sets the player names and current player.
     *
     * @param player1 The {@code name} of player 1.
     * @param player2 The {@code name} of player 2.
     * @param current_player The {@code name} of the current player.
     */
    public void setPlayer(String player1, String player2, String current_player) {
        this.player1 = player1;
        this.player2 = player2;
        this.current_player = current_player;
    }

    /**
     * Retrieves the name of Player 1.
     *
     * @return the {@code name} of Player 1
     */
    public String getPlayer1() {
        return player1;
    }

    /**
     * Retrieves the name of Player 2.
     *
     * @return the {@code name} of Player 2
     */
    public String getPlayer2() {
        return player2;
    }

    /**
     * Retrieves the name of current player.
     *
     * @return the {@code name} of current player.
     */
    public String getCurrent_player() {
        return current_player;
    }

    /**
     * Retrieves the number of operations for Player 1.
     *
     * @return the {@code number} of operations for Player 1
     */
    public int getOperationsOfPlayer1() {
        return operationsOfPlayer1;
    }

    /**
     * Retrieves the number of operations for Player 2.
     *
     * @return the {@code number} of operations for Player 2
     */
    public int getOperationsOfPlayer2() {
        return operationsOfPlayer2;
    }

    /**
     * Retrieves the name of winner.
     *
     * @return the {@code name} of winner.
     */
    public String getWinner() {
        return winner;
    }


    /**
     * The size of the game board.
     */
    public static final int BOARD_SIZE = 3;

    private ReadOnlyObjectWrapper<Square>[][] board = new ReadOnlyObjectWrapper[BOARD_SIZE][BOARD_SIZE];

    /**
     * Initializes the game board with a size of {@link #BOARD_SIZE}.
     * Each cell of the board is initially set to {@link Square#NONE}.
     */
    public BoardGameModel() {
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new ReadOnlyObjectWrapper<Square>(Square.NONE);
            }
        }
    }

    /**
     * Retrieves the read-only object property of the square at the specified position on the game board.
     *
     * @param i the {@code row index} of the square
     * @param j the {@code column index} of the square
     * @return the read-only object property representing the square at the specified position
     */
    public ReadOnlyObjectProperty<Square> squareProperty(int i, int j) {
        return board[i][j].getReadOnlyProperty();
    }

    /**
     * Moves the square at the specified position on the game board.
     * The square's color will be changed according to the following sequence:
     * NONE -> RED -> YELLOW -> GREEN.
     *
     * @param i the {@code row index} of the square
     * @param j the {@code column index} of the square
     */
    public void move(int i, int j) {
        board[i][j].set(
                switch (board[i][j].get()) {
                    case NONE -> Square.RED;
                    case RED -> Square.YELLOW;
                    case YELLOW, GREEN -> Square.GREEN;
                }
        );
    }

    /**
     * Checks if the square at the specified position on the game board is green.
     *
     * @param i the {@code row index} of the square
     * @param j the {@code column index} of the square
     * @return {@code true} if the square is green, {@code false} otherwise
     */
    public boolean isGreen(int i, int j) {
        return board[i][j].get().equals(Square.GREEN);
    }

    /**
     * Checks if the game is complete.
     * A game is considered complete if there is a row, column, or diagonal with three squares of the same type (other than "NONE").
     *
     * @return {@code true} if the game is complete with a winning combination, {@code false} otherwise
     */
    public boolean isGameComplete() {
        for (var row = 0; row < 3; row++) {
            if (board[row][0].get() == board[row][1].get() && board[row][1].get() == board[row][2].get() && board[row][0].get() != Square.NONE) {
                return true;
            }
        }

        for (var col = 0; col < 3; col++) {
            if (board[0][col].get() == board[1][col].get() && board[1][col].get() == board[2][col].get() && board[0][col].get() != Square.NONE) {
                return true;
            }
        }

        if (board[0][0].get() == board[1][1].get() && board[1][1].get() == board[2][2].get() && board[0][0].get() != Square.NONE) {
            return true;
        }

        if (board[2][0].get() == board[1][1].get() && board[1][1].get() == board[0][2].get() && board[2][0].get() != Square.NONE) {
            return true;
        }

        return false;
    }

    /**
     * Exchanges the current player in the game. If the game is not complete,
     * it swaps the current player between player1 and player2.
     * Additionally, it increments the operation count of the respective player.
     */
    public void playerExchange() {
        if(!isGameComplete()) {
            if(current_player.equals(player1)) {
                current_player = player2;
                operationsOfPlayer1 ++;
            }
            else {
                current_player = player1;
                operationsOfPlayer2 ++;
            }
        }
    }

    /**
     * Sets the winner's names and increases 1 to operation times of winner.
     */
    public void setWinner() {
        if(isGameComplete()) {
            this.winner = current_player;
            if(this.winner.equals(player1)) {
                operationsOfPlayer1 += 1;
            }
            else {
                operationsOfPlayer2 += 1;
            }
        }
    }

    /**
     * Returns a string representation of the game board.
     *
     * @return a string representation of the game board, where each square is represented by its ordinal value followed by a space,
     *         and each row is separated by a newline character
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                sb.append(board[i][j].get().ordinal()).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

}
