package Controllers;

public class PromptedState extends GameState {

    /**
     * Make the player select its entry and display it on the screen
     * @param controller Game controller to use
     * @param input /
     * @return Player's choice
     */
    @Override
    public int handleState(GameController controller, int input){
        //display the game board
        controller.displayGame();

        //refresh playable slots for the current player
        controller.refresh(controller.getCurrentPlayer());

        //get the choice from the user
        int choice = controller.selectSlot(controller.getCurrentPlayer());
        controller.displayMessage(controller.getName(controller.getCurrentPlayer()) + " harvests the slot " + choice);

        //plug in the Playing state
        controller.setNextState(controller.m_playing);

        return choice;
    }
}
