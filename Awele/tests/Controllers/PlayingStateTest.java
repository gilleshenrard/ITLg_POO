package Controllers;

import Models.Game;
import Models.Player;
import Models.Point;
import Views.GameView;
import Views.RandomSelect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PlayingStateTest {
    GameController g = new GameController();
    GameView gv = new GameView(g);

    /**
     * Check if handleState() processes a simple scattering properly (no capture, no starvation)
     */
    @DisplayName("handleState() with neither capture nor starvation - should not fail")
    @Test
    void handleState_noCaptureNoStarve_shouldnot_fail() {
        Game.getInstance().setSeeds(1, 0);
        Game.getInstance().setSeeds(2, 0);
        g.setNextState(GameController.m_playing);
        g.setCurrentPlayer(1);
        int ret = g.handleState(6);
        Assertions.assertEquals(0, ret);
        Assertions.assertTrue(g.getNextState() instanceof StoringState);
    }

    /**
     * Check if handleState() processes a simple scattering properly (2 captures, no starvation)
     */
    @DisplayName("handleState() with a capture case - should not fail")
    @Test
    void handleState_CaptureNoStarve_shouldnot_fail() {
        Game.getInstance().setSeeds(1, 0);
        Game.getInstance().setSeeds(2, 0);
        g.resetGame();
        g.getBoardController().getBoard().setSlotSeeds(new Point(1, 1), 2);
        g.getBoardController().getBoard().setSlotSeeds(new Point(3, 1), 1);
        g.getBoardController().getBoard().setSlotSeeds(new Point(4, 1), 9);
        g.setNextState(GameController.m_playing);
        g.setCurrentPlayer(1);
        int ret = g.handleState(6);
        Assertions.assertEquals(5, ret);
        Assertions.assertTrue(g.getNextState() instanceof StoringState);
    }

    /**
     * Check if handleState() processes a starvation properly
     */
    @DisplayName("handleState() with a starvation case, no forfeit - should not fail")
    @Test
    void handleState_noCaptureStarveNoForfeit_shouldnot_fail() {
        Game.getInstance().setSeeds(1, 0);
        Game.getInstance().setSeeds(2, 0);
        Game.getInstance().setPlayer(new Player(1, "Test", new RandomSelect(g.getBoardController(), 1)));
        g.getBoardController().resetBoard();
        g.getBoardController().getBoard().setSlotSeeds(new Point(0, 1), 1);
        g.getBoardController().getBoard().setSlotSeeds(new Point(1, 1), 2);
        g.getBoardController().getBoard().emptySlotSeeds(new Point(2, 1));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(3, 1));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(4, 1));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(5, 1));
        g.getBoardController().getBoard().setSlotSeeds(new Point(5, 0), 2);
        g.getBoardController().getBoard().setRemainingSeeds(2, 3);
        g.getBoardController().getBoard().setRemainingSeeds(1, 22);
        g.setNextState(GameController.m_playing);
        g.setCurrentPlayer(1);
        g.refresh(1);
        int ret = g.handleState(6);
        Assertions.assertEquals(0, ret);
        Assertions.assertTrue(g.getNextState() instanceof PromptingState);
    }

    /**
     * Check if handleState() forbids a self-starvation by scattering to another row
     */
    @DisplayName("handleState() with self-starvation to other row, forfeit - should not fail")
    @Test
    void handleState_selfStarvationForfeit_otherRow_shouldnot_fail() {
        Game.getInstance().setSeeds(1, 0);
        Game.getInstance().setSeeds(2, 0);
        Game.getInstance().setPlayer(new Player(1, "Test", new RandomSelect(g.getBoardController(), 1)));
        Game.getInstance().setPlayer(new Player(2, "Test", new RandomSelect(g.getBoardController(), 2)));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(0, 0));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(1, 0));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(2, 0));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(3, 0));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(4, 0));
        g.getBoardController().getBoard().setSlotSeeds(new Point(5, 0), 1);
        g.getBoardController().getBoard().setRemainingSeeds(1, 1);
        g.setNextState(GameController.m_playing);
        g.setCurrentPlayer(1);
        g.refresh(1);
        int ret = g.handleState(6);
        Assertions.assertEquals(-2, ret);
        Assertions.assertTrue(g.getNextState() instanceof PromptingState);
    }

    /**
     * Check if handleState() forbids a self-starvation by scattering within a row
     */
    @DisplayName("handleState() with self-starvation within a row, no forfeit - should not fail")
    @Test
    void handleState_selfStarvationNoForfeit_sameRow_shouldnot_fail() {
        Game.getInstance().setSeeds(1, 0);
        Game.getInstance().setSeeds(2, 0);
        Game.getInstance().setPlayer(new Player(1, "Test", new RandomSelect(g.getBoardController(), 1)));
        Game.getInstance().setPlayer(new Player(2, "Test", new RandomSelect(g.getBoardController(), 2)));
        g.getBoardController().resetBoard();
        g.getBoardController().getBoard().emptySlotSeeds(new Point(0, 0));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(1, 0));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(2, 0));
        g.getBoardController().getBoard().emptySlotSeeds(new Point(3, 0));
        g.getBoardController().getBoard().setSlotSeeds(new Point(4, 0), 1);
        g.getBoardController().getBoard().setSlotSeeds(new Point(5, 0), 1);
        g.getBoardController().getBoard().setRemainingSeeds(1, 2);
        g.setCurrentPlayer(1);
        g.setNextState(GameController.m_playing);
        g.refresh(1);
        int ret = g.handleState(5);
        Assertions.assertEquals(0, ret);
        Assertions.assertTrue(g.getNextState() instanceof PromptingState);
    }
}
