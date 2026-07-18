package no.lukew.connect4.ui;

import javafx.application.Application;import javafx.scene.Scene;import javafx.scene.control.Label;import javafx.scene.layout.StackPane;import javafx.stage.Stage;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application {
    static void main() {
        launch();
    }
    @Override
    public void start(Stage stage) throws Exception {
        Label l = new Label("Hello, JavaFX");
        Scene scene = new Scene(new StackPane(l), 640, 480);
        stage.setScene(scene);
        stage.setTitle("Connect 4 UI");
        stage.show();
    }
}
