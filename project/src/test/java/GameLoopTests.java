import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jpasikainen.tira.Board;
import com.jpasikainen.tira.GameLoop;
import com.jpasikainen.tira.GameViewController;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameLoopTests {
    private Board board;
    private int[][] tiles;
    private GameLoop gl;

    @BeforeEach
    public void init() {
        tiles = new int[4][4];
        this.gl = new GameLoop(tiles);
        this.board = new Board();
    }

    private int emptyTiles() {
        int emptyTiles = 0;
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                if (tiles[y][x] == 0) {
                    emptyTiles++;
                }
            }
        }
        return emptyTiles;
    }

    @Test
    void testInitCorrect() {
        assertEquals(emptyTiles(), 15);
    }

    @Test
    void testMovingAround() {
        gl.moveTiles(KeyCode.UP);
        gl.moveTiles(KeyCode.DOWN);
        gl.moveTiles(KeyCode.LEFT);
        gl.moveTiles(KeyCode.RIGHT);
        assertEquals(emptyTiles() < 15, true);
    }
}
