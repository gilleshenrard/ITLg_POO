package Projet1.Awele;

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
        game.setPlayer(1, new Player(1, "Gilles", new RandomSelect(game.getBoard(), 1)));
        game.setPlayer(2, new Player(2, "AI", new RandomSelect(game.getBoard(), 2)));
        BoardView b = new BoardView(game.getBoard());
        GameView gameView = new GameView();
        int choice, player = 0, outcome = 0;

        //main game loop
        while (outcome != 1) {
            gameView.displayMessage("This is " + game.getName(player+1) + "'s season");
            b.displayBoard();

            //refresh playable slots for player 1 if he plays randomly
            if(game.getPlayer(1).getBehaviour() instanceof RandomSelect){
                RandomSelect r = (RandomSelect) game.getPlayer(1).getBehaviour();
                r.reset();
            }

            //refresh playable slots for player 2 if he plays randomly
            if(game.getPlayer(2).getBehaviour() instanceof RandomSelect){
                RandomSelect r = (RandomSelect) game.getPlayer(2).getBehaviour();
                r.reset();
            }

            //loop while the user selects a wrong slot or the opponent risks being starved
            do {
                try {
                    //select a slot, then play it
                    choice = game.getPlayer(player + 1).selectSlot();
                    gameView.displayMessage(game.getName(player + 1) + " harvests the slot " + choice);
                    outcome = game.playSlot(player + 1, choice);

                    //in case of starvation or empty slot played
                    if (outcome == 2 || outcome == 3) {
                        //player starved
                        if (outcome == 2)
                            gameView.displayWarning("A player can't be starved. Its amount of seeds can't get to 0");

                        //empty slot played
                        if (outcome == 3)
                            gameView.displayWarning("An empty slot can not be harvested");

                        //if player 1 plays randomly and don't have any possible moves left, forfeit
                        if (game.getPlayer(1).getBehaviour() instanceof RandomSelect) {
                            RandomSelect r = (RandomSelect) game.getPlayer(1).getBehaviour();
                            if (r.getShotsLeft() == 0) {
                                gameView.displayMessage(game.getName(1) + " can't make any move. He forfeits !");
                                System.exit(0);
                            }
                        }

                        //if player 2 plays randomly and don't have any possible moves left, forfeit
                        if (game.getPlayer(2).getBehaviour() instanceof RandomSelect) {
                            RandomSelect r = (RandomSelect) game.getPlayer(2).getBehaviour();
                            if (r.getShotsLeft() == 0) {
                                gameView.displayMessage(game.getName(2) + " can't make any move. He forfeits !");
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
            }while (outcome == 2 || outcome == 3);

            gameView.displayMessage("--------------------------------------------------------------------");

            //Game is won by the current player.
            if (outcome == 1) {
                b.displayBoard();
                gameView.displayMessage(game.getName(player + 1) + " won the game !");
            }

            //Select next player
            player++;
            player %= 2;
        }
    }
}
