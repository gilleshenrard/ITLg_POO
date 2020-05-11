/****************************************************************************************************/
/*  Interface iObserver                                                                             */
/*  Defines the base of an Observer pattern allowing the UI to be refreshed independently of        */
/*      the UI technology used                                                                      */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 11/05/2020                                                                        */
/****************************************************************************************************/
package Controllers;

public interface iObserver {
    void update();
    Object getContent();
    void setController(BoardController controller);
}
