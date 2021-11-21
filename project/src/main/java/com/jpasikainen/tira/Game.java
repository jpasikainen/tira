package com.jpasikainen.tira;

import com.jpasikainen.tira.gui.GameViewController;
import com.jpasikainen.tira.logic.GameLoop;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Game extends javafx.application.Application {
    private static int depth = -1;

    /**
     * Starts the application by first setting up graphics and then launching the game loop.
     * @param stage stage
     * @throws IOException Exception
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Setup the graphics
        System.out.println(GameViewController.class);
        FXMLLoader fxmlLoader = new FXMLLoader(GameViewController.class.getResource("game-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        GameViewController controller = fxmlLoader.getController();
        stage.setTitle("2048");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        // Start the game loop
        new GameLoop(controller, scene, depth);
    }

    /**
     * Launches the application.
     * @param args args
     */
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--depth") && i + 1 <= args.length - 1) {
                depth = Integer.valueOf(args[i+1]);
            }
        }
        launch();
    }
}
