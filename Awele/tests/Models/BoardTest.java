package Models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Board b = new Board();

    /**
     * Check that getSlots() returns a default Slot[2][6]
     */
    @Test
    void getSlots_shouldnot_fail() {
        Slot s[][] = b.getSlots();
        Slot buffer = new Slot(0,0);

        for(int l=0 ; l<1 ; l++){
            for(int c=0 ; c<6 ; c++){
                buffer.setCoordinates(c, l);
                Assertions.assertEquals(s[l][c], buffer);
            }
        }
    }

    /**
     * Check if validateCoordinates() throws an exception with a negative X
     */
    @Test
    void validateCoordinates_negativeX_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            Board.validateCoordinates(-1, 0, "");
        });
    }

    /**
     * Check if validateCoordinates() throws an exception with a negative Y
     */
    @Test
    void validateCoordinates_negativeY_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            Board.validateCoordinates(0, -1, "");
        });
    }

    /**
     * Check if validateCoordinates() throws an exception with X above 5
     */
    @Test
    void validateCoordinates_xAbove5_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            Board.validateCoordinates(6, 0, "");
        });
    }

    /**
     * Check if validateCoordinates() throws an exception with Y above 1
     */
    @Test
    void validateCoordinates_yAbove1_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            Board.validateCoordinates(0, 2, "");
        });
    }

    /**
     * Check if validateCoordinates() fails to validate maximum values
     */
    @Test
    void validateCoordinates_maxValues_shouldnot_fail() {
            Board.validateCoordinates(5, 1, "");
    }

    /**
     * Check if validateCoordinates() fails to validate minimum values
     */
    @Test
    void validateCoordinates_minValues_shouldnot_fail() {
        Board.validateCoordinates(0, 0, "");
    }

    /**
     * Check if getRemainingSeeds() throws an exception with an invalid ID
     */
    @Test
    void getRemainingSeeds_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.getRemainingSeeds(0);
        });
    }

    /**
     * Check if getStoredSeeds() throws an exception with an invalid ID
     */
    @Test
    void getStoredSeeds_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.getStoredSeeds(0);
        });
    }

    /**
     * Check if setRemainingSeeds() throws an exception with an invalid ID
     */
    @Test
    void setRemainingSeeds_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.setRemainingSeeds(0, 0);
        });
    }

    /**
     * Check if setRemainingSeeds() throws an exception with a negative value
     */
    @Test
    void setRemainingSeeds_negativeValue_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.setRemainingSeeds(1, -1);
        });
    }

    /**
     * Check if setRemainingSeeds() throws an exception with a value above 48
     */
    @Test
    void setRemainingSeeds_above48_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.setRemainingSeeds(1, 49);
        });
    }

    /**
     * Check if getRemainingSeeds() returns the proper amount
     */
    @Test
    void getRemainingSeeds_shouldnot_fail() {
        Assertions.assertEquals(b.getRemainingSeeds(1), 24);
    }

    /**
     * Check if getSlot() throws an exception with an X value over 5
     */
    @Test
    void getSlot_Xover5_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.getSlot(6, 0);
        });
    }

    /**
     * Check if getSlot() throws an exception with an invalid Y value
     */
    @Test
    void getSlot_invalidY_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.getSlot(0, 3);
        });
    }

    /**
     * Check if getSlot() throws an exception with an X value below 0
     */
    @Test
    void getSlot_Xbelow0_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.getSlot(-1, 0);
        });
    }

    /**
     * Check if getSlot() fails returning a slot
     */
    @Test
    void getSlot_shouldnot_fail() {
        b.getSlot(0, 0);
    }

    /**
     * Check if getSlotSeeds() throws an exception with an X value over 5
     */
    @Test
    void getSloSeeds_Xover5_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.getSlotSeeds(6, 0);
        });
    }

    /**
     * Check if getSlotSeeds() throws an exception with an invalid Y value
     */
    @Test
    void getSlotSeeds_invalidY_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.getSlotSeeds(0, 3);
        });
    }

    /**
     * Check if getSlotSeeds() throws an exception with an X value below 0
     */
    @Test
    void getSlotSeeds_Xbelow0_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.getSlotSeeds(-1, 0);
        });
    }

    /**
     * Check if getSlotSeeds() fails returning the amount of seeds
     */
    @Test
    void getSlotSeeds_shouldnot_fail() {
        Assertions.assertEquals(4, b.getSlot(0, 0).getNbSeeds());
    }

    /**
     * Check if getRemainingSeeds() returns the proper coordinates for next slot (within a row)
     */
    @Test
    void getNext_withinARow_shouldnot_fail() {
        Slot s = b.getSlot(2, 0);
        Assertions.assertEquals(b.getNext(s).getX(), 3);
        Assertions.assertEquals(b.getNext(s).getY(), 0);
    }

    /**
     * Check if getRemainingSeeds() returns the proper coordinates for next slot (end of the first row)
     */
    @Test
    void getNext_endOfFirstRow_shouldnot_fail() {
        Slot s = b.getSlot(5, 0);
        Assertions.assertEquals(b.getNext(s).getX(), 0);
        Assertions.assertEquals(b.getNext(s).getY(), 1);
    }

    /**
     * Check if getRemainingSeeds() returns the proper coordinates for next slot (end of the 2nd row)
     */
    @Test
    void getNext_endOfSecondRow_shouldnot_fail() {
        Slot s = b.getSlot(5, 1);
        Assertions.assertEquals(b.getNext(s).getX(), 0);
        Assertions.assertEquals(b.getNext(s).getY(), 0);
    }

    /**
     * Check if playSlot() returns a cancellation code when selecting a slot with no seed
     */
    @Test
    void playSlot_0seeds_shouldnot_fail() {
        b.getSlot(0, 0).setNbSeeds(0);
        int ret = b.playSlot(1, 1);
        Assertions.assertEquals(3, ret);
    }

    /**
     * Check if getSlot() throws an exception with an invalid ID
     */
    @Test
    void playSlot_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.playSlot(3, 3);
        });
    }

    /**
     * Check if getSlot() throws an exception with a slot below 1
     */
    @Test
    void playSlot_below1_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.playSlot(1, 0);
        });
    }

    /**
     * Check if getSlot() throws an exception with a slot above 6
     */
    @Test
    void playSlot_above6_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.playSlot(1, 7);
        });
    }

    /**
     * Check if playSlot() processes a simple scattering properly (no capture, no starvation)
     */
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
     * Check if resetBoard() sets the proper inial values
     */
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
}
