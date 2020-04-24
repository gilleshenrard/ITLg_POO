package Controllers;

import Models.Board;
import Models.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

public class BoardControllerTest {
    Board board = new Board();
    BoardController b = new BoardController(board);

    /**
     * Check if getBoard() returns the right instance of Board
     */
    @DisplayName("getBoard() - should not fail")
    @Test
    void getBoard_shouldnot_fail() {
        Board b2 = new Board(), b3;
        b.setBoard(b2);
        b3 = b.getBoard();
        Assertions.assertEquals(b2, b3);
    }

    /**
     * Check if setBoard() throws an exception with a null instance
     */
    @DisplayName("setBoard() with a NULL instance - should fail")
    @Test
    void setBoard_nullBoard_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            b.setBoard(null);
        });
    }

    /**
     * Check if setBoard() fails setting an instance of Board
     */
    @DisplayName("setBoard() - should not fail")
    @Test
    void setBoard_shouldnot_fail() {
        b.setBoard(new Board());
    }

    /**
     * Check if getStoredSeeds() throws an exception with an invalid ID
     */
    @DisplayName("getStoredSeeds() with invalid ID - should fail")
    @Test
    void getStoredSeeds_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.getStoredSeeds(0);
        });
    }

    /**
     * Check if getSlotSeeds() throws an exception with an X value over 5
     */
    @DisplayName("getSlotSeeds() with X over 5 - should fail")
    @Test
    void getSloSeeds_Xover5_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.getSlotSeeds(6, 0);
        });
    }

    /**
     * Check if getSlotSeeds() throws an exception with an invalid Y value
     */
    @DisplayName("getSlotSeeds() with Y over 1 - should fail")
    @Test
    void getSlotSeeds_invalidY_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.getSlotSeeds(0, 3);
        });
    }

    /**
     * Check if getSlotSeeds() throws an exception with an X value below 0
     */
    @DisplayName("getSlotSeeds() with X below 0 - should fail")
    @Test
    void getSlotSeeds_Xbelow0_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.getSlotSeeds(-1, 0);
        });
    }

    /**
     * Check if getSlotSeeds() fails returning the amount of seeds
     */
    @DisplayName("getSlotSeeds() with proper values - should not fail")
    @Test
    void getSlotSeeds_shouldnot_fail() {
        Assertions.assertEquals(4, b.getSlotSeeds(0, 0));
    }

    /**
     * Check if playSlot() returns a cancellation code when selecting a slot with no seed
     */
    @DisplayName("playSlot() with an empty slot - should not fail")
    @Test
    void playSlot_0seeds_shouldnot_fail() {
        b.getBoard().getSlot(0, 0).setNbSeeds(0);
        int ret = b.playSlot(1, 1);
        Assertions.assertEquals(2, ret);
    }

    /**
     * Check if playSlot() throws an exception with an invalid ID
     */
    @DisplayName("playSlot() with an invalid ID - should fail")
    @Test
    void playSlot_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.playSlot(3, 3);
        });
    }

    /**
     * Check if playSlot() throws an exception with a slot below 1
     */
    @DisplayName("playSlot() with an slot below 0 - should fail")
    @Test
    void playSlot_below1_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.playSlot(1, 0);
        });
    }

    /**
     * Check if playSlot() throws an exception with a slot above 6
     */
    @DisplayName("playSlot() with an slot above 6 - should fail")
    @Test
    void playSlot_above6_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.playSlot(1, 7);
        });
    }

    /**
     * Check if playSlot() processes a simple scattering properly (no capture, no starvation)
     */
    @DisplayName("playSlot() with neither capture nor starvation - should not fail")
    @Test
    void playSlot_noCaptureNoStarve_shouldnot_fail() {
        int ret = b.playSlot(1, 6);
        Assertions.assertEquals(0, ret);
        Assertions.assertEquals(0, b.getBoard().getSlot(5, 0).getNbSeeds());
        Assertions.assertEquals(5, b.getBoard().getSlot(0, 1).getNbSeeds());
        Assertions.assertEquals(5, b.getBoard().getSlot(1, 1).getNbSeeds());
        Assertions.assertEquals(5, b.getBoard().getSlot(2, 1).getNbSeeds());
        Assertions.assertEquals(5, b.getBoard().getSlot(3, 1).getNbSeeds());
        Assertions.assertEquals(4, b.getBoard().getSlot(4, 1).getNbSeeds());
        Assertions.assertEquals(0, Game.getInstance().getSeeds(1));
        Assertions.assertEquals(0, Game.getInstance().getSeeds(2));
        Assertions.assertEquals(20, b.getBoard().getRemainingSeeds(1));
        Assertions.assertEquals(28, b.getBoard().getRemainingSeeds(2));
    }

    /**
     * Check if playSlot() properly skips the selected slot when scattering
     */
    @DisplayName("playSlot() with a slot skipped at scattering - should not fail")
    @Test
    void playSlot_skipSelected_shouldnot_fail() {
        Game g = Game.getInstance();
        g.setBoardController(b);
        g.resetGame();
        b.getBoard().getSlot(3, 0).setNbSeeds(12);
        b.getBoard().getSlot(2, 0).emptySeeds();
        b.getBoard().getSlot(1, 0).emptySeeds();
        int ret = b.playSlot(1, 4);
        Assertions.assertEquals(0, ret);
        Assertions.assertEquals(5, b.getBoard().getSlot(0, 1).getNbSeeds());
        Assertions.assertEquals(5, b.getBoard().getSlot(1, 1).getNbSeeds());
        Assertions.assertEquals(5, b.getBoard().getSlot(2, 1).getNbSeeds());
        Assertions.assertEquals(5, b.getBoard().getSlot(3, 1).getNbSeeds());
        Assertions.assertEquals(5, b.getBoard().getSlot(4, 1).getNbSeeds());
        Assertions.assertEquals(5, b.getBoard().getSlot(5, 1).getNbSeeds());
        Assertions.assertEquals(5, b.getBoard().getSlot(0, 0).getNbSeeds());
        Assertions.assertEquals(1, b.getBoard().getSlot(1, 0).getNbSeeds());
        Assertions.assertEquals(1, b.getBoard().getSlot(2, 0).getNbSeeds());
        Assertions.assertEquals(0, b.getBoard().getSlot(3, 0).getNbSeeds());
        Assertions.assertEquals(6, b.getBoard().getSlot(4, 0).getNbSeeds());
        Assertions.assertEquals(5, b.getBoard().getSlot(5, 0).getNbSeeds());
        Assertions.assertEquals(0, Game.getInstance().getSeeds(1));
        Assertions.assertEquals(0, Game.getInstance().getSeeds(2));
        Assertions.assertEquals(30, b.getBoard().getRemainingSeeds(2));
        Assertions.assertEquals(18, b.getBoard().getRemainingSeeds(1));
    }

    /**
     * Check if playSlot() processes a simple scattering properly (2 captures, no starvation)
     */
    @DisplayName("playSlot() with a capture case - should not fail")
    @Test
    void playSlot_CaptureNoStarve_shouldnot_fail() {
        b.getBoard().getSlot(1, 1).setNbSeeds(2);
        b.getBoard().getSlot(3, 1).setNbSeeds(1);
        b.getBoard().getSlot(4, 1).setNbSeeds(9);
        int ret = b.playSlot(1, 6);
        Assertions.assertEquals(0, ret);
        Assertions.assertEquals(5, b.getBoard().getSlot(0, 1).getNbSeeds());
        Assertions.assertEquals(0, b.getBoard().getSlot(1, 1).getNbSeeds());
        Assertions.assertEquals(5, b.getBoard().getSlot(2, 1).getNbSeeds());
        Assertions.assertEquals(0, b.getBoard().getSlot(3, 1).getNbSeeds());
        Assertions.assertEquals(9, b.getBoard().getSlot(4, 1).getNbSeeds());
        Assertions.assertEquals(4, b.getBoard().getSlot(5, 1).getNbSeeds());
        Assertions.assertEquals(0, b.getBoard().getSlot(5, 0).getNbSeeds());
        Assertions.assertEquals(5, Game.getInstance().getSeeds(1));
        Assertions.assertEquals(0, Game.getInstance().getSeeds(2));
        Assertions.assertEquals(23, b.getBoard().getRemainingSeeds(2));
        Assertions.assertEquals(20, b.getBoard().getRemainingSeeds(1));
    }

    /**
     * Check if playSlot() processes a starvation properly
     */
    @DisplayName("playSlot() with a starvation case - should not fail")
    @Test
    void playSlot_noCaptureStarve_shouldnot_fail() {
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
        Assertions.assertEquals(1, ret);
        Assertions.assertEquals(1, b.getBoard().getSlot(0, 1).getNbSeeds());
        Assertions.assertEquals(2, b.getBoard().getSlot(1, 1).getNbSeeds());
        Assertions.assertEquals(0, b.getBoard().getSlot(2, 1).getNbSeeds());
        Assertions.assertEquals(0, b.getBoard().getSlot(3, 1).getNbSeeds());
        Assertions.assertEquals(0, b.getBoard().getSlot(4, 1).getNbSeeds());
        Assertions.assertEquals(0, b.getBoard().getSlot(5, 1).getNbSeeds());
        Assertions.assertEquals(3, b.getBoard().getRemainingSeeds(2));
        Assertions.assertEquals(0, Game.getInstance().getSeeds(1));
        Assertions.assertEquals(0, Game.getInstance().getSeeds(2));
    }

    /**
     * Check if playSlot() processes a victory season
     */
    @DisplayName("playSlot() with a victory case - should not fail")
    @Test
    void playSlot_victory_shouldnot_fail() {
        b.getBoard().getSlot(3, 1).setNbSeeds(1);
        b.getBoard().getSlot(1, 1).setNbSeeds(2);
        Game.getInstance().storeSeeds(1, 20);
        int ret = b.playSlot(1, 6);
        Assertions.assertEquals(0, ret);
        Assertions.assertEquals(5, b.getBoard().getSlot(0, 1).getNbSeeds());
        Assertions.assertEquals(0, b.getBoard().getSlot(1, 1).getNbSeeds());
        Assertions.assertEquals(5, b.getBoard().getSlot(2, 1).getNbSeeds());
        Assertions.assertEquals(0, b.getBoard().getSlot(3, 1).getNbSeeds());
        Assertions.assertEquals(4, b.getBoard().getSlot(4, 1).getNbSeeds());
        Assertions.assertEquals(25, Game.getInstance().getSeeds(1));
        Assertions.assertEquals(0, Game.getInstance().getSeeds(2));
    }

    /**
     * Check if playSlot() forbids a self-starvation by scattering to another row
     */
    @DisplayName("playSlot() with self-starvation to other row - should not fail")
    @Test
    void playSlot_selfStarvation_otherRow_shouldnot_fail() {
        b.getBoard().getSlot(0, 0).emptySeeds();
        b.getBoard().getSlot(1, 0).emptySeeds();
        b.getBoard().getSlot(2, 0).emptySeeds();
        b.getBoard().getSlot(3, 0).emptySeeds();
        b.getBoard().getSlot(4, 0).emptySeeds();
        b.getBoard().getSlot(5, 0).setNbSeeds(1);
        b.getBoard().setRemainingSeeds(1, 1);
        int ret = b.playSlot(1, 6);
        Assertions.assertEquals(1, ret);
        Assertions.assertEquals(0, b.getBoard().getSlot(0, 0).getNbSeeds());
        Assertions.assertEquals(0, b.getBoard().getSlot(1, 0).getNbSeeds());
        Assertions.assertEquals(0, b.getBoard().getSlot(2, 0).getNbSeeds());
        Assertions.assertEquals(0, b.getBoard().getSlot(3, 0).getNbSeeds());
        Assertions.assertEquals(0, b.getBoard().getSlot(4, 0).getNbSeeds());
        Assertions.assertEquals(1, b.getBoard().getSlot(5, 0).getNbSeeds());
        Assertions.assertEquals(1, b.getBoard().getRemainingSeeds(1));
        Assertions.assertEquals(0, Game.getInstance().getSeeds(1));
        Assertions.assertEquals(0, Game.getInstance().getSeeds(2));
    }

    /**
     * Check if playSlot() forbids a self-starvation by scattering within a row
     */
    @DisplayName("playSlot() with self-starvation within a row - should not fail")
    @Test
    void playSlot_selfStarvation_sameRow_shouldnot_fail() {
        b.getBoard().getSlot(0, 0).emptySeeds();
        b.getBoard().getSlot(1, 0).emptySeeds();
        b.getBoard().getSlot(2, 0).emptySeeds();
        b.getBoard().getSlot(3, 0).emptySeeds();
        b.getBoard().getSlot(4, 0).setNbSeeds(1);
        b.getBoard().getSlot(5, 0).setNbSeeds(1);
        b.getBoard().setRemainingSeeds(1, 2);
        int ret = b.playSlot(1, 5);
        Assertions.assertEquals(1, ret);
        Assertions.assertEquals(0, b.getBoard().getSlot(0, 0).getNbSeeds());
        Assertions.assertEquals(0, b.getBoard().getSlot(1, 0).getNbSeeds());
        Assertions.assertEquals(0, b.getBoard().getSlot(2, 0).getNbSeeds());
        Assertions.assertEquals(0, b.getBoard().getSlot(3, 0).getNbSeeds());
        Assertions.assertEquals(1, b.getBoard().getSlot(4, 0).getNbSeeds());
        Assertions.assertEquals(1, b.getBoard().getSlot(5, 0).getNbSeeds());
        Assertions.assertEquals(2, b.getBoard().getRemainingSeeds(1));
        Assertions.assertEquals(0, Game.getInstance().getSeeds(1));
        Assertions.assertEquals(0, Game.getInstance().getSeeds(2));
    }

    /**
     * Check if resetBoard() sets the proper inial values
     */
    @DisplayName("resetBoard() - should not fail")
    @Test
    void resetBoard_shouldnot_fail() {
        b.resetBoard();
        Assertions.assertEquals(24, b.getBoard().getRemainingSeeds(1));
        Assertions.assertEquals(24, b.getBoard().getRemainingSeeds(2));
        for (int l = 0 ; l < 2 ; l++){
            for (int c = 0 ; c < 6 ; c++){
                Assertions.assertEquals(4, b.getBoard().getSlot(c, l).getNbSeeds());
            }
        }
    }
}
