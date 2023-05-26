package boardgame.gui.controller;

import boardgame.record.GameResult;
import boardgame.record.JsonGameResultManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

}
