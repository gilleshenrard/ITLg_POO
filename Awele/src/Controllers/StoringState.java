package Controllers;

public class StoringState extends GameState {

    /**
     * Store the seeds captured by the player
     * @param controller Game controller to use
     * @param input Amount of seeds captured by the player
     * @return 0 if ok, -2 if victory
     */
    @Override
    public int handleState(GameController controller, int input){
        //store the seeds
        if(input > 0)
            controller.storeSeeds(controller.getCurrentPlayer(), input);

        //Game is won by the current player.
        if (controller.getSeeds(controller.getCurrentPlayer()) > 24) {
            controller.displayGame();
            controller.displayMessage(controller.getName(controller.getCurrentPlayer()) + " won the game !");

            return -2;
        }
        else
            //Go to the player switching state
            controller.setNextState(controller.m_switching);

        return 0;
    }
}