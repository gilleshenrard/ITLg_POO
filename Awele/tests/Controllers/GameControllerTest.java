package Controllers;

import Models.Board;
import Models.Game;
import Models.Player;
import Views.GameView;
import Views.RandomSelect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

public class GameControllerTest {
    GameController g = new GameController(new GameView());

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
        BoardController b = new BoardController(new Board(), new GameController(new GameView())), b2;
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
        g.setBoardController(new BoardController(new Board(), new GameController(new GameView())));
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
    @Disabled
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
        g.setBoardController(new BoardController(new Board(), new GameController(new GameView())));
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
        g.setBoardController(new BoardController(new Board(), new GameController(new GameView())));
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
        g.setBoardController(new BoardController(new Board(), new GameController(new GameView())));
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
        g.setBoardController(new BoardController(new Board(), new GameController(new GameView())));
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
        g.setBoardController(new BoardController(board, new GameController(new GameView())));
        g.setPlayer(2, new Player(2, "", new RandomSelect(g.getBoardController(), 2)));
        g.getPlayer(2).getBehaviour().refresh();
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
            g.refresh(3);
        });
    }

    /**
     * Check if reset() resets the array of playable slots properly
     */
    @DisplayName("reset() - should not fail")
    @Test
    void reset_shouldnot_fail() {
        g.setPlayer(1, new Player(1, "", new RandomSelect(new BoardController(new Board(), new GameController(new GameView())), 1)));
        g.refresh(1);
    }

    /**
     * Check if playSlot() processes a simple scattering properly (no capture, no starvation)
     */
    @DisplayName("playSlot() with neither capture nor starvation - should not fail")
    @Test
    void playSlot_noCaptureNoStarve_shouldnot_fail() {
        BoardController b = new BoardController(new Board(), g);
        g.setBoardController(b);
        int ret = g.playSlot(1, 6);
        Assertions.assertEquals(0, ret);
        Assertions.assertEquals(0, Game.getInstance().getSeeds(1));
        Assertions.assertEquals(0, Game.getInstance().getSeeds(2));
    }

    /**
     * Check if playSlot() processes a simple scattering properly (2 captures, no starvation)
     */
    @DisplayName("playSlot() with a capture case - should not fail")
    @Test
    void playSlot_CaptureNoStarve_shouldnot_fail() {
        BoardController b = new BoardController(new Board(), g);
        g.setBoardController(b);
        b.getBoard().getSlot(1, 1).setNbSeeds(2);
        b.getBoard().getSlot(3, 1).setNbSeeds(1);
        b.getBoard().getSlot(4, 1).setNbSeeds(9);
        int ret = g.playSlot(1, 6);
        Assertions.assertEquals(5, ret);
        Assertions.assertEquals(5, Game.getInstance().getSeeds(1));
        Assertions.assertEquals(0, Game.getInstance().getSeeds(2));
    }

    /**
     * Check if playSlot() processes a starvation properly
     */
    @DisplayName("playSlot() with a starvation case - should not fail")
    @Test
    void playSlot_noCaptureStarve_shouldnot_fail() {
        BoardController b = new BoardController(new Board(), g);
        g.setBoardController(b);
        b.getBoard().getSlot(0, 1).setNbSeeds(1);
        b.getBoard().getSlot(1, 1).setNbSeeds(2);
        b.getBoard().getSlot(2, 1).emptySeeds();
        b.getBoard().getSlot(3, 1).emptySeeds();
        b.getBoard().getSlot(4, 1).emptySeeds();
        b.getBoard().getSlot(5, 1).emptySeeds();
        b.getBoard().getSlot(5, 0).setNbSeeds(2);
        b.getBoard().setRemainingSeeds(2, 3);
        b.getBoard().setRemainingSeeds(1, 22);
        int ret = b.playSlot(1, 6);
        Assertions.assertEquals(-1, ret);
        Assertions.assertEquals(0, Game.getInstance().getSeeds(1));
        Assertions.assertEquals(0, Game.getInstance().getSeeds(2));
    }

    /**
     * Check if playSlot() processes a victory season
     */
    @DisplayName("playSlot() with a victory case - should not fail")
    @Test
    void playSlot_victory_shouldnot_fail() {
        BoardController b = new BoardController(new Board(), g);
        g.setBoardController(b);
        b.getBoard().getSlot(3, 1).setNbSeeds(1);
        b.getBoard().getSlot(1, 1).setNbSeeds(2);
        Game.getInstance().setSeeds(1, 20);
        int ret = g.playSlot(1, 6);
        Assertions.assertEquals(5, ret);
        Assertions.assertEquals(25, Game.getInstance().getSeeds(1));
        Assertions.assertEquals(0, Game.getInstance().getSeeds(2));
    }

    /**
     * Check if playSlot() forbids a self-starvation by scattering to another row
     */
    @DisplayName("playSlot() with self-starvation to other row - should not fail")
    @Test
    void playSlot_selfStarvation_otherRow_shouldnot_fail() {
        BoardController b = new BoardController(new Board(), g);
        g.setBoardController(b);
        b.getBoard().getSlot(0, 0).emptySeeds();
        b.getBoard().getSlot(1, 0).emptySeeds();
        b.getBoard().getSlot(2, 0).emptySeeds();
        b.getBoard().getSlot(3, 0).emptySeeds();
        b.getBoard().getSlot(4, 0).emptySeeds();
        b.getBoard().getSlot(5, 0).setNbSeeds(1);
        b.getBoard().setRemainingSeeds(1, 1);
        int ret = b.playSlot(1, 6);
        Assertions.assertEquals(-1, ret);
        Assertions.assertEquals(0, Game.getInstance().getSeeds(1));
        Assertions.assertEquals(0, Game.getInstance().getSeeds(2));
    }

    /**
     * Check if playSlot() forbids a self-starvation by scattering within a row
     */
    @DisplayName("playSlot() with self-starvation within a row - should not fail")
    @Test
    void playSlot_selfStarvation_sameRow_shouldnot_fail() {
        BoardController b = new BoardController(new Board(), g);
        g.setBoardController(b);
        b.getBoard().getSlot(0, 0).emptySeeds();
        b.getBoard().getSlot(1, 0).emptySeeds();
        b.getBoard().getSlot(2, 0).emptySeeds();
        b.getBoard().getSlot(3, 0).emptySeeds();
        b.getBoard().getSlot(4, 0).setNbSeeds(1);
        b.getBoard().getSlot(5, 0).setNbSeeds(1);
        b.getBoard().setRemainingSeeds(1, 2);
        int ret = b.playSlot(1, 5);
        Assertions.assertEquals(-1, ret);
        Assertions.assertEquals(0, Game.getInstance().getSeeds(1));
        Assertions.assertEquals(0, Game.getInstance().getSeeds(2));
    }
}
