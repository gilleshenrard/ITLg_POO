package Models;

import Controllers.BoardController;
import Views.KeyboardSelect;
import Views.RandomSelect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

class GameTest {
    Game g = new Game();

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
     * Check if getName() returns the right player name
     */
    @DisplayName("getName() - should not fail")
    @Test
    void getName_shouldnot_fail() {
        g.setPlayer(new Player(1, "Test"));
        Assertions.assertEquals("Test", g.getName(1));
    }

    /**
     * Check if setSeeds() sets the proper value to stored seeds
     */
    @DisplayName("setSeeds() - should not fail")
    @Test
    void setSeeds_shouldnot_fail() {
        g.setSeeds(1, 15);
        Assertions.assertEquals(g.getSeeds(1), 15);
    }

    /**
     * Check if setSeeds() throws an exception with an invalid ID
     */
    @DisplayName("setSeeds() with an invalid ID - should fail")
    @Test
    void setSeeds_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.setSeeds(3, 15);
        });
    }

    /**
     * Check if setSeeds() throws an exception with an amount of seeds above 23
     */
    @DisplayName("setSeeds() with an invalid ID - should fail")
    @Test
    void setSeeds_SeedsAbove23_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.setSeeds(1, 49);
        });
    }

    /**
     * Check if setSeeds() throws an exception with a negative amount of seeds
     */
    @DisplayName("setSeeds() with negative nb_seeds - should fail")
    @Test
    void setSeeds_negativeSeedsNb_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.setSeeds(1, -2);
        });
    }

    /**
     * Check if getSeeds() throws an exception with an invalid ID
     */
    @DisplayName("getSeeds() with an invalid ID - should fail")
    @Test
    void getSeeds_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.getSeeds(3);
        });
    }

    /**
     * Check if getSeeds() returns the right amount of seeds
     */
    @DisplayName("getSeeds() - should not fail")
    @Test
    void getSeeds_shouldnot_fail() {
            g.setSeeds(1, 12);
            Assertions.assertEquals(g.getSeeds(1), 12);
    }

    /**
     * Check if getInstance() returns the right instance of Game
     */
    @DisplayName("getInstance() - should not fail")
    @Test
    void getInstance_shouldnot_fail() {
        Game g2 = new Game();
        Assertions.assertEquals(g.getInstance(), g2.getInstance());
    }
}