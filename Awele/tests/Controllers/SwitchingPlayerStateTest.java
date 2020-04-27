package Controllers;

import Models.Game;
import Models.Player;
import Views.GameView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SwitchingPlayerStateTest {

    /**
     * Check if handleState() sets the proper ID
     */
    @DisplayName("handleState() - should not fail")
    @Test
    void handleState_shouldnot_fail() {
        GameController g = new GameController();
        GameView gv = new GameView(g);
        Game.getInstance().setPlayer(new Player(1, "Test"));
        Game.getInstance().setPlayer(new Player(2, "Test"));
        Assertions.assertEquals(1, g.getCurrentPlayer());
        g.setNextState(GameController.m_switching);
        g.handleState(0);
        Assertions.assertEquals(2, g.getCurrentPlayer());
        Assertions.assertTrue(g.getNextState() instanceof PromptingState);
    }
}
