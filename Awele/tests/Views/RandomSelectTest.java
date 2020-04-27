package Views;

import Controllers.BoardController;
import Models.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RandomSelectTest {
    BoardController b = new BoardController(new Board());
    RandomSelect r = new RandomSelect(b, 1);

    /**
     * Verify that selectSlot() generates 0 <= value <= 5
     */
    @DisplayName("selectSlot() - should not fail")
    @Test
    void selectSlot_shouldnot_fail() {
        r.refresh();
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
     * Verify that refresh() generates 0 <= value <= 5
     */
    @DisplayName("refresh() - should not fail")
    @Test
    void refresh_shouldnot_fail() {
        r.refresh();
    }

    /**
     * Verify that getShotsLeft() returns the proper value
     */
    @DisplayName("getShotsLeft() - should not fail")
    @Test
    void getShotsLeft_shouldnot_fail() {
        r.refresh();
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