package Views;

import ITLg.POO.GillesHenrard.Awele.Controllers.GameController;
import ITLg.POO.GillesHenrard.Awele.Views.BoardConsoleView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardConsoleViewTest {
    BoardConsoleView bv = new BoardConsoleView();

    /**
     * Check if displaySlot() fails displaying a slot
     */
    @DisplayName("displaySlot() - should not fail")
    @Test
    void displaySlot_shouldnot_fail() {
        bv.displaySlot(2, true);
    }

    /**
     * Check if displayBoard() fails displaying a row of slots
     */
    @DisplayName("displayBoard() - should not fail")
    @Test
    void displayBoard_shouldnot_fail() {
        GameController gc = new GameController();
        gc.getBoardController().attach(bv);
        bv.displayBoard();
    }
}