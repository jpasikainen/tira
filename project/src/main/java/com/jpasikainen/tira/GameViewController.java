package com.jpasikainen.tira;


import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameViewController {
    @FXML
    private TilePane board;

    @FXML
    public void initialize() {
        board.setPrefColumns(4);
        for(int i = 0; i < 16; i++) {
            Rectangle rect = new Rectangle();
            rect.setHeight(100);
            rect.setWidth(100);
            rect.setStroke(Color.DARKGRAY);
            rect.setFill(Color.LIGHTGRAY);
            rect.setArcHeight(5);
            rect.setArcWidth(5);
            board.getChildren().add(rect);
        }
    }
}