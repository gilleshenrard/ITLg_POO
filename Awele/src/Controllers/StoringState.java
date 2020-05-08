package Controllers;

public class StoringState implements iGameState {
    /**
     * Store the seeds captured by the player
     * @param controller Game controller to use
     * @return 0 if ok, -2 if victory
     */
    @Override
    public int handleState(GameController controller){
        //Game is won by the current player.
        if (controller.getStoredSeeds(controller.getCurrentPlayer()) > 24) {
            controller.updateObservers();
            controller.displayMessage(controller.getName(controller.getCurrentPlayer()) + " won the game !");

            return -2;
        }
        else
            //Go to the player switching state
            controller.setNextState(controller.m_switching);

        return 0;
    }
}