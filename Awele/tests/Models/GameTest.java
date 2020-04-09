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

    @Test
    void getInstance_shouldnot_fail() {
        Game g2 = new Game();
        Assertions.assertEquals(g.getInstance(), g2.getInstance());
    }

    @Test
    void setPlayer_shouldnot_fail() {
            g.setPlayer(1, new Player(1, "test"));
    }

    @Test
    void setPlayer_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.setPlayer(0, new Player(1, "test"));
        });
    }

    @Test
    void setPlayer_nullPlayer_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.setPlayer(1, null);
        });
    }

    @Test
    void setBoard_nullBoard_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.setBoard(null);
        });
    }

    @Test
    void setBoard_shouldnot_fail() {
            g.setBoard(new Board());
    }
}