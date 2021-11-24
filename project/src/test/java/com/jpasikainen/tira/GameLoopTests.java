package com.jpasikainen.tira;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jpasikainen.tira.logic.GameLoop;
import com.jpasikainen.tira.util.Solver;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameLoopTests {
    private int[][] tiles;
    private GameLoop gl;

    @BeforeEach
    public void init() {
        tiles = new int[4][4];
        this.gl = new GameLoop(tiles);
    }

    private int emptyTiles() {
        int emptyTiles = 0;
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                if (tiles[y][x] == 0) {
                    emptyTiles++;
                }
            }
        }
        return emptyTiles;
    }

    @Test
    void testInitCorrect() {
        assertEquals(emptyTiles(), 15);
    }

}
