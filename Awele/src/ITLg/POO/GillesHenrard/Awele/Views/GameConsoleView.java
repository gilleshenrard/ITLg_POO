/****************************************************************************************************/
/*  Class GameConsoleView                                                                           */
/*  Implements SystemMessage                                                                        */
/*  Provides game console system messages methods, and thus communicates with the game controller   */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 11/05/2020                                                                        */
/****************************************************************************************************/
package ITLg.POO.GillesHenrard.Awele.Views;

import ITLg.POO.GillesHenrard.Awele.Controllers.GameController;

public class GameConsoleView extends SystemMessage{

    /**
     * Create a new GameView
     * @param g Game controller to use
     */
    public GameConsoleView(GameController g){
        super(g);
    }

    /**
     * Display a warning message in the out channel
     * @param msg Message to display
     */
    @Override
    public void displayWarning(String msg){
        System.out.println(msg);
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
