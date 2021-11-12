package com.jpasikainen.tira;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;

/**
 * Game loop that is run every "frame". Passes data to the GUI.
 */
public class GameLoop extends AnimationTimer {
    private long pastTick;
    private Board board;

    public GameLoop() {
        board = new Board();
        board.spawnRandom();
    }

    private ArrayList<Integer> convertToArrayList() {
        ArrayList<Integer> resArr = new ArrayList<>();
        int[][] tiles = board.getTiles();
        for(int y = 0; y < tiles.length; y++)
            for(int x = 0; x < tiles.length; x++)
                resArr.add(tiles[y][x]);
        return resArr;
    }

    public ArrayList<Integer> getTiles() {
        return convertToArrayList();
    }

    public ArrayList<Integer> moveTiles(KeyCode key) {
        ArrayList<Integer> prev = getTiles();
        board.moveTiles(key);
        if(!prev.equals(getTiles())) board.spawnRandom();
        return convertToArrayList();
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
    private void update(double delta) {
    }
}
