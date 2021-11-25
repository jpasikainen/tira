package com.jpasikainen.tira;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.jpasikainen.tira.util.AlphaKey;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

public class AlphaKeyTests {
    @Test
    void testCorrectAlpha() {
        AlphaKey ak = new AlphaKey(1, KeyCode.UP);
        assertEquals(ak.alpha, 1);
    }

    @Test
    void testCorrectKey() {
        AlphaKey ak = new AlphaKey(1, KeyCode.UP);
        assertEquals(ak.key, KeyCode.UP);
    }
}
