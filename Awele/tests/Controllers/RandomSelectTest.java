package Controllers;

import Controllers.RandomSelect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RandomSelectTest {
    RandomSelect r = new RandomSelect();

    @Test
    void selectSlot_shouldnot_fail() {
        int buffer = r.selectSlot();
        Assertions.assertTrue(buffer >=0 && buffer <6);
    }
}