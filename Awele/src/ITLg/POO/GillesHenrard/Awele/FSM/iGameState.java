/****************************************************************************************************/
/*  Interface iGameState                                                                            */
/*  Defines the base of a Finite Machine State pattern holding all the game states possible         */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 11/05/2020                                                                        */
/****************************************************************************************************/
package ITLg.POO.GillesHenrard.Awele.FSM;

import ITLg.POO.GillesHenrard.Awele.Controllers.GameController;

public interface iGameState {
    /**
     * Base handleState method. Defines the signature for all children
     * @param controller Game controller to use
     * @param input Input of a state
     * @return Output of the state
     */
    int handleState(GameController controller);
}
