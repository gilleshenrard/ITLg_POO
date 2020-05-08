package Controllers;

import Views.BoardConsoleView;
import Views.GameConsoleView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StoringStateTest {
    GameController g = new GameController();
    GameConsoleView gv = new GameConsoleView(g);

    /**
     * Check if handleState() fails storing seeds
     */
    @DisplayName("handleState() - should not fail")
    @Test
    void handleState_shouldnot_fail() {
        g.getBoardController().attach(new BoardConsoleView());
        g.setNextState(GameController.m_storing);
        g.setCurrentPlayer(1);
        g.getBoardController().getBoard().setStoredSeeds(1, 23);
        g.handleState();
    }
}
