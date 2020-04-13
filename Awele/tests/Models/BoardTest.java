package Models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.util.ArrayList;

class BoardTest {
    Board b = new Board();

    /**
     * Check if validateCoordinates() throws an exception with a negative X
     */
    @DisplayName("validateCoordinates() with negative X - should fail")
    @Test
    void validateCoordinates_negativeX_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            Board.validateCoordinates(-1, 0, "");
        });
    }

    /**
     * Check if validateCoordinates() throws an exception with a negative Y
     */
    @DisplayName("validateCoordinates() with negative Y - should fail")
    @Test
    void validateCoordinates_negativeY_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            Board.validateCoordinates(0, -1, "");
        });
    }

    /**
     * Check if validateCoordinates() throws an exception with X above 5
     */
    @DisplayName("validateCoordinates() with X above 5 - should fail")
    @Test
    void validateCoordinates_xAbove5_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            Board.validateCoordinates(6, 0, "");
        });
    }

    /**
     * Check if validateCoordinates() throws an exception with Y above 1
     */
    @DisplayName("validateCoordinates() with Y above 1 - should fail")
    @Test
    void validateCoordinates_yAbove1_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            Board.validateCoordinates(0, 2, "");
        });
    }

    /**
     * Check if validateCoordinates() fails to validate maximum values
     */
    @DisplayName("validateCoordinates() with maximum values - should not fail")
    @Test
    void validateCoordinates_maxValues_shouldnot_fail() {
            Board.validateCoordinates(5, 1, "");
    }

    /**
     * Check if validateCoordinates() fails to validate minimum values
     */
    @DisplayName("validateCoordinates() with minimum values - should not fail")
    @Test
    void validateCoordinates_minValues_shouldnot_fail() {
        Board.validateCoordinates(0, 0, "");
    }

    /**
     * Check if getRemainingSeeds() throws an exception with an invalid ID
     */
    @DisplayName("getRemainingSeeds() with invalid ID - should fail")
    @Test
    void getRemainingSeeds_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.getRemainingSeeds(0);
        });
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
     * Check if setRemainingSeeds() throws an exception with an invalid ID
     */
    @DisplayName("setRemainingSeeds() with invalid ID - should fail")
    @Test
    void setRemainingSeeds_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.setRemainingSeeds(0, 0);
        });
    }

    /**
     * Check if setRemainingSeeds() throws an exception with a negative value
     */
    @DisplayName("setRemainingSeeds() with negative value - should fail")
    @Test
    void setRemainingSeeds_negativeValue_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.setRemainingSeeds(1, -1);
        });
    }

    /**
     * Check if setRemainingSeeds() throws an exception with a value above 48
     */
    @DisplayName("setRemainingSeeds() with value above 48 - should fail")
    @Test
    void setRemainingSeeds_above48_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.setRemainingSeeds(1, 49);
        });
    }

    /**
     * Check if getRemainingSeeds() returns the proper amount
     */
    @DisplayName("getRemainingSeeds() - should not fail")
    @Test
    void getRemainingSeeds_shouldnot_fail() {
        Assertions.assertEquals(b.getRemainingSeeds(1), 24);
    }

    /**
     * Check if getSlot() throws an exception with an X value over 5
     */
    @DisplayName("getSlot() with X over 5 - should fail")
    @Test
    void getSlot_Xover5_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.getSlot(6, 0);
        });
    }

    /**
     * Check if getSlot() throws an exception with an invalid Y value
     */
    @DisplayName("getSlot() with Y over 1 - should fail")
    @Test
    void getSlot_invalidY_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.getSlot(0, 3);
        });
    }

    /**
     * Check if getSlot() throws an exception with an X value below 0
     */
    @DisplayName("getSlot() with X below 0 - should fail")
    @Test
    void getSlot_Xbelow0_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.getSlot(-1, 0);
        });
    }

    /**
     * Check if getSlot() fails returning a slot
     */
    @DisplayName("getSlot() with proper values - should not fail")
    @Test
    void getSlot_shouldnot_fail() {
        b.getSlot(0, 0);
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
        Assertions.assertEquals(4, b.getSlot(0, 0).getNbSeeds());
    }

    /**
     * Check if getNext() returns the proper coordinates for next slot (within a row)
     */
    @DisplayName("getNext() within a row - should not fail")
    @Test
    void getNext_withinARow_shouldnot_fail() {
        Slot s = b.getSlot(2, 0);
        Assertions.assertEquals(3, b.getNext(s).getX());
        Assertions.assertEquals(0, b.getNext(s).getY());
    }

    /**
     * Check if getNext() returns the proper coordinates for next slot (end of the first row)
     */
    @DisplayName("getNext() at the end of 1st row - should not fail")
    @Test
    void getNext_endOfFirstRow_shouldnot_fail() {
        Slot s = b.getSlot(5, 0);
        Assertions.assertEquals(0, b.getNext(s).getX());
        Assertions.assertEquals(1, b.getNext(s).getY());
    }

    /**
     * Check if getNext() returns the proper coordinates for next slot (end of the 2nd row)
     */
    @DisplayName("getNext() at the end of 2nd row - should not fail")
    @Test
    void getNext_endOfSecondRow_shouldnot_fail() {
        Slot s = b.getSlot(5, 1);
        Assertions.assertEquals(0, b.getNext(s).getX());
        Assertions.assertEquals(0, b.getNext(s).getY());
    }

    /**
     * Check if playSlot() returns a cancellation code when selecting a slot with no seed
     */
    @DisplayName("playSlot() with an empty slot - should not fail")
    @Test
    void playSlot_0seeds_shouldnot_fail() {
        b.getSlot(0, 0).setNbSeeds(0);
        int ret = b.playSlot(1, 1);
        Assertions.assertEquals(3, ret);
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
        Assertions.assertEquals(0, b.getSlot(5, 0).getNbSeeds());
        Assertions.assertEquals(5, b.getSlot(0, 1).getNbSeeds());
        Assertions.assertEquals(5, b.getSlot(1, 1).getNbSeeds());
        Assertions.assertEquals(5, b.getSlot(2, 1).getNbSeeds());
        Assertions.assertEquals(5, b.getSlot(3, 1).getNbSeeds());
        Assertions.assertEquals(4, b.getSlot(4, 1).getNbSeeds());
        Assertions.assertEquals(0, Game.getInstance().getSeeds(1));
        Assertions.assertEquals(20, b.getRemainingSeeds(1));
        Assertions.assertEquals(28, b.getRemainingSeeds(2));
    }

    /**
     * Check if playSlot() properly skips the selected slot when scattering
     */
    @DisplayName("playSlot() with a slot skipped at scattering - should not fail")
    @Test
    void playSlot_skipSelected_shouldnot_fail() {
        Game g = Game.getInstance();
        g.setBoard(b);
        g.resetGame();
        b.getSlot(3, 0).setNbSeeds(12);
        b.getSlot(2, 0).emptySeeds();
        b.getSlot(1, 0).emptySeeds();
        int ret = b.playSlot(1, 4);
        Assertions.assertEquals(0, ret);
        Assertions.assertEquals(5, b.getSlot(0, 1).getNbSeeds());
        Assertions.assertEquals(5, b.getSlot(1, 1).getNbSeeds());
        Assertions.assertEquals(5, b.getSlot(2, 1).getNbSeeds());
        Assertions.assertEquals(5, b.getSlot(3, 1).getNbSeeds());
        Assertions.assertEquals(5, b.getSlot(4, 1).getNbSeeds());
        Assertions.assertEquals(5, b.getSlot(5, 1).getNbSeeds());
        Assertions.assertEquals(5, b.getSlot(0, 0).getNbSeeds());
        Assertions.assertEquals(1, b.getSlot(1, 0).getNbSeeds());
        Assertions.assertEquals(1, b.getSlot(2, 0).getNbSeeds());
        Assertions.assertEquals(0, b.getSlot(3, 0).getNbSeeds());
        Assertions.assertEquals(6, b.getSlot(4, 0).getNbSeeds());
        Assertions.assertEquals(5, b.getSlot(5, 0).getNbSeeds());
        Assertions.assertEquals(0, Game.getInstance().getSeeds(1));
        Assertions.assertEquals(30, b.getRemainingSeeds(2));
        Assertions.assertEquals(18, b.getRemainingSeeds(1));
    }

    /**
     * Check if playSlot() processes a simple scattering properly (2 captures, no starvation)
     */
    @DisplayName("playSlot() with a capture case - should not fail")
    @Test
    void playSlot_CaptureNoStarve_shouldnot_fail() {
        b.getSlot(1, 1).setNbSeeds(2);
        b.getSlot(3, 1).setNbSeeds(1);
        b.getSlot(4, 1).setNbSeeds(9);
        int ret = b.playSlot(1, 6);
        Assertions.assertEquals(0, ret);
        Assertions.assertEquals(5, b.getSlot(0, 1).getNbSeeds());
        Assertions.assertEquals(0, b.getSlot(1, 1).getNbSeeds());
        Assertions.assertEquals(5, b.getSlot(2, 1).getNbSeeds());
        Assertions.assertEquals(0, b.getSlot(3, 1).getNbSeeds());
        Assertions.assertEquals(9, b.getSlot(4, 1).getNbSeeds());
        Assertions.assertEquals(4, b.getSlot(5, 1).getNbSeeds());
        Assertions.assertEquals(0, b.getSlot(5, 0).getNbSeeds());
        Assertions.assertEquals(5, Game.getInstance().getSeeds(1));
        Assertions.assertEquals(23, b.getRemainingSeeds(2));
        Assertions.assertEquals(20, b.getRemainingSeeds(1));
    }

    /**
     * Check if playSlot() processes a starvation properly
     */
    @DisplayName("playSlot() with a starvation case - should not fail")
    @Test
    void playSlot_noCaptureStarve_shouldnot_fail() {
        b.getSlot(0, 1).setNbSeeds(1);
        b.getSlot(1, 1).setNbSeeds(2);
        b.getSlot(2, 1).emptySeeds();
        b.getSlot(3, 1).emptySeeds();
        b.getSlot(4, 1).emptySeeds();
        b.getSlot(5, 1).emptySeeds();
        b.getSlot(5, 0).setNbSeeds(2);
        int ret = b.playSlot(1, 6);
        Assertions.assertEquals(2, ret);
        Assertions.assertEquals(1, b.getSlot(0, 1).getNbSeeds());
        Assertions.assertEquals(2, b.getSlot(1, 1).getNbSeeds());
        Assertions.assertEquals(0, b.getSlot(2, 1).getNbSeeds());
        Assertions.assertEquals(0, b.getSlot(3, 1).getNbSeeds());
        Assertions.assertEquals(0, b.getSlot(4, 1).getNbSeeds());
        Assertions.assertEquals(0, b.getSlot(5, 1).getNbSeeds());
        Assertions.assertEquals(3, b.getRemainingSeeds(2));
        Assertions.assertEquals(0, Game.getInstance().getSeeds(1));
    }

    /**
     * Check if playSlot() processes a victory season
     */
    @DisplayName("playSlot() with a victory case - should not fail")
    @Test
    void playSlot_victory_shouldnot_fail() {
        b.getSlot(3, 1).setNbSeeds(1);
        b.getSlot(1, 1).setNbSeeds(2);
        Game.getInstance().storeSeeds(1, 20);
        int ret = b.playSlot(1, 6);
        Assertions.assertEquals(1, ret);
        Assertions.assertEquals(5, b.getSlot(0, 1).getNbSeeds());
        Assertions.assertEquals(0, b.getSlot(1, 1).getNbSeeds());
        Assertions.assertEquals(5, b.getSlot(2, 1).getNbSeeds());
        Assertions.assertEquals(0, b.getSlot(3, 1).getNbSeeds());
        Assertions.assertEquals(4, b.getSlot(4, 1).getNbSeeds());
        Assertions.assertEquals(25, Game.getInstance().getSeeds(1));
    }

    /**
     * Check if playSlot() forbids a self-starvation
     */
    @DisplayName("playSlot() with self-starvation - should not fail")
    @Test
    void playSlot_selfStarvation_shouldnot_fail() {
        b.getSlot(0, 0).emptySeeds();
        b.getSlot(1, 0).emptySeeds();
        b.getSlot(2, 0).emptySeeds();
        b.getSlot(3, 0).emptySeeds();
        b.getSlot(4, 0).emptySeeds();
        b.getSlot(5, 0).setNbSeeds(1);
        b.setRemainingSeeds(1, 1);
        int ret = b.playSlot(1, 6);
        Assertions.assertEquals(2, ret);
        Assertions.assertEquals(0, b.getSlot(0, 0).getNbSeeds());
        Assertions.assertEquals(0, b.getSlot(1, 0).getNbSeeds());
        Assertions.assertEquals(0, b.getSlot(2, 0).getNbSeeds());
        Assertions.assertEquals(0, b.getSlot(3, 0).getNbSeeds());
        Assertions.assertEquals(0, b.getSlot(4, 0).getNbSeeds());
        Assertions.assertEquals(1, b.getSlot(5, 0).getNbSeeds());
        Assertions.assertEquals(1, b.getRemainingSeeds(1));
    }

    /**
     * Check if resetBoard() sets the proper inial values
     */
    @DisplayName("resetBoard() - should not fail")
    @Test
    void resetBoard_shouldnot_fail() {
        b.resetBoard();
        Assertions.assertEquals(24, b.getRemainingSeeds(1));
        Assertions.assertEquals(24, b.getRemainingSeeds(2));
        for (int l = 0 ; l < 2 ; l++){
            for (int c = 0 ; c < 6 ; c++){
                Assertions.assertEquals(4, b.getSlot(c, l).getNbSeeds());
            }
        }
    }

    /**
     * Check if getPlayableSlots() returns the proper playable slots
     */
    @DisplayName("getPlayableSlots() - should not fail")
    @Test
    void getPlayableSlots_shouldnot_fail() {
        b.getSlot(0, 1).emptySeeds();
        b.getSlot(2, 1).emptySeeds();
        b.getSlot(3, 1).emptySeeds();
        ArrayList<Integer> array = b.getPlayableSlots(2);
        Assertions.assertEquals(3, array.size());
        Assertions.assertEquals(2, array.get(0));
        Assertions.assertEquals(5, array.get(1));
        Assertions.assertEquals(6, array.get(2));
    }

    /**
     * Check if getPlayableSlots() throws an exception with an invalid ID
     */
    @DisplayName("getPlayableSlots() with an invalid ID - should fail")
    @Test
    void getPlayableSlots_above6_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.getPlayableSlots(3);
        });
    }
}
