package Views;

import com.gilleshenrard.Awele.Models.Player;
import com.gilleshenrard.Awele.Models.Point;
import com.gilleshenrard.Awele.Views.Console.KeyboardSelect;
import com.gilleshenrard.Awele.Controllers.BoardController;
import com.gilleshenrard.Awele.Models.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class KeyboardSelectTest {
    BoardController b = new BoardController(new Board());
    KeyboardSelect k = new KeyboardSelect(b);

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

    /**
     * Check if selectSlot() properly returns an error if no slots available
     */
    @DisplayName("selectSlot() with no slots left - should not fail")
    @Test
    void selectSlot_Forfeit_shouldnot_fail() {
        b.getBoard().setStoredSeeds(1, 0);
        b.getBoard().setStoredSeeds(2, 0);
        b.getBoard().emptySlotSeeds(new Point(0, 0));
        b.getBoard().emptySlotSeeds(new Point(1, 0));
        b.getBoard().emptySlotSeeds(new Point(2, 0));
        b.getBoard().emptySlotSeeds(new Point(3, 0));
        b.getBoard().emptySlotSeeds(new Point(4, 0));
        b.getBoard().emptySlotSeeds(new Point(5, 0));
        Player p = new Player(1, "test", k);
        int ret = p.selectSlot();
        Assertions.assertEquals(-2, ret);
    }
}