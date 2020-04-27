package Projet1.Awele;

import Controllers.BoardController;
import Controllers.GameController;
import Models.Game;
import Views.KeyboardSelect;
import Views.RandomSelect;
import Models.Board;
import Models.Player;
import Views.BoardView;
import Views.GameView;

public class Main {

    public static void main(String[] args) {

        //game setup
        GameView gameView = new GameView();
        GameController game = new GameController(gameView);
        gameView.setController(game);

        //board setup
        Board board = new Board();
        BoardController boardController = new BoardController(board);
        game.setBoardController(boardController);
        BoardView b = new BoardView(boardController);

        //players setup
        Game.getInstance().setPlayer(new Player(1, "Gilles", new KeyboardSelect()));
        Game.getInstance().setPlayer(new Player(2, "AI", new RandomSelect(boardController, 2)));

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
