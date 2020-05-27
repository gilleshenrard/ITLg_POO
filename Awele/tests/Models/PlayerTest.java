package Models;

import com.gilleshenrard.Awele.Models.Player;
import com.gilleshenrard.Awele.Views.Console.KeyboardSelect;
import com.gilleshenrard.Awele.Controllers.BoardController;
import com.gilleshenrard.Awele.Models.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

class PlayerTest {
    Player p = new Player(1, "Testname");

    /**
     * Check if Player() throws an exception using a null instance for m_behaviour
     */
    @DisplayName("Player() with a NULL instance of m_behaviour - should fail")
    @Test
    void constructor_invalidBehaviour_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            Player p5 = new Player(1, "test", null);
        });
    }

    /**
     * Check if setID() fails setting an ID to a player
     */
    @DisplayName("setID() - should not fail")
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
    @DisplayName("setID() with a negative ID - should fail")
    @Test
    void setID_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            p.setID(-1);
        });
    }

    /**
     * Check if getID() returns the proper ID
     */
    @DisplayName("getID() - should not fail")
    @Test
    void getID_shouldnot_fail() {
        Assertions.assertEquals(p.getID(), 1);
    }

    /**
     * Check if getName() returns the right name
     */
    @DisplayName("getName() - should not fail")
    @Test
    void getName_shouldnot_fail() {
        Assertions.assertEquals(p.getName(), "Testname");
    }

    /**
     * Check if setBehaviour() throws an exception with a null instance
     */
    @DisplayName("setBehaviour() with a NULL instance - should fail")
    @Test
    void setBehaviour_null_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            p.setBehaviour(null);
        });
    }

    /**
     * Check if setBehaviour() fails setting an m_behaviour instance
     */
    @DisplayName("setBehaviour() - should not fail")
    @Test
    void setBehaviour_shouldnot_fail() {
            p.setBehaviour(new KeyboardSelect(new BoardController(new Board())));
    }

    /**
     * Check if equals() fails recognising instances as equal
     */
    @DisplayName("equals() - should not fail")
    @Test
    void equals_shouldnot_fail(){
        Player p2 = new Player(1, "Testname");
        Assertions.assertEquals(p, p2);
    }

    /**
     * Check if equals() returns the proper value with different IDs
     */
    @DisplayName("equals() with different IDs - should not fail")
    @Test
    void equals_differentID_should_fail(){
        Player p3 = new Player(2, "Testname");
        Assertions.assertNotEquals(p, p3);
    }

    /**
     * Check if equals() returns the proper value with different names
     */
    @DisplayName("equals() with different names - should not fail")
    @Test
    void equals_differentName_should_fail(){
        Player p4 = new Player(1, "WrongName");
        Assertions.assertNotEquals(p, p4);
    }

    /**
     * Check if equals() returns the proper value with different behaviours
     */
    @DisplayName("equals() with different behaviours - should not fail")
    @Test
    void equals_differentBehaviour_should_fail(){
        Player p4 = new Player(1, "Testname", new KeyboardSelect(new BoardController(new Board())));
        Assertions.assertNotEquals(p, p4);
    }

    /**
     * Check if selectSlot() throws an exception when used and m_behaviour is null
     */
    @DisplayName("selectSlot() with a NULL instance of m_behaviour - should fail")
    @Test
    void selectSlot_null_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            p.selectSlot();
        });
    }
}