package Views;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class KeyboardSelectTest {
    KeyboardSelect k = new KeyboardSelect();

    @Disabled("Need to find a way to emulate user keyboard inputs")
    @Test
    void selectSlot_shouldnot_fail() {
        k.selectSlot();
    }
}