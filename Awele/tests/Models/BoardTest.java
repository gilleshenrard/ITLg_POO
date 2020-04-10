package Models;

import org.junit.jupiter.api.Assertions;
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
     * Check if getRemainingSeeds() throws an exception with an invalid ID
     */
    @Test
    void getRemainingSeeds_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.getRemainingSeeds(0);
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
     * Check if getSlot() fails returning a slot
     */
    @Test
    void getSlot_shouldnot_fail() {
        b.getSlot(0, 0);
    }
}