package Views;

import Controllers.BoardController;
import Models.Board;
import Models.Game;
import Models.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

class BoardViewTest {
    Board b = new Board();
    BoardController bc = new BoardController(b);
    BoardView bv = new BoardView(bc);

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
     * Check if displayBoard() fails displaying the board
     */
    @DisplayName("displayBoard() - should not fail")
    @Test
    void displayBoard() {
        Game g = Game.getInstance();
        g.setPlayer(1, new Player(1, "test"));
        g.setPlayer(2, new Player(2, "test"));
        bv.displayBoard();
    }

    /**
     * Check if displaySlot() fails displaying a slot
     */
    @DisplayName("displaySlot() - should not fail")
    @Test
    void displaySlot_shouldnot_fail() {
        bv.displaySlot(2);
    }
}