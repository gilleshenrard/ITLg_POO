package Views;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

class GameViewTest {
    GameView g = new GameView();

    @Test
    void displayWin_shouldnot_fail() {
        g.displayWin(1);
    }

    @Test
    void displayWin_invalidID_should_fail() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            g.displayWin(3);
        });
    }
}