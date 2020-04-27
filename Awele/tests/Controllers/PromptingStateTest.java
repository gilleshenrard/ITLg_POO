package Controllers;

import Models.Board;
import Models.Game;
import Models.Player;
import Views.BoardView;
import Views.GameView;
import Views.RandomSelect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PromptingStateTest {

    /**
     * Check if handleState() gets the proper value selected
     */
    @DisplayName("handleState() - should not fail")
    @Test
    void handleState_shouldnot_fail() {
        BoardController bc = new BoardController(new Board());
        BoardView bv = new BoardView(bc);
        GameController g = new GameController(bc);
        GameView gv = new GameView(g);
        Game.getInstance().setPlayer(new Player(1, "Test", new RandomSelect(bc, 1)));
        Game.getInstance().setPlayer(new Player(2, "Test", new RandomSelect(bc, 2)));
        g.setNextState(GameController.m_prompting);
        int output = g.handleState(0);
        Assertions.assertTrue(output > 0 && output < 7);
        Assertions.assertTrue(g.getNextState() instanceof PlayingState);
    }
}
