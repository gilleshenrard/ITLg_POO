package Views;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomSelectTest {
    RandomSelect r = new RandomSelect();

    @Test
    void selectSlot_shouldnot_fail() {
        int buffer = r.selectSlot();
        Assertions.assertTrue(buffer >=0 && buffer <6);
    }
}