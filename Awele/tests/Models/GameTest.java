package Models;

import Controllers.BoardController;
import Views.RandomSelect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.util.ArrayList;

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
     * Check if getBoardController() returns the right instance of Board
     */
    @DisplayName("getBoardController() - should not fail")
    @Test
    void getBoardController_shouldnot_fail() {
        BoardController b = new BoardController(new Board()), b2;
        g.setBoardController(b);
        b2 = g.getBoardController();
        Assertions.assertEquals(b, b2);
    }

    /**
     * Check if setBoardController() throws an exception with a null instance
     */
    @DisplayName("setBoardController() with a NULL instance - should fail")
    @Test
    void setBoardController_nullBoard_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            g.setBoardController(null);
        });
    }

    /**
     * Check if setBoardController() fails setting an instance of Board
     */
    @DisplayName("setBoardController() - should not fail")
    @Test
    void setBoardController_shouldnot_fail() {
            g.setBoardController(new BoardController(new Board()));
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
        g.setBoardController(new BoardController(new Board()));
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
        g.setBoardController(new BoardController(new Board()));
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
        g.setBoardController(new BoardController(new Board()));
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
        g.setBoardController(new BoardController(new Board()));
        g.resetGame();
        Assertions.assertEquals(0, g.getSeeds(1));
        Assertions.assertEquals(0, g.getSeeds(2));
    }

    /**
     * Check if selectSlot() throws an exception with wrong ID
     */
    @DisplayName("selectSlot() with a wrong ID - should fail")
    @Test
    void selectSlot_wrongID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.selectSlot(3);
        });
    }

    /**
     * Check if selectSlot() returns a proper value
     */
    @DisplayName("selectSlot() - should not fail")
    @Test
    void selectSlot_shouldnot_fail() {
        Board board = new Board();
        g.setBoardController(new BoardController(board));
        g.setPlayer(2, new Player(2, "", new RandomSelect(board, 2)));
        g.getPlayer(2).getBehaviour().reset();
        int ret = g.selectSlot(2);
        Assertions.assertTrue(ret > 0 && ret <= 6);
    }

    /**
     * Check if reset() throws an exception with wrong ID
     */
    @DisplayName("reset() with a wrong ID - should fail")
    @Test
    void reset_wrongID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.reset(3);
        });
    }

    /**
     * Check if reset() resets the array of playable slots properly
     */
    @DisplayName("reset() - should not fail")
    @Test
    void reset_shouldnot_fail() {
        g.setPlayer(1, new Player(1, "", new RandomSelect(new Board(), 1)));
        g.reset(1);
    }
}