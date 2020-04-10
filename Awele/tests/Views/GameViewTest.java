package Views;

import org.junit.jupiter.api.Test;

class GameViewTest {
    GameView g = new GameView();

    /**
     * Check if displayWin() fails displaying the winning message
     */
    @Test
    void displayWin_shouldnot_fail() {
        g.displayWin("Test");
    }

    /**
     * Check if displayWin() throws an exception when given a null instance (should not)
     */
    @Test
    void displayWin_nullName_shouldnot_fail() {
        g.displayWin(null);
    }

    /**
     * Check if displayError() fails displaying an error message
     */
    @Test
    void displayError_shouldnot_fail() {
        g.displayError("A player cannot be hungry");
    }

    /**
     * Check if displayError() throws an exception when given a null instance (should not)
     */
    @Test
    void displayError_nullmsg_shouldnot_fail() {
        g.displayError(null);
    }
}