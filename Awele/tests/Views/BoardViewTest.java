package Views;

import Models.Board;
import Models.Game;
import Models.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

class BoardViewTest {
    Board b = new Board();
    BoardView bv = new BoardView(b);

    /**
     * Check if boardView() throws an exception when given a null instance
     */
    @Test
    void boardView_nullBoard_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            BoardView b2 = new BoardView(null);
        });
    }

    /**
     * Check if displayBoard() fails displaying the board
     */
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
    @Test
    void displaySlot_shouldnot_fail() {
        bv.displaySlot(2);
    }
}