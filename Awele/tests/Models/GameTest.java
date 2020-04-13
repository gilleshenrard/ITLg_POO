package Models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game g = new Game();

    /**
     * Check if validateID() throws an exception with an ID different than 1 or 2
     */
    @DisplayName("validateID() with a wrong ID - should fail")
    @Test
    void validateIDs_wrong_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            Game.validateID(3, "");
        });
    }

    /**
     * Check if validateID() fails to validate correct ID
     */
    @DisplayName("validateID() - should not fail")
    @Test
    void validateID_shouldnot_fail() {
        Game.validateID(1, "");
        Game.validateID(2, "");
    }

    /**
     * Check if storeSeeds() sets the proper value to stored seeds
     */
    @DisplayName("storeSeeds() - should not fail")
    @Test
    void storeSeeds_shouldnot_fail() {
        g.storeSeeds(1, 15);
        Assertions.assertEquals(g.getSeeds(1), 15);
    }

    /**
     * Check if storeSeeds() throws an exception with an invalid ID
     */
    @DisplayName("storeSeeds() with an invalid ID - should fail")
    @Test
    void storeSeeds_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.storeSeeds(3, 15);
        });
    }

    /**
     * Check if storeSeeds() throws an exception with an amount of seeds above 23
     */
    @DisplayName("storeSeeds() with an invalid ID - should fail")
    @Test
    void storeSeeds_SeedsAbove23_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.storeSeeds(1, 24);
        });
    }

    /**
     * Check if storeSeeds() throws an exception with a negative amount of seeds
     */
    @DisplayName("storeSeeds() with negative nb_seeds - should fail")
    @Test
    void storeSeeds_negativeSeedsNb_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.storeSeeds(1, -2);
        });
    }

    /**
     * Check if getSeeds() throws an exception with an invalid ID
     */
    @DisplayName("getSeeds() with an invalid ID - should fail")
    @Test
    void getSeeds_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.getSeeds(3);
        });
    }

    /**
     * Check if getSeeds() returns the right amount of seeds
     */
    @DisplayName("getSeeds() - should not fail")
    @Test
    void getSeeds_shouldnot_fail() {
            g.storeSeeds(1, 12);
            Assertions.assertEquals(g.getSeeds(1), 12);
    }

    /**
     * Check if getInstance() returns the right instance of Game
     */
    @DisplayName("getInstance() - should not fail")
    @Test
    void getInstance_shouldnot_fail() {
        Game g2 = new Game();
        Assertions.assertEquals(g.getInstance(), g2.getInstance());
    }

    /**
     * Check if setPlayer() fails setting an instance of Player
     */
    @DisplayName("setPlayer() - should not fail")
    @Test
    void setPlayer_shouldnot_fail() {
        g.setPlayer(1, new Player(1, "test"));
    }

    /**
     * Check if setPlayer() throws an exception with an invalid ID
     */
    @DisplayName("setPlayer() with an invalid ID - should fail")
    @Test
    void setPlayer_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.setPlayer(0, new Player(1, "test"));
        });
    }

    /**
     * Check if setPlayer() throws an exception with a null instance
     */
    @DisplayName("setPlayer() with a NULL instance - should fail")
    @Test
    void setPlayer_nullPlayer_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            g.setPlayer(1, null);
        });
    }

    /**
     * Check if getPlayer() throws an exception with an invalid ID
     */
    @DisplayName("getPlayer() with an invalid ID - should fail")
    @Test
    void getPlayer_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.getPlayer(3);
        });
    }

    /**
     * Check if getPlayer() returns the right instance of Player
     */
    @DisplayName("getPlayer() - should not fail")
    @Test
    void getPlayer_shouldnot_fail() {
        Player p = new Player(1, "Test"), p2;
        g.setPlayer(1, p);
        p2 = g.getPlayer(1);
        Assertions.assertEquals(p, p2);
    }

    /**
     * Check if getBoard() returns the right instance of Board
     */
    @DisplayName("getBoard() - should not fail")
    @Test
    void getBoard_shouldnot_fail() {
        Board b = new Board(), b2;
        g.setBoard(b);
        b2 = g.getBoard();
        Assertions.assertEquals(b, b2);
    }

    /**
     * Check if setBoard() throws an exception with a null instance
     */
    @DisplayName("setBoard() with a NULL instance - should fail")
    @Test
    void setBoard_nullBoard_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            g.setBoard(null);
        });
    }

    /**
     * Check if setBoard() fails setting an instance of Board
     */
    @DisplayName("setBoard() - should not fail")
    @Test
    void setBoard_shouldnot_fail() {
            g.setBoard(new Board());
    }

    /**
     * Check if getName() returns the right player name
     */
    @DisplayName("getName() - should not fail")
    @Test
    void getName_shouldnot_fail() {
        g.setPlayer(1, new Player(1, "Testname"));
        Assertions.assertEquals(g.getName(1), "Testname");
    }

    /**
     * Check if getName() throws an exception while fetching a null instance
     */
    @DisplayName("getName() with a NULL instance - should fail")
    @Test
    void getName_nullPlayer_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            g.getName(1);
        });
    }

    /**
     * Check if getName() throws an exception using an invalid ID
     */
    @DisplayName("getName() with an invalid ID - should fail")
    @Test
    void getName_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.getName(5);
        });
    }

    /**
     * Check if getSlot() throws an exception with an invalid ID
     */
    @DisplayName("getSlot() with an invalid ID - should fail")
    @Test
    void playSlot_invalidID_should_fail() {
        g.setBoard(new Board());
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.playSlot(3, 3);
        });
    }

    /**
     * Check if getSlot() throws an exception with a slot below 1
     */
    @DisplayName("getSlot() with a slot below 1 - should fail")
    @Test
    void playSlot_below1_should_fail() {
        g.setBoard(new Board());
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.playSlot(1, 0);
        });
    }

    /**
     * Check if getSlot() throws an exception with a slot above 6
     */
    @DisplayName("getSlot() with a slot above 6 - should fail")
    @Test
    void playSlot_above6_should_fail() {
        g.setBoard(new Board());
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.playSlot(1, 7);
        });
    }

    /**
     * Check if resetGame() sets the proper values
     */
    @DisplayName("resetGame() - should not fail")
    @Test
    void resetGame_shouldnot_fail() {
        g.setBoard(new Board());
        g.resetGame();
        Assertions.assertEquals(0, g.getSeeds(1));
        Assertions.assertEquals(0, g.getSeeds(2));
    }

    /**
     * Check if getPlayableSlots() returns the proper playable slots
     */
    @DisplayName("getPlayableSlots() - should not fail")
    @Test
    void getPlayableSlots_shouldnot_fail() {
        g.setBoard(new Board());
        g.getBoard().getSlot(0, 1).emptySeeds();
        g.getBoard().getSlot(2, 1).emptySeeds();
        g.getBoard().getSlot(3, 1).emptySeeds();
        ArrayList<Integer> array = g.getPlayableSlots(2);
        Assertions.assertEquals(3, array.size());
        Assertions.assertEquals(1, array.get(0));
        Assertions.assertEquals(4, array.get(1));
        Assertions.assertEquals(5, array.get(2));
    }

    /**
     * Check if getPlayableSlots() throws an exception with an invalid ID
     */
    @DisplayName("getPlayableSlots() with an invalid ID - should fail")
    @Test
    void getPlayableSlots_above6_should_fail() {
        g.setBoard(new Board());
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.getPlayableSlots(3);
        });
    }
}