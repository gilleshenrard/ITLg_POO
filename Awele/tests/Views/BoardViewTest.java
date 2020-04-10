package Views;

import Models.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

class BoardViewTest {
    Board b = new Board();
    BoardView bv = new BoardView(b);

    @Test
    void boardView_nullBoard_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            BoardView b2 = new BoardView(null);
        });
    }

    @Test
    void displayBoard() {
        bv.displayBoard();
    }

    @Test
    void displaySlot_shouldnot_fail() {
        bv.displaySlot(2);
    }
}