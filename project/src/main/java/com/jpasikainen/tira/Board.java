package com.jpasikainen.tira;

import javafx.scene.input.KeyCode;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Board {
    private int[][] tiles = new int[4][4];
    private int score = 0;

    public Board() {
        Arrays.stream(tiles).forEach(tile -> Arrays.fill(tile, 0));
        System.out.println(tiles[0][0]);
        spawnRandom();
        printBoard();
    }

    private void printBoard() {
        for(int y = 0; y < tiles.length; y++) {
            for(int x = 0; x < tiles.length; x++) {
                System.out.print(tiles[y][x]);
            }
            System.out.println();
        }
    }

    public int[][] getTiles() {
        return tiles;
    }

    public void spawnRandom() {
        ArrayList<Pair<Integer, Integer>> freeIntegers = getFreeTiles();
        Pair<Integer, Integer> tile = freeIntegers.get(ThreadLocalRandom.current().nextInt(0, freeIntegers.size()));
        int value = 2;
        if(ThreadLocalRandom.current().nextInt(0, 10) == 9) value = 4;
        tiles[tile.getKey()][tile.getValue()] = value;
    }

    private ArrayList<Pair<Integer, Integer>> getFreeTiles() {
        ArrayList<Pair<Integer, Integer>> freeIntegers = new ArrayList<>();
        for(int y = 0; y < tiles.length; y++) {
            for(int x = 0; x < tiles.length; x++) {
                if(tiles[y][x] == 0) freeIntegers.add(new Pair(y, x));
            }
        }
        return freeIntegers;
    }

    public void moveTiles(KeyCode key) {
        // No free tiles
        if(getFreeTiles().size() == 0) return;

        // Move
        if(key == KeyCode.RIGHT) {
            for(int y = 0; y < tiles.length; y++) {
                for (int x = 3; x >= 0; x--) {
                    int tile = tiles[y][x];
                    // Don't move empty tiles
                    if(tile == 0) continue;

                    // Find the next free spot or merge
                    int value = tiles[y][x];
                    tiles[y][x] = 0;
                    int newX = x + 1;
                    while(newX < 4 && (tiles[y][newX] == 0 || tiles[y][newX] == tile)) {
                        // Merge
                        if(tiles[y][newX] == tile) {
                            value = tile * 2;
                            newX++;
                            break;
                        }
                        newX++;
                    }
                    tiles[y][newX-1] = value;

                }
            }
        }
        if(key == KeyCode.LEFT) {
            for(int y = 0; y < tiles.length; y++) {
                for (int x = 0; x <= 3; x++) {
                    int tile = tiles[y][x];
                    // Don't move empty tiles
                    if(tile == 0) continue;

                    // Find the next free spot or merge
                    int value = tiles[y][x];
                    tiles[y][x] = 0;
                    int newX = x - 1;
                    while(newX >= 0 && (tiles[y][newX] == 0 || tiles[y][newX] == tile)) {
                        // Merge
                        if(tiles[y][newX] == tile) {
                            value = tile * 2;
                            newX--;
                            break;
                        }
                        newX--;
                    }
                    tiles[y][newX+1] = value;

                }
            }
        }

        if(key == KeyCode.DOWN) {
            for(int y = 3; y >= 0; y--) {
                for (int x = 0; x < tiles.length; x++) {
                    int tile = tiles[y][x];
                    // Don't move empty tiles
                    if(tile == 0) continue;

                    // Find the next free spot or merge
                    int value = tiles[y][x];
                    tiles[y][x] = 0;
                    int newY = y + 1;
                    while(newY < 4 && (tiles[newY][x] == 0 || tiles[newY][x] == tile)) {
                        // Merge
                        if(tiles[newY][x] == tile) {
                            value = tile * 2;
                            newY++;
                            break;
                        }
                        newY++;
                    }
                    tiles[newY-1][x] = value;

                }
            }
        }
        if(key == KeyCode.UP) {
            for(int y = 0; y <= 3; y++) {
                for (int x = 0; x < tiles.length; x++) {
                    int tile = tiles[y][x];
                    // Don't move empty tiles
                    if(tile == 0) continue;

                    // Find the next free spot or merge
                    int value = tiles[y][x];
                    tiles[y][x] = 0;
                    int newY = y - 1;
                    while(newY >= 0 && (tiles[newY][x] == 0 || tiles[newY][x] == tile)) {
                        // Merge
                        if(tiles[newY][x] == tile) {
                            value = tile * 2;
                            newY--;
                            break;
                        }
                        newY--;
                    }
                    tiles[newY+1][x] = value;

                }
            }
        }
        printBoard();
    }
}
