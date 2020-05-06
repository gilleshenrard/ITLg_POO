package Views;

import Controllers.GameController;

public class GameView {
    private GameController m_controller;

    /**
     * Create a new GameView
     * @param controller Game controller to use
     * @throws NullPointerException
     */
    public GameView(GameController controller) throws NullPointerException{
        if (controller == null)
            throw new NullPointerException("GameView() : NULL instance of GameController");

        this.m_controller = controller;
        this.m_controller.setView(this);
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
