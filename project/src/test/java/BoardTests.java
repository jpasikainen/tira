import static org.junit.jupiter.api.Assertions.assertEquals;
import com.jpasikainen.tira.Board;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

public class BoardTests {
    private final Board board = new Board();

    @Test
    void boardEmptyOnInit() {
        int emptyTiles = 0;
        for(int y = 0; y < board.getTiles().length; y++) {
            for(int x = 0; x < board.getTiles().length; x++) {
                if(board.getTiles()[y][x] == 0) emptyTiles++;
            }
        }
        assertEquals(emptyTiles, 16);
    }

    @Test
    void testTilesPersistAfterMove() {
        board.spawnRandom();
        board.moveTiles(KeyCode.UP);
        int emptyTiles = 0;
        for(int y = 0; y < board.getTiles().length; y++) {
            for(int x = 0; x < board.getTiles().length; x++) {
                if(board.getTiles()[y][x] == 0) emptyTiles++;
            }
        }
        assertEquals(emptyTiles, 15);
    }

    @Test
    void testRightMove() {
        int[][] tiles = new int[4][4];
        tiles[0][0] = 1;
        Board tBoard = new Board(tiles);
        tBoard.moveTiles(KeyCode.RIGHT);
        assertEquals(tBoard.getTiles()[0][3], 1);
    }

    @Test
    void testRightMoveMerge() {
        int[][] tiles = new int[4][4];
        tiles[0][0] = 1;
        tiles[0][1] = 1;
        Board tBoard = new Board(tiles);
        tBoard.moveTiles(KeyCode.RIGHT);
        assertEquals(tBoard.getTiles()[0][3], 2);
    }

    @Test
    void testLeftMove() {
        int[][] tiles = new int[4][4];
        tiles[0][3] = 1;
        Board tBoard = new Board(tiles);
        tBoard.moveTiles(KeyCode.LEFT);
        assertEquals(tBoard.getTiles()[0][0], 1);
    }

    @Test
    void testLeftMoveMerge() {
        int[][] tiles = new int[4][4];
        tiles[0][3] = 1;
        tiles[0][2] = 1;
        Board tBoard = new Board(tiles);
        tBoard.moveTiles(KeyCode.LEFT);
        assertEquals(tBoard.getTiles()[0][0], 2);
    }

    @Test
    void testUpMove() {
        int[][] tiles = new int[4][4];
        tiles[3][0] = 1;
        Board tBoard = new Board(tiles);
        tBoard.moveTiles(KeyCode.UP);
        assertEquals(tBoard.getTiles()[0][0], 1);
    }

    @Test
    void testUpMoveMerge() {
        int[][] tiles = new int[4][4];
        tiles[3][0] = 1;
        tiles[2][0] = 1;
        Board tBoard = new Board(tiles);
        tBoard.moveTiles(KeyCode.UP);
        assertEquals(tBoard.getTiles()[0][0], 2);
    }

    @Test
    void testDownMove() {
        int[][] tiles = new int[4][4];
        tiles[0][0] = 1;
        Board tBoard = new Board(tiles);
        tBoard.moveTiles(KeyCode.DOWN);
        assertEquals(tBoard.getTiles()[3][0], 1);
    }

    @Test
    void testDownMoveMerge() {
        int[][] tiles = new int[4][4];
        tiles[0][0] = 1;
        tiles[1][0] = 1;
        Board tBoard = new Board(tiles);
        tBoard.moveTiles(KeyCode.DOWN);
        assertEquals(tBoard.getTiles()[3][0], 2);
    }
}
