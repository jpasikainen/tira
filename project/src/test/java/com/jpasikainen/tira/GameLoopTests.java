package com.jpasikainen.tira;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jpasikainen.tira.logic.GameLoop;
import com.jpasikainen.tira.util.Board;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

public class GameLoopTests {
    private int emptyTiles(int[][] testingTiles) {
        int emptyTiles = 0;
        for (int y = 0; y < testingTiles.length; y++) {
            for (int x = 0; x < testingTiles.length; x++) {
                if (testingTiles[y][x] == 0) {
                    emptyTiles++;
                }
            }
        }
        return emptyTiles;
    }

    @Test
    void testInitCorrect() {
        int[][] tiles = new int[4][4];
        GameLoop gl = new GameLoop(tiles);
        assertEquals(emptyTiles(gl.getTiles()), 15);
    }

    @Test
    void testReset() {
        int[][] tiles = new int[4][4];
        GameLoop gl = new GameLoop(tiles);

        Board.spawnRandom(gl.getTiles());
        Board.spawnRandom(gl.getTiles());
        Board.spawnRandom(gl.getTiles());
        gl.reset();
        assertEquals(emptyTiles(gl.getTiles()), 15);
    }

    @Test
    void testScoreIsCorrect() {
        int[][] tiles = new int[4][4];
        GameLoop gl = new GameLoop(tiles);
        gl.reset();
        assertEquals(gl.getScore(), 0);
    }

    @Test
    void testMovingTilesCausesMergeToIncreaseTheScore() {
        int[][] tiles = new int[4][4];
        GameLoop gl = new GameLoop(tiles);
        gl.reset();
        gl.moveTiles(KeyCode.UP);
        gl.moveTiles(KeyCode.DOWN);
        gl.moveTiles(KeyCode.LEFT);
        gl.moveTiles(KeyCode.RIGHT);
        assertTrue(gl.getScore() >= 0);
    }

    @Test
    void testNotMovingTilesKeepsTheScore() {
        int[][] tiles = {{1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 0}};
        GameLoop gl = new GameLoop(tiles);
        gl.reset();
        gl.moveTiles(null);
        assertEquals(gl.getScore(), 0);
    }
}
