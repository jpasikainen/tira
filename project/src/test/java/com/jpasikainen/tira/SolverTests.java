package com.jpasikainen.tira;

import com.jpasikainen.tira.util.Solver;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.jpasikainen.tira.util.Board;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class SolverTests {
    @Test
    void testVariation1Depth1() {
        int[][] tiles = {{2, 2, 0, 0}, {0, 4, 0, 0}, {4, 2, 0, 0}, {0, 0, 0, 0}};
        KeyCode bestMove = Solver.solve(tiles, 1);
        assertEquals(bestMove, KeyCode.LEFT);
    }

    @Test
    void testVariation1Depth4() {
        int[][] tiles = {{2, 2, 0, 0}, {0, 4, 0, 0}, {4, 2, 0, 0}, {0, 0, 0, 0}};
        KeyCode bestMove = Solver.solve(tiles, 4);
        assertEquals(bestMove, KeyCode.UP);
    }

    @Test
    void testVariation1Depth6() {
        int[][] tiles = {{2, 2, 0, 0}, {0, 4, 0, 0}, {4, 2, 0, 0}, {0, 0, 0, 0}};
        KeyCode bestMove = Solver.solve(tiles, 4);
        assertEquals(bestMove, KeyCode.UP);
    }

    @Test
    void testVariation2Depth1() {
        int[][] tiles = {{2, 2, 0, 8}, {0, 4, 0, 0}, {4, 2, 0, 0}, {0, 0, 4, 4}};
        KeyCode bestMove = Solver.solve(tiles, 1);
        assertEquals(bestMove, KeyCode.LEFT);
    }

    @Test
    void testVariation2Depth4() {
        int[][] tiles = {{2, 2, 0, 8}, {0, 4, 0, 0}, {4, 2, 0, 0}, {0, 0, 4, 4}};
        KeyCode bestMove = Solver.solve(tiles, 4);
        assertEquals(bestMove, KeyCode.UP);
    }

    @Test
    void testVariation2Depth6() {
        int[][] tiles = {{2, 2, 0, 8}, {0, 4, 0, 0}, {4, 2, 0, 0}, {0, 0, 4, 4}};
        KeyCode bestMove = Solver.solve(tiles, 6);
        assertEquals(bestMove, KeyCode.LEFT);
    }
}
