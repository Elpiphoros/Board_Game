package boardgame.model;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

public class BoardGameModel {

    private String current_player, player1, player2, winner;

    private int operationsOfPlayer1 = 0, operationsOfPlayer2 = 0;

    public void setPlayer(String player1, String player2, String current_player) {
        this.player1 = player1;
        this.player2 = player2;
        this.current_player = current_player;
    }

    public void setOperationsOfPlayer1(int number) {
        this.operationsOfPlayer1 = number;
    }

    public void setOperationsOfPlayer2(int number) {
        this.operationsOfPlayer2 = number;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public String getCurrent_player() {
        return current_player;
    }

    public int getOperationsOfPlayer1() {
        return operationsOfPlayer1;
    }

    public int getOperationsOfPlayer2() {
        return operationsOfPlayer2;
    }

    public String getWinner() {
        return winner;
    }

    public static final int BOARD_SIZE = 3;

    private ReadOnlyObjectWrapper<Square>[][] board = new ReadOnlyObjectWrapper[BOARD_SIZE][BOARD_SIZE];

    public BoardGameModel() {
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new ReadOnlyObjectWrapper<Square>(Square.NONE);
            }
        }
    }

    public ReadOnlyObjectProperty<Square> squareProperty(int i, int j) {
        return board[i][j].getReadOnlyProperty();
    }

    public void move(int i, int j) {
        board[i][j].set(
                switch (board[i][j].get()) {
                    case NONE -> Square.RED;
                    case RED -> Square.YELLOW;
                    case YELLOW -> Square.GREEN;
                    case GREEN -> Square.NONE;
                }
        );
    }

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

    public void setWinner() {
        if(isGameComplete()) {
            this.winner = current_player;
        }
    }

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

    public static void main(String[] args) {
        var model = new BoardGameModel();
        System.out.println(model);
    }

}
