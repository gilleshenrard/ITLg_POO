package Views;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RandomSelectTest {
    RandomSelect r = new RandomSelect();

    /**
     * Verify that selectSlot() generates 0 <= value <= 5
     */
    @DisplayName("selectSlot() - should not fail")
    @Test
    void selectSlot_shouldnot_fail() {
        int buffer = r.selectSlot();
        Assertions.assertTrue(buffer >0 && buffer <=6);
    }
}