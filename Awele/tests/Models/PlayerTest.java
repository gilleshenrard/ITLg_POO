package Models;

import Controllers.RandomSelect;
import Views.KeyboardSelect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

class PlayerTest {
    Player p = new Player(1, "Testname");

    /**
     * Check if Player') throws an exception using a null instance for m_behaviour
     */
    @Test
    void constructor_invalidBehaviour_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            Player p5 = new Player(1, "test", null);
        });
    }

    /**
     * Check if setID() fails setting an ID to a player
     */
    @Test
    void setID_shouldnot_fail() {
            Player p5 = new Player(1, "test");
            p5.setID(1);
            p5.setID(2);
            p5.setID(5);
    }

    /**
     * Check if setID() throws an exception using a negative ID
     */
    @Test
    void setID_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            p.setID(-1);
        });
    }

    /**
     * Check if getID() returns the proper ID
     */
    @Test
    void getID_shouldnot_fail() {
        Assertions.assertEquals(p.getID(), 1);
    }

    /**
     * Check if getName() returns the right name
     */
    @Test
    void getName_shouldnot_fail() {
        Assertions.assertEquals(p.getName(), "Testname");
    }

    /**
     * Check if setBehaviour() throws an exception with a null instance
     */
    @Test
    void setBehaviour_null_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            p.setBehaviour(null);
        });
    }

    /**
     * Check if setBehaviour() fails setting an m_behaviour instance
     */
    @Test
    void setBehaviour_shouldnot_fail() {
            p.setBehaviour(new RandomSelect());
    }

    /**
     * Check if equals() fails recognising instances as equal
     */
    @Test
    void equals_shouldnot_fail(){
        Player p2 = new Player(1, "Testname");
        Assertions.assertEquals(p, p2);
    }

    /**
     * Check if equals() returns the proper value with different IDs
     */
    @Test
    void equals_differentID_should_fail(){
        Player p3 = new Player(2, "Testname");
        Assertions.assertNotEquals(p, p3);
    }

    /**
     * Check if equals() returns the proper value with different names
     */
    @Test
    void equals_differentName_should_fail(){
        Player p4 = new Player(1, "WrongName");
        Assertions.assertNotEquals(p, p4);
    }

    /**
     * Check if equals() returns the proper value with different behaviours
     */
    @Test
    void equals_differentBehaviour_should_fail(){
        Player p4 = new Player(1, "Testname", new KeyboardSelect());
        Assertions.assertNotEquals(p, p4);
    }

    /**
     * Check if selectSlot() throws an exception when used and m_behaviour is null
     */
    @Test
    void selectSlot_null_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            p.selectSlot();
        });
    }
}