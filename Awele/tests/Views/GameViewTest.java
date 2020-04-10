package Views;

import org.junit.jupiter.api.Test;

class GameViewTest {
    GameView g = new GameView();

    @Test
    void displayWin_shouldnot_fail() {
        g.displayWin("Test");
    }

    @Test
    void displayWin_nullName_shouldnot_fail() {
        g.displayWin(null);
    }

    @Test
    void displayError_shouldnot_fail() {
        g.displayError("A player cannot be hungry");
    }
}