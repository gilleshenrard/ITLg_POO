package Views;

import ITLg.POO.GillesHenrard.Awele.Views.KeyboardSelect;
import ITLg.POO.GillesHenrard.Awele.Controllers.BoardController;
import ITLg.POO.GillesHenrard.Awele.Models.Board;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class KeyboardSelectTest {
    KeyboardSelect k = new KeyboardSelect(new BoardController(new Board()), 1);

    /**
     * Check if selectSlot() properly returns the value set by the user on the keyboard
     * This test is disabled until a solution to test keyboard strokes is found
     */
    @DisplayName("selectSlot() - should not fail")
    @Disabled("Need to find a way to emulate user keyboard inputs")
    @Test
    void selectSlot_shouldnot_fail() {
        k.selectSlot();
    }
}