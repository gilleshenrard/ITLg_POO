package Controllers;

import com.gilleshenrard.Awele.Controllers.BoardController;
import com.gilleshenrard.Awele.Models.Board;
import com.gilleshenrard.Awele.Models.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

public class BoardControllerTest {
    BoardController b = new BoardController(new Board());

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
     * Check if getSlotSeeds() throws an exception with an X value over 5
     */
    @DisplayName("getSlotSeeds() with X over 5 - should fail")
    @Test
    void getSlotSeeds_Xover5_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.getSlotSeeds(new Point(6, 0));
        });
    }

    /**
     * Check if getSlotSeeds() throws an exception with an invalid Y value
     */
    @DisplayName("getSlotSeeds() with Y over 1 - should fail")
    @Test
    void getSlotSeeds_invalidY_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.getSlotSeeds(new Point(0, 3));
        });
    }

    /**
     * Check if getSlotSeeds() throws an exception with an X value below 0
     */
    @DisplayName("getSlotSeeds() with X below 0 - should fail")
    @Test
    void getSlotSeeds_Xbelow0_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.getSlotSeeds(new Point(-1, 0));
        });
    }

    /**
     * Check if getSlotSeeds() fails returning the amount of seeds
     */
    @DisplayName("getSlotSeeds() with proper values - should not fail")
    @Test
    void getSlotSeeds_shouldnot_fail() {
        Assertions.assertEquals(4, b.getSlotSeeds(new Point(0, 0)));
    }

    /**
     * Check if storeSeeds() throws an exception with an invalid ID
     */
    @DisplayName("storeSeeds() with an invalid ID - should fail")
    @Test
    void storeSeeds_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.storeSeeds(3, 3);
        });
    }

    /**
     * Check if storeSeeds() throws an exception when storing above 48
     */
    @DisplayName("storeSeeds() above 48 - should fail")
    @Test
    void storeSeeds_above48_should_fail() {
        b.getBoard().setStoredSeeds(1, 23);
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.storeSeeds(1, 26);
        });
    }

    /**
     * Check if storeSeeds() throws an exception when storing below 0
     */
    @DisplayName("storeSeeds() negative amount - should fail")
    @Test
    void storeSeeds_below_0_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.storeSeeds(1, -2);
        });
    }

    /**
     * Check if storeSeeds() fails storing seeds
     */
    @DisplayName("storeSeeds() - should not fail")
    @Test
    void storeSeeds_shouldnot_fail() {
        b.getBoard().setStoredSeeds(1, 23);
        b.storeSeeds(1, 25);
    }

    /**
     * Check if checkOutcome() returns a cancellation code when selecting a slot with no seed
     */
    @DisplayName("checkOutcome() with an empty slot - should not fail")
    @Test
    void checkOutcome_0seeds_shouldnot_fail() {
        b.getBoard().setSlotSeeds(new Point(0, 0), 0);
        int ret = b.checkOutcome(new Point(0, 0));
        Assertions.assertEquals(-2, ret);
    }

    /**
     * Check if playSlot() returns a cancellation code when selecting a slot with no seed
     */
    @DisplayName("playSlot() with an empty slot - should not fail")
    @Test
    void playSlot_0seeds_shouldnot_fail() {
        b.getBoard().setSlotSeeds(new Point(0, 0), 0);
        int ret = b.playSlot(new Point(0, 0));
        Assertions.assertEquals(-2, ret);
    }

    /**
     * Check if checkOutcome() throws an exception with an invalid ID
     */
    @DisplayName("checkOutcome() with an invalid ID - should fail")
    @Test
    void checkOutcome_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.checkOutcome(new Point(3, 3));
        });
    }

    /**
     * Check if playSlot() throws an exception with an invalid ID
     */
    @DisplayName("playSlot() with an invalid ID - should fail")
    @Test
    void playSlot_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.playSlot(new Point(3, 3));
        });
    }

    /**
     * Check if checkOutcome() throws an exception with a slot below 0
     */
    @DisplayName("checkOutcome() with an slot below 0 - should fail")
    @Test
    void checkOutcome_below0_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.checkOutcome(new Point(-1, 0));
        });
    }

    /**
     * Check if playSlot() throws an exception with a slot below 0
     */
    @DisplayName("playSlot() with an slot below 0 - should fail")
    @Test
    void playSlot_below0_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.playSlot(new Point(-1, 0));
        });
    }

    /**
     * Check if checkOutcome() throws an exception with a slot above 5
     */
    @DisplayName("checkOutcome() with an slot above 5 - should fail")
    @Test
    void checkOutcome_above5_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.checkOutcome(new Point(1, 6));
        });
    }

    /**
     * Check if playSlot() throws an exception with a slot above 5
     */
    @DisplayName("playSlot() with an slot above 5 - should fail")
    @Test
    void playSlot_above5_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.playSlot(new Point(1, 6));
        });
    }

    /**
     * Check if scattering without capture occurs properly
     */
    @DisplayName("Scattering without capture")
    @Test
    void scattering_noCapture_otherRow() {
        //check the outcome
        int ret = b.checkOutcome(new Point(5, 0));
        Assertions.assertEquals(0, ret);

        //play the slot
        ret = b.playSlot(new Point(5, 0));
        Assertions.assertEquals(0, ret);

        //check the board state afterwards
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(5, 0)));
        Assertions.assertEquals(5, b.getSlotSeeds(new Point(0, 1)));
        Assertions.assertEquals(5, b.getSlotSeeds(new Point(1, 1)));
        Assertions.assertEquals(5, b.getSlotSeeds(new Point(2, 1)));
        Assertions.assertEquals(5, b.getSlotSeeds(new Point(3, 1)));
        Assertions.assertEquals(4, b.getSlotSeeds(new Point(4, 1)));
        Assertions.assertEquals(0, b.getStoredSeeds(1));
        Assertions.assertEquals(0, b.getStoredSeeds(2));
    }

    /**
     * Check if scattering without capture across empty opponent occurs properly
     */
    @DisplayName("Scattering without capture across empty opponent")
    @Test
    void scattering_noCapture_opponentEmpty() {
        //prepare the board (empty opponent's side)
        b.getBoard().emptySlotSeeds(new Point(0, 1));
        b.getBoard().emptySlotSeeds(new Point(1, 1));
        b.getBoard().emptySlotSeeds(new Point(2, 1));
        b.getBoard().emptySlotSeeds(new Point(3, 1));
        b.getBoard().emptySlotSeeds(new Point(4, 1));
        b.getBoard().emptySlotSeeds(new Point(5, 1));
        b.getBoard().setSlotSeeds(new Point(0, 0), 4);
        b.getBoard().setSlotSeeds(new Point(1, 0), 4);
        b.getBoard().setSlotSeeds(new Point(5, 0), 8);

        //check the outcome
        int ret = b.checkOutcome(new Point(5, 0));
        Assertions.assertEquals(0, ret);

        //play the slot
        ret = b.playSlot(new Point(5, 0));
        Assertions.assertEquals(0, ret);

        //check the board state afterwards
        Assertions.assertEquals(1, b.getSlotSeeds(new Point(0, 1)));
        Assertions.assertEquals(1, b.getSlotSeeds(new Point(1, 1)));
        Assertions.assertEquals(1, b.getSlotSeeds(new Point(2, 1)));
        Assertions.assertEquals(1, b.getSlotSeeds(new Point(3, 1)));
        Assertions.assertEquals(1, b.getSlotSeeds(new Point(4, 1)));
        Assertions.assertEquals(1, b.getSlotSeeds(new Point(5, 1)));
        Assertions.assertEquals(5, b.getSlotSeeds(new Point(0, 0)));
        Assertions.assertEquals(5, b.getSlotSeeds(new Point(1, 0)));
    }

    /**
     * Check if capture on other row occurs properly (2 captures, no starvation)
     */
    @DisplayName("Capture on other row, no starvation")
    @Test
    void capture_otherRow_noStarvation() {
        //prepare the board (capturable slots non-consecutive)
        b.getBoard().setSlotSeeds(new Point(5, 0), 4);
        b.getBoard().setSlotSeeds(new Point(0, 1), 4);
        b.getBoard().setSlotSeeds(new Point(1, 1), 2);
        b.getBoard().setSlotSeeds(new Point(2, 1), 4);
        b.getBoard().setSlotSeeds(new Point(3, 1), 1);
        b.getBoard().setSlotSeeds(new Point(4, 1), 9);

        //check the outcome
        int ret = b.checkOutcome(new Point(5, 0));
        Assertions.assertEquals(2, ret);

        //play the slot
        ret = b.playSlot(new Point(5, 0));
        Assertions.assertEquals(2, ret);

        //check the board state afterwards
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(5, 0)));
        Assertions.assertEquals(5, b.getSlotSeeds(new Point(0, 1)));
        Assertions.assertEquals(3, b.getSlotSeeds(new Point(1, 1)));
        Assertions.assertEquals(5, b.getSlotSeeds(new Point(2, 1)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(3, 1)));
        Assertions.assertEquals(9, b.getSlotSeeds(new Point(4, 1)));
        Assertions.assertEquals(4, b.getSlotSeeds(new Point(5, 1)));
        Assertions.assertEquals(2, b.getStoredSeeds(1));
        Assertions.assertEquals(0, b.getStoredSeeds(2));
    }

    /**
     * Check if capture on other row doesn't capture on player's row
     */
    @DisplayName("Capture on other row, no capture on player's side")
    @Test
    void capture_otherRow_noPlayerRow() {
        //prepare the board (capturable slots non-consecutive, only one to be captured)
        b.getBoard().setSlotSeeds(new Point(0, 0), 4);
        b.getBoard().setSlotSeeds(new Point(1, 0), 4);
        b.getBoard().setSlotSeeds(new Point(2, 0), 4);
        b.getBoard().setSlotSeeds(new Point(3, 0), 6);
        b.getBoard().setSlotSeeds(new Point(4, 0), 1);
        b.getBoard().setSlotSeeds(new Point(5, 0), 1);
        b.getBoard().setSlotSeeds(new Point(0, 1), 4);
        b.getBoard().setSlotSeeds(new Point(1, 1), 2);
        b.getBoard().setSlotSeeds(new Point(2, 1), 4);
        b.getBoard().setSlotSeeds(new Point(3, 1), 1);
        b.getBoard().setSlotSeeds(new Point(4, 1), 9);
        b.getBoard().setSlotSeeds(new Point(5, 1), 4);

        //check the outcome
        int ret = b.checkOutcome(new Point(3, 0));
        Assertions.assertEquals(2, ret);

        //play the slot
        ret = b.playSlot(new Point(3, 0));
        Assertions.assertEquals(2, ret);

        //check the board state afterwards
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(3, 0)));
        Assertions.assertEquals(2, b.getSlotSeeds(new Point(4, 0)));
        Assertions.assertEquals(2, b.getSlotSeeds(new Point(5, 0)));
        Assertions.assertEquals(5, b.getSlotSeeds(new Point(0, 1)));
        Assertions.assertEquals(3, b.getSlotSeeds(new Point(1, 1)));
        Assertions.assertEquals(5, b.getSlotSeeds(new Point(2, 1)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(3, 1)));
        Assertions.assertEquals(9, b.getSlotSeeds(new Point(4, 1)));
        Assertions.assertEquals(4, b.getSlotSeeds(new Point(5, 1)));
    }

    /**
     * Check if capture with starvation is properly forbidden
     */
    @DisplayName("Capture with starvation of opponent")
    @Test
    void capture_otherRow_starvation() {
        //prepare the board (only capturable slots on opponent's side)
        b.getBoard().setSlotSeeds(new Point(0, 0), 1);
        b.getBoard().setSlotSeeds(new Point(1, 0), 2);
        b.getBoard().emptySlotSeeds(new Point(2, 0));
        b.getBoard().emptySlotSeeds(new Point(3, 0));
        b.getBoard().emptySlotSeeds(new Point(4, 0));
        b.getBoard().emptySlotSeeds(new Point(5, 0));
        b.getBoard().setSlotSeeds(new Point(5, 1), 2);

        //check the outcome
        int ret = b.checkOutcome(new Point(5, 1));
        Assertions.assertEquals(-1, ret);

        //play the slot
        ret = b.playSlot(new Point(5, 1));
        Assertions.assertEquals(-1, ret);

        //check the board state afterwards
        Assertions.assertEquals(1, b.getSlotSeeds(new Point(0, 0)));
        Assertions.assertEquals(2, b.getSlotSeeds(new Point(1, 0)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(2, 0)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(3, 0)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(4, 0)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(5, 0)));
        Assertions.assertEquals(0, b.getStoredSeeds(2));
        Assertions.assertEquals(0, b.getStoredSeeds(1));
    }

    /**
     * Check if checkOutcome() processes a self-starvation by scattering to another row
     */
    @DisplayName("checkOutcome() with self-starvation to other row - should not fail")
    @Test
    void checkOutcome_selfStarvation_otherRow_shouldnot_fail() {
        b.getBoard().emptySlotSeeds(new Point(0, 0));
        b.getBoard().emptySlotSeeds(new Point(1, 0));
        b.getBoard().emptySlotSeeds(new Point(2, 0));
        b.getBoard().emptySlotSeeds(new Point(3, 0));
        b.getBoard().emptySlotSeeds(new Point(4, 0));
        b.getBoard().setSlotSeeds(new Point(5, 0), 1);
    }

    /**
     * Check if playSlot() processes a self-starvation by scattering to another row
     */
    @DisplayName("Self-starvation to other row")
    @Test
    void playSlot_selfStarvation_otherRow_shouldnot_fail() {
        //prepare the board (1 seed left at end of row)
        b.getBoard().emptySlotSeeds(new Point(0, 0));
        b.getBoard().emptySlotSeeds(new Point(1, 0));
        b.getBoard().emptySlotSeeds(new Point(2, 0));
        b.getBoard().emptySlotSeeds(new Point(3, 0));
        b.getBoard().emptySlotSeeds(new Point(4, 0));
        b.getBoard().setSlotSeeds(new Point(5, 0), 1);

        //check the outcome
        int ret = b.checkOutcome(new Point(5, 0));
        Assertions.assertEquals(0, ret);

        //play the slot
        ret = b.playSlot(new Point(5, 0));
        Assertions.assertEquals(0, ret);

        //check the board state afterwards
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(0, 0)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(1, 0)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(2, 0)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(3, 0)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(4, 0)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(5, 0)));
        Assertions.assertEquals(0, b.getStoredSeeds(1));
        Assertions.assertEquals(0, b.getStoredSeeds(2));
    }

    /**
     * Make sure a capture doesn't occur on player's side (same row)
     */
    @DisplayName("No capture on player's side (same row)")
    @Test
    void noCapture_Player1_sameRow() {
        //prepare the board
        b.getBoard().emptySlotSeeds(new Point(0, 0));
        b.getBoard().emptySlotSeeds(new Point(1, 0));
        b.getBoard().emptySlotSeeds(new Point(2, 0));
        b.getBoard().emptySlotSeeds(new Point(3, 0));
        b.getBoard().setSlotSeeds(new Point(4, 0), 1);
        b.getBoard().setSlotSeeds(new Point(5, 0), 1);

        //check the outcome
        int ret = b.checkOutcome(new Point(4, 0));
        Assertions.assertEquals(0, ret);

        //play the slot
        ret = b.playSlot(new Point(4, 0));
        Assertions.assertEquals(0, ret);

        //check the board state afterwards
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(0, 0)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(1, 0)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(2, 0)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(3, 0)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(4, 0)));
        Assertions.assertEquals(2, b.getSlotSeeds(new Point(5, 0)));
        Assertions.assertEquals(0, b.getStoredSeeds(1));
        Assertions.assertEquals(0, b.getStoredSeeds(2));
    }

    /**
     * Make sure a capture doesn't occur on player's side (across other row)
     */
    @DisplayName("No capture on player's side (across other row)")
    @Test
    void noCapture_Player1_acrossOtherRow() {
        //prepare the board
        b.getBoard().setSlotSeeds(new Point(0, 1), 1);
        b.getBoard().setSlotSeeds(new Point(1, 1), 1);
        b.getBoard().setSlotSeeds(new Point(2, 1), 1);
        b.getBoard().setSlotSeeds(new Point(3, 1), 1);
        b.getBoard().setSlotSeeds(new Point(4, 1), 1);
        b.getBoard().setSlotSeeds(new Point(5, 1), 1);
        b.getBoard().setSlotSeeds(new Point(5, 0), 7);
        b.getBoard().setSlotSeeds(new Point(0, 0), 1);

        //check the outcome
        int ret = b.checkOutcome(new Point(5, 0));
        Assertions.assertEquals(0, ret);

        //play the slot
        ret = b.playSlot(new Point(5, 0));
        Assertions.assertEquals(0, ret);

        //check the board state afterwards
        Assertions.assertEquals(2, b.getSlotSeeds(new Point(0, 1)));
        Assertions.assertEquals(2, b.getSlotSeeds(new Point(1, 1)));
        Assertions.assertEquals(2, b.getSlotSeeds(new Point(2, 1)));
        Assertions.assertEquals(2, b.getSlotSeeds(new Point(3, 1)));
        Assertions.assertEquals(2, b.getSlotSeeds(new Point(4, 1)));
        Assertions.assertEquals(2, b.getSlotSeeds(new Point(5, 1)));
        Assertions.assertEquals(2, b.getSlotSeeds(new Point(0, 0)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(5, 0)));
        Assertions.assertEquals(0, b.getStoredSeeds(1));
        Assertions.assertEquals(0, b.getStoredSeeds(2));
    }

    /**
     * Check if moving a seed to another slot within a nearly empty row is allowed
     */
    @DisplayName("Moving seed within an empty row")
    @Test
    void seedMovement_emptyRow() {
        //prepare the board (only 1 seed in first slot)
        b.getBoard().setSlotSeeds(new Point(0, 0), 1);
        b.getBoard().emptySlotSeeds(new Point(1, 0));
        b.getBoard().emptySlotSeeds(new Point(2, 0));
        b.getBoard().emptySlotSeeds(new Point(3, 0));
        b.getBoard().emptySlotSeeds(new Point(4, 0));
        b.getBoard().emptySlotSeeds(new Point(5, 0));

        //check outcome
        int ret = b.checkOutcome(new Point(0, 0));
        Assertions.assertEquals(0, ret);

        //play the slot
        ret = b.playSlot(new Point(0, 0));
        Assertions.assertEquals(0, ret);

        //check the board state afterwards
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(0, 0)));
        Assertions.assertEquals(1, b.getSlotSeeds(new Point(1, 0)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(2, 0)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(3, 0)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(4, 0)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(5, 0)));
        Assertions.assertEquals(0, b.getStoredSeeds(1));
        Assertions.assertEquals(0, b.getStoredSeeds(2));
    }

    /**
     * Check if playSlot() properly skips the selected slot when scattering
     */
    @DisplayName("playSlot() with a slot skipped at scattering")
    @Test
    void playSlot_skipSelected_shouldnot_fail() {
        //prepare the board
        b.getBoard().setSlotSeeds(new Point(3, 0), 12);
        b.getBoard().emptySlotSeeds(new Point(1, 0));
        b.getBoard().emptySlotSeeds(new Point(2, 0));

        //play the slot
        int ret = b.playSlot(new Point(3, 0));
        Assertions.assertEquals(0, ret);

        //check the board state afterwards
        Assertions.assertEquals(5, b.getSlotSeeds(new Point(0, 1)));
        Assertions.assertEquals(5, b.getSlotSeeds(new Point(1, 1)));
        Assertions.assertEquals(5, b.getSlotSeeds(new Point(2, 1)));
        Assertions.assertEquals(5, b.getSlotSeeds(new Point(3, 1)));
        Assertions.assertEquals(5, b.getSlotSeeds(new Point(4, 1)));
        Assertions.assertEquals(5, b.getSlotSeeds(new Point(5, 1)));
        Assertions.assertEquals(5, b.getSlotSeeds(new Point(0, 0)));
        Assertions.assertEquals(1, b.getSlotSeeds(new Point(1, 0)));
        Assertions.assertEquals(1, b.getSlotSeeds(new Point(2, 0)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(3, 0)));
        Assertions.assertEquals(6, b.getSlotSeeds(new Point(4, 0)));
        Assertions.assertEquals(5, b.getSlotSeeds(new Point(5, 0)));
        Assertions.assertEquals(0, b.getStoredSeeds(1));
        Assertions.assertEquals(0, b.getStoredSeeds(2));
    }

    /**
     * Check if playSlot() properly skips the selected slot when scattering
     */
    @DisplayName("playSlot() with a slot skipped at scattering")
    @Test
    void playSlot_capture_skipSelected_shouldnot_fail() {
        //prepare the board
        b.getBoard().setSlotSeeds(new Point(0, 0), 1);
        b.getBoard().setSlotSeeds(new Point(1, 0), 1);
        b.getBoard().setSlotSeeds(new Point(2, 0), 1);
        b.getBoard().setSlotSeeds(new Point(3, 0), 1);
        b.getBoard().setSlotSeeds(new Point(4, 0), 15);
        b.getBoard().setSlotSeeds(new Point(5, 0), 1);
        b.getBoard().setSlotSeeds(new Point(0, 1), 1);
        b.getBoard().setSlotSeeds(new Point(1, 1), 1);
        b.getBoard().setSlotSeeds(new Point(2, 1), 1);
        b.getBoard().setSlotSeeds(new Point(3, 1), 1);
        b.getBoard().setSlotSeeds(new Point(4, 1), 1);
        b.getBoard().setSlotSeeds(new Point(5, 1), 1);

        //check the outcome
        int ret = b.checkOutcome(new Point(4, 0));
        Assertions.assertEquals(9, ret);

        //play the slot
        ret = b.playSlot(new Point(4, 0));
        Assertions.assertEquals(9, ret);

        //check the board state afterwards
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(0, 1)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(1, 1)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(2, 1)));
        Assertions.assertEquals(2, b.getSlotSeeds(new Point(3, 1)));
        Assertions.assertEquals(2, b.getSlotSeeds(new Point(4, 1)));
        Assertions.assertEquals(2, b.getSlotSeeds(new Point(5, 1)));
        Assertions.assertEquals(2, b.getSlotSeeds(new Point(0, 0)));
        Assertions.assertEquals(2, b.getSlotSeeds(new Point(1, 0)));
        Assertions.assertEquals(2, b.getSlotSeeds(new Point(2, 0)));
        Assertions.assertEquals(2, b.getSlotSeeds(new Point(3, 0)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(4, 0)));
        Assertions.assertEquals(3, b.getSlotSeeds(new Point(5, 0)));
    }

    /**
     * Check if playSlot() forbids a player to let the other starve
     */
    @DisplayName("playSlot() with other player starved - should not fail")
    @Test
    void playSlot_starvation_otherRow_shouldnot_fail() {
        //prepare the board (empty opponent)
        b.getBoard().setSlotSeeds(new Point(0, 1), 1);
        b.getBoard().setSlotSeeds(new Point(1, 1), 1);
        b.getBoard().setSlotSeeds(new Point(2, 1), 1);
        b.getBoard().setSlotSeeds(new Point(3, 1), 5);
        b.getBoard().setSlotSeeds(new Point(4, 1), 5);
        b.getBoard().setSlotSeeds(new Point(5, 1), 6);
        b.getBoard().emptySlotSeeds(new Point(0, 0));
        b.getBoard().emptySlotSeeds(new Point(1, 0));
        b.getBoard().emptySlotSeeds(new Point(2, 0));
        b.getBoard().emptySlotSeeds(new Point(3, 0));
        b.getBoard().emptySlotSeeds(new Point(4, 0));
        b.getBoard().emptySlotSeeds(new Point(5, 0));

        //check the outcome
        int ret = b.checkOutcome(new Point(0, 1));
        Assertions.assertEquals(-1, ret);

        //play the slot
        ret = b.playSlot(new Point(0, 1));
        Assertions.assertEquals(-1, ret);

        //check the board state afterwards
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(0, 0)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(1, 0)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(2, 0)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(3, 0)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(4, 0)));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(5, 0)));
        Assertions.assertEquals(1, b.getSlotSeeds(new Point(0, 1)));
        Assertions.assertEquals(0, b.getStoredSeeds(1));
        Assertions.assertEquals(0, b.getStoredSeeds(2));
    }

    /**
     * Check if resetBoard() sets the proper initial values
     */
    @DisplayName("resetBoard() - should not fail")
    @Test
    void resetBoard_shouldnot_fail() {
        b.resetBoard();
        Point p = new Point(0, 0);
        for (int l = 0 ; l < 2 ; l++){
            for (int c = 0 ; c < 6 ; c++){
                p.setCoordinates(c, l);
                Assertions.assertEquals(4, b.getBoard().getSlotSeeds(p));
            }
        }
    }
}
