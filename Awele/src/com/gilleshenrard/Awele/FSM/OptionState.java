/****************************************************************************************************/
/*  Class OptionState                                                                               */
/*  Implementation of the FSM design pattern                                                        */
/*  Makes the game display an Options panel to change players name or behaviours                    */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 27/05/2020                                                                        */
/****************************************************************************************************/
package com.gilleshenrard.Awele.FSM;

import com.gilleshenrard.Awele.Controllers.GameController;

import java.util.logging.Level;
import java.util.logging.Logger;

public class OptionState implements iGameState {
    @Override
    public void handleState(GameController controller) {
        //plug in the Options state
        Logger.getLogger(this.getClass().getName()).log(Level.FINE, "Player " + controller.getCurrentPlayer() + " : next state -> Prompting");
        controller.setNextState(State.PROMPTING);
    }
}
