package com.jpasikainen.tira.gui;


import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GameViewController {
    @FXML
    private TilePane board;

    @FXML
    public void initialize() {
        // Draw the board
        board.setPrefColumns(4);
        for(int i = 0; i < 16; i++) {
            StackPane group = new StackPane();
            Rectangle rect = new Rectangle();
            rect.setHeight(100);
            rect.setWidth(100);
            rect.setStroke(Color.DARKGRAY);
            rect.setFill(Color.LIGHTGRAY);
            rect.setArcHeight(5);
            rect.setArcWidth(5);
            Text text = new Text("");
            text.setFont(Font.font(24));
            group.getChildren().addAll(rect, text);
            board.getChildren().add(group);
        }
    }

    public void drawTiles(int[] tiles) {
        for(int i = 0; i < tiles.length; i++) {
            int tile = tiles[i];
            StackPane group = (StackPane) board.getChildren().get(i);
            Rectangle rect = (Rectangle) group.getChildren().get(0);
            if(tile == 0) rect.setFill(Color.DARKGRAY);
            else if(tile == 2) rect.setFill(Color.LIGHTYELLOW);
            else if(tile == 4) rect.setFill(Color.YELLOW);
            else if(tile == 8) rect.setFill(Color.ORANGE);
            else if(tile == 16) rect.setFill(Color.DARKORANGE);
            else if(tile == 32) rect.setFill(Color.ORANGERED);
            else if(tile == 64) rect.setFill(Color.RED);
            else if(tile == 128) rect.setFill(Color.DARKRED);
            else if(tile == 256) rect.setFill(Color.MEDIUMPURPLE);
            else if(tile == 512) rect.setFill(Color.PURPLE);
            else if(tile == 1024) rect.setFill(Color.DARKBLUE);
            else if(tile == 2048) rect.setFill(Color.GREEN);
            Text text = (Text) group.getChildren().get(1);
            String ts = tile == 0 ? "" : Integer.toString(tile);
            text.setText(ts);
        }
    }
}