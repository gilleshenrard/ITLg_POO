package Projet1.Awele;

import Controllers.RandomSelect;
import Models.Board;
import Models.Game;
import Models.Player;
import Views.BoardView;
import Views.KeyboardSelect;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        game.setPlayer(1, new Player(1, "Gilles", new KeyboardSelect()));
        game.setPlayer(2, new Player(2, "AI", new RandomSelect()));
        game.setBoard(new Board());
        BoardView b = new BoardView(game.getBoard());
        int choice, player = 0, outcome = 0;

        while (true) {
            b.displayBoard();
            do {
                choice = game.getPlayer(player + 1).selectSlot();
                game.getBoard().playSlot(player + 1, choice);
            }while (outcome == 2);

            player++;
            player %= 2;
        }
    }
}
