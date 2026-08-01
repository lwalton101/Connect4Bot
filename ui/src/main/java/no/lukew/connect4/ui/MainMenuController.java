package no.lukew.connect4.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void handleSingleplayerButton() throws IOException {
        startGame(GameMode.Singleplayer);
    }

    @FXML
    private void handleMultiplayerButton() throws IOException {
        startGame(GameMode.Multiplayer);
    }

    private void startGame(GameMode gameMode) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Game.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        GameController controller = loader.getController();
        controller.setGameMode(gameMode);

        Scene scene = new Scene(root, 800,600);

        Stage stage = new Stage();
        stage.setScene(scene);


        stage.show();
    }

    @FXML
    private void handleExitButton() {
        Platform.exit();
        System.exit(0);
    }
}
