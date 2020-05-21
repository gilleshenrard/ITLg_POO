package Views;

import ITLg.POO.GillesHenrard.Awele.Controllers.GameController;
import ITLg.POO.GillesHenrard.Awele.Models.Game;
import ITLg.POO.GillesHenrard.Awele.Models.Player;
import ITLg.POO.GillesHenrard.Awele.Views.Console.BoardConsoleView;
import ITLg.POO.GillesHenrard.Awele.Views.Console.GameConsoleView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameConsoleViewTest {
    GameController gc = new GameController();
    GameConsoleView g = new GameConsoleView(gc);

    /**
     * Check if displayError() fails displaying an error message
     */
    @DisplayName("displayError() - should not fail")
    @Test
    void displayError_shouldnot_fail() {
        g.displayError("A player cannot be hungry");
    }

    /**
     * Check if displayError() throws an exception when given a null instance (should not)
     */
    @DisplayName("displayError() with a NULL message - should not fail")
    @Test
    void displayError_nullmsg_shouldnot_fail() {
        g.displayError(null);
    }

    /**
     * Check if displayGame() fails displaying the game board
     */
    @DisplayName("displayGame() - should not fail")
    @Test
    void displayGame_shouldnot_fail() {
        gc.getBoardController().attach(new BoardConsoleView());
        Game.getInstance().setPlayer(new Player(1, "Test1"));
        Game.getInstance().setPlayer(new Player(2, "Test2"));
        gc.displayGame();
    }
}