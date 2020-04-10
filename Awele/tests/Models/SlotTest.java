package Models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

class SlotTest {
    Slot s = new Slot(0, 0);

    /**
     * Check if setCoordinates() fails while setting coordinates
     */
    @Test
    void setCoordinates_shouldnot_fail() {
        for(int l=0 ; l<2 ; l++)
            for(int c=0 ; c<6 ; c++)
                s.setCoordinates(c, l);
    }

    /**
     * Check if setCoordinates() throws an exception when using an invalid X value
     */
    @Test
    void setCoordinates_invalidX_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            s.setCoordinates(3, 5);
        });
    }

    /**
     * Check if setCoordinates() throws an exception when using a negative X value
     */
    @Test
    void setCoordinates_negativeX_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            s.setCoordinates(-3, 5);
        });
    }

    /**
     * Check if setCoordinates() throws an exception when using an invalid Y value
     */
    @Test
    void setCoordinates_invalidY_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            s.setCoordinates(0, 10);
        });
    }

    /**
     * Check if setCoordinates() throws an exception when using a negative X value
     */
    @Test
    void setCoordinates_negativeY_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            s.setCoordinates(0, -5);
        });
    }

    /**
     * Check if getX() and getY() return the right values
     */
    @Test
    void get_coordinates_shouldnot_fail() {
        s.setCoordinates(3, 1);
        Assertions.assertEquals(s.getX(), 3);
        Assertions.assertEquals(s.getY(), 1);
    }

    /**
     * Check if getNbSeeds() returns the proper value
     */
    @Test
    void getNbSeeds_default_shouldnot_fail() {
        Assertions.assertEquals(s.getNbSeeds(), 4);
    }

    /**
     * Check if emptySeeds() properly sets m_nbseeds to 0
     */
    @Test
    void emptySeeds_shouldnot_fail() {
        s.emptySeeds();
        Assertions.assertEquals(s.getNbSeeds(), 0);
    }

    /**
     * Check if incrementSeeds() properly increments m_nbseeds
     */
    @Test
    void incrementSeeds_shouldnot_fail() {
        s.incrementSeeds();
        Assertions.assertEquals(s.getNbSeeds(), 5);
    }

    /**
     * Check if incrementSeeds() throws an exception when rising m_nbseeds above 48 (max seeds on the board)
     */
    @Test
    void incrementSeeds_above48_should_fail() {
        for(int i=0 ; i<44 ; i++)
            s.incrementSeeds();

        Assertions.assertThrows(IllegalStateException.class, () -> {
            s.incrementSeeds();
        });
    }

    /**
     * Check if decrementSeeds() properly decrements m_nbseeds
     */
    @Test
    void decrementSeeds_shouldnot_fail() {
        s.decrementSeeds();
        Assertions.assertEquals(s.getNbSeeds(), 3);
    }

    /**
     * Check if decrementSeeds() throws an exception when lowering m_nbseeds below 0
     */
    @Test
    void decrementSeeds_negativeamount_should_fail() {
        s.emptySeeds();
        Assertions.assertThrows(IllegalStateException.class, () -> {
            s.decrementSeeds();
        });
    }

    /**
     * Check if equals() recognises instances with equal values
     */
    @Test
    void equals_shouldnot_fail() {
        Slot s1 = new Slot(0, 0);
        Assertions.assertEquals(s, s1);
    }

    /**
     * Check if equals() returns the proper value when instances have different X values
     */
    @Test
    void equals_differentX_should_fail() {
        Slot s1 = new Slot(1, 0);
        Assertions.assertNotEquals(s, s1);
    }

    /**
     * Check if equals() returns the proper value when instances have different Y values
     */
    @Test
    void equals_differentY_should_fail() {
        Slot s1 = new Slot(0, 1);
        Assertions.assertNotEquals(s, s1);
    }

    /**
     * Check if equals() returns the proper value when instances have different m_nbseeds values
     */
    @Test
    void equals_differentNbSeeds_should_fail() {
        Slot s1 = new Slot(0, 0);
        s1.incrementSeeds();
        Assertions.assertNotEquals(s, s1);
    }
}