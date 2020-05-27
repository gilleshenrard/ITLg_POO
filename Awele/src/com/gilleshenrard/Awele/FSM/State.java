/****************************************************************************************************/
/*  Enumeration State                                                                               */
/*  Holds an implementation of each state available. It eases up the use of FSM in the game control.*/
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 27/05/2020                                                                        */
/****************************************************************************************************/
package com.gilleshenrard.Awele.FSM;

import com.gilleshenrard.Awele.Controllers.GameController;

public enum State{
    PROMPTING(new PromptingState()),
    PLAYING(new PlayingState()),
    STORING(new StoringState()),
    SWITCHING(new SwitchingPlayerState()),
    MENU(new MenuState());

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
    public void handleState(GameController game){
        this.m_state.handleState(game);
    }
}
