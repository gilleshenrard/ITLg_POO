package Controllers;

import Models.Game;
import Models.Player;
import Models.Point;
import Views.BoardView;
import Views.GameView;
import Views.KeyboardSelect;
import Views.RandomSelect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PromptingStateTest {
    GameController g = new GameController();
    BoardView bv = new BoardView(g.getBoardController());
    GameView gv = new GameView(g);

    /**
     * Check if handleState() gets the proper value selected
     */
    @DisplayName("handleState() - should not fail")
    @Test
    void handleState_shouldnot_fail() {
        Game.getInstance().setPlayer(new Player(1, "Test", new RandomSelect(g.getBoardController(), 1)));
        Game.getInstance().setPlayer(new Player(2, "Test", new RandomSelect(g.getBoardController(), 2)));
        g.setNextState(GameController.m_prompting);
        int output = g.handleState(0);
        Assertions.assertTrue(output > 0 && output < 7);
        Assertions.assertTrue(g.getNextState() instanceof PlayingState);
    }

    /**
     * Check if handleState() processes a self-starvation by scattering to another row
     */
    @DisplayName("handleState() with self-starvation to other row, no forfeit - should not fail")
    @Test
    void handleState_selfStarvationNoForfeit_otherRow_shouldnot_fail() {
        g.getBoardController().getBoard().setStoredSeeds(1, 0);
        g.getBoardController().getBoard().setStoredSeeds(2, 0);
        Game.getInstance().setPlayer(new Player(1, "Test", new RandomSelect(g.getBoardController(), 1)));
        Game.getInstance().setPlayer(new Player(2, "Test", new RandomSelect(g.getBoardController(), 2)));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(0, 0));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(1, 0));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(2, 0));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(3, 0));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(4, 0));
        g.getBoardController().getBoard().setSlotSeeds(new Point(5, 0), 1);
        g.getBoardController().getBoard().setRemainingSeeds(1, 1);
        g.setNextState(GameController.m_prompting);
        g.setCurrentPlayer(1);
        int ret = g.handleState(6);
        Assertions.assertEquals(6, ret);
    }

    /**
     * Check if handleState() forfeits for a keyboardSelect without any slots available
     */
    @DisplayName("handleState() with KeyboardSelect and no slots available, no forfeit - should not fail")
    @Test
    void handleState_Forfeit_otherRow_shouldnot_fail() {
        g.getBoardController().getBoard().setStoredSeeds(1, 0);
        g.getBoardController().getBoard().setStoredSeeds(2, 0);
        Game.getInstance().setPlayer(new Player(1, "Test", new KeyboardSelect(g.getBoardController(), 1)));
        Game.getInstance().setPlayer(new Player(2, "Test", new RandomSelect(g.getBoardController(), 2)));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(0, 0));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(1, 0));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(2, 0));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(3, 0));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(4, 0));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(5, 0));
        g.getBoardController().getBoard().setRemainingSeeds(1, 0);
        g.setNextState(GameController.m_prompting);
        g.setCurrentPlayer(1);
        int ret = g.handleState(6);
        Assertions.assertEquals(-2, ret);
    }
}
