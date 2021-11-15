package com.jpasikainen.tira;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Game loop that is run every "frame". Passes data to the GUI.
 */
public class GameLoop extends AnimationTimer {
    private long pastTick;
    private Solver solver;
    private GameViewController gvc;
    private Scene scene;
    private Board board;
    private boolean solve = false;

    public GameLoop(GameViewController gvc, Scene scene) {
        // Set the variables
        this.gvc = gvc;
        this.scene = scene;
        this.board = new Board();

        // Spawn the first tile
        board.spawnRandom();

        // Draw the graphics
        draw();

        // Start the solver
        if (solve) {
            Solver solver = new Solver(this, board);
            this.solver = solver;
        } else {
            getInput();
        }

        // Start the "clock"
        start();
    }

    public GameLoop(Board board) {
        this.board = board;
        board.spawnRandom();
    }

    private int[] tilesToArray() {
        int[] resArr = new int[16];
        int[][] tiles = board.getTiles();
        int index = 0;
        for(int y = 0; y < tiles.length; y++) {
            for(int x = 0; x < tiles.length; x++) {
                resArr[index] = tiles[y][x];
                index++;
            }
        }
        return resArr;
    }

    public void moveTiles(KeyCode key) {
        int[] prevBoard = tilesToArray();
        board.moveTiles(key);

        // Move moved tiles to some direction
        if(!Arrays.equals(prevBoard, tilesToArray())) {
            board.spawnRandom();
        }
    }

    /**
     * Run each animation frame
     * @param l
     */
    @Override
    public void handle(long l) {
        double delta = ((l - pastTick) / 1e9);
        update(delta);
        pastTick = l;
    }

    /**
     * Update loop run every frame
     * @param delta time
     */
    private float t = 0;
    private void update(double delta) {
        t += delta;
        if(t >= 1) {
            if (solve) {
                solver.solve();
            }
            t = 0;
        }
        draw();
    }

    /**
     * Draw the graphics.
     */
    private void draw() {
        gvc.drawTiles(tilesToArray());
    }

    private void getInput() {
        scene.setOnKeyPressed(event -> {
            moveTiles(event.getCode());
        });
    }
}
