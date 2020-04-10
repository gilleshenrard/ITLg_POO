package Views;

public class GameView {
    /**
     * Tell which player won the game
     * @param ID ID of the player
     */
    public void displayWin(String name){
        System.out.println(name + " won the game !");
    }

    /**
     * Tell which player won the game
     * @param ID ID of the player
     */
    public void displayError(String msg){
        System.err.println(msg);
    }
}
