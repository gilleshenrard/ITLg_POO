package Views;

import Controllers.BoardController;
import Controllers.GameController;
import Models.Board;
import Models.Game;
import Models.Player;
import Models.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

class GameViewTest {
    GameView g = new GameView();

    /**
     * Check if displayMessage() fails displaying the winning message
     */
    @DisplayName("displayMessage() - should not fail")
    @Test
    void displayMessage_shouldnot_fail() {
        g.displayMessage("Test");
    }

    /**
     * Check if displayMessage() throws an exception when given a null instance (should not)
     */
    @DisplayName("displayMessage() with a NULL message - should not fail")
    @Test
    void displayMessage_nullName_shouldnot_fail() {
        g.displayMessage(null);
    }

    /**
     * Check if displayWarning() fails displaying the winning message
     */
    @DisplayName("displayWarning() - should not fail")
    @Test
    void displayWarning_shouldnot_fail() {
        g.displayWarning("Test");
    }

    /**
     * Check if displayWarning() throws an exception when given a null instance (should not)
     */
    @DisplayName("displayWarning() with a NULL message - should not fail")
    @Test
    void displayWarning_nullName_shouldnot_fail() {
        g.displayWarning(null);
    }

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
     * Check if displayGame() throws an exception with a NULL controller
     */
    @DisplayName("displayGame() with a NULL controller - should fail")
    @Test
    void displayGame_nullController_should_fail() {
        g.setBoardView(new BoardView(new BoardController(new Board(), new GameController())));
        Assertions.assertThrows(NullPointerException.class, () -> {
            g.displayGame();
        });
    }

    /**
     * Check if displayGame() throws an exception with a NULL board view
     */
    @DisplayName("displayGame() with a NULL board view - should fail")
    @Test
    void displayGame_nullBoardView_should_fail() {
        g.setController(new GameController());
        Assertions.assertThrows(NullPointerException.class, () -> {
            g.displayGame();
        });
    }

    /**
     * Check if displayGame() fails displaying the game board
     */
    @DisplayName("displayGame() - should not fail")
    @Test
    void displayGame_shouldnot_fail() {
        g.setBoardView(new BoardView(new BoardController(new Board(), new GameController())));
        g.setController(new GameController());
        Game.getInstance().setPlayer(new Player(1, "Test1"));
        Game.getInstance().setPlayer(new Player(2, "Test2"));
        g.displayGame();
    }
}