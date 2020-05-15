/****************************************************************************************************/
/*  Interface iSelectable                                                                           */
/*  Defines the base of a Strategy design pattern to choose players' behaviours on the fly          */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 11/05/2020                                                                        */
/****************************************************************************************************/
package ITLg.POO.GillesHenrard.Awele.Views;

public interface iSelectable {
    boolean isAI();
    int selectSlot();
}
