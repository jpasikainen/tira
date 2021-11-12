import static org.junit.jupiter.api.Assertions.assertEquals;
import com.jpasikainen.tira.GameLoop;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

public class GameLoopTests {
    private final GameLoop gl = new GameLoop();

    @Test
    void testInitCorrect() {
        int emptyTiles = 0;
        for(int tile : gl.getTiles())
            if(tile == 0) emptyTiles++;
        assertEquals(emptyTiles, 15);
    }

    @Test
    void testMovingAround() {
        gl.moveTiles(KeyCode.UP);
        gl.moveTiles(KeyCode.DOWN);
        gl.moveTiles(KeyCode.LEFT);
        gl.moveTiles(KeyCode.RIGHT);
        int emptyTiles = 0;
        for(int tile : gl.getTiles())
            if(tile == 0) emptyTiles++;
        assertEquals(emptyTiles < 15, true);
    }
}
