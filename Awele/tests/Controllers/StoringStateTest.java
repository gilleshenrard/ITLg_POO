package Controllers;

import Models.Board;
import Models.Game;
import Views.BoardView;
import Views.GameView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

public class StoringStateTest {
    GameView gv = new GameView();
    GameController g = new GameController(gv);

    /**
     * Check if handleState() throws an exception when storing above 48
     */
    @DisplayName("handleState() above 48 - should fail")
    @Test
    void handleState_above48_should_fail() {
        g.setNextState(GameController.m_storing);
        g.setCurrentPlayer(1);
        Game.getInstance().setSeeds(1, 23);
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.handleState(49);
        });
    }

    /**
     * Check if handleState() fails storing seeds
     */
    @DisplayName("handleState() - should not fail")
    @Test
    void handleState_shouldnot_fail() {
        BoardController bc = new BoardController(new Board());
        BoardView bv = new BoardView(bc);
        g.setBoardController(bc);
        gv.setController(g);
        g.setNextState(GameController.m_storing);
        g.setCurrentPlayer(1);
        Game.getInstance().setSeeds(1, 23);
        g.handleState(25);
    }
}
