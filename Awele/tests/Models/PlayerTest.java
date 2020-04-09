package Models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PlayerTest {
    Player p = new Player(1, "Testname");
    Player p2 = new Player(1, "Testname");
    Player p3 = new Player(2, "Testname");
    Player p4 = new Player(1, "WrongName");

    @Test
    void setBehaviour_null_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            p.setBehaviour(null);
        });
    }

    @Test
    void equals_shouldnot_fail(){
        Assertions.assertEquals(p, p2);
    }

    @Test
    void equals_should_fail(){
        Assertions.assertNotEquals(p, p3);
        Assertions.assertNotEquals(p, p4);
    }
}