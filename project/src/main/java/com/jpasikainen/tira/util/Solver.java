package com.jpasikainen.tira.util;

import com.jpasikainen.tira.logic.GameLoop;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Arrays;

public class Solver {
    //private static final int[][] weightedTiles = {{15,14,13,12}, {8,9,10,11}, {7,6,5,4}, {0,1,2,3}}; //{{6,5,4,3}, {5,4,3,2}, {4,3,2,1}, {3,2,1,0}};//
    private static final int[][] weightedTilesHorizontal = {{15,14,13,12}, {8,9,10,11}, {7,6,5,4}, {0,1,2,3}};
    private static final int[][] weightedTilesVertical = {{15,8,7,0}, {14,9,6,1}, {13,10,5,2}, {12,11,4,3}};
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
        AlphaKey<Double, KeyCode> res = expectiMiniMax(tiles, depth, null,true);
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

            double newAlpha = expectiMiniMax(simulatedTiles, depth - 1, move, false).alpha;
            if (newAlpha > alpha) {
                alpha = newAlpha;
                bestMove = move;
            } else if (bestMove == null) {
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
    private static AlphaKey<Double, KeyCode> expectiBoard(int[][] tiles, int depth, KeyCode previousKey) {
        double alpha = -1.0;
        // Simulate all free tiles as 2s or 4s
        ArrayList<int[]> freeTiles = Board.getFreeTiles(tiles);
        for (int[] tile : freeTiles) {
            // Add 2
            int[][] simulatedTiles = Arrays.stream(tiles).map(int[]::clone).toArray(int[][]::new);
            simulatedTiles[tile[0]][tile[1]] = 2;
            double newAlpha = expectiMiniMax(simulatedTiles, depth - 1, previousKey, true).alpha;
            if (newAlpha != -1.0) {
                alpha += 0.9 * newAlpha;
            }

            // Add 4
            simulatedTiles = Arrays.stream(tiles).map(int[]::clone).toArray(int[][]::new);
            simulatedTiles[tile[0]][tile[1]] = 4;
            newAlpha = expectiMiniMax(simulatedTiles, depth - 1, previousKey, true).alpha;
            if (newAlpha != -1.0) {
                alpha += 0.1 * newAlpha;
            }
        }
        return new AlphaKey(alpha, previousKey);
    }

    private static AlphaKey<Double, KeyCode> expectiMiniMax(int[][] tiles, int depth, KeyCode previousKey, boolean playerTurn) {
        if (depth == 0) {
            return new AlphaKey(heuristicValue(tiles), previousKey);
        }
        if (playerTurn) {
            return playerMax(tiles, depth);
        }
        return expectiBoard(tiles, depth, previousKey);
    }

    private static double heuristicValue(int[][] tiles) {
        // Evaluate the board's score
        double score = 0.0;// maxTilePosition(tiles) + monotonicityHorizontal(tiles) + monotonicityVertical(tiles) + smoothnessVertical(tiles) + smoothnessHorizontal(tiles);
        //return score * weightScore(tiles, weightedTilesHorizontal) * weightScore(tiles, weightedTilesVertical);

        // Smoothness
        score += smoothness(tiles);

        // Decide which is the better snake
        double hor = monotonicityHorizontal(tiles);
        double vert = monotonicityVertical(tiles);
        if (hor < vert) {
            score += vert;
            score *= weightScore(tiles, weightedTilesVertical);
        } else {
            score += hor;
            score *= weightScore(tiles, weightedTilesHorizontal);
        }
        //System.out.println(hor + " vs " + vert);
        //System.out.println(score);
        return score;
        //return weightScore(tiles, weightedTilesHorizontal);
    }

    private static int weightScore(int[][] tiles, int[][] weightedTiles) {
        int score = 0;
        int freeTiles = Board.getFreeTiles(tiles).size();
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                // Weight matrix
                score += tiles[y][x] * weightedTiles[y][x];
            }
        }
        return score * freeTiles;
    }

    /**
     * Reward for having values increase or decrease in a snake-like pattern.
     * @param tiles
     * @return
     */
    private static int monotonicityHorizontal(int[][] tiles) {
        int monotonicity = 0;

        // Evaluate rows
        for (int[] row : tiles) {
            int diff = row[0] - row[1];
            for (int x = 0; x < tiles.length - 1; x++) {
                if ((row[x] - row[x + 1]) * diff <= 0) {
                    monotonicity += 1;
                }
                diff = row[x] - row[x + 1];
            }
        }

        return monotonicity;
    }

    private static int monotonicityVertical(int[][] tiles) {
        int monotonicity = 0;

        for (int x = 0; x < tiles.length; x++) {
            int diff = tiles[0][x] - tiles[1][x];
            for (int y = 0; y < tiles.length - 1; y++) {
                if ((tiles[y][x] - tiles[y + 1][x]) * diff <= 0) {
                    monotonicity += 1;
                }
                diff = tiles[y][x] - tiles[y + 1][x];
            }
        }

        return monotonicity;
    }

    private static int smoothness(int[][] tiles) {
        int smoothness = 0;
        for (int[] row : tiles) {
            for (int x = 0; x < tiles.length - 1; x++) {
                smoothness += Math.abs(row[x] - row[x + 1]);
            }
        }
        for (int y = 0; y < tiles.length - 1; y++) {
            for (int x = 0; x < tiles.length; x++) {
                smoothness += Math.abs(tiles[y][x] - tiles[y + 1][x]);
            }
        }
        return smoothness;
    }
}
