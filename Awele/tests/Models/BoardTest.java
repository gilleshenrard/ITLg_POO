package Models;

import ITLg.POO.GillesHenrard.Awele.Models.Board;
import ITLg.POO.GillesHenrard.Awele.Models.Point;
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
            Board.validateCoordinates(new Point(-1, 0), "");
        });
    }

    /**
     * Check if validateCoordinates() throws an exception with a negative Y
     */
    @DisplayName("validateCoordinates() with negative Y - should fail")
    @Test
    void validateCoordinates_negativeY_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            Board.validateCoordinates(new Point(0, -1), "");
        });
    }

    /**
     * Check if validateCoordinates() throws an exception with X above 5
     */
    @DisplayName("validateCoordinates() with X above 5 - should fail")
    @Test
    void validateCoordinates_xAbove5_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            Board.validateCoordinates(new Point(6, 0), "");
        });
    }

    /**
     * Check if validateCoordinates() throws an exception with Y above 1
     */
    @DisplayName("validateCoordinates() with Y above 1 - should fail")
    @Test
    void validateCoordinates_yAbove1_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            Board.validateCoordinates(new Point(0, 2), "");
        });
    }

    /**
     * Check if validateCoordinates() fails to validate maximum values
     */
    @DisplayName("validateCoordinates() with maximum values - should not fail")
    @Test
    void validateCoordinates_maxValues_shouldnot_fail() {
            Board.validateCoordinates(new Point(5, 1), "");
    }

    /**
     * Check if validateCoordinates() fails to validate minimum values
     */
    @DisplayName("validateCoordinates() with minimum values - should not fail")
    @Test
    void validateCoordinates_minValues_shouldnot_fail() {
        Board.validateCoordinates(new Point(0, 0), "");
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
     * Check if setRemainingSeeds() sets the proper value
     */
    @DisplayName("setRemainingSeeds() - should not fail")
    @Test
    void setRemainingSeeds_shouldnot_fail() {
        b.setRemainingSeeds(1, 6);
        Assertions.assertEquals(6, b.getRemainingSeeds(1));
    }

    /**
     * Check if addRemainingSeeds() throws an exception while adding above 48
     */
    @DisplayName("addRemainingSeeds() adding above 48 - should fail")
    @Test
    void addRemainingSeeds_above48_should_fail() {
        b.setRemainingSeeds(1, 40);
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.addRemainingSeeds(1, 10);
        });
    }

    /**
     * Check if addRemainingSeeds() while substracting below 0
     */
    @DisplayName("addRemainingSeeds() adding below 0 - should fail")
    @Test
    void addRemainingSeeds_below0_should_fail() {
        b.setRemainingSeeds(1, 5);
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.addRemainingSeeds(1, -7);
        });
    }

    /**
     * Check if addRemainingSeeds() sets the proper value
     */
    @DisplayName("addRemainingSeeds() - should not fail")
    @Test
    void addRemainingSeeds_shouldnot_fail() {
        b.setRemainingSeeds(1, 5);
        b.addRemainingSeeds(1, 6);
        Assertions.assertEquals(11, b.getRemainingSeeds(1));
    }

    /**
     * Check if removeRemainingSeeds() throws an exception while adding above 48
     */
    @DisplayName("removeRemainingSeeds() adding above 48 - should fail")
    @Test
    void removeRemainingSeeds_above48_should_fail() {
        b.setRemainingSeeds(1, 40);
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.removeRemainingSeeds(1, -10);
        });
    }

    /**
     * Check if removeRemainingSeeds() while substracting below 0
     */
    @DisplayName("removeRemainingSeeds() adding below 0 - should fail")
    @Test
    void removeRemainingSeeds_below0_should_fail() {
        b.setRemainingSeeds(1, 5);
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.removeRemainingSeeds(1, 7);
        });
    }

    /**
     * Check if removeRemainingSeeds() sets the proper value
     */
    @DisplayName("removeRemainingSeeds() - should not fail")
    @Test
    void removeRemainingSeeds_shouldnot_fail() {
        b.setRemainingSeeds(1, 6);
        b.removeRemainingSeeds(1, 5);
        Assertions.assertEquals(1, b.getRemainingSeeds(1));
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
     * Check if setSlotSeeds() throws an exception with an X value over 5
     */
    @DisplayName("setSlotSeeds() with X over 5 - should fail")
    @Test
    void setSlotSeeds_Xover5_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.setSlotSeeds(new Point(6, 0), 0);
        });
    }

    /**
     * Check if setSlotSeeds() throws an exception with an invalid Y value
     */
    @DisplayName("setSlotSeeds() with Y over 1 - should fail")
    @Test
    void setSlotSeeds_invalidY_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.setSlotSeeds(new Point(0, 3), 0);
        });
    }

    /**
     * Check if setSlotSeeds() throws an exception with an X value below 0
     */
    @DisplayName("setSlotSeeds() with X below 0 - should fail")
    @Test
    void setSlotSeeds_Xbelow0_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.setSlotSeeds(new Point(-1, 0), 0);
        });
    }

    /**
     * Check if setSlotSeeds() sets the proper value
     */
    @DisplayName("setSlotSeeds() with proper values - should not fail")
    @Test
    void setSlotSeeds_shouldnot_fail() {
        b.setSlotSeeds(new Point(0, 0), 3);
        Assertions.assertEquals(3, b.getSlotSeeds(new Point(0, 0)));
    }

    /**
     * Check if setSlotSeeds() throws an exception when giving a negative amount of seeds
     */
    @DisplayName("setSlotSeeds() with negative seeds - should fail")
    @Test
    void setSlotSeeds_negative_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.setSlotSeeds(new Point(0, 0), -1);
        });
    }

    /**
     * Check if setSlotSeeds() throws an exception when giving an amount of seeds above 48
     */
    @DisplayName("setSlotSeeds() with seeds above 48 - should fail")
    @Test
    void setSlotSeeds_above48_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.setSlotSeeds(new Point(0, 0), 49);
        });
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
     * Check if emptySlotSeeds() properly sets slots seeds to 0
     */
    @DisplayName("emptySlotSeeds() - should not fail")
    @Test
    void emptySlotSeeds_shouldnot_fail() {
        b.emptySlotSeeds(new Point(0, 0));
        Assertions.assertEquals(0, b.getSlotSeeds(new Point(0, 0)));
    }

    /**
     * Check if incrementSlotSeeds() properly increments m_nbseeds
     */
    @DisplayName("incrementSlotSeeds() - should not fail")
    @Test
    void incrementSlotSeeds_shouldnot_fail() {
        b.incrementSlotSeeds(new Point(0, 0));
        Assertions.assertEquals(5, b.getSlotSeeds(new Point(0, 0)));
    }

    /**
     * Check if incrementSlotSeeds() throws an exception when rising m_nbseeds above 48 (max seeds on the board)
     */
    @DisplayName("incrementSlotSeeds() when incrementing above 48 - should fail")
    @Test
    void incrementSlotSeeds_above48_should_fail() {
        b.setSlotSeeds(new Point(0, 0), 48);

        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.incrementSlotSeeds(new Point(0, 0));
        });
    }

    /**
     * Check if decrementSlotSeeds() properly decrements m_nbseeds
     */
    @DisplayName("decrementSlotSeeds() - should not fail")
    @Test
    void decrementSlotSeeds_shouldnot_fail() {
        b.decrementSlotSeeds(new Point(0, 0));
        Assertions.assertEquals(3, b.getSlotSeeds(new Point(0, 0)));
    }

    /**
     * Check if decrementSlotSeeds() throws an exception when lowering m_nbseeds below 0
     */
    @DisplayName("decrementSlotSeeds() when decrementing below 0 - should fail")
    @Test
    void decrementSlotSeeds_negativeamount_should_fail() {
        b.emptySlotSeeds(new Point(0, 0));
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.decrementSlotSeeds(new Point(0, 0));
        });
    }


    /**
     * Check if setStoredSeeds() sets the proper value to stored seeds
     */
    @DisplayName("setStoredSeeds() - should not fail")
    @Test
    void setStoredSeeds_shouldnot_fail() {
        b.setStoredSeeds(1, 15);
        Assertions.assertEquals(b.getStoredSeeds(1), 15);
    }

    /**
     * Check if setStoredSeeds() throws an exception with an invalid ID
     */
    @DisplayName("setStoredSeeds() with an invalid ID - should fail")
    @Test
    void setStoredSeeds_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.setStoredSeeds(3, 15);
        });
    }

    /**
     * Check if setStoredSeeds() throws an exception with an amount of seeds above 23
     */
    @DisplayName("setStoredSeeds() with an invalid ID - should fail")
    @Test
    void setStoredSeeds_SeedsAbove23_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.setStoredSeeds(1, 49);
        });
    }

    /**
     * Check if setStoredSeeds() throws an exception with a negative amount of seeds
     */
    @DisplayName("setStoredSeeds() with negative nb_seeds - should fail")
    @Test
    void setStoredSeeds_negativeSeedsNb_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.setStoredSeeds(1, -2);
        });
    }

    /**
     * Check if getStoredSeeds() throws an exception with an invalid ID
     */
    @DisplayName("getStoredSeeds() with an invalid ID - should fail")
    @Test
    void getStoredSeeds_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.getStoredSeeds(3);
        });
    }

    /**
     * Check if getStoredSeeds() returns the right amount of seeds
     */
    @DisplayName("getStoredSeeds() - should not fail")
    @Test
    void getStoredSeeds_shouldnot_fail() {
        b.setStoredSeeds(1, 12);
        Assertions.assertEquals(b.getStoredSeeds(1), 12);
    }

    /**
     * Check if getNext() returns the proper coordinates for next slot (within a row)
     */
    @DisplayName("getNext() within a row - should not fail")
    @Test
    void getNext_withinARow_shouldnot_fail() {
        Point p = new Point(2, 0);
        Assertions.assertEquals(3, b.getNext(p).getX());
        Assertions.assertEquals(0, b.getNext(p).getY());
    }

    /**
     * Check if getNext() returns the proper coordinates for next slot (end of the first row)
     */
    @DisplayName("getNext() at the end of 1st row - should not fail")
    @Test
    void getNext_endOfFirstRow_shouldnot_fail() {
        Point p = new Point(5, 0);
        Assertions.assertEquals(0, b.getNext(p).getX());
        Assertions.assertEquals(1, b.getNext(p).getY());
    }

    /**
     * Check if getNext() returns the proper coordinates for next slot (end of the 2nd row)
     */
    @DisplayName("getNext() at the end of 2nd row - should not fail")
    @Test
    void getNext_endOfSecondRow_shouldnot_fail() {
        Point p = new Point(5, 1);
        Assertions.assertEquals(0, b.getNext(p).getX());
        Assertions.assertEquals(0, b.getNext(p).getY());
    }

    /**
     * Check if getNonEmpty() sets the proper inial values
     */
    @DisplayName("getNonEmpty() - should not fail")
    @Test
    void getNonEmpty_shouldnot_fail() {
        b.emptySlotSeeds(new Point(0, 0));
        b.emptySlotSeeds(new Point(1, 0));
        b.setSlotSeeds(new Point(2, 0), 1);
        b.emptySlotSeeds(new Point(3, 0));
        b.setSlotSeeds(new Point(4, 0), 3);
        b.setSlotSeeds(new Point(5, 0), 4);
        ArrayList<Integer> array = b.getNonEmpty(1);
        Assertions.assertEquals(3, array.size());
        Assertions.assertEquals(2, array.get(0));
        Assertions.assertEquals(4, array.get(1));
        Assertions.assertEquals(5, array.get(2));
    }
}
