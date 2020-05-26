package Controllers;

import com.gilleshenrard.Awele.Controllers.GameController;
import com.gilleshenrard.Awele.Models.Game;
import com.gilleshenrard.Awele.Models.Player;
import com.gilleshenrard.Awele.Models.Point;
import com.gilleshenrard.Awele.Views.AI.RandomSelect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

public class GameControllerTest {
    GameController g = new GameController();

    /**
     * Check if getName() returns the right player name
     */
    @DisplayName("getName() - should not fail")
    @Test
    void getName_shouldnot_fail() {
        Game.getInstance().setPlayer(new Player(1, "Testname"));
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
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.playSlot(new Point(3, 3));
        });
    }

    /**
     * Check if getSlot() throws an exception with a slot below 0
     */
    @DisplayName("getSlot() with a slot below 0 - should fail")
    @Test
    void playSlot_below0_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.playSlot(new Point(-1, 0));
        });
    }

    /**
     * Check if getSlot() throws an exception with a slot above 5
     */
    @DisplayName("getSlot() with a slot above 5 - should fail")
    @Test
    void playSlot_above5_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.playSlot(new Point(1, 6));
        });
    }

    /**
     * Check if playSlot() processes a simple scattering properly (no capture, no starvation)
     */
    @DisplayName("playSlot() with neither capture nor starvation - should not fail")
    @Test
    void playSlot_noCaptureNoStarve_shouldnot_fail() {
        g.getBoardController().getBoard().setStoredSeeds(1, 0);
        g.getBoardController().getBoard().setStoredSeeds(2, 0);
        g.resetGame();
        int ret = g.playSlot(new Point(5, 0));
        Assertions.assertEquals(0, ret);
        Assertions.assertEquals(0, g.getStoredSeeds(1));
        Assertions.assertEquals(0, g.getStoredSeeds(2));
    }

    /**
     * Check if playSlot() processes a simple scattering properly (2 captures, no starvation)
     */
    @DisplayName("playSlot() with a capture case - should not fail")
    @Test
    void playSlot_CaptureNoStarve_shouldnot_fail() {
        g.getBoardController().getBoard().setStoredSeeds(1, 0);
        g.getBoardController().getBoard().setStoredSeeds(2, 0);
        g.resetGame();
        g.getBoardController().getBoard().setSlotSeeds(new Point(1, 1), 2);
        g.getBoardController().getBoard().setSlotSeeds(new Point(3, 1), 1);
        g.getBoardController().getBoard().setSlotSeeds(new Point(4, 1), 9);
        int ret = g.playSlot(new Point(5, 0));
        Assertions.assertEquals(2, ret);
        Assertions.assertEquals(2, g.getStoredSeeds(1));
        Assertions.assertEquals(0, g.getStoredSeeds(2));
    }

    /**
     * Check if playSlot() processes a starvation properly
     */
    @DisplayName("playSlot() with a starvation case - should not fail")
    @Test
    void playSlot_noCaptureStarve_shouldnot_fail() {
        g.getBoardController().getBoard().setStoredSeeds(1, 0);
        g.getBoardController().getBoard().setStoredSeeds(2, 0);
        g.getBoardController().getBoard().setSlotSeeds(new Point(0, 1), 1);
        g.getBoardController().getBoard().setSlotSeeds(new Point(1, 1), 2);
        g.getBoardController().getBoard().emptySlotSeeds(new Point(2, 1));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(3, 1));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(4, 1));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(5, 1));
        g.getBoardController().getBoard().setSlotSeeds(new Point(5, 0), 2);
        int ret = g.playSlot(new Point(5, 0));
        Assertions.assertEquals(-1, ret);
        Assertions.assertEquals(0, g.getStoredSeeds(1));
        Assertions.assertEquals(0, g.getStoredSeeds(2));
    }

    /**
     * Check if playSlot() processes a victory season
     */
    @DisplayName("playSlot() with a victory case - should not fail")
    @Test
    void playSlot_victory_shouldnot_fail() {
        g.resetGame();
        g.getBoardController().getBoard().setSlotSeeds(new Point(3, 1), 1);
        g.getBoardController().getBoard().setSlotSeeds(new Point(1, 1), 2);
        g.getBoardController().getBoard().setStoredSeeds(2, 0);
        g.getBoardController().getBoard().setStoredSeeds(1, 20);
        int ret = g.playSlot(new Point(5, 0));
        Assertions.assertEquals(2, ret);
        Assertions.assertEquals(22, g.getStoredSeeds(1));
        Assertions.assertEquals(0, g.getStoredSeeds(2));
    }

    /**
     * Check if playSlot() processes a self-starvation by scattering to another row
     */
    @DisplayName("playSlot() with self-starvation to other row - should not fail")
    @Test
    void playSlot_selfStarvation_otherRow_shouldnot_fail() {
        g.resetGame();
        g.getBoardController().getBoard().setStoredSeeds(1, 0);
        g.getBoardController().getBoard().setStoredSeeds(2, 0);
        g.getBoardController().getBoard().emptySlotSeeds(new Point(0, 0));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(1, 0));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(2, 0));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(3, 0));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(4, 0));
        g.getBoardController().getBoard().setSlotSeeds(new Point(5, 0), 1);
        int ret = g.playSlot(new Point(5, 0));
        Assertions.assertEquals(0, ret);
        Assertions.assertEquals(0, g.getStoredSeeds(1));
        Assertions.assertEquals(0, g.getStoredSeeds(2));
    }

    /**
     * Check if playSlot() processes a self-starvation by scattering within a row
     */
    @DisplayName("playSlot() with self-starvation within a row - should not fail")
    @Test
    void playSlot_selfStarvation_sameRow_shouldnot_fail() {
        g.getBoardController().getBoard().setStoredSeeds(1, 0);
        g.getBoardController().getBoard().setStoredSeeds(2, 0);
        g.getBoardController().getBoard().emptySlotSeeds(new Point(0, 0));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(1, 0));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(2, 0));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(3, 0));
        g.getBoardController().getBoard().setSlotSeeds(new Point(4, 0), 1);
        g.getBoardController().getBoard().setSlotSeeds(new Point(5, 0), 1);
        int ret = g.playSlot(new Point(4, 0));
        Assertions.assertEquals(0, ret);
        Assertions.assertEquals(0, g.getStoredSeeds(1));
        Assertions.assertEquals(0, g.getStoredSeeds(2));
    }

    /**
     * Check if resetGame() sets the proper values
     */
    @DisplayName("resetGame() - should not fail")
    @Test
    void resetGame_shouldnot_fail() {
        g.resetGame();
        Assertions.assertEquals(0, g.getStoredSeeds(1));
        Assertions.assertEquals(0, g.getStoredSeeds(2));
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
        Game.getInstance().setPlayer(new Player(2, "", new RandomSelect(g.getBoardController(), 2)));
        int ret = g.selectSlot(2);
        Assertions.assertTrue(ret > 0 && ret <= 6);
    }
}
