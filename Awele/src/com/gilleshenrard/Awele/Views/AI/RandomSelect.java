/****************************************************************************************************/
/*  Class RandomSelect                                                                              */
/*  Implementation of the Strategy design pattern                                                   */
/*  Allows a player to select a slot randomly among all legal slots he owns                         */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 27/05/2020                                                                        */
/****************************************************************************************************/
package com.gilleshenrard.Awele.Views.AI;

import com.gilleshenrard.Awele.Controllers.BoardController;
import com.gilleshenrard.Awele.Views.Selectable;

import java.util.Random;

public class RandomSelect extends Selectable {

    /**
     * Create a new RandomSelect
     * @param board Game board on which use this behaviour
     *
     */
    public RandomSelect(BoardController board){
        super(board);
    }

    /**
     * Tell if the current behaviour is an AI one or not
     * @return true
     */
    @Override
    public boolean isAI() {
        return true;
    }

    /**
     * Randomly select a slot (between 1 and 6)
     * @return Random slot number, or -2 if error
     */
    @Override
    public int selectSlot(){
        //check if there are any legal slots left
        int ret = super.selectSlot();
        if (ret < 0)
            return ret;

        Random r = new Random();
        //randomly pick a slot
        Integer index = this.getLegal().get(r.nextInt(this.getLegal().size()));
        ret = 1 + index.intValue();
        return ret;
    }
}
