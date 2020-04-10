package Views;

import Models.Game;

import java.security.InvalidParameterException;

public class GameView {
    Game m_game;

    /**
     * Create a new Game View
     */
    public GameView(){
        this.m_game = Game.getInstance();
    }

    /**
     * Tell which player won the game
     * @param ID ID of the player
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public void displayWin(int ID) throws InvalidParameterException, NullPointerException{
        System.out.println(this.m_game.getName(ID) + " won the game !");
    }
}
