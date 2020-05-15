package Controllers;

import ITLg.POO.GillesHenrard.Awele.Controllers.GameController;
import ITLg.POO.GillesHenrard.Awele.Controllers.PlayingState;
import ITLg.POO.GillesHenrard.Awele.Controllers.State;
import ITLg.POO.GillesHenrard.Awele.Models.Game;
import ITLg.POO.GillesHenrard.Awele.Models.Player;
import ITLg.POO.GillesHenrard.Awele.Models.Point;
import ITLg.POO.GillesHenrard.Awele.Views.BoardConsoleView;
import ITLg.POO.GillesHenrard.Awele.Views.GameConsoleView;
import ITLg.POO.GillesHenrard.Awele.Views.RandomSelect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PromptingStateTest {
    GameController g = new GameController();
    BoardConsoleView bv = new BoardConsoleView();
    GameConsoleView gv = new GameConsoleView(g);

    /**
     * Check if handleState() gets the proper value selected
     */
    @DisplayName("handleState() - should not fail")
    @Test
    void handleState_shouldnot_fail() {
        Game.getInstance().setPlayer(new Player(1, "Test", new RandomSelect(g.getBoardController(), 1)));
        Game.getInstance().setPlayer(new Player(2, "Test", new RandomSelect(g.getBoardController(), 2)));
        g.setNextState(State.PROMPTING);
        int output = g.handleState();
        Assertions.assertTrue(output > 0 && output < 7);
        Assertions.assertTrue(g.getNextState().getState() instanceof PlayingState);
    }

    /**
     * Check if handleState() forbids a self-starvation by scattering to another row
     */
    @DisplayName("handleState() with self-starvation to other row, forfeit - should not fail")
    @Test
    void handleState_selfStarvationForfeit_otherRow_shouldnot_fail() {
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
        g.setNextState(State.PROMPTING);
        g.setCurrentPlayer(1);
        int ret = g.handleState();
        Assertions.assertEquals(6, ret);
        Assertions.assertTrue(g.getNextState().getState() instanceof PlayingState);
    }
}
