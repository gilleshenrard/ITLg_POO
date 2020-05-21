/****************************************************************************************************/
/*  Interface iObserver                                                                             */
/*  Defines the base of an Observer pattern allowing the UI to be refreshed independently of        */
/*      the UI technology used                                                                      */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 11/05/2020                                                                        */
/****************************************************************************************************/
package ITLg.POO.GillesHenrard.Awele.Views;

import ITLg.POO.GillesHenrard.Awele.Controllers.BoardController;

public interface iObserver {
    void update();
    Object getContent();
    void setController(BoardController controller);
    void sendMessage(String msg);
}
