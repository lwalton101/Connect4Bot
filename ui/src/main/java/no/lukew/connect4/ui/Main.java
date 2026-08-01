package no.lukew.connect4.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;import javafx.scene.control.Label;import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application {
    static void main() {
        launch();
    }
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Connect 4");
        stage.setScene(scene);
        stage.show();
    }
}
