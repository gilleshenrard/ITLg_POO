package Views;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.util.ArrayList;

class RandomSelectTest {
    RandomSelect r = new RandomSelect();

    /**
     * Verify that selectSlot() generates 0 <= value <= 5
     */
    @DisplayName("selectSlot() - should not fail")
    @Test
    void selectSlot_shouldnot_fail() {
        ArrayList<Integer> test = new ArrayList<>();
        test.add(2);
        r.setPlayableSlots(test);
        int buffer = r.selectSlot();
        Assertions.assertTrue(buffer >0 && buffer <=6);
    }

    /**
     * Verify that selectSlot() throws an exception with a NULL instance
     */
    @DisplayName("selectSlot() with a null instance - should fail")
    @Test
    void selectSlot_null_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            r.selectSlot();
        });
    }

    /**
     * Verify that setPlayableSlots() properly sets the array of possible slots
     */
    @DisplayName("setPlayableSlots() - should not fail")
    @Test
    void setPlayableSlots_shouldnot_fail() {
        r.setPlayableSlots(new ArrayList<>());
    }

    /**
     * Verify that setPlayableSlots() throws an exception with a NULL instance
     */
    @DisplayName("setPlayableSlots() with a null instance - should fail")
    @Test
    void setPlayableSlots_null_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            r.setPlayableSlots(null);
        });
    }
}