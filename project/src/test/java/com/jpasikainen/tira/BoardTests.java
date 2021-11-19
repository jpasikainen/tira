package com.jpasikainen.tira;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.jpasikainen.tira.logic.Board;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTests {
    private final Board board = new Board();
    private int[][] tiles;

    @BeforeEach
    void init() {
        tiles = new int[4][4];
    }

    @Test
    void boardEmptyOnInit() {
        int emptyTiles = 0;
        for(int y = 0; y < tiles.length; y++) {
            for(int x = 0; x < tiles.length; x++) {
                if(tiles[y][x] == 0) emptyTiles++;
            }
        }
        assertEquals(emptyTiles, 16);
    }

    @Test
    void testTilesPersistAfterMove() {
        board.spawnRandom(tiles);
        board.moveTiles(KeyCode.UP, tiles);
        int emptyTiles = 0;
        for(int y = 0; y < tiles.length; y++) {
            for(int x = 0; x < tiles.length; x++) {
                if(tiles[y][x] == 0) emptyTiles++;
            }
        }
        assertEquals(emptyTiles, 15);
    }

    @Test
    void testRightMove() {
        tiles[0][0] = 1;
        board.moveTiles(KeyCode.RIGHT, tiles);
        assertEquals(tiles[0][3], 1);
    }

    @Test
    void testRightMoveMerge() {
        tiles[0][0] = 1;
        tiles[0][1] = 1;
        board.moveTiles(KeyCode.RIGHT, tiles);
        assertEquals(tiles[0][3], 2);
    }

    @Test
    void testLeftMove() {
        tiles[0][3] = 1;
        board.moveTiles(KeyCode.LEFT, tiles);
        assertEquals(tiles[0][0], 1);
    }

    @Test
    void testLeftMoveMerge() {
        tiles[0][3] = 1;
        tiles[0][2] = 1;
        board.moveTiles(KeyCode.LEFT, tiles);
        assertEquals(tiles[0][0], 2);
    }

    @Test
    void testUpMove() {
        tiles[3][0] = 1;
        board.moveTiles(KeyCode.UP, tiles);
        assertEquals(tiles[0][0], 1);
    }

    @Test
    void testUpMoveMerge() {
        tiles[3][0] = 1;
        tiles[2][0] = 1;
        board.moveTiles(KeyCode.UP, tiles);
        assertEquals(tiles[0][0], 2);
    }

    @Test
    void testDownMove() {
        tiles[0][0] = 1;
        board.moveTiles(KeyCode.DOWN, tiles);
        assertEquals(tiles[3][0], 1);
    }

    @Test
    void testDownMoveMerge() {
        tiles[0][0] = 1;
        tiles[1][0] = 1;
        board.moveTiles(KeyCode.DOWN, tiles);
        assertEquals(tiles[3][0], 2);
    }
}