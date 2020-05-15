package Controllers;

import ITLg.henrard_gilles.Awele.Controllers.*;
import ITLg.henrard_gilles.Awele.Models.Game;
import ITLg.henrard_gilles.Awele.Models.Player;
import ITLg.henrard_gilles.Awele.Models.Point;
import ITLg.henrard_gilles.Awele.Views.GameConsoleView;
import ITLg.henrard_gilles.Awele.Views.RandomSelect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PlayingStateTest {
    GameController g = new GameController();
    GameConsoleView gv = new GameConsoleView(g);

    /**
     * Check if handleState() processes a simple scattering properly (no capture, no starvation)
     */
    @DisplayName("handleState() with neither capture nor starvation - should not fail")
    @Test
    void handleState_noCaptureNoStarve_shouldnot_fail() {
        g.getBoardController().getBoard().setStoredSeeds(1, 0);
        g.getBoardController().getBoard().setStoredSeeds(2, 0);
        g.getBoardController().getBoard().setSlotSeeds(new Point(5, 0), 4);
        g.getBoardController().getBoard().setRemainingSeeds(1, 4);
        g.setNextState(State.PLAYING);
        g.setCurrentPlayer(1);
        ((PlayingState)State.PLAYING.getState()).setInput(6);
        int ret = g.handleState();
        Assertions.assertEquals(0, ret);
        Assertions.assertTrue(g.getNextState().getState() instanceof StoringState);
    }

    /**
     * Check if handleState() processes a simple scattering properly (2 captures, no starvation)
     */
    @DisplayName("handleState() with a capture case - should not fail")
    @Test
    void handleState_CaptureNoStarve_shouldnot_fail() {
        g.getBoardController().getBoard().setStoredSeeds(1, 0);
        g.getBoardController().getBoard().setStoredSeeds(2, 0);
        g.resetGame();
        g.getBoardController().getBoard().setSlotSeeds(new Point(1, 1), 2);
        g.getBoardController().getBoard().setSlotSeeds(new Point(3, 1), 1);
        g.getBoardController().getBoard().setSlotSeeds(new Point(4, 1), 9);
        g.setNextState(State.PLAYING);
        g.setCurrentPlayer(1);
        ((PlayingState)State.PLAYING.getState()).setInput(6);
        int ret = g.handleState();
        Assertions.assertEquals(5, ret);
        Assertions.assertTrue(g.getNextState().getState() instanceof StoringState);
    }

    /**
     * Check if handleState() processes a starvation properly
     */
    @DisplayName("handleState() with a starvation case, no forfeit - should not fail")
    @Test
    void handleState_noCaptureStarveNoForfeit_shouldnot_fail() {
        g.getBoardController().getBoard().setStoredSeeds(1, 0);
        g.getBoardController().getBoard().setStoredSeeds(2, 0);
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
        g.setNextState(State.PLAYING);
        g.setCurrentPlayer(1);
        ((PlayingState)State.PLAYING.getState()).setInput(6);
        int ret = g.handleState();
        Assertions.assertEquals(0, ret);
        Assertions.assertTrue(g.getNextState().getState() instanceof PromptingState);
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
        g.setNextState(State.PLAYING);
        g.setCurrentPlayer(1);
        ((PlayingState)State.PLAYING.getState()).setInput(6);
        int ret = g.handleState();
        Assertions.assertEquals(0, ret);
        Assertions.assertTrue(g.getNextState().getState() instanceof StoringState);
    }

    /**
     * Check if handleState() processes a self-starvation by scattering within a row
     */
    @DisplayName("handleState() with self-starvation within a row, no forfeit - should not fail")
    @Test
    void handleState_selfStarvationNoForfeit_sameRow_shouldnot_fail() {
        g.getBoardController().getBoard().setStoredSeeds(1, 0);
        g.getBoardController().getBoard().setStoredSeeds(2, 0);
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
        g.setNextState(State.PLAYING);
        ((PlayingState)State.PLAYING.getState()).setInput(5);
        int ret = g.handleState();
        Assertions.assertEquals(2, ret);
        Assertions.assertTrue(g.getNextState().getState() instanceof StoringState);
    }
}
