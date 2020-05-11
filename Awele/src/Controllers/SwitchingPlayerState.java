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
     * @return /
     */
    @Override
    public int handleState(GameController controller){
        //update the game board
        controller.updateObservers();

        //switch user
        controller.setCurrentPlayer(controller.getOpponent());

        //plug in the Prompting state
        controller.setNextState(State.PROMPTING);

        return 0;
    }
}