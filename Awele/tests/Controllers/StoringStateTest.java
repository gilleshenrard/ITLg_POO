package Controllers;

import ITLg.POO.GillesHenrard.Awele.Controllers.GameController;
import ITLg.POO.GillesHenrard.Awele.Controllers.State;
import ITLg.POO.GillesHenrard.Awele.Views.BoardConsoleView;
import ITLg.POO.GillesHenrard.Awele.Views.GameConsoleView;
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
        g.setNextState(State.STORING);
        g.setCurrentPlayer(1);
        g.getBoardController().getBoard().setStoredSeeds(1, 23);
        g.handleState();
    }
}
