package com.jpasikainen.tira.solver;

import com.jpasikainen.tira.logic.Board;
import com.jpasikainen.tira.logic.GameLoop;
import javafx.scene.input.KeyCode;
import javafx.util.Pair;

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
        Pair<Double, KeyCode> res = expectiMiniMax(tiles, null, depth, true);
        return res.getValue();
    }

    private static Pair<Double, KeyCode> expectiMiniMax(int[][] tiles, KeyCode key, int depth, boolean playerTurn) {
        double alpha = 0;
        if (depth == 0) {
            return new Pair(heuristicValue(tiles), key);
        }
        if (playerTurn) {
            // Simulate moves
            for (KeyCode move : moves) {
                int[][] simulatedTiles = Arrays.stream(tiles).map(int[]::clone).toArray(int[][]::new);
                Board.moveTiles(move, simulatedTiles);

                // Board changed after the move
                if (Arrays.deepEquals(simulatedTiles, tiles)) {
                    continue;
                }

                Pair<Double, KeyCode> res = expectiMiniMax(simulatedTiles, move, depth - 1, false);
                double newAlpha = res.getKey();
                if (newAlpha > alpha) {
                    alpha = newAlpha;
                    key = res.getValue();
                }
            }
        } else {
            // Simulate all free tiles as 2s or 4s
            ArrayList<Pair<Integer,Integer>> freeTiles = Board.getFreeTiles(tiles);
            for (Pair<Integer, Integer> tile : freeTiles) {
                // Add 2
                int[][] simulatedTiles = Arrays.stream(tiles).map(int[]::clone).toArray(int[][]::new);
                simulatedTiles[tile.getKey()][tile.getValue()] = 2;
                alpha += 0.9 * expectiMiniMax(simulatedTiles, key, depth - 1, true).getKey();

                // Add 4
                simulatedTiles = Arrays.stream(tiles).map(int[]::clone).toArray(int[][]::new);
                simulatedTiles[tile.getKey()][tile.getValue()] = 4;
                alpha += 0.1 * expectiMiniMax(simulatedTiles, key, depth - 1, true).getKey();
            }
            //alpha /= freeTiles.size();
        }
        //System.out.println(alpha);
        //Board.printBoard(node.tiles);
        //System.out.println("--------");
        //System.out.println(key);
        return new Pair(alpha, key);
    }

    private static double heuristicValue(int[][] tiles) {
        // Evaluate the board's score
        double score = 0;
        int freeTiles = Board.getFreeTiles(tiles).size();
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                score += tiles[y][x] * weightedTiles[y][x] * freeTiles;//Math.pow(tiles[y][x], weightedTiles[y][x]) * freeTiles;
            }
        }
        return score;
    }
}
