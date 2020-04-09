package Models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PlayerTest {
    Player p = new Player((byte)1, "Testname");

    @Test
    void setBehaviour_null_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            p.setBehaviour(null);
        });
    }
}