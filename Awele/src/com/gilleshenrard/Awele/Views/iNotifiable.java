/****************************************************************************************************/
/*  Class iNotifiable                                                                               */
/*  Interface linked to the Game Controller, providing system messages methods                      */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 27/05/2020                                                                        */
/****************************************************************************************************/
package com.gilleshenrard.Awele.Views;

public interface iNotifiable {

    public abstract void displayError(String msg);

    public abstract void displayMenu();
}
