/****************************************************************************************************/
/*  Class MenuState                                                                                 */
/*  Implementation of the FSM design pattern                                                        */
/*  Makes the game display an Options panel to change players name or behaviours                    */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 27/05/2020                                                                        */
/****************************************************************************************************/
package com.gilleshenrard.Awele.FSM;

import com.gilleshenrard.Awele.Controllers.GameController;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuState implements iGameState {
    @Override
    public void handleState(GameController controller) {
        //plug in the Prompting state
        controller.setNextState(State.PROMPTING);
    }
}
