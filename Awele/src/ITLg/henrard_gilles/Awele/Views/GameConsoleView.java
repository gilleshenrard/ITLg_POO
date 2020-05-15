/****************************************************************************************************/
/*  Class GameConsoleView                                                                           */
/*  Implements SystemMessage                                                                        */
/*  Provides game console system messages methods, and thus communicates with the game controller   */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 11/05/2020                                                                        */
/****************************************************************************************************/
package ITLg.henrard_gilles.Awele.Views;

import ITLg.henrard_gilles.Awele.Controllers.GameController;

public class GameConsoleView extends SystemMessage{

    /**
     * Create a new GameView
     * @param g Game controller to use
     */
    public GameConsoleView(GameController g){
        super(g);
    }

    /**
     * Display a message in the out channel
     * @param msg Message to display
     */
    @Override
    public void displayMessage(String msg){
        System.out.println(msg);
    }

    /**
     * Display a warning message in the out channel
     * @param msg Message to display
     */
    @Override
    public void displayWarning(String msg){
        this.displayMessage(msg);
    }

    /**
     * Display an error message in the err channel
     * @param msg Message to display
     */
    @Override
    public void displayError(String msg){
        System.err.println(msg);
    }
}
