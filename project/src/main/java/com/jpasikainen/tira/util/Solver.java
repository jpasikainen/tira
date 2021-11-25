package com.jpasikainen.tira.util;

import com.jpasikainen.tira.logic.GameLoop;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Arrays;

public class Solver {
    private static final int[][] weightedTiles = {{15,14,13,12}, {8,9,10,11}, {7,6,5,4}, {0,1,2,3}}; //{{6,5,4,3}, {5,4,3,2}, {4,3,2,1}, {3,2,1,0}};//
    private static final KeyCode[] moves = {KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP, KeyCode.DOWN};

    private GameLoop gl;

    public Solver(GameLoop gl) {
        this.gl = gl;
    }

    /**
     * Solve the given board.
     * @param tiles is the board to solve
     * @return true if there was something to solve, otherwise false
     */
    public static KeyCode solve(int[][] tiles, int depth) {
        AlphaKey<Double, KeyCode> res = expectiMiniMax(tiles, depth, true);
        return res.key;
    }

    /**
     * Maximize player's score.
     * @param tiles
     * @param depth
     * @return alpha value and the best move.
     */
    private static AlphaKey<Double, KeyCode> playerMax(int[][] tiles, int depth) {
        double alpha = -1.0;

        // Simulate moves
        KeyCode bestMove = null;
        for (KeyCode move : moves) {
            int[][] simulatedTiles = Arrays.stream(tiles).map(int[]::clone).toArray(int[][]::new);
            Board.moveTiles(move, simulatedTiles);

            // Board changed after the move
            if (Arrays.deepEquals(simulatedTiles, tiles)) {
                continue;
            }

            double newAlpha = expectiMiniMax(simulatedTiles, depth - 1, false).alpha;
            if (newAlpha > alpha) {
                alpha = newAlpha;
                bestMove = move;
            }
        }

        return new AlphaKey(alpha, bestMove);
    }

    /**
     * Expecti board's score.
     * @param tiles
     * @param depth
     * @return alpha value and null move.
     */
    private static AlphaKey<Double, KeyCode> expectiBoard(int[][] tiles, int depth) {
        double alpha = -1.0;
        // Simulate all free tiles as 2s or 4s
        ArrayList<int[]> freeTiles = Board.getFreeTiles(tiles);
        for (int[] tile : freeTiles) {
            // Add 2
            int[][] simulatedTiles = Arrays.stream(tiles).map(int[]::clone).toArray(int[][]::new);
            simulatedTiles[tile[0]][tile[1]] = 2;
            double newAlpha = expectiMiniMax(simulatedTiles, depth - 1, true).alpha;
            if (newAlpha != -1.0) {
                alpha += 0.9 * newAlpha;
            }

            // Add 4
            simulatedTiles = Arrays.stream(tiles).map(int[]::clone).toArray(int[][]::new);
            simulatedTiles[tile[0]][tile[1]] = 4;
            newAlpha = expectiMiniMax(simulatedTiles, depth - 1, true).alpha;
            if (newAlpha != -1.0) {
                alpha += 0.1 * newAlpha;
            }
        }
        return new AlphaKey(alpha, null);
    }
    private static int dep = 0;
    private static AlphaKey<Double, KeyCode> expectiMiniMax(int[][] tiles, int depth, boolean playerTurn) {
        if (depth == 0) {
            return new AlphaKey(heuristicValue(tiles), null);
        }
        if (playerTurn) {
            return playerMax(tiles, depth);
        }
        dep = depth;
        return expectiBoard(tiles, depth);
    }

    private static double heuristicValue(int[][] tiles) {
        // Evaluate the board's score
        double score = 0.0;
        int freeTiles = Board.getFreeTiles(tiles).size();
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                // Weight matrix
                score += tiles[y][x] * weightedTiles[y][x];
                // Largest value on top-left
                score += maxTilePosition(tiles);
                // Empty tiles
                score *= freeTiles;
                // Monotonicity
                score += monotonicity(tiles);
                // Smoothness
                score += smoothness(tiles);
            }
        }
        return score;
    }

    private static int maxTilePosition(int[][] tiles) {
        int maxValue = tiles[0][0];
        for (int j = 0; j < tiles.length; j++) {
            for (int i = 0; i < tiles[j].length; i++) {
                if (tiles[j][i] > maxValue) {
                    maxValue = tiles[j][i];
                }
            }
        }
        if (tiles[0][0] == maxValue) {
            return maxValue;
        }
        return -maxValue;
    }

    private static int monotonicity(int[][] tiles) {
        int mono = 0;

        for (int[] row : tiles) {
            int diff = row[0] - row[1];
            for (int x = 0; x < tiles.length - 1; x++) {
                if ((row[x] - row[x + 1]) * diff <= 0) {
                    mono += 1;
                }
                diff = row[x] - row[x + 1];
            }
        }

        for (int x = 0; x < tiles.length; x++) {
            int diff = tiles[0][x] - tiles[1][x];
            for (int y = 0; y < tiles.length - 1; y++) {
                if ((tiles[y][x] - tiles[y + 1][x]) * diff <= 0) {
                    mono += 1;
                }
                diff = tiles[y][x] - tiles[y + 1][x];
            }
        }

        return mono;
    }

    private static int smoothness(int[][] tiles) {
        int smoothness = 0;

        for (int[] row : tiles) {
            for (int y = 0; y < tiles.length - 1; y++) {
                smoothness += Math.abs(row[y] - row[y + 1]);
            }
        }

        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles.length - 1; y++) {
                smoothness += Math.abs(tiles[y][x] - tiles[y + 1][x]);
            }
        }

        return smoothness;
    }
}
