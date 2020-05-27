/****************************************************************************************************/
/*  Class SystemMessage                                                                             */
/*  Base Abstract class linked to the Game Controller, providing system messages methods            */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 27/05/2020                                                                        */
/****************************************************************************************************/
package com.gilleshenrard.Awele.Views;

import com.gilleshenrard.Awele.Controllers.GameController;

public abstract class SystemMessage {

    /**
     * Create a new SystemMessage
     * @param controller Game controller to use
     * @throws NullPointerException
     */
    public SystemMessage(GameController controller) throws NullPointerException{
        if (controller == null)
            throw new NullPointerException("GameView() : NULL instance of GameController");

        controller.setView(this);
    }

    public abstract void displayError(String msg);

    public abstract void displayMenu();
}
