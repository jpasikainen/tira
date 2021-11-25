package com.jpasikainen.tira.gui;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameViewController {
    @FXML
    private TilePane board;

    @FXML
    private Text moveTime;

    @FXML
    private Text scoreText;

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
            if(tile == 0) rect.setFill(Color.LIGHTGRAY);
            else if(tile == 2) rect.setFill(Color.web("#eee4da"));
            else if(tile == 4) rect.setFill(Color.web("#ede0c8"));
            else if(tile == 8) rect.setFill(Color.web("#f2b179"));
            else if(tile == 16) rect.setFill(Color.web("#f59563"));
            else if(tile == 32) rect.setFill(Color.web("#f67c5f"));
            else if(tile == 64) rect.setFill(Color.web("#f65e3b"));
            else if(tile == 128) rect.setFill(Color.web("#edcf72"));
            else if(tile == 256) rect.setFill(Color.web("#edcc61"));
            else if(tile == 512) rect.setFill(Color.web("#edc850"));
            else if(tile == 1024) rect.setFill(Color.web("#edc53f"));
            else if(tile == 2048) rect.setFill(Color.web("#edc22e"));
            else rect.setFill(Color.web("#d6aa12"));
            Text text = (Text) group.getChildren().get(1);
            String ts = tile == 0 ? "" : Integer.toString(tile);
            text.setText(ts);
        }
    }

    /**
     * Update the time text on the GUI.
     * @param time ms
     */
    public void updateMoveTime(float time) {
        String timeText = String.format("Move took %.0f ms", time);
        moveTime.setText(timeText);
    }

    public void updateScoreText(int score) {
        String scoreString = String.format("Score: %d", score);
        scoreText.setText(scoreString);
    }
}