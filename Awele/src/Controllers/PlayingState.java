package Controllers;

import Models.Point;

public class PlayingState implements iGameState {

    /**
     * Make the player play a slot
     * @param controller Game controller to use
     * @param input Slot selected by the user
     * @return 0 if starvation or empty, amount captured otherwise
     */
    @Override
    public int handleState(GameController controller, int input){
        //play the slot selected
        int outcome = controller.playSlot(new Point(input - 1, controller.getCurrentPlayer() - 1));

        if (outcome < 0) {  //player starved or empty slot, get back to prompting state and display forfeit
            controller.setNextState(controller.m_prompting);
            handleOutcome(controller, outcome);
            return 0;
        }
        else {  //Go to the Storing state
            controller.setNextState(controller.m_storing);
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
        if (outcome == -1)
            controller.displayWarning("A player can't be starved. Its amount of seeds can't get to 0");

        //empty slot played
        if (outcome == -2)
            controller.displayWarning("An empty slot can not be harvested");
    }
}