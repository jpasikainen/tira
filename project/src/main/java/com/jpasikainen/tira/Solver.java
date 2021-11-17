package com.jpasikainen.tira;

import javafx.scene.input.KeyCode;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class Solver {
    private static class Node {
        private KeyCode move;
        private int[][] tiles;
        private ArrayList<Node> children = new ArrayList<>();
    }
    private Node root;
    private KeyCode bestMove;

    private final int[][] weightedTiles = {{6,5,4,3}, {5,4,3,2}, {4,3,2,1}, {3,2,1,0}};
    private final KeyCode[] moves = {KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP, KeyCode.DOWN};

    private GameLoop gl;

    public Solver(GameLoop gl, int[][] tiles) {
        this.gl = gl;
        this.root = new Node();
        this.root.tiles = tiles;
    }

    public void solve() {
        System.out.println(expectiMiniMax(this.root, 2, false));
        //board.moveTiles(bestMove, this.root.tiles);
        //gl.applySolverTiles(root.tiles);

        gl.moveTiles(bestMove, root.tiles);
        bestMove = null;
    }

    private float expectiMiniMax(Node node, int depth, boolean playerTurn) {
        float alpha = 0;
        if (depth == 0) {
            return heuristicValue(node);
        }
        if (playerTurn) {
            // Simulate moves
            for (KeyCode move : moves) {
                int[][] simulatedTiles = Arrays.stream(node.tiles).map(int[]::clone).toArray(int[][]::new);
                Board.moveTiles(move, simulatedTiles);

                // Board changed after the move
                if (Arrays.deepEquals(simulatedTiles, node.tiles)) {
                    continue;
                }

                Node child = new Node();
                child.tiles = simulatedTiles;
                child.move = move;
                node.children.add(child);
            }

            // Ascend by one
            for (Node child : node.children) {
                float newAlpha = expectiMiniMax(child, depth - 1, false);
                if (newAlpha > alpha) {
                    alpha = newAlpha;
                    bestMove = child.move;
                }
            }
        } else {
            // Simulate all free tiles as 2s or 4s
            for (Pair<Integer, Integer> tile : Board.getFreeTiles(node.tiles)) {
                int[][] simulatedTiles = Arrays.stream(node.tiles).map(int[]::clone).toArray(int[][]::new);

                // Add 2
                simulatedTiles[tile.getKey()][tile.getValue()] = 2;
                Node child = new Node();
                child.move = node.move;
                child.tiles = simulatedTiles;
                node.children.add(child);

                // Add 4
                simulatedTiles[tile.getKey()][tile.getValue()] = 4;
                child.tiles = simulatedTiles;
                node.children.add(child);
            }

            // Ascend by one
            for (Node child : node.children) {
                alpha = Math.max(alpha, expectiMiniMax(child, depth - 1, true));
            }
        }
        System.out.println(alpha);
        return alpha;
    }

    private float heuristicValue(Node node) {
        // Evaluate the board's score
        float score = 0f;
        int[][] tiles = node.tiles;
        //board.moveTiles(node.move, node.tiles);
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                score += Math.pow(tiles[y][x], weightedTiles[y][x]);
            }
        }
        return score;
    }
}