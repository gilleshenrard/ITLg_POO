package Controllers;

public class PromptingState implements iGameState {
    /**
     * Make the player select its entry and display it on the screen
     * @param controller Game controller to use
     * @return Value to return to the main loop (-2 if forfeit, Player's choice otherwise)
     */
    @Override
    public int handleState(GameController controller){
        //display the game board
        controller.updateObservers();

        //get the choice from the user
        int choice = controller.selectSlot(controller.getCurrentPlayer());
        if(choice > 0) {
            if (controller.isPlayerAI(controller.getCurrentPlayer()))
                controller.displayMessage(controller.getName(controller.getCurrentPlayer()) + " harvests the slot " + choice);

            //plug in the Playing state
            controller.setNextState(State.PLAYING);
            ((PlayingState)State.PLAYING.getState()).setInput(choice);

            return choice;
        }
        else{
            controller.displayMessage(controller.getName(controller.getCurrentPlayer()) + " can't make any move. He forfeits !");

            //Easter egg : when both players play randomly and one of them forfeits, he says the last quote of the W.P.O.R. in the movie Wargames
            if(controller.isPlayerAI(1) && controller.isPlayerAI(2)) {
                controller.displayMessage("\n" + controller.getName(controller.getCurrentPlayer()) + " : 'A strange game... The only winning move is not to play...'");
                controller.displayMessage(controller.getName(controller.getCurrentPlayer()) + " : '......................... How about a nice game of chess?'");
            }
            return -2;
        }
    }
}
