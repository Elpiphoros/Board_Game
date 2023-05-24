package boardgame.gui.controller;

import boardgame.model.BoardGameModel;
import javafx.beans.binding.ObjectBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;
import org.tinylog.Logger;

import java.time.LocalDateTime;

public class GameController {

    private FXMLLoader fxmlLoader = new FXMLLoader();

    private LocalDateTime startOfGame;

    @FXML
    private GridPane board;

    @FXML
    private Label player1_label;

    @FXML
    private Label player2_label;

    @FXML
    private Label warningLabel;

    @FXML
    private Label nextPlayer;

    @FXML
    private Label operationOfPlayer1;

    @FXML
    private Label operationOfPlayer2;

    private boolean ready = false;

    private BoardGameModel model = new BoardGameModel();

    public void initialPlayer(String player1, String player2) {
        model.setPlayer(player1, player2, player1);
        Logger.info("record the players' names");
    }

    @FXML
    private void initialize() {
        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare(i, j);
                board.add(square, j, i);
            }
        }
        Logger.info("Initialize a game board");
    }

    @FXML
    private void startRecord(ActionEvent event) {
        player1_label.setText("Player 1: " + model.getPlayer1());
        player2_label.setText("Player 2: " + model.getPlayer2());
        operationOfPlayer1.setText("Operation times: 0");
        operationOfPlayer2.setText("Operation times: 0");
        nextPlayer.setText("It's turns to " + model.getCurrent_player());
        ready = true;
        Logger.info("Game starts!");
    }

    private StackPane createSquare(int i, int j) {
        var square = new StackPane();
        square.getStyleClass().add("square");
        var piece = new Circle(50);

        piece.fillProperty().bind(
                new ObjectBinding<Paint>() {
                    {
                        super.bind(model.squareProperty(i, j));
                    }

                    @Override
                    protected Paint computeValue() {
                        return switch (model.squareProperty(i, j).get()) {
                            case NONE -> Color.TRANSPARENT;
                            case RED -> Color.RED;
                            case YELLOW -> Color.YELLOW;
                            case GREEN -> Color.GREEN;
                        };
                    }
                }
        );
        square.getChildren().add(piece);
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        if(model.isGameComplete()){
            Logger.info("Game end");
        }
        else if(ready) {
            var square = (StackPane) event.getSource();
            var row = GridPane.getRowIndex(square);
            var col = GridPane.getColumnIndex(square);
            model.move(row, col);
            Logger.debug("{} Click on square ({}, {})",model.getCurrent_player(), row, col);

            model.playerExchange();
            operationOfPlayer1.setText("Operation times: " + model.getOperationsOfPlayer1());
            operationOfPlayer2.setText("Operation times: " + model.getOperationsOfPlayer2());
            nextPlayer.setText("It's turns to " + model.getCurrent_player());

            if(model.isGameComplete()){
                model.setWinner();
                nextPlayer.setText("Winner is " + model.getWinner());
                Logger.debug("winner is {}",model.getWinner());
                Logger.info("Game end");
            }
        }
        else {
            warningLabel.setText("After loading, you can start!");
            Logger.warn("please click 'load info and start the game' to start the game");
        }
    }
}