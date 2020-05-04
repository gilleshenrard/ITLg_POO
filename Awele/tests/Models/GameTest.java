package Models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

class GameTest {
    Game g = Game.getInstance();

    /**
     * Check if validateID() throws an exception with an ID different than 1 or 2
     */
    @DisplayName("validateID() with a wrong ID - should fail")
    @Test
    void validateIDs_wrong_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            Game.validateID(3, "");
        });
    }

    /**
     * Check if validateID() fails to validate correct ID
     */
    @DisplayName("validateID() - should not fail")
    @Test
    void validateID_shouldnot_fail() {
        Game.validateID(1, "");
        Game.validateID(2, "");
    }

    /**
     * Check if getName() throws an exception with an invalid ID
     */
    @DisplayName("getName() with an invalid ID - should fail")
    @Test
    void getName_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.getName(3);
        });
    }

    /**
     * Check if getName() throws an exception while fetching a null instance
     */
    @Disabled
    @DisplayName("getName() with a NULL instance - should fail")
    @Test
    void getName_nullPlayer_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            g.getName(1);
        });
    }

    /**
     * Check if getName() returns the right player name
     */
    @DisplayName("getName() - should not fail")
    @Test
    void getName_shouldnot_fail() {
        g.setPlayer(new Player(1, "Test"));
        Assertions.assertEquals("Test", g.getName(1));
    }

    /**
     * Check if selectSlot() throws an exception with an invalid ID
     */
    @DisplayName("selectSlot() with an invalid ID - should fail")
    @Test
    void selectSlot_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.selectSlot(3);
        });
    }

    /**
     * Check if selectSlot() throws an exception with a NULL instance of Player
     */
    @DisplayName("selectSlot() with a NULL Player - should fail")
    @Test
    void selectSlot_NULLinstance_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            g.selectSlot(1);
        });
    }

    /**
     * Check if getInstance() returns the right instance of Game
     */
    @DisplayName("getInstance() - should not fail")
    @Test
    void getInstance_shouldnot_fail() {
        Game g2 = Game.getInstance();
        Assertions.assertEquals(g.getInstance(), g2.getInstance());
    }
}