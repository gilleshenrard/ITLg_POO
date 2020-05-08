package Controllers;

public class SwitchingPlayerState implements iGameState {
    /**
     * Switch players (1 becomes 2, 2 becomes 1)
     * @param controller Game controller to use
     * @return /
     */
    @Override
    public int handleState(GameController controller){
        //switch user
        if(controller.getCurrentPlayer() == 1)
            controller.setCurrentPlayer(2);
        else
            controller.setCurrentPlayer(1);

        //plug in the Prompting state
        controller.setNextState(State.PROMPTING.getState());

        return 0;
    }
}