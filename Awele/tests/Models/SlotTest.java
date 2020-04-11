package Models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

class SlotTest {
    Slot s = new Slot(0, 0);

    /**
     * Check if validateNbSeeds() throws an exception when using a negative amount
     */
    @Test
    void validateNbSeeds_negative_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            Slot.validateNbSeeds(-1, "");
        });
    }

    /**
     * Check if validateNbSeeds() throws an exception when using an amount above 48
     */
    @Test
    void validateNbSeeds_above48_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            Slot.validateNbSeeds(49, "");
        });
    }

    /**
     * Check if validateNbSeeds() fails validating a proper amount of seeds
     */
    @Test
    void validateNbSeeds_shouldnot_fail() {
        Slot.validateNbSeeds(48, "");
    }

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
     * Check if setNbSeeds() throws an exception when giving a negative amount of seeds
     */
    @Test
    void setNbSeeds_negative_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            s.setNbSeeds(-1);
        });
    }

    /**
     * Check if setNbSeeds() throws an exception when giving an amount of seeds above 48
     */
    @Test
    void setNbSeeds_above48_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            s.setNbSeeds(49);
        });
    }

    /**
     * Check if setNbSeeds() properly sets m_nbseeds
     */
    @Test
    void setNbSeeds_shouldnot_fail() {
        s.setNbSeeds(24);
        Assertions.assertEquals(s.getNbSeeds(), 24);
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

        Assertions.assertThrows(InvalidParameterException.class, () -> {
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
        Assertions.assertThrows(InvalidParameterException.class, () -> {
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