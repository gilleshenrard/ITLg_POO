package Controllers;

import Views.BoardView;
import Views.GameView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StoringStateTest {
    GameController g = new GameController();
    GameView gv = new GameView(g);

    /**
     * Check if handleState() fails storing seeds
     */
    @DisplayName("handleState() - should not fail")
    @Test
    void handleState_shouldnot_fail() {
        BoardView bv = new BoardView(g.getBoardController());
        g.setNextState(GameController.m_storing);
        g.setCurrentPlayer(1);
        g.getBoardController().getBoard().setStoredSeeds(1, 23);
        g.handleState(25);
    }
}
