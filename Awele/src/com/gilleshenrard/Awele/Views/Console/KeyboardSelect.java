/****************************************************************************************************/
/*  Class KeyboardSelect                                                                            */
/*  Implementation of the Strategy design pattern                                                   */
/*  Allows a player to select a slot via a keyboard in console mode                                 */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 11/05/2020                                                                        */
/****************************************************************************************************/
package com.gilleshenrard.Awele.Views.Console;

import com.gilleshenrard.Awele.Controllers.BoardController;
import com.gilleshenrard.Awele.Models.Point;
import com.gilleshenrard.Awele.Views.iSelectable;

import java.util.ArrayList;
import java.util.Scanner;

public class KeyboardSelect implements iSelectable {
    private int m_id;
    private Scanner m_scanner;
    private BoardController m_controller;

    /**
     * Create a new Keyboard slot selector
     */
    public KeyboardSelect(BoardController controller, int ID){
        this.m_id = ID;
        this.m_scanner = new Scanner(System.in);
        this.m_controller = controller;
    }

    /**
     * Tell if the current behaviour is an AI one or not
     * @return false
     */
    @Override
    public boolean isAI() {
        return false;
    }

    /**
     * Prompt the user for a slot selection, and get it
     * @return -2 if no shots available, Slot selected by the user otherwise
     */
    @Override
    public int selectSlot() {
        //fill an array with legal shots available
        ArrayList<Integer> legalArray = new ArrayList<>();
        Point p = new Point(0, this.m_id - 1);
        for(int i=0 ; i<6 ; i++) {
            p.setX(i);
            if (this.m_controller.isLegal(p))
                legalArray.add(i);
        }

        //if no shots available, return code
        if (legalArray.size() == 0)
            return -2;

        //prompt the user and return its choice
        System.out.println("Select which slot to harvest :");
        return this.m_scanner.nextInt();
    }
}
