package no.lukew.connect4.ui;

import javafx.application.Application;import javafx.scene.Scene;import javafx.scene.control.Label;import javafx.scene.layout.StackPane;import javafx.stage.Stage;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application {
    static void main() {
        launch();
    }
    @Override
    public void start(Stage stage) throws Exception {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        Scene scene = new Scene(new StackPane(l), 640, 480);
        stage.setScene(scene);
        stage.setTitle("Connect 4 UI");
        stage.show();
    }
}
