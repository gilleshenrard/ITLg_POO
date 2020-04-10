package Controllers;

import Controllers.RandomSelect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RandomSelectTest {
    RandomSelect r = new RandomSelect();

    /**
     * Verify that selectSlot() generates 0 <= value <= 5
     */
    @Test
    void selectSlot_shouldnot_fail() {
        int buffer = r.selectSlot();
        Assertions.assertTrue(buffer >=0 && buffer <6);
    }
}