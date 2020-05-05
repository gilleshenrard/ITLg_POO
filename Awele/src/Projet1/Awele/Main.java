package Projet1.Awele;

import Controllers.GameController;
import Views.BoardView;
import Views.KeyboardSelect;
import Models.Player;
import Views.GameView;
import Views.MinimaxSelect;

public class Main {

    public static void main(String[] args) {
        //game setup
        GameController game = new GameController();
        GameView gameView = new GameView(game);
        BoardView boardView = new BoardView(game.getBoardController());

        //players setup
        game.setPlayer(new Player(1, "Gilles", new KeyboardSelect()));
        game.setPlayer(new Player(2, "AI", new MinimaxSelect(game.getBoardController(), 2)));

        //state variables
        int outcome = 0;

        try {
            //main game loop, while no victory
            while (outcome != -2 && outcome != -1) {
                outcome = game.handleState(outcome);
            }
        }
        catch (Exception e){
            game.displayError(e.getMessage());
            System.exit(-1);
        }
        //system error, exit with an error
        if (outcome == -1)
            System.exit(outcome);
    }
}
