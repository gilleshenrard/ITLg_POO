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

import java.security.InvalidParameterException;

public class Main {

    public static void main(String[] args) {

        //game setup
        GameView gameView = new GameView();
        GameController game = new GameController(gameView);

        //board setup
        Board board = new Board();
        BoardController boardController = new BoardController(board, game);
        BoardView b = new BoardView(boardController);
        boardController.setBoardView(b);
        game.setBoardController(boardController);

        //players setup
        Game.getInstance().setPlayer(1, new Player(1, "Gilles", new KeyboardSelect()));
        Game.getInstance().setPlayer(2, new Player(2, "AI", new RandomSelect(boardController, 2)));

        //state variables
        int choice, player = 0, outcome = 0;

        //main game loop
        while (outcome != 3) {
            game.displayMessage("This is " + game.getName(player+1) + "'s season");
            boardController.displayBoard();

            //refresh playable slots for the current player
            game.refresh(player + 1);

            //loop while the user selects a wrong slot or the opponent risks being starved
            do {
                try {
                    //select a slot, then play it
                    choice = game.selectSlot(player + 1);
                    game.displayMessage(game.getName(player + 1) + " harvests the slot " + choice);
                    outcome = game.playSlot(player + 1, choice);

                    //in case of starvation or empty slot played
                    if (outcome < 0) {
                        //player starved
                        if (outcome == -1)
                            game.displayWarning("A player can't be starved. Its amount of seeds can't get to 0");

                        //empty slot played
                        if (outcome == -2)
                            game.displayWarning("An empty slot can not be harvested");

                        //if current player plays randomly and don't have any possible moves left, forfeit
                        if (game.isPlayerAI(player + 1)) {
                            if (game.getShotsLeft(player + 1) == 0) {
                                game.displayMessage(game.getName(player + 1) + " can't make any move. He forfeits !");

                                //Easter egg : when both players play randomly and one of them forfeits, he says the last quote of the W.P.O.R. in the movie Wargames
                                if(game.isPlayerAI(1) && game.isPlayerAI(2)) {
                                    game.displayMessage("\n" + game.getName(player + 1) + " : 'A strange game... The only winning move is not to play...'");
                                    game.displayMessage(game.getName(player + 1) + " : '......................... How about a nice game of chess?'");
                                }
                                System.exit(0);
                            }
                        }
                    }
                }
                //System error. Display error message
                catch (NullPointerException e){
                    game.displayError(e.getMessage());
                    System.exit(-1);
                }
                //Slot out of range or other system error. Display message and get back in the loop
                catch (InvalidParameterException e){
                    game.displayError(e.getMessage());
                    System.exit(-2);
                }
            }while (outcome < 0);

            game.displayMessage("--------------------------------------------------------------------");

            //Game is won by the current player.
            if (game.getSeeds(player + 1) > 24) {
                boardController.displayBoard();
                game.displayMessage(game.getName(player + 1) + " won the game !");
                outcome = 3;
            }

            //Select next player
            player++;
            player %= 2;
        }
    }
}
