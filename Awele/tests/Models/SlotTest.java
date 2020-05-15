package Models;

import ITLg.POO.GillesHenrard.Awele.Models.Point;
import ITLg.POO.GillesHenrard.Awele.Models.Slot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

class SlotTest {
    Slot s = new Slot(0, 0);

    /**
     * Check if validateNbSeeds() throws an exception when using a negative amount
     */
    @DisplayName("validateNbSeeds() with negative amount - should fail")
    @Test
    void validateNbSeeds_negative_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            Slot.validateNbSeeds(-1, "");
        });
    }

    /**
     * Check if validateNbSeeds() throws an exception when using an amount above 48
     */
    @DisplayName("validateNbSeeds() with an amount above 48 - should fail")
    @Test
    void validateNbSeeds_above48_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            Slot.validateNbSeeds(49, "");
        });
    }

    /**
     * Check if validateNbSeeds() fails validating a proper amount of seeds
     */
    @DisplayName("validateNbSeeds() - should not fail")
    @Test
    void validateNbSeeds_shouldnot_fail() {
        Slot.validateNbSeeds(48, "");
    }

    /**
     * Check if setCoordinates() fails while setting coordinates
     */
    @DisplayName("setCoordinates() - should not fail")
    @Test
    void setCoordinates_shouldnot_fail() {
        for(int l=0 ; l<2 ; l++)
            for(int c=0 ; c<6 ; c++)
                s.setCoordinates(c, l);
    }

    /**
     * Check if getCoordinates() return the right values
     */
    @DisplayName("getCoordinates() - should not fail")
    @Test
    void getCoordinates_shouldnot_fail() {
        s.setCoordinates(3, 1);
        Point p = new Point(3, 1);
        Assertions.assertEquals(p, s.getCoordinates());
    }

    /**
     * Check if getNbSeeds() returns the proper value
     */
    @DisplayName("getNbSeeds() - should not fail")
    @Test
    void getNbSeeds_default_shouldnot_fail() {
        Assertions.assertEquals(4, s.getNbSeeds());
    }

    /**
     * Check if setNbSeeds() throws an exception when giving a negative amount of seeds
     */
    @DisplayName("setNbSeeds() with negative seeds - should fail")
    @Test
    void setNbSeeds_negative_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            s.setNbSeeds(-1);
        });
    }

    /**
     * Check if setNbSeeds() throws an exception when giving an amount of seeds above 48
     */
    @DisplayName("setNbSeeds() with seeds above 48 - should fail")
    @Test
    void setNbSeeds_above48_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            s.setNbSeeds(49);
        });
    }

    /**
     * Check if setNbSeeds() properly sets m_nbseeds
     */
    @DisplayName("setNbSeeds() - should not fail")
    @Test
    void setNbSeeds_shouldnot_fail() {
        s.setNbSeeds(24);
        Assertions.assertEquals(24, s.getNbSeeds());
    }

    /**
     * Check if emptySeeds() properly sets m_nbseeds to 0
     */
    @DisplayName("emptySeeds() - should not fail")
    @Test
    void emptySeeds_shouldnot_fail() {
        s.emptySeeds();
        Assertions.assertEquals(0, s.getNbSeeds());
    }

    /**
     * Check if incrementSeeds() properly increments m_nbseeds
     */
    @DisplayName("incrementSeeds() - should not fail")
    @Test
    void incrementSeeds_shouldnot_fail() {
        s.incrementSeeds();
        Assertions.assertEquals(5, s.getNbSeeds());
    }

    /**
     * Check if incrementSeeds() throws an exception when rising m_nbseeds above 48 (max seeds on the board)
     */
    @DisplayName("incrementSeeds() when incrementing above 48 - should fail")
    @Test
    void incrementSeeds_above48_should_fail() {
        s.setNbSeeds(48);

        Assertions.assertThrows(InvalidParameterException.class, () -> {
            s.incrementSeeds();
        });
    }

    /**
     * Check if decrementSeeds() properly decrements m_nbseeds
     */
    @DisplayName("decrementSeeds() - should not fail")
    @Test
    void decrementSeeds_shouldnot_fail() {
        s.decrementSeeds();
        Assertions.assertEquals(3, s.getNbSeeds());
    }

    /**
     * Check if decrementSeeds() throws an exception when lowering m_nbseeds below 0
     */
    @DisplayName("decrementSeeds() when decrementing below 0 - should fail")
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
    @DisplayName("equals() - should not fail")
    @Test
    void equals_shouldnot_fail() {
        Slot s1 = new Slot(0, 0);
        Assertions.assertEquals(s, s1);
    }

    /**
     * Check if equals() returns the proper value when instances have different X values
     */
    @DisplayName("equals() with different X coordinates - should not fail")
    @Test
    void equals_differentX_should_fail() {
        Slot s1 = new Slot(1, 0);
        Assertions.assertNotEquals(s, s1);
    }

    /**
     * Check if equals() returns the proper value when instances have different Y values
     */
    @DisplayName("equals() with different Y coordinates - should not fail")
    @Test
    void equals_differentY_should_fail() {
        Slot s1 = new Slot(0, 1);
        Assertions.assertNotEquals(s, s1);
    }

    /**
     * Check if equals() returns the proper value when instances have different m_nbseeds values
     */
    @DisplayName("equals() with different amount of seeds - should not fail")
    @Test
    void equals_differentNbSeeds_should_fail() {
        Slot s1 = new Slot(0, 0);
        s1.incrementSeeds();
        Assertions.assertNotEquals(s, s1);
    }
}