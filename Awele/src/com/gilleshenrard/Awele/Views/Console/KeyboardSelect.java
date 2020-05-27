/****************************************************************************************************/
/*  Class KeyboardSelect                                                                            */
/*  Implementation of the Strategy design pattern                                                   */
/*  Allows a player to select a slot via a keyboard in console mode                                 */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 27/05/2020                                                                        */
/****************************************************************************************************/
package com.gilleshenrard.Awele.Views.Console;

import com.gilleshenrard.Awele.Controllers.BoardController;
import com.gilleshenrard.Awele.Models.Point;
import com.gilleshenrard.Awele.Views.Selectable;

import java.util.ArrayList;
import java.util.Scanner;

public class KeyboardSelect extends Selectable {

    /**
     * Create a new Keyboard slot selector
     */
    public KeyboardSelect(BoardController controller, int ID){
        super(controller, ID);
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
        //check if there are any legal slots available
        int ret = super.selectSlot();
        if (ret < 0)
            return ret;

        //prompt the user and return its keyboard choice
        System.out.println("Select which slot to harvest :");
        Scanner scanner = new Scanner(System.in);
        ret = scanner.nextInt();
        return ret;
    }
}
