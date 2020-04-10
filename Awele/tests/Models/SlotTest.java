package Models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

class SlotTest {
    Slot s = new Slot(0, 0);

    @Test
    void setCoordinates_shouldnot_fail() {
        for(int l=0 ; l<2 ; l++)
            for(int c=0 ; c<6 ; c++)
                s.setCoordinates(c, l);
    }

    @Test
    void setCoordinates_invalidX_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            s.setCoordinates(3, 5);
        });
    }

    @Test
    void setCoordinates_negativeX_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            s.setCoordinates(-3, 5);
        });
    }

    @Test
    void setCoordinates_invalidY_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            s.setCoordinates(0, 10);
        });
    }

    @Test
    void setCoordinates_negativeY_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            s.setCoordinates(0, -5);
        });
    }

    @Test
    void getNbSeeds_default_shouldnot_fail() {
        Assertions.assertEquals(s.getNbSeeds(), 4);
    }

    @Test
    void emptySeeds_shouldnot_fail() {
        s.emptySeeds();
        Assertions.assertEquals(s.getNbSeeds(), 0);
    }

    @Test
    void incrementSeeds_shouldnot_fail() {
        s.incrementSeeds();
        Assertions.assertEquals(s.getNbSeeds(), 5);
    }

    @Test
    void incrementSeeds_above48_should_fail() {
        for(int i=0 ; i<44 ; i++)
            s.incrementSeeds();

        Assertions.assertThrows(IllegalStateException.class, () -> {
            s.incrementSeeds();
        });
    }

    @Test
    void decrementSeeds_shouldnot_fail() {
        s.decrementSeeds();
        Assertions.assertEquals(s.getNbSeeds(), 3);
    }

    @Test
    void decrementSeeds_negativeamount_should_fail() {
        s.emptySeeds();
        Assertions.assertThrows(IllegalStateException.class, () -> {
            s.decrementSeeds();
        });
    }

    @Test
    void equals_shouldnot_fail() {
        Slot s1 = new Slot(0, 0);
        Assertions.assertEquals(s, s1);
    }

    @Test
    void equals_differentX_should_fail() {
        Slot s1 = new Slot(1, 0);
        Assertions.assertNotEquals(s, s1);
    }

    @Test
    void equals_differentY_should_fail() {
        Slot s1 = new Slot(0, 1);
        Assertions.assertNotEquals(s, s1);
    }

    @Test
    void equals_differentNbSeeds_should_fail() {
        Slot s1 = new Slot(0, 0);
        s1.incrementSeeds();
        Assertions.assertNotEquals(s, s1);
    }
}