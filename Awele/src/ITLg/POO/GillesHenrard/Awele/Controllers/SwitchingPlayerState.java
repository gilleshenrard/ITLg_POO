/****************************************************************************************************/
/*  Class SwitchingPlayerState                                                                      */
/*  Implementation of the FSM design pattern                                                        */
/*  Updates the UI, switches between player 1 and player 2, then leads to the Prompting state       */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 11/05/2020                                                                        */
/****************************************************************************************************/
package ITLg.POO.GillesHenrard.Awele.Controllers;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SwitchingPlayerState implements iGameState {
    /**
     * Switch players (1 becomes 2, 2 becomes 1)
     * @param controller Game controller to use
     * @return /
     */
    @Override
    public int handleState(GameController controller){
        //update the game board
        Logger.getLogger("Awele").log(Level.FINE, "Player " + controller.getCurrentPlayer() + " enters Switching state");
        controller.updateObservers();

        //switch user
        controller.setCurrentPlayer(controller.getOpponent());
        Logger.getLogger("Awele").log(Level.INFO, "Player " + controller.getCurrentPlayer() + "'s turn");

        //plug in the Prompting state
        Logger.getLogger("Awele").log(Level.FINE, "Player " + controller.getCurrentPlayer() + " : next state -> Prompting");
        controller.setNextState(State.PROMPTING);

        return 0;
    }
}