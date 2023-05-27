package boardgame.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

/**
 * Controls the opening interface.
 */
public class StartController {

    private FXMLLoader fxmlLoader = new FXMLLoader();
    @FXML
    private TextField player1;

    @FXML
    private TextField player2;

    @FXML
    private Label warningLabel;

    @FXML
    private void startGame(ActionEvent event) throws IOException {
        if(player1.getText().isEmpty() || player2.getText().isEmpty()){
            warningLabel.setText("Please enter name!");
            Logger.warn("Please enter name!");
        }
        else {
            Logger.info("Game Launch");
            fxmlLoader.setLocation(getClass().getResource("/fxml/game.fxml"));
            Parent root = fxmlLoader.load();
            fxmlLoader.<GameController>getController().initialPlayer(player1.getText(), player2.getText());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
}
