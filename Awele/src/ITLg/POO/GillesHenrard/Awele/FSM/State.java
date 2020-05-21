/****************************************************************************************************/
/*  Enumeration State                                                                               */
/*  Holds an implementation of each state available. It eases up the use of FSM in the game control.*/
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 11/05/2020                                                                        */
/****************************************************************************************************/
package ITLg.POO.GillesHenrard.Awele.FSM;

import ITLg.POO.GillesHenrard.Awele.Controllers.GameController;

public enum State{
    PROMPTING((iGameState) new PromptingState(), "Prompting"),
    PLAYING((iGameState) new PlayingState(), "Playing"),
    STORING((iGameState) new StoringState(), "Storing"),
    SWITCHING((iGameState) new SwitchingPlayerState(), "Switching");

    private iGameState m_state;
    private String m_name;

    /**
     * Initialise the State assigned to an enumeration
     * @param state State to assign
     */
    State(iGameState state, String name) {
        this.m_state = state;
        this.m_name = name;
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

    /**
     * Return the current state name as a string
     * @return State name
     */
    public String toString(){
        return this.m_name;
    }
}
