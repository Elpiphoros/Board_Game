package boardgame.model;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

public class BoardGameModel {

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

    public boolean isGameComplete(){
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
