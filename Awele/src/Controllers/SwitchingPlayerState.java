package Controllers;

public class SwitchingPlayerState extends GameState {
    /**
     * Switch players (1 becomes 2, 2 becomes 1)
     * @param controller Game controller to use
     * @param input /
     * @return /
     */
    @Override
    public int handleInput(GameController controller, int input){
        if(controller.getCurrentPlayer() == 1)
            controller.setCurrentPlayer(2);
        else
            controller.setCurrentPlayer(1);

        controller.displayMessage("This is " + controller.getName(controller.getCurrentPlayer()) + "'s season");

        controller.setNextState(controller.m_prompting);

        return 0;
    }
}