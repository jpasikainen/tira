package com.jpasikainen.tira.logic;

import com.jpasikainen.tira.gui.GameViewController;
import com.jpasikainen.tira.solver.Solver;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.Arrays;

/**
 * Game loop that is run every "frame". Passes data to the GUI.
 */
public class GameLoop extends AnimationTimer {
    private int score = 0;
    private boolean gameIsRunning = true;
    /**
     * How long previous tick took.
     */
    private long pastTick;
    /**
     * GameViewController.
     */
    private GameViewController gvc;
    /**
     * Scene.
     */
    private Scene scene;
    /**
     * Tiles.
     */
    private int[][] tiles;
    /**
     * To solve or not to solve.
     */
    private boolean solve;
    private int depth = 1;

    /**
     * Constructor.
     * @param gvc
     * @param scene
     */
    public GameLoop(GameViewController gvc, Scene scene, int depth) {
        // Set the variables
        this.gvc = gvc;
        this.scene = scene;
        this.depth = depth;
        this.tiles = new int[4][4];

        // Draw the graphics
        draw();

        // Spawn the first tile
        Board.spawnRandom(tiles);

        // Use either the solver or keyboard input from the user
        if (this.depth >= 0) {
            // Pass a clone of the tiles
            solve = true;
        } else {
            getInput();
        }

        // Start the "clock"
        start();
    }

    /**
     * Constructor for testing.
     * @param tiles to test
     */
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


    /**
     * Moves the tiles using Board utility and spawns a new tile if the move was successful.
     * @param key
     */
    public void moveTiles(KeyCode key) {
        int[] prevBoard = tilesToArray();
        Board.moveTiles(key, tiles);

        // Move moved tiles to some direction
        if (!Arrays.equals(prevBoard, tilesToArray()) || Board.getFreeTiles(tiles).size() == 1) {
            Board.spawnRandom(tiles);
        }
    }

    /**
     * Run each animation frame.
     * @param l
     */
    @Override
    public void handle(long l) {
        if (!gameIsRunning) {
            return;
        }

        double delta = ((l - pastTick) / 1e9);
        update(delta);
        pastTick = l;
    }

    /**
     * Update loop run every frame.
     * @param delta time
     */
    private float t = 0;
    private void update(double delta) {
        t += delta;
        if (t >= 0.1) {
            if (solve) {
                KeyCode bestMove = Solver.solve(this.tiles, depth);
                if (bestMove == null) {
                    gameIsRunning = false;
                }
                moveTiles(bestMove);
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
            moveTiles(event.getCode());
        });
    }
}
