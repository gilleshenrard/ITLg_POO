/****************************************************************************************************/
/*  Class SwitchingPlayerState                                                                      */
/*  Implementation of the FSM design pattern                                                        */
/*  Switches between player 1 and player 2, then leads to the Prompting state                       */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 11/05/2020                                                                        */
/****************************************************************************************************/
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

        //display the current player's name
        controller.displaySeparator();
        controller.displayMessage("This is " + controller.getName(controller.getCurrentPlayer()) + "'s season");

        //plug in the Prompting state
        controller.setNextState(controller.m_prompting);

        return 0;
    }
}