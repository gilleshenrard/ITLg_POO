package Models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Board b = new Board();

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

    @Test
    void getRemainingSeeds_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            b.getRemainingSeeds(0);
        });
    }

    @Test
    void getRemainingSeeds_shouldnot_fail() {
        Assertions.assertEquals(b.getRemainingSeeds(1), 24);
    }
}