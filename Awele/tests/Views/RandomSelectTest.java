package Views;

import Controllers.BoardController;
import Controllers.GameController;
import Models.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RandomSelectTest {
    BoardController b = new BoardController(new Board(), new GameController(new GameView()));
    RandomSelect r = new RandomSelect(b, 1);

    /**
     * Verify that selectSlot() generates 0 <= value <= 5
     */
    @DisplayName("selectSlot() - should not fail")
    @Test
    void selectSlot_shouldnot_fail() {
        r.reset();
        int buffer = r.selectSlot();
        Assertions.assertTrue(buffer >0 && buffer <=6);
    }

    /**
     * Verify that selectSlot() throws an exception with a NULL instance
     */
    @DisplayName("selectSlot() with m_playable NULL - should fail")
    @Test
    void selectSlot_NULL_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            int buffer = r.selectSlot();
        });
    }

    /**
     * Verify that reset() generates 0 <= value <= 5
     */
    @DisplayName("reset() - should not fail")
    @Test
    void reset_shouldnot_fail() {
        r.reset();
    }

    /**
     * Verify that getShotsLeft() returns the proper value
     */
    @DisplayName("getShotsLeft() - should not fail")
    @Test
    void getShotsLeft_shouldnot_fail() {
        r.reset();
        Assertions.assertEquals(6, r.getShotsLeft());
    }

    /**
     * Verify that getShotsLeft() throws an exception with a NULL instance
     */
    @DisplayName("getShotsLeft() with m_playable NULL - should fail")
    @Test
    void getShotsLeft_NULL_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            r.getShotsLeft();
        });
    }
}