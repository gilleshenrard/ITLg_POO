package Views;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameViewTest {
    GameView g = new GameView();

    /**
     * Check if displayMessage() fails displaying the winning message
     */
    @DisplayName("displayMessage() - should not fail")
    @Test
    void displayMessage_shouldnot_fail() {
        g.displayMessage("Test");
    }

    /**
     * Check if displayMessage() throws an exception when given a null instance (should not)
     */
    @DisplayName("displayMessage() with a NULL message - should not fail")
    @Test
    void displayMessage_nullName_shouldnot_fail() {
        g.displayMessage(null);
    }

    /**
     * Check if displayWarning() fails displaying the winning message
     */
    @DisplayName("displayWarning() - should not fail")
    @Test
    void displayWarning_shouldnot_fail() {
        g.displayWarning("Test");
    }

    /**
     * Check if displayWarning() throws an exception when given a null instance (should not)
     */
    @DisplayName("displayWarning() with a NULL message - should not fail")
    @Test
    void displayWarning_nullName_shouldnot_fail() {
        g.displayWarning(null);
    }

    /**
     * Check if displayError() fails displaying an error message
     */
    @DisplayName("displayError() - should not fail")
    @Test
    void displayError_shouldnot_fail() {
        g.displayError("A player cannot be hungry");
    }

    /**
     * Check if displayError() throws an exception when given a null instance (should not)
     */
    @DisplayName("displayError() with a NULL message - should not fail")
    @Test
    void displayError_nullmsg_shouldnot_fail() {
        g.displayError(null);
    }
}