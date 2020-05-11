/****************************************************************************************************/
/*  Enumeration State                                                                               */
/*  Holds an implementation of each state available. It eases up the use of FSM in the game control.*/
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 11/05/2020                                                                        */
/****************************************************************************************************/
package Controllers;

public enum State{
    PROMPTING((iGameState) new PromptingState()),
    PLAYING((iGameState) new PlayingState()),
    STORING((iGameState) new StoringState()),
    SWITCHING((iGameState) new SwitchingPlayerState());

    private iGameState m_state;

    /**
     * Initialise the State assigned to an enumeration
     * @param state State to assign
     */
    State(iGameState state) {
        this.m_state = state;
    }

    /**
     * Get the state assigned to an enumeration
     * @return State
     */
    public iGameState getState(){
        return this.m_state;
    }

    /**
     * Process everything which has to be done in the state
     * @param game Game controller to use
     * @return State output
     */
    public int handleState(GameController game){
        return this.m_state.handleState(game);
    }
}
