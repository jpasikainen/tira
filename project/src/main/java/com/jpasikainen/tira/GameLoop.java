package com.jpasikainen.tira;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.Arrays;

/**
 * Game loop that is run every "frame". Passes data to the GUI.
 */
public class GameLoop extends AnimationTimer {
    private long pastTick;
    private Solver solver;
    private GameViewController gvc;
    private Scene scene;
    private int[][] tiles;
    private boolean solve = true;

    public GameLoop(GameViewController gvc, Scene scene) {
        // Set the variables
        this.gvc = gvc;
        this.scene = scene;
        this.tiles = new int[4][4];

        // Draw the graphics
        draw();

        // Spawn the first tile
        Board.spawnRandom(tiles);

        // Use either the solver or keyboard input from the user
        if (solve) {
            // Pass a clone of the tiles
            Solver solver = new Solver(this, this.tiles);
            this.solver = solver;
        } else {
            getInput();
        }

        // Start the "clock"
        start();
    }

    public GameLoop(int[][] tiles) {
        this.tiles = tiles;

        // Spawn the first tile
        Board.spawnRandom(this.tiles);
    }

    /**
     * Convert tiles to a 1D array.
     * @return 1D array of tiles
     */
    private int[] tilesToArray() {
        int[] resArr = new int[16];
        int index = 0;
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                resArr[index] = tiles[y][x];
                index++;
            }
        }
        return resArr;
    }


    public void moveTiles(KeyCode key, int[][] tiles) {
        int[] prevBoard = tilesToArray();
        Board.moveTiles(key, tiles);

        // Move moved tiles to some direction
        if(!Arrays.equals(prevBoard, tilesToArray())) {
            Board.spawnRandom(tiles);
        }
    }

    public void applySolverTiles(int[][] tiles) {
        Board.spawnRandom(tiles);
        this.tiles = tiles;
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
        if(t >= 0.1) {
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

    /**
     * Get input from the player.
     */
    private void getInput() {
        scene.setOnKeyPressed(event -> {
            moveTiles(event.getCode(), tiles);
        });
    }
}
