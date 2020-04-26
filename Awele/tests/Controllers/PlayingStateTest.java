package Controllers;

import Models.Board;
import Models.Game;
import Models.Player;
import Models.Point;
import Views.GameView;
import Views.RandomSelect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PlayingStateTest {
    GameController g = new GameController(new GameView());

    /**
     * Check if handleState() processes a simple scattering properly (no capture, no starvation)
     */
    @DisplayName("handleState() with neither capture nor starvation - should not fail")
    @Test
    void handleState_noCaptureNoStarve_shouldnot_fail() {
        Game.getInstance().setSeeds(1, 0);
        Game.getInstance().setSeeds(2, 0);
        BoardController b = new BoardController(new Board(), g);
        g.setBoardController(b);
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
        BoardController b = new BoardController(new Board(), g);
        g.setBoardController(b);
        b.getBoard().setSlotSeeds(new Point(1, 1), 2);
        b.getBoard().setSlotSeeds(new Point(3, 1), 1);
        b.getBoard().setSlotSeeds(new Point(4, 1), 9);
        g.setNextState(GameController.m_playing);
        g.setCurrentPlayer(1);
        int ret = g.handleState(6);
        Assertions.assertEquals(5, ret);
        Assertions.assertTrue(g.getNextState() instanceof StoringState);
    }

    /**
     * Check if handleState() processes a starvation properly
     */
    @DisplayName("handleState() with a starvation case - should not fail")
    @Test
    void handleState_noCaptureStarve_shouldnot_fail() {
        Game.getInstance().setSeeds(1, 0);
        Game.getInstance().setSeeds(2, 0);
        BoardController b = new BoardController(new Board(), g);
        g.setBoardController(b);
        b.getBoard().setSlotSeeds(new Point(0, 1), 1);
        b.getBoard().setSlotSeeds(new Point(1, 1), 2);
        b.getBoard().emptySlotSeeds(new Point(2, 1));
        b.getBoard().emptySlotSeeds(new Point(3, 1));
        b.getBoard().emptySlotSeeds(new Point(4, 1));
        b.getBoard().emptySlotSeeds(new Point(5, 1));
        b.getBoard().setSlotSeeds(new Point(5, 0), 2);
        b.getBoard().setRemainingSeeds(2, 3);
        b.getBoard().setRemainingSeeds(1, 22);
        g.setNextState(GameController.m_playing);
        g.setCurrentPlayer(1);
        g.refresh(1);
        int ret = g.handleState(6);
        Assertions.assertEquals(-1, ret);
        Assertions.assertTrue(g.getNextState() instanceof PromptingState);
    }

    /**
     * Check if handleState() processes a victory season
     */
    @DisplayName("handleState() with a victory case - should not fail")
    @Test
    void handleState_victory_shouldnot_fail() {
        BoardController b = new BoardController(new Board(), g);
        g.setBoardController(b);
        b.getBoard().setSlotSeeds(new Point(3, 1), 1);
        b.getBoard().setSlotSeeds(new Point(1, 1), 2);
        Game.getInstance().setSeeds(2, 0);
        Game.getInstance().setSeeds(1, 20);
        g.setNextState(GameController.m_playing);
        g.setCurrentPlayer(1);
        int ret = g.handleState(6);
        Assertions.assertEquals(5, ret);
        Assertions.assertTrue(g.getNextState() instanceof StoringState);
    }

    /**
     * Check if handleState() forbids a self-starvation by scattering to another row
     */
    @DisplayName("handleState() with self-starvation to other row - should not fail")
    @Test
    void handleState_selfStarvation_otherRow_shouldnot_fail() {
        Game.getInstance().setSeeds(1, 0);
        Game.getInstance().setSeeds(2, 0);
        BoardController bc = new BoardController(new Board(), g);
        Game.getInstance().setPlayer(new Player(1, "Test", new RandomSelect(bc, 1)));
        Game.getInstance().setPlayer(new Player(2, "Test", new RandomSelect(bc, 2)));
        g.setBoardController(bc);
        bc.getBoard().emptySlotSeeds(new Point(0, 0));
        bc.getBoard().emptySlotSeeds(new Point(1, 0));
        bc.getBoard().emptySlotSeeds(new Point(2, 0));
        bc.getBoard().emptySlotSeeds(new Point(3, 0));
        bc.getBoard().emptySlotSeeds(new Point(4, 0));
        bc.getBoard().setSlotSeeds(new Point(5, 0), 1);
        bc.getBoard().setRemainingSeeds(1, 1);
        g.setNextState(GameController.m_playing);
        g.setCurrentPlayer(1);
        g.refresh(1);
        int ret = g.handleState(6);
        Assertions.assertEquals(-1, ret);
        Assertions.assertTrue(g.getNextState() instanceof PromptingState);
    }

    /**
     * Check if handleState() forbids a self-starvation by scattering within a row
     */
    @DisplayName("handleState() with self-starvation within a row - should not fail")
    @Test
    void handleState_selfStarvation_sameRow_shouldnot_fail() {
        Game.getInstance().setSeeds(1, 0);
        Game.getInstance().setSeeds(2, 0);
        BoardController b = new BoardController(new Board(), g);
        g.setBoardController(b);
        b.getBoard().emptySlotSeeds(new Point(0, 0));
        b.getBoard().emptySlotSeeds(new Point(1, 0));
        b.getBoard().emptySlotSeeds(new Point(2, 0));
        b.getBoard().emptySlotSeeds(new Point(3, 0));
        b.getBoard().setSlotSeeds(new Point(4, 0), 1);
        b.getBoard().setSlotSeeds(new Point(5, 0), 1);
        b.getBoard().setRemainingSeeds(1, 2);
        g.setCurrentPlayer(1);
        g.setNextState(GameController.m_playing);
        g.refresh(1);
        int ret = g.handleState(5);
        Assertions.assertEquals(-1, ret);
        Assertions.assertTrue(g.getNextState() instanceof PromptingState);
    }
}
