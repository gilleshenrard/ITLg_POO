/****************************************************************************************************/
/*  Class PlayingState                                                                              */
/*  Implementation of the FSM design pattern                                                        */
/*  Makes the player play the slot selected in the Prompting state, then                            */
/*      either leads back to the Prompting state, or leads to the Storing state                     */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 11/05/2020                                                                        */
/****************************************************************************************************/
package ITLg.POO.GillesHenrard.Awele.Controllers;

import ITLg.POO.GillesHenrard.Awele.Models.Point;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayingState implements iGameState {
    private int m_slot = 0;

    /**
     * Set the slot to play
     * @param input Slot index to play
     */
    public void setInput(int input) {
        this.m_slot = input;
    }

    /**
     * Make the player play a slot
     * @param controller Game controller to use
     * @return 0 if starvation or empty, amount captured otherwise
     */
    @Override
    public int handleState(GameController controller){
        //play the slot selected
        Logger.getLogger("Awele").log(Level.FINE, "Player " + controller.getCurrentPlayer() + " enters Playing state");
        int outcome = controller.playSlot(new Point(this.m_slot - 1, controller.getCurrentPlayer() - 1));
        Logger.getLogger("Awele").log(Level.INFO, "Player " + controller.getCurrentPlayer() + " : playSlot() returned " + outcome);

        if (outcome < 0) {  //player starved or empty slot, get back to prompting state and display forfeit
            Logger.getLogger("Awele").log(Level.FINE, "Player " + controller.getCurrentPlayer() + " : next state -> Prompting");
            controller.setNextState(State.PROMPTING);
            handleOutcome(controller, outcome);
            return 0;
        }
        else {  //Go to the Storing state
            Logger.getLogger("Awele").log(Level.FINE, "Player " + controller.getCurrentPlayer() + " : next state -> Storing");
            controller.setNextState(State.STORING);
            return outcome;
        }
    }

    /**
     * Display a message according the outcome
     * @param controller Game controller to use
     * @param outcome Outcome of the current season played
     */
    private void handleOutcome(GameController controller, int outcome){
        //player starved
        if (outcome == -1) {
            controller.displayWarning("A player can't be starved. Its amount of seeds can't get to 0");
            Logger.getLogger("Awele").log(Level.FINE, "Player " + controller.getCurrentPlayer() + " : message displayed");
        }

        //empty slot played
        if (outcome == -2) {
            controller.displayWarning("An empty slot can not be harvested");
            Logger.getLogger("Awele").log(Level.FINE, "Player " + controller.getCurrentPlayer() + " : message displayed");
        }
    }
}