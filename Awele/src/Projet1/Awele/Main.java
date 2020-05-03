package Projet1.Awele;

import Controllers.GameController;
import Models.Game;
import Views.BoardView;
import Views.KeyboardSelect;
import Views.RandomSelect;
import Models.Player;
import Views.GameView;

public class Main {

    public static void main(String[] args) {
        //game setup
        GameController game = new GameController();
        GameView gameView = new GameView(game);
        BoardView boardView = new BoardView(game.getBoardController());

        //players setup
        Game.getInstance().setPlayer(new Player(1, "Gilles", new KeyboardSelect()));
        Game.getInstance().setPlayer(new Player(2, "AI", new RandomSelect(game.getBoardController(), 2)));

        //state variables
        int outcome = 0;

        //main game loop, while no victory
        while (outcome != -2){
            outcome = game.handleState(outcome);

            //system error, exit with an error
            if (outcome == -1)
                System.exit(-1);
        }
    }
}
