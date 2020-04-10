package Views;

import Models.Game;
import Models.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

class GameViewTest {
    GameView g = new GameView();

    @Test
    void displayWin_shouldnot_fail() {
        Game game = Game.getInstance();
        game.setPlayer(1, new Player(1, "Testname"));
        g.displayWin(1);
    }

    @Test
    void displayWin_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.displayWin(3);
        });
    }

    @Test
    void displayWin_nullPlayer_should_fail() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            g.displayWin(2);
        });
    }
}