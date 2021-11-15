package com.jpasikainen.tira;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Game extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Load the graphics
        FXMLLoader fxmlLoader = new FXMLLoader(GameViewController.class.getResource("game-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        GameViewController controller = fxmlLoader.getController();
        stage.setTitle("2048");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        // Start the game loop
        new GameLoop(controller, scene);
    }

    public static void main(String[] args) {
        launch();
    }
}
