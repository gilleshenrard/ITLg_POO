/****************************************************************************************************/
/*  Class PromptingState                                                                            */
/*  Implementation of the FSM design pattern                                                        */
/*  Makes the player select a slot, then either leads to the Playing state,                         */
/*      or returns an error code (-2 in case of forfeit)                                            */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 27/05/2020                                                                        */
/****************************************************************************************************/
package com.gilleshenrard.Awele.FSM;

import com.gilleshenrard.Awele.Controllers.GameController;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PromptingState implements iGameState {
    /**
     * Make the player select its entry and display it on the screen
     * @param controller Game controller to use
     * @return Value to return to the main loop (-2 if forfeit, Player's choice otherwise)
     */
    @Override
    public void handleState(GameController controller){
        //update the game board
        controller.displayGame();

        //make any AI player wait for 1s before choosing
        if (controller.isPlayerAI(controller.getCurrentPlayer())) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, "Player " + controller.getCurrentPlayer() + " : waits for 1s");
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){}
        }

        //get the choice from the user
        int choice = controller.selectSlot(controller.getCurrentPlayer());
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Player " + controller.getCurrentPlayer() + " : selectSlot() returned " + choice);

        if (controller.isPauseRequested()){
            controller.pauseSeason();
        }

        if(controller.isMenuRequested()){
            //plug in the Menu state
            controller.setNextState(State.MENU);
        }
        else if(choice > 0) {
            if (controller.isPlayerAI(controller.getCurrentPlayer())) {
                controller.displayMessage(controller.getName(controller.getCurrentPlayer()) + " harvests the slot " + choice);
                Logger.getLogger(this.getClass().getName()).log(Level.FINE, "Player " + controller.getCurrentPlayer() + " : message displayed");
            }

            //plug in the Playing state
            controller.setNextState(State.PLAYING);
            ((PlayingState)State.PLAYING.getState()).setInput(choice);
        }
        else{
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Player " + controller.getCurrentPlayer() + " forfeits");
            controller.displayMessage(controller.getName(controller.getCurrentPlayer()) + " can't make any move. He forfeits !");

            //make the main thread wait for 2s
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Game waits for 2s");
            try {
                Thread.sleep(2000);
            }catch (InterruptedException e){}

            //Easter egg : when both players play randomly and one of them forfeits, he says the last quote of the W.P.O.R. in the movie Wargames
            if(controller.isPlayerAI(1) && controller.isPlayerAI(2)) {
                controller.displayMessage("\n" + controller.getName(controller.getCurrentPlayer()) + " : 'A strange game... The only winning move is not to play...'");
                controller.displayMessage(controller.getName(controller.getCurrentPlayer()) + " : '......................... How about a nice game of chess?'");
            }

            //request a pause at the end of the season
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Pause the season");
            controller.pauseSeason();
        }
    }
}
