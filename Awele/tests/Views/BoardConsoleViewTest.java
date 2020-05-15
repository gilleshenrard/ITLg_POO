package Views;

import ITLg.POO.GillesHenrard.Awele.Controllers.BoardController;
import ITLg.POO.GillesHenrard.Awele.Controllers.GameController;
import ITLg.POO.GillesHenrard.Awele.Models.Board;
import ITLg.POO.GillesHenrard.Awele.Views.BoardConsoleView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardConsoleViewTest {
    BoardController b = new BoardController(new Board());

    /**
     * Check if displaySlot() fails displaying a slot
     */
    @DisplayName("displaySlot() - should not fail")
    @Test
    void displaySlot_shouldnot_fail() {
        BoardConsoleView bv = new BoardConsoleView();
        bv.displaySlot(2, true);
    }

    /**
     * Check if displayBoard() fails displaying a row of slots
     */
    @DisplayName("displayBoard() - should not fail")
    @Test
    void displayBoard_shouldnot_fail() {
        GameController g = new GameController();
        g.getBoardController().attach(new BoardConsoleView());
        g.updateObservers();
    }
}