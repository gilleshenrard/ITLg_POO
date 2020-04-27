package Views;

import Controllers.BoardController;
import Models.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardViewTest {
    BoardView bv = new BoardView(new BoardController(new Board()));

    /**
     * Check if boardView() throws an exception when given a null instance
     */
    @DisplayName("boardView() with a NULL Board instance - should fail")
    @Test
    void boardView_nullBoard_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            BoardView b2 = new BoardView(null);
        });
    }

    /**
     * Check if displaySlot() fails displaying a slot
     */
    @DisplayName("displaySlot() - should not fail")
    @Test
    void displaySlot_shouldnot_fail() {
        bv.displaySlot(2);
    }

    /**
     * Check if displayRow() fails displaying a row of slots
     */
    @DisplayName("displayRow() - should not fail")
    @Test
    void displayRow_shouldnot_fail() {
        bv.displayRow(1, false);
    }
}