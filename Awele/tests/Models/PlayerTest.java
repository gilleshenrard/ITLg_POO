package Models;

import Views.KeyboardSelect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

class PlayerTest {
    Player p = new Player(1, "Testname");

    @Test
    void constructor_invalidBehaviour_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            Player p5 = new Player(1, "test", null);
        });
    }

    @Test
    void setID_shouldnot_fail() {
            Player p5 = new Player(1, "test");
            p5.setID(1);
            p5.setID(2);
    }

    @Test
    void setID_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            p.setID(5);
        });
    }

    @Test
    void getID_shouldnot_fail() {
        Assertions.assertEquals(p.getID(), 1);
    }

    @Test
    void getName_shouldnot_fail() {
        Assertions.assertEquals(p.getName(), "Testname");
    }

    @Test
    void setBehaviour_null_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            p.setBehaviour(null);
        });
    }

    @Test
    void setBehaviour_shouldnot_fail() {
            p.setBehaviour(new RandomSelect());
    }

    @Test
    void equals_shouldnot_fail(){
        Player p2 = new Player(1, "Testname");
        Assertions.assertEquals(p, p2);
    }

    @Test
    void equals_differentID_should_fail(){
        Player p3 = new Player(2, "Testname");
        Assertions.assertNotEquals(p, p3);
    }

    @Test
    void equals_differentName_should_fail(){
        Player p4 = new Player(1, "WrongName");
        Assertions.assertNotEquals(p, p4);
    }

    @Test
    void equals_differentBehaviour_should_fail(){
        Player p4 = new Player(1, "Testname", new KeyboardSelect());
        Assertions.assertNotEquals(p, p4);
    }

    @Test
    void selectSlot_null_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            p.selectSlot();
        });
    }
}