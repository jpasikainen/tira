package com.jpasikainen.tira;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Solver {
    GameLoop gl;
    Board board;
    Node root = new Node();

    final int[][] weightedTiles = {{6,5,4,3}, {5,4,3,2}, {4,3,2,1}, {3,2,1,0}};//{{15,14,13,12}, {8,9,10,11}, {7,6,5,4}, {0, 1, 2, 3}};
    final KeyCode[] moves = {KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP, KeyCode.DOWN};
    
    public Solver(GameLoop gl, Board board) {
        this.gl = gl;
        this.board = board;

        // Deep clone the board
        root.board = Arrays.stream(board.getTiles()).map(int[]::clone).toArray(int[][]::new);
    }

    public void solve() {
        System.out.println(expectiminimax(root, 4, false));
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
            for (Node child : node.children) {
                alpha += probabilityOfTileToBeChosen() * expectiminimax(child, depth - 1, true);
            }
        } else {
            KeyCode bestMove = null;
            for (KeyCode move : moves) {
                // Make a deep clone to not affect the original board
                Board testingBoard = new Board(Arrays.stream(node.board).map(int[]::clone).toArray(int[][]::new));
                testingBoard.moveTiles(move);

                // Move didn't change the board, ignore
                if (Arrays.deepEquals(testingBoard.getTiles(), board.getTiles())) {
                    continue;
                }

                // Add children to the node
                Node child = new Node();
                child.board = testingBoard.getTiles();
                node.children.add(child);

                for(Node childL : node.children) {
                    alpha = Math.max(alpha, expectiminimax(childL, depth - 1, false));
                }
            }
        }
        return alpha;
    }

    private float heuristicValue(Node node) {
        int score = 0;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                score += Math.pow(node.board[y][x], weightedTiles[y][x]);
            }
        }
        return score;
    }

    private boolean tilesEqual(int[][] a, int[][] b) {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (a[y][x] != b[y][x]) {
                    return false;
                }
            }
        }
        return true;
    }

    private float probabilityOfTileToBeChosen() {
        float value = 2.0f;
        if (ThreadLocalRandom.current().nextInt(0, 10) == 9) {
            value = 4.0f;
        }
        value *= 1.0f / board.getFreeTiles().size();
        return value;
    }

    private static class Node {
        private int[][] board;
        private ArrayList<Node> children = new ArrayList<>();
    }
}
