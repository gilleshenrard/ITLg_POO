package Controllers;

import com.gilleshenrard.Awele.Controllers.GameController;
import com.gilleshenrard.Awele.FSM.State;
import com.gilleshenrard.Awele.Views.Console.BoardConsoleView;
import com.gilleshenrard.Awele.Views.Console.GameConsoleView;
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
