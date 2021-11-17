package com.jpasikainen.tira;

import javafx.scene.input.KeyCode;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class Solver {
    private Node root = new Node();

    private final int[][] weightedTiles = {{6,5,4,3}, {5,4,3,2}, {4,3,2,1}, {3,2,1,0}};//{{15,14,13,12}, {8,9,10,11}, {7,6,5,4}, {0, 1, 2, 3}};
    private final KeyCode[] moves = {KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP, KeyCode.DOWN};

    private static class Node {
        private int[][] tiles;
        private ArrayList<Node> children = new ArrayList<>();
    }

    public Solver(Board board) {
        root.tiles = board.getTiles();
    }

    public void solve() {
        System.out.println(expectiminimax(root, 1, false));
    }

    /**
     *
     * @param node
     * @param depth
     * @param player false = tile placer, true = tile mover
     * @return
     */
    private float expectiminimax(Node node, int depth, boolean player) {
        float alpha = 0;
        if (depth == 0) {
            return heuristicValue(node);
        }
        if (!player) {
            // Create two new tiles on each empty cell with values 2 and 4
            for (Node child : node.children) {
                for (Pair<Integer, Integer> tile : getFreeTiles(child.tiles)) {
                    int[][] twoTiles = child.tiles;
                    twoTiles[tile.getKey()][tile.getValue()] = 2;
                    Node two = new Node();
                    two.tiles = twoTiles;

                    int[][] fourTiles = child.tiles;
                    fourTiles[tile.getKey()][tile.getValue()] = 4;
                    Node four = new Node();
                    four.tiles = fourTiles;
                }
                alpha += expectiminimax(child, depth - 1, true);
            }
        } else {
            for(Node childL : node.children) {
                alpha = Math.max(alpha, expectiminimax(childL, depth - 1, false));
                for (KeyCode move : moves) {
                    // Move didn't change the board, ignore
                    int[][] prevBoard = node.tiles;
                    board.moveTiles(move);

                    // Move moved tiles to some direction
                    if (!Arrays.equals(prevBoard, tilesToArray())) {
                        board.spawnRandom();
                    }

                    if (Arrays.deepEquals(testingBoard.getTiles(), board.getTiles())) {
                        continue;
                    }

                    // Add children to the node
                    Node child = new Node();
                    child.board = testingBoard.getTiles();
                    node.children.add(child);
                }
            }
        }
        return alpha;
    }

    private float heuristicValue(Node node) {
        // Evaluate the board's score
        float score = 0f;
        int[][] tiles = node.tiles;
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                score += Math.pow(tiles[y][x], weightedTiles[y][x]);
            }
        }
        return score;
    }

    /**
     * Get the slots that do not contain any tiles.
     * @return free slots as Pair(s) containing (y,x) coordinates.
     */
    private ArrayList<Pair<Integer, Integer>> getFreeTiles(int[][] tiles) {
        ArrayList<Pair<Integer, Integer>> freeIntegers = new ArrayList<>();
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                if (tiles[y][x] == 0) {
                    freeIntegers.add(new Pair(y, x));
                }
            }
        }
        return freeIntegers;
    }
}
