package Controllers;

public class SwitchingPlayerState implements iGameState {
    /**
     * Switch players (1 becomes 2, 2 becomes 1)
     * @param controller Game controller to use
     * @param input /
     * @return /
     */
    @Override
    public int handleState(GameController controller, int input){
        //switch user
        if(controller.getCurrentPlayer() == 1)
            controller.setCurrentPlayer(2);
        else
            controller.setCurrentPlayer(1);

        //plug in the Prompting state
        controller.setNextState(controller.m_prompting);

        return 0;
    }
}