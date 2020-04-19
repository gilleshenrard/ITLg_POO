package Projet1.Awele;

import Views.KeyboardSelect;
import Views.RandomSelect;
import Models.Board;
import Models.Game;
import Models.Player;
import Views.BoardView;
import Views.GameView;

import java.security.InvalidParameterException;

public class Main {

    public static void main(String[] args) {
        Game game = Game.getInstance();
        game.setBoard(new Board());
        game.setPlayer(1, new Player(1, "Gilles", new KeyboardSelect()));
        game.setPlayer(2, new Player(2, "AI", new RandomSelect(game.getBoard(), 2)));
        BoardView b = new BoardView(game.getBoard());
        GameView gameView = new GameView();
        int choice, player = 0, outcome = 0;

        //main game loop
        while (outcome != 3) {
            gameView.displayMessage("This is " + game.getName(player+1) + "'s season");
            b.displayBoard();

            //refresh playable slots for the current player if he plays randomly
            game.getPlayer(player + 1).getBehaviour().reset();

            //loop while the user selects a wrong slot or the opponent risks being starved
            do {
                try {
                    //select a slot, then play it
                    choice = game.selectSlot(player + 1);
                    gameView.displayMessage(game.getName(player + 1) + " harvests the slot " + choice);
                    outcome = game.playSlot(player + 1, choice);

                    //in case of starvation or empty slot played
                    if (outcome == 1 || outcome == 2) {
                        //player starved
                        if (outcome == 1)
                            gameView.displayWarning("A player can't be starved. Its amount of seeds can't get to 0");

                        //empty slot played
                        if (outcome == 2)
                            gameView.displayWarning("An empty slot can not be harvested");

                        //if current player plays randomly and don't have any possible moves left, forfeit
                        if (game.getPlayer(player + 1).getBehaviour() instanceof RandomSelect) {
                            RandomSelect r = (RandomSelect) game.getPlayer(player + 1).getBehaviour();
                            if (r.getShotsLeft() == 0) {
                                gameView.displayMessage(game.getName(player + 1) + " can't make any move. He forfeits !");

                                //Easter egg : when both players play randomly and one of them forfeits, he says the last quote of the W.P.O.R. in the movie Wargames
                                if(game.getPlayer(1).getBehaviour() instanceof RandomSelect && game.getPlayer(2).getBehaviour() instanceof RandomSelect) {
                                    gameView.displayMessage("\n" + game.getName(player + 1) + " : 'A strange game... The only winning move is not to play...'");
                                    gameView.displayMessage(game.getName(player + 1) + " : '......................... How about a nice game of chess?'");
                                }
                                System.exit(0);
                            }
                        }
                    }
                }
                //System error. Display error message
                catch (NullPointerException e){
                    gameView.displayError(e.getMessage());
                    System.exit(-1);
                }
                //Slot out of range or other system error. Display message and get back in the loop
                catch (InvalidParameterException e){
                    gameView.displayError(e.getMessage());
                    System.exit(-2);
                }
            }while (outcome == 1 || outcome == 2);

            gameView.displayMessage("--------------------------------------------------------------------");

            //Game is won by the current player.
            if (game.getSeeds(player + 1) > 24) {
                b.displayBoard();
                gameView.displayMessage(game.getName(player + 1) + " won the game !");
                outcome = 3;
            }

            //Select next player
            player++;
            player %= 2;
        }
    }
}
