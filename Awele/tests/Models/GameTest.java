package Models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game g = new Game();

    /**
     * Check if storeSeeds() sets the proper value to stored seeds
     */
    @Test
    void storeSeeds_shouldnot_fail() {
        g.storeSeeds(1, 15);
        Assertions.assertEquals(g.getSeeds(1), 15);
    }

    /**
     * Check if storeSeeds() throws an exception with an invalid ID
     */
    @Test
    void storeSeeds_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.storeSeeds(3, 15);
        });
    }

    /**
     * Check if storeSeeds() throws an exception with an amount of seeds above 23
     */
    @Test
    void storeSeeds_SeedsAbove23_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.storeSeeds(1, 24);
        });
    }

    /**
     * Check if storeSeeds() throws an exception with a negative amount of seeds
     */
    @Test
    void storeSeeds_negativeSeedsNb_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.storeSeeds(1, -2);
        });
    }

    /**
     * Check if getSeeds() throws an exception with an invalid ID
     */
    @Test
    void getSeeds_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.getSeeds(3);
        });
    }

    /**
     * Check if getSeeds() returns the right amount of seeds
     */
    @Test
    void getSeeds_shouldnot_fail() {
            g.storeSeeds(1, 12);
            Assertions.assertEquals(g.getSeeds(1), 12);
    }

    /**
     * Check if getInstance() returns the right instance of Game
     */
    @Test
    void getInstance_shouldnot_fail() {
        Game g2 = new Game();
        Assertions.assertEquals(g.getInstance(), g2.getInstance());
    }

    /**
     * Check if setPlayer() fails setting an instance of Player
     */
    @Test
    void setPlayer_shouldnot_fail() {
        g.setPlayer(1, new Player(1, "test"));
    }

    /**
     * Check if setPlayer() throws an exception with an invalid ID
     */
    @Test
    void setPlayer_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.setPlayer(0, new Player(1, "test"));
        });
    }

    /**
     * Check if setPlayer() throws an exception with a null instance
     */
    @Test
    void setPlayer_nullPlayer_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            g.setPlayer(1, null);
        });
    }

    /**
     * Check if getPlayer() throws an exception with an invalid ID
     */
    @Test
    void getPlayer_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.getPlayer(3);
        });
    }

    /**
     * Check if getPlayer() returns the right instance of Player
     */
    @Test
    void getPlayer_shouldnot_fail() {
        Player p = new Player(1, "Test"), p2;
        g.setPlayer(1, p);
        p2 = g.getPlayer(1);
        Assertions.assertEquals(p, p2);
    }

    /**
     * Check if setBoard() throws an exception with a null instance
     */
    @Test
    void setBoard_nullBoard_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            g.setBoard(null);
        });
    }

    /**
     * Check if setBoard() fails setting an instance of Board
     */
    @Test
    void setBoard_shouldnot_fail() {
            g.setBoard(new Board());
    }

    /**
     * Check if getName() returns the right player name
     */
    @Test
    void getName_shouldnot_fail() {
        g.setPlayer(1, new Player(1, "Testname"));
        Assertions.assertEquals(g.getName(1), "Testname");
    }

    /**
     * Check if getName() throws an exception while fetching a null instance
     */
    @Test
    void getName_nullPlayer_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            g.getName(1);
        });
    }

    /**
     * Check if getName() throws an exception using an invalid ID
     */
    @Test
    void getName_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.getName(5);
        });
    }
}