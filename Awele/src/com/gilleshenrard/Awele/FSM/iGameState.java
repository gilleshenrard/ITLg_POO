/****************************************************************************************************/
/*  Interface iGameState                                                                            */
/*  Defines the base of a Finite Machine State pattern holding all the game states possible         */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 11/05/2020                                                                        */
/****************************************************************************************************/
package com.gilleshenrard.Awele.FSM;

import com.gilleshenrard.Awele.Controllers.GameController;

public interface iGameState {
    /**
     * Base handleState method. Defines the signature for all children
     * @param controller Game controller to use
     * @param input Input of a state
     * @return Output of the state
     */
    void handleState(GameController controller);
}
