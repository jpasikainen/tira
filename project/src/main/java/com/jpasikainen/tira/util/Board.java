package com.jpasikainen.tira.util;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Utility class that requires tiles to be passed to every method.
 */
public final class Board {
    /**
     * Private constructor to prevent misusage.
     */
    private Board() {
    }

     /**
     * Print the game board as 4x4 grid.
      * @param tiles tiles
     */
    public static void printBoard(int[][] tiles) {
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                System.out.print(tiles[y][x]);
            }
            System.out.println();
        }
    }

    /**
     * Spawns a tile at a random position on the board.
     * Has either value of 2 or 4 with 10% and 90% chance respectively.
     * @param tiles tiles
     */
    public static void spawnRandom(int[][] tiles) {
        ArrayList<int[]> freeIntegers = getFreeTiles(tiles);
        int[] tile = freeIntegers.get(
                ThreadLocalRandom.current().nextInt(0, freeIntegers.size())
        );
        int value = 2;
        if (ThreadLocalRandom.current().nextInt(0, 10) == 9) {
            value = 4;
        }
        tiles[tile[0]][tile[1]] = value;
    }

    /**
     * Get the slots that do not contain any tiles.
     * @param tiles tiles
     * @return free slots as Pair(s) containing (y,x) coordinates.
     */
    public static ArrayList<int[]> getFreeTiles(int[][] tiles) {
        ArrayList<int[]> freeIntegers = new ArrayList<>();
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                if (tiles[y][x] == 0) {
                    freeIntegers.add(new int[]{y, x});
                }
            }
        }
        return freeIntegers;
    }

    /**
     * Flip tiles horizontally.
     * @param tiles tiles
     */
    private static void flipHorizontally(int[][] tiles) {
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles.length / 2; x++) {
                int temp = tiles[y][x];
                tiles[y][x] = tiles[y][tiles.length - x - 1];
                tiles[y][tiles.length - x - 1] = temp;
            }
        }
    }

    /**
     * Flip tiles vertically.
     * @param tiles tiles
     */
    private static void flipVertically(int[][] tiles) {
        for (int y = 0; y < tiles.length / 2; y++) {
            for (int x = 0; x < tiles.length; x++) {
                int temp = tiles[y][x];
                tiles[y][x] = tiles[tiles.length - y - 1][x];
                tiles[tiles.length - y - 1][x] = temp;
            }
        }
    }

    /**
     * Move the tiles.
     * Can be simplified further.
     * @param key code of the pressed key
     * @param tiles tiles
     */
    public static int moveTiles(final KeyCode key, int[][] tiles) {
        //System.out.println(key);
        int mergeScore = 0;

        // Move
        if (key == KeyCode.RIGHT || key == KeyCode.LEFT) {
            if (key == KeyCode.LEFT) {
                flipHorizontally(tiles);
            }
            for (int y = 0; y < tiles.length; y++) {
                for (int x = 3; x >= 0; x--) {
                    int tile = tiles[y][x];
                    // Don't move empty tiles
                    if (tile == 0) {
                        continue;
                    }

                    // Find the next free spot or merge
                    int value = tiles[y][x];
                    tiles[y][x] = 0;
                    int newX = x + 1;
                    while (newX < 4 && (tiles[y][newX] == 0 || tiles[y][newX] == tile)) {
                        // Merge
                        if (tiles[y][newX] == tile) {
                            mergeScore += value;
                            value = tile * 2;
                            newX++;
                            break;
                        }
                        newX++;
                    }
                    tiles[y][newX - 1] = value;

                }
            }
            if (key == KeyCode.LEFT) {
                flipHorizontally(tiles);
            }
        }

        if (key == KeyCode.DOWN || key == KeyCode.UP) {
            if (key == KeyCode.UP) {
                flipVertically(tiles);
            }
            for (int y = 3; y >= 0; y--) {
                for (int x = 0; x < tiles.length; x++) {
                    int tile = tiles[y][x];
                    // Don't move empty tiles
                    if (tile == 0) {
                        continue;
                    }

                    // Find the next free spot or merge
                    int value = tiles[y][x];
                    tiles[y][x] = 0;
                    int newY = y + 1;
                    while (newY < 4 && (tiles[newY][x] == 0 || tiles[newY][x] == tile)) {
                        // Merge
                        if (tiles[newY][x] == tile) {
                            mergeScore += value;
                            value = tile * 2;
                            newY++;
                            break;
                        }
                        newY++;
                    }
                    tiles[newY - 1][x] = value;

                }
            }
            if (key == KeyCode.UP) {
                flipVertically(tiles);
            }
        }

        return mergeScore;
    }
}
