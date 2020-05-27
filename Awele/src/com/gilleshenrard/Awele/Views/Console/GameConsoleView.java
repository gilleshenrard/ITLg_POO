/****************************************************************************************************/
/*  Class GameConsoleView                                                                           */
/*  Implements SystemMessage                                                                        */
/*  Provides game console system messages methods, and thus communicates with the game controller   */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 27/05/2020                                                                        */
/****************************************************************************************************/
package com.gilleshenrard.Awele.Views.Console;

import com.gilleshenrard.Awele.Controllers.GameController;
import com.gilleshenrard.Awele.Views.SystemMessage;

public class GameConsoleView extends SystemMessage {

    /**
     * Create a new GameView
     * @param g Game controller to use
     */
    public GameConsoleView(GameController g){
        super(g);
    }

    /**
     * Display an error message in the err channel
     * @param msg Message to display
     */
    @Override
    public void displayError(String msg){
        System.err.println(msg);
    }

    /**
     * Display a console menu
     */
    @Override
    public void displayMenu() {

    }
}
