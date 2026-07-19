package no.lukew.connect4.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void handleSingleplayerButton(MouseEvent mouseEvent) {
        System.out.println(mouseEvent.getButton().name());
    }

    @FXML
    private void handleExitButton(MouseEvent mouseEvent) {
        Platform.exit();
        System.exit(0);
    }
}
