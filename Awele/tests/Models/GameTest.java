package Models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game g = new Game();

    @Test
    void storeSeeds_shouldnot_fail() {
            g.storeSeeds(1, 15);
    }

    @Test
    void storeSeeds_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.storeSeeds(3, 15);
        });
    }

    @Test
    void storeSeeds_invalidSeedsNb_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.storeSeeds(1, 45);
        });
    }

    @Test
    void storeSeeds_negativeSeedsNb_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.storeSeeds(1, -2);
        });
    }

    @Test
    void getSeeds_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.getSeeds(3);
        });
    }

    @Test
    void getSeeds_shouldnot_fail() {
            g.storeSeeds(1, 12);
            Assertions.assertEquals(g.getSeeds(1), 12);
    }
}