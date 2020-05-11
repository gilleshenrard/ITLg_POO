/****************************************************************************************************/
/*  Class GameView                                                                                  */
/*  Provides game display methods, and thus communicates with the game controller                   */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 11/05/2020                                                                        */
/****************************************************************************************************/
package Views;

import Controllers.GameController;

import java.security.InvalidParameterException;

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
     * Display the whole board and score of the two players
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public void displayGame() throws InvalidParameterException, NullPointerException {
        if (this.m_controller == null)
            throw new NullPointerException("GameView.displayGame() : NULL instance of GameController");

        //display the board
        //OPPONENT
        //|  0 |
        //|  6 ||  5 ||  4 ||  3 ||  2 ||  1 |
        //|  1 ||  2 ||  3 ||  4 ||  5 ||  6 |
        //|  0 |
        //PLAYER

        System.out.println(this.m_controller.getName(2));
        this.m_controller.displayBoard();
        System.out.println(this.m_controller.getName(1));
    }

    /**
     * Display a separator in the console
     */
    public void displaySeparator(){
        System.out.println("=====================================================");
    }
}
