package Views;

import java.security.InvalidParameterException;

public class GameView {
    /**
     * Tell which player won the game
     * @param ID ID of the player
     * @throws InvalidParameterException
     */
    public void displayWin(int ID) throws InvalidParameterException{
        if(ID != 1 && ID !=2)
            throw new InvalidParameterException("The ID must be 1 or 2");

        System.out.println("Player " + ID + " won the game !");
    }
}
