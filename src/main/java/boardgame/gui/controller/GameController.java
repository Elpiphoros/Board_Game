package boardgame.gui.controller;

import boardgame.model.BoardGameModel;
import boardgame.record.GameResultManager;
import boardgame.record.JsonGameResultManager;
import boardgame.record.GameResult;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.ObjectBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameController {

    private FXMLLoader fxmlLoader = new FXMLLoader();

    private LocalDateTime startOfGame;

    private LocalDateTime endOfGame;

    private String duration;

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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

    @FXML
    private Label timeShow;

    @FXML
    private Label startLabel;

    @FXML
    private Label durationLabel;

    @FXML
    private Label endLabel;

    Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e ->
            timeShow.setText("Time: " + LocalDateTime.now().format(formatter))
    ), new KeyFrame(javafx.util.Duration.seconds(1)));

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
    private void startRecord() {
        player1_label.setText("Player 1: " + model.getPlayer1());
        player2_label.setText("Player 2: " + model.getPlayer2());
        operationOfPlayer1.setText("Operation times: 0");
        operationOfPlayer2.setText("Operation times: 0");
        nextPlayer.setText("It's turns to " + model.getCurrent_player());
        startOfGame = LocalDateTime.now();
        timeDisplay();
        startLabel.setText("Start Time: " + startOfGame.format(formatter));
        ready = true;
        warningLabel.setText("");
        Logger.info(startOfGame.format(formatter) + " Game starts!");
    }

    private void timeDisplay() {
        if(!model.isGameComplete()) {
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
        else {
            timeline.stop();
        }
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

    private String calculateDuration(LocalDateTime start, LocalDateTime end) {
        var hours = Duration.between(start, end).toHours();
        var minutes = Duration.between(start, end).toMinutesPart();
        var seconds = Duration.between(start, end).toSecondsPart();
        return hours + "h:" + minutes + "min:" + seconds + "s";
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
            nextPlayer.setText("It's turn to " + model.getCurrent_player());

            if(model.isGameComplete()){
                if(model.getCurrent_player().equals(model.getPlayer1())) {
                    model.setOperationsOfPlayer1(model.getOperationsOfPlayer1() + 1);
                    operationOfPlayer1.setText("Operation times: " + model.getOperationsOfPlayer1());
                }
                else {
                    model.setOperationsOfPlayer2(model.getOperationsOfPlayer2() + 1);
                    operationOfPlayer2.setText("Operation times: " + model.getOperationsOfPlayer2());
                }

                endOfGame = LocalDateTime.now();
                timeDisplay();
                endLabel.setText("End Time: " + endOfGame.format(formatter));
                Logger.info(endOfGame.format(formatter) + "Game end!");

                duration = calculateDuration(startOfGame,endOfGame);
                durationLabel.setText("duration time: " + duration);
                Logger.info("duration time is " + duration);

                model.setWinner();
                nextPlayer.setText("Winner is " + model.getWinner());
                Logger.debug("winner is {}",model.getWinner());

                addResult();
            }
        }
        else {
            warningLabel.setText("After loading, you can start!");
            Logger.warn("please click 'load info and start the game' to start the game");
        }
    }

    private void addResult() {
        GameResult gameResult = GameResult.builder()
                .player1(model.getPlayer1())
                .operations1(model.getOperationsOfPlayer1())
                .player2(model.getPlayer2())
                .operations2(model.getOperationsOfPlayer2())
                .winner(model.getWinner())
                .startOfGame(startOfGame.format(formatter))
                .duration(duration)
                .build();
        try {
            GameResultManager manager = new JsonGameResultManager(Path.of("results.json"));
            manager.add(gameResult);
        }
        catch (IOException e) {
            Logger.error("Error occurred while saving game result to file: " + e.getMessage());
        }
        catch (Exception e) {
            Logger.error("An unexpected error occurred: " + e.getMessage());
        }
    }

    @FXML
    private void Restart(ActionEvent event) throws IOException {
        fxmlLoader.setLocation(getClass().getResource("/fxml/start.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void seeResult(ActionEvent event) throws IOException {
        fxmlLoader.setLocation(getClass().getResource("/fxml/results.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}