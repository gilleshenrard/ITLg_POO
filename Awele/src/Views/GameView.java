package Views;

import Controllers.GameController;

public class GameView extends SystemMessage{

    /**
     * Create a new GameView
     * @param g Game controller to use
     */
    public GameView(GameController g){
        super(g);
    }

    /**
     * Display a message in the out channel
     * @param msg Message to display
     */
    public void displayMessage(String msg){
        System.out.println(msg);
    }

    /**
     * Display a warning message in the out channel
     * @param msg Message to display
     */
    public void displayWarning(String msg){
        this.displayMessage(msg);
    }

    /**
     * Display an error message in the err channel
     * @param msg Message to display
     */
    public void displayError(String msg){
        System.err.println(msg);
    }

    /**
     * Display a separator in the console
     */
    public void displaySeparator(){
        System.out.println("=====================================================");
    }
}
