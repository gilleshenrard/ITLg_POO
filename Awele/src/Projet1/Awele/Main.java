package Projet1.Awele;

import Views.RandomSelect;
import Models.Board;
import Models.Game;
import Models.Player;
import Views.BoardView;
import Views.GameView;
import Views.KeyboardSelect;

import java.security.InvalidParameterException;

public class Main {

    public static void main(String[] args) {
        Game game = Game.getInstance();
        game.setPlayer(1, new Player(1, "Gilles", new KeyboardSelect()));
        game.setPlayer(2, new Player(2, "AI", new RandomSelect()));
        game.setBoard(new Board());
        BoardView b = new BoardView(game.getBoard());
        GameView gameView = new GameView();
        int choice, player = 0, outcome = 0;

        //main game loop
        while (outcome != 1) {
            gameView.displayMessage("This is " + game.getName(player+1) + "'s season");
            b.displayBoard();

            //loop while the user selects a wrong slot or the opponent risks being starved
            do {
                try {
                    choice = game.getPlayer(player + 1).selectSlot();
                    outcome = game.playSlot(player + 1, choice);
                    gameView.displayMessage(game.getName(player+1) + " harvests the slot " + game.getLastSlotPlayed());

                    //player starved
                    if (outcome == 2)
                        gameView.displayWarning("A player can't be starved. Its amount of seeds can't get to 0");

                    //player starved
                    if (outcome == 3)
                        gameView.displayWarning("An empty slot can not be harvested");
                }
                //System error. Display error message
                catch (NullPointerException e){
                    gameView.displayError(e.getMessage());
                }
                //Slot out of range or other system error. Display message and get back in the loop
                catch (InvalidParameterException e){
                    gameView.displayError(e.getMessage());
                    outcome = 2;
                }
            }while (outcome == 2 || outcome == 3);

            gameView.displayMessage("--------------------------------------------------------------------");

            //Game is won by the current player.
            if (outcome == 1)
                gameView.displayMessage(game.getName(player+1) + " won the game !");

            //Select next player
            player++;
            player %= 2;
        }
    }
}
