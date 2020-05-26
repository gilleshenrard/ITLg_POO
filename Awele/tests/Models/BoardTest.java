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
     * Check if getNext() returns the proper coordinates for next slot (within a row)
     */
    @DisplayName("getNext() within a row - should not fail")
    @Test
    void getNext_once_withinARow_shouldnot_fail() {
        Point p = new Point(2, 0);
        Assertions.assertEquals(3, b.getNext(p).getX());
        Assertions.assertEquals(0, b.getNext(p).getY());
    }

    /**
     * Check if getNext() returns the proper coordinates for next slot (end of the first row)
     */
    @DisplayName("getNext() at the end of 1st row - should not fail")
    @Test
    void getNext_once_endOfFirstRow_shouldnot_fail() {
        Point p = new Point(5, 0);
        Assertions.assertEquals(0, b.getNext(p).getX());
        Assertions.assertEquals(1, b.getNext(p).getY());
    }

    /**
     * Check if getNext() returns the proper coordinates for next slot (end of the 2nd row)
     */
    @DisplayName("getNext() at the end of 2nd row - should not fail")
    @Test
    void getNext_once_endOfSecondRow_shouldnot_fail() {
        Point p = new Point(5, 1);
        Assertions.assertEquals(0, b.getNext(p).getX());
        Assertions.assertEquals(0, b.getNext(p).getY());
    }

    /**
     * Check if getSubsequent() returns the proper coordinates for 14th subsequent slot
     */
    @DisplayName("getSubsequent() {0,0} plus 14 - should not fail")
    @Test
    void getSubsequent_00plus14_shouldnot_fail() {
        Point p = new Point(0, 0);
        Assertions.assertEquals(3, b.getSubsequent(p, 14).getX());
        Assertions.assertEquals(0, b.getSubsequent(p, 14).getY());
    }

    /**
     * Check if getSubsequent() returns the proper coordinates for 10th subsequent slot
     */
    @DisplayName("getSubsequent() {3,0} plus 10 - should not fail")
    @Test
    void getSubsequent_30plus14_shouldnot_fail() {
        Point p = new Point(3, 0);
        Assertions.assertEquals(1, b.getSubsequent(p, 10).getX());
        Assertions.assertEquals(0, b.getSubsequent(p, 10).getY());
    }

    /**
     * Check if getSubsequent() returns the proper coordinates for 14th subsequent slot
     */
    @DisplayName("getSubsequent() {5,0} plus 14 - should not fail")
    @Test
    void getSubsequent_50plus14_shouldnot_fail() {
        Point p = new Point(5, 0);
        Assertions.assertEquals(2, b.getSubsequent(p, 14).getX());
        Assertions.assertEquals(1, b.getSubsequent(p, 14).getY());
    }

    /**
     * Check if getSubsequent() returns the proper coordinates for 40th subsequent slot
     */
    @DisplayName("getSubsequent() {5,0} plus 40 - should not fail")
    @Test
    void getSubsequent_50plus40_shouldnot_fail() {
        Point p = new Point(5, 0);
        Assertions.assertEquals(0, b.getSubsequent(p, 40).getX());
        Assertions.assertEquals(0, b.getSubsequent(p, 40).getY());
    }

    /**
     * Check if getSubsequent() returns the proper coordinates for 12th subsequent slot
     */
    @DisplayName("getSubsequent() {3,0} plus 12 - should not fail")
    @Test
    void getSubsequent_30plus12_shouldnot_fail() {
        Point p = new Point(3, 0);
        Assertions.assertEquals(4, b.getSubsequent(p, 12).getX());
        Assertions.assertEquals(0, b.getSubsequent(p, 12).getY());
    }

    /**
     * Check if getSubsequent() returns the proper coordinates for 23th subsequent slot
     */
    @DisplayName("getSubsequent() {3,0} plus 23 - should not fail")
    @Test
    void getSubsequent_30plus23_shouldnot_fail() {
        Point p = new Point(3, 0);
        Assertions.assertEquals(4, b.getSubsequent(p, 12).getX());
        Assertions.assertEquals(0, b.getSubsequent(p, 12).getY());
    }

    /**
     * Check if getPrevious() returns the proper coordinates for previous slot (within a row)
     */
    @DisplayName("getPrevious() within a row - should not fail")
    @Test
    void getPrevious_withinARow_shouldnot_fail() {
        Point p = new Point(2, 0);
        Assertions.assertEquals(1, b.getPrevious(p).getX());
        Assertions.assertEquals(0, b.getPrevious(p).getY());
    }

    /**
     * Check if getPrevious() returns the proper coordinates for previous slot (beginning of the first row)
     */
    @DisplayName("getPrevious() at the beginning of 1st row - should not fail")
    @Test
    void getPrevious_endOfFirstRow_shouldnot_fail() {
        Point p = new Point(0, 0);
        Assertions.assertEquals(5, b.getPrevious(p).getX());
        Assertions.assertEquals(1, b.getPrevious(p).getY());
    }

    /**
     * Check if getPrevious() returns the proper coordinates for previous slot (beginning of the 2nd row)
     */
    @DisplayName("getPrevious() at the beginning of 2nd row - should not fail")
    @Test
    void getPrevious_endOfSecondRow_shouldnot_fail() {
        Point p = new Point(0, 1);
        Assertions.assertEquals(5, b.getPrevious(p).getX());
        Assertions.assertEquals(0, b.getPrevious(p).getY());
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

    /**
     * Check if getFinalSeeds() returns the proper value
     */
    @DisplayName("getFinalSeeds() from {2,0} with 20 seeds - should not fail")
    @Test
    void getFinalSeeds_02plus20_shouldnot_fail() {
        Point p = new Point(2, 0);
        b.setSlotSeeds(new Point(4, 1), 1);
        int ret = b.getFinalSeeds(p, b.getSubsequent(p, 20), new Point(4, 1), 20);
        Assertions.assertEquals(3, ret);
    }

    /**
     * Check if getFinalSeeds() returns the proper value
     */
    @DisplayName("getFinalSeeds() from {5,0} with 40 seeds - should not fail")
    @Test
    void getFinalSeeds_05plus40_shouldnot_fail() {
        Point p = new Point(5, 0);
        b.setSlotSeeds(new Point(3, 1), 1);
        int ret = b.getFinalSeeds(p, b.getSubsequent(p, 40), new Point(3, 1), 40);
        Assertions.assertEquals(5, ret);
    }

    /**
     * Check if getFinalSeeds() returns the proper value
     */
    @DisplayName("getFinalSeeds() from {0,0} with 5 seeds - should not fail")
    @Test
    void getFinalSeeds_00plus5_shouldnot_fail() {
        Point p = new Point(0, 0);
        b.setSlotSeeds(new Point(5, 0), 1);
        int ret = b.getFinalSeeds(p, b.getSubsequent(p, 5), new Point(5, 0), 5);
        Assertions.assertEquals(2, ret);
    }

    /**
     * Check if getFinalSeeds() returns the proper value
     */
    @DisplayName("getFinalSeeds() from {3,0} with 15 seeds - should not fail")
    @Test
    void getFinalSeeds_30plus15_shouldnot_fail() {
        b.setSlotSeeds(new Point(0, 0), 0);
        b.setSlotSeeds(new Point(1, 0), 3);
        b.setSlotSeeds(new Point(2, 0), 2);
        b.setSlotSeeds(new Point(3, 0), 15);
        b.setSlotSeeds(new Point(4, 0), 1);
        b.setSlotSeeds(new Point(5, 0), 0);
        b.setSlotSeeds(new Point(0, 1), 0);
        b.setSlotSeeds(new Point(1, 1), 5);
        b.setSlotSeeds(new Point(2, 1), 1);
        b.setSlotSeeds(new Point(3, 1), 4);
        b.setSlotSeeds(new Point(4, 1), 3);
        b.setSlotSeeds(new Point(5, 1), 1);
        Assertions.assertEquals(1, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 15), new Point(0, 0), 15));
        Assertions.assertEquals(4, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 15), new Point(1, 0), 15));
        Assertions.assertEquals(3, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 15), new Point(2, 0), 15));
        Assertions.assertEquals(0, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 15), new Point(3, 0), 15));
        Assertions.assertEquals(3, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 15), new Point(4, 0), 15));
        Assertions.assertEquals(2, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 15), new Point(5, 0), 15));
        Assertions.assertEquals(2, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 15), new Point(0, 1), 15));
        Assertions.assertEquals(7, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 15), new Point(1, 1), 15));
        Assertions.assertEquals(2, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 15), new Point(2, 1), 15));
        Assertions.assertEquals(5, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 15), new Point(3, 1), 15));
        Assertions.assertEquals(4, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 15), new Point(4, 1), 15));
        Assertions.assertEquals(2, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 15), new Point(5, 1), 15));
    }

    /**
     * Check if getFinalSeeds() returns the proper value
     */
    @DisplayName("getFinalSeeds() from {3,0} with 9 seeds - should not fail")
    @Test
    void getFinalSeeds_30plus9_shouldnot_fail() {
        b.setSlotSeeds(new Point(0, 0), 0);
        b.setSlotSeeds(new Point(1, 0), 3);
        b.setSlotSeeds(new Point(2, 0), 2);
        b.setSlotSeeds(new Point(3, 0), 9);
        b.setSlotSeeds(new Point(4, 0), 1);
        b.setSlotSeeds(new Point(5, 0), 0);
        b.setSlotSeeds(new Point(0, 1), 0);
        b.setSlotSeeds(new Point(1, 1), 5);
        b.setSlotSeeds(new Point(2, 1), 1);
        b.setSlotSeeds(new Point(3, 1), 4);
        b.setSlotSeeds(new Point(4, 1), 3);
        b.setSlotSeeds(new Point(5, 1), 1);
        Assertions.assertEquals(1, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 9), new Point(0, 0), 9));
        Assertions.assertEquals(3, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 9), new Point(1, 0), 9));
        Assertions.assertEquals(2, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 9), new Point(2, 0), 9));
        Assertions.assertEquals(0, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 9), new Point(3, 0), 9));
        Assertions.assertEquals(2, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 9), new Point(4, 0), 9));
        Assertions.assertEquals(1, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 9), new Point(5, 0), 9));
        Assertions.assertEquals(1, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 9), new Point(0, 1), 9));
        Assertions.assertEquals(6, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 9), new Point(1, 1), 9));
        Assertions.assertEquals(2, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 9), new Point(2, 1), 9));
        Assertions.assertEquals(5, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 9), new Point(3, 1), 9));
        Assertions.assertEquals(4, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 9), new Point(4, 1), 9));
        Assertions.assertEquals(2, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 9), new Point(5, 1), 9));
    }

    /**
     * Check if getFinalSeeds() returns the proper value
     */
    @DisplayName("getFinalSeeds() from {3,0} with 6 seeds - should not fail")
    @Test
    void getFinalSeeds_30plus6_shouldnot_fail() {
        b.setSlotSeeds(new Point(0, 0), 0);
        b.setSlotSeeds(new Point(1, 0), 3);
        b.setSlotSeeds(new Point(2, 0), 2);
        b.setSlotSeeds(new Point(3, 0), 6);
        b.setSlotSeeds(new Point(4, 0), 1);
        b.setSlotSeeds(new Point(5, 0), 0);
        b.setSlotSeeds(new Point(0, 1), 0);
        b.setSlotSeeds(new Point(1, 1), 5);
        b.setSlotSeeds(new Point(2, 1), 1);
        b.setSlotSeeds(new Point(3, 1), 4);
        b.setSlotSeeds(new Point(4, 1), 3);
        b.setSlotSeeds(new Point(5, 1), 1);
        Assertions.assertEquals(0, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 6), new Point(0, 0), 6));
        Assertions.assertEquals(3, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 6), new Point(1, 0), 6));
        Assertions.assertEquals(2, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 6), new Point(2, 0), 6));
        Assertions.assertEquals(0, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 6), new Point(3, 0), 6));
        Assertions.assertEquals(2, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 6), new Point(4, 0), 6));
        Assertions.assertEquals(1, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 6), new Point(5, 0), 6));
        Assertions.assertEquals(1, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 6), new Point(0, 1), 6));
        Assertions.assertEquals(6, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 6), new Point(1, 1), 6));
        Assertions.assertEquals(2, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 6), new Point(2, 1), 6));
        Assertions.assertEquals(5, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 6), new Point(3, 1), 6));
        Assertions.assertEquals(3, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 6), new Point(4, 1), 6));
        Assertions.assertEquals(1, b.getFinalSeeds(new Point(3, 0), b.getSubsequent(new Point(3, 0), 6), new Point(5, 1), 6));
    }
}
