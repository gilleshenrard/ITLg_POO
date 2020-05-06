package Views;

import Controllers.BoardController;
import Controllers.GameController;
import Models.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardViewTest {
    BoardController b = new BoardController(new Board());

    /**
     * Check if displaySlot() fails displaying a slot
     */
    @DisplayName("displaySlot() - should not fail")
    @Test
    void displaySlot_shouldnot_fail() {
        BoardView bv = new BoardView();
        bv.displaySlot(2, true);
    }

    /**
     * Check if displayBoard() fails displaying a row of slots
     */
    @DisplayName("displayBoard() - should not fail")
    @Test
    void displayBoard_shouldnot_fail() {
        GameController g = new GameController();
        g.getBoardController().attach(new BoardView());
        g.updateObservers();
    }
}