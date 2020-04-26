package Controllers;

public class StoringState extends GameState {

    /**
     * Store the seeds captured by the player
     * @param controller Game controller to use
     * @param input Slot selected by the user
     * @return 0 if ok, 1 if victory
     */
    @Override
    public int handleInput(GameController controller, int input){
        if(input > 0)
            controller.storeSeeds(controller.getCurrentPlayer(), input);

        //Game is won by the current player.
        if (controller.getSeeds(controller.getCurrentPlayer()) > 24) {
            controller.displayGame();
            controller.displayMessage(controller.getName(controller.getCurrentPlayer()) + " won the game !");

            return 1;
        }
        else
            controller.setNextState(controller.m_switching);

        return 0;
    }
}