package boardgame.gui.controller;

import boardgame.record.JsonGameResultManager;
import boardgame.record.PlayerStatistics;
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

public class RankingController {

    private final FXMLLoader fxmlLoader = new FXMLLoader();

    @FXML
    private TableView<PlayerStatistics> tableView;

    @FXML
    private TableColumn<PlayerStatistics, String> player;

    @FXML
    private TableColumn<PlayerStatistics, Long> winningTimes;

    @FXML
    private void initialize() throws IOException {

        player.setCellValueFactory(new PropertyValueFactory<>("winner"));

        winningTimes.setCellValueFactory(new PropertyValueFactory<>("numberOfWins"));

        ObservableList<PlayerStatistics> observableList = FXCollections.observableArrayList();

        observableList.addAll(new JsonGameResultManager(Path.of("results.json")).getBestPlayers(5));

        tableView.setItems(observableList);

    }

    @FXML
    private void restart(ActionEvent event) throws IOException {
        fxmlLoader.setLocation(getClass().getResource("/fxml/start.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        Logger.info("Game Restarts!");
    }

    @FXML
    private void seeResult(ActionEvent event) throws IOException {
        fxmlLoader.setLocation(getClass().getResource("/fxml/results.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        Logger.info("Results displayed");
    }

    @FXML
    private void exit() {
        Platform.exit();
        Logger.info("Program terminated");
    }


}
