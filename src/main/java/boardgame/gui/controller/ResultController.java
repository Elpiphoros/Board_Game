package boardgame.gui.controller;

import boardgame.record.GameResult;
import boardgame.record.JsonGameResultManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;
import java.nio.file.Path;

public class ResultController {

    @FXML
    private TableView<GameResult> tableView;

    @FXML
    private TableColumn<GameResult, String> player1;

    @FXML
    private TableColumn<GameResult, Integer> step1;

    @FXML
    private TableColumn<GameResult, String> player2;

    @FXML
    private TableColumn<GameResult, Integer> step2;

    @FXML
    private TableColumn<GameResult, String> winner;

    @FXML
    private TableColumn<GameResult, String> startTime;

    @FXML
    private TableColumn<GameResult, String> duration;

    private final FXMLLoader fxmlLoader = new FXMLLoader();

    @FXML
    public void initialize() throws IOException {

        player1.setCellValueFactory(new PropertyValueFactory<>("player1"));

        step1.setCellValueFactory(new PropertyValueFactory<>("operations1"));

        player2.setCellValueFactory(new PropertyValueFactory<>("player2"));

        step2.setCellValueFactory(new PropertyValueFactory<>("operations2"));

        winner.setCellValueFactory(new PropertyValueFactory<>("winner"));

        startTime.setCellValueFactory(new PropertyValueFactory<>("startOfGame"));

        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));

        ObservableList<GameResult> observableList = FXCollections.observableArrayList();

        observableList.addAll(new JsonGameResultManager(Path.of("results.json")).getAll());

        tableView.setItems(observableList);

    }

    @FXML
    private void Restart(ActionEvent event) throws IOException {
        fxmlLoader.setLocation(getClass().getResource("/fxml/start.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        Logger.info("Game Restarts!");
    }

    @FXML
    private void seeRanking(ActionEvent event) throws IOException {
        fxmlLoader.setLocation(getClass().getResource("/fxml/ranking.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        Logger.info("Ranking displayed!");
    }

    @FXML
    private void exitGame() {
        Platform.exit();
        Logger.info("Program terminated");
    }

}
