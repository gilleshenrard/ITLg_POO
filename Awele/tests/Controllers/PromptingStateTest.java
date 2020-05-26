package Controllers;

import com.gilleshenrard.Awele.Controllers.GameController;
import com.gilleshenrard.Awele.FSM.PlayingState;
import com.gilleshenrard.Awele.FSM.PromptingState;
import com.gilleshenrard.Awele.FSM.State;
import com.gilleshenrard.Awele.Models.Game;
import com.gilleshenrard.Awele.Models.Player;
import com.gilleshenrard.Awele.Models.Point;
import com.gilleshenrard.Awele.Views.Console.BoardConsoleView;
import com.gilleshenrard.Awele.Views.Console.GameConsoleView;
import com.gilleshenrard.Awele.Views.Console.KeyboardSelect;
import com.gilleshenrard.Awele.Views.AI.RandomSelect;
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
        g.setRunning(true);
        g.handleState();
        Assertions.assertEquals(true, g.isRunning());
        Assertions.assertTrue(g.getNextState().getState() instanceof PlayingState);
    }

    /**
     * Check if handleState() forfeits for a keyboardSelect without any slots available
     */
    @DisplayName("handleState() with KeyboardSelect and no slots available, no forfeit - should not fail")
    @Test
    void handleState_Forfeit_shouldnot_fail() {
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
        g.setNextState(State.PROMPTING);
        g.setCurrentPlayer(1);
        g.setRunning(true);
        g.handleState();
        Assertions.assertEquals(false, g.isRunning());
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
        g.setNextState(State.PROMPTING);

        g.setCurrentPlayer(1);
        g.setRunning(true);
        g.handleState();
        Assertions.assertEquals(true, g.isRunning());
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
        g.setNextState(State.PROMPTING);
        g.setCurrentPlayer(1);
        g.setRunning(true);
        g.handleState();
        Assertions.assertEquals(false, g.isRunning());
        Assertions.assertTrue(g.getNextState().getState() instanceof PromptingState);
    }
}
