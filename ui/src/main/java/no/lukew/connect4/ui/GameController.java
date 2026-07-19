package no.lukew.connect4.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    public Label gameModeLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setGameModeLabelText(String text) {
        this.gameModeLabel.setText(text);
    }
}
