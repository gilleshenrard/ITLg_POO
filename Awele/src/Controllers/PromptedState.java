package Controllers;

public class PromptedState extends GameState {

    /**
     * Make the player select its entry and display it on the screen
     * @param controller Game controller to use
     * @param input /
     * @return /
     */
    @Override
    public int handleInput(GameController controller, int input){
        controller.displayGame();
        //refresh playable slots for the current player
        controller.refresh(controller.getCurrentPlayer());

        int choice = controller.selectSlot(controller.getCurrentPlayer());
        controller.displayMessage(controller.getName(controller.getCurrentPlayer()) + " harvests the slot " + choice);

        controller.setNextState(controller.m_playing);

        return 0;
    }
}
