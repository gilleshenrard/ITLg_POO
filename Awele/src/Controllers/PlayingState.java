package Controllers;

import Models.Point;

import java.security.InvalidParameterException;

public class PlayingState implements iGameState {

    /**
     * Make the player play a slot
     * @param controller Game controller to use
     * @param input Slot selected by the user
     * @return -2 if forfeit, -1 if error, 0 if ok (or cancelled)
     */
    @Override
    public int handleState(GameController controller, int input){
        //play the slot selected
        int outcome = 0;
        try {
            outcome = controller.playSlot(new Point(input - 1, controller.getCurrentPlayer() - 1));
        }
        //System error. Display error message and quit
        catch (NullPointerException e){
            controller.displayError(e.getMessage());
            return -1;
        }
        //Slot out of range or other system error. Display error message and quit
        catch (InvalidParameterException e){
            controller.displayError(e.getMessage());
            return -1;
        }

        if (outcome < 0) {  //player starved or empty slot, get back to prompting state and display forfeit
            controller.setNextState(controller.m_prompting);
            return handleOutcome(controller, outcome);
        }
        else {  //Go to the Storing state
            controller.setNextState(controller.m_storing);
            return outcome;
        }
    }

    /**
     * Handle any starving or empty slot outcome
     * @param controller Game controller to use
     * @param outcome Outcome of the current season played
     * @return Value to return to the main loop (-2 if forfeit, 0 if ok)
     */
    private int handleOutcome(GameController controller, int outcome){
        //player starved
        if (outcome == -1)
            controller.displayWarning("A player can't be starved. Its amount of seeds can't get to 0");

        //empty slot played
        if (outcome == -2)
            controller.displayWarning("An empty slot can not be harvested");

        //if current player doesn't have any possible moves left, forfeit
        if (controller.getShotsLeft(controller.getCurrentPlayer()) <= 0) {
            controller.displayMessage(controller.getName(controller.getCurrentPlayer()) + " can't make any move. He forfeits !");

            //Easter egg : when both players play randomly and one of them forfeits, he says the last quote of the W.P.O.R. in the movie Wargames
            if(controller.isPlayerAI(1) && controller.isPlayerAI(2)) {
                controller.displayMessage("\n" + controller.getName(controller.getCurrentPlayer()) + " : 'A strange game... The only winning move is not to play...'");
                controller.displayMessage(controller.getName(controller.getCurrentPlayer()) + " : '......................... How about a nice game of chess?'");
            }
            return -2;
        }

        return 0;
    }
}