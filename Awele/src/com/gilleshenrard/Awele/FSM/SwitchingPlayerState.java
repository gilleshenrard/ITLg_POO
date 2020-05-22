/****************************************************************************************************/
/*  Class SwitchingPlayerState                                                                      */
/*  Implementation of the FSM design pattern                                                        */
/*  Updates the UI, switches between player 1 and player 2, then leads to the Prompting state       */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 11/05/2020                                                                        */
/****************************************************************************************************/
package com.gilleshenrard.Awele.FSM;

import com.gilleshenrard.Awele.Controllers.GameController;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SwitchingPlayerState implements iGameState {
    /**
     * Switch players (1 becomes 2, 2 becomes 1)
     * @param controller Game controller to use
     * @return /
     */
    @Override
    public void handleState(GameController controller){
        //update the game board
        controller.displayGame();

        //switch user
        controller.setCurrentPlayer(controller.getOpponent());
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Player " + controller.getCurrentPlayer() + "'s turn");

        //plug in the Prompting state
        Logger.getLogger(this.getClass().getName()).log(Level.FINE, "Player " + controller.getCurrentPlayer() + " : next state -> Prompting");
        controller.setNextState(State.PROMPTING);
    }
}