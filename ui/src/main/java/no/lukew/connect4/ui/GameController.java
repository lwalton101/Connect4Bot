package no.lukew.connect4.ui;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    public Label gameModeLabel;

    @FXML
    private GridPane board;

    private GameMode gameMode;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 6; col++) {

                StackPane cell = new StackPane();

                Circle piece = new Circle();
                piece.setFill(Color.WHITE);
                piece.setStroke(Color.BLACK);

                // Circle always fits the cell
                piece.radiusProperty().bind(
                        Bindings.min(
                                cell.widthProperty(),
                                cell.heightProperty()
                        ).divide(2.5)
                );

                cell.getChildren().add(piece);
                board.add(cell, col, row);
            }

            ChangeListener<Number> resizeListener = (obs, oldVal, newVal) -> updateCellSize();
            board.widthProperty().addListener(resizeListener);
            board.heightProperty().addListener(resizeListener);
        }
    }

    private void updateCellSize() {
        double hGaps = board.getHgap() * 5; // 5 gaps between 6 columns
        double vGaps = board.getVgap() * 6; // 6 gaps between 7 rows
        double cellSize = Math.min(
                (board.getWidth() - hGaps) / 6,
                (board.getHeight() - vGaps) / 7
        );

        for (ColumnConstraints cc : board.getColumnConstraints()) {
            cc.setPrefWidth(cellSize);
        }
        for (RowConstraints rc : board.getRowConstraints()) {
            rc.setPrefHeight(cellSize);
        }
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
        switch (gameMode) {
            case Singleplayer -> {
                this.gameModeLabel.setText("You are playing singleplayer");
            }
            case Multiplayer -> {
                this.gameModeLabel.setText("You are playing multiplayer");
            }
        }


    }
}
