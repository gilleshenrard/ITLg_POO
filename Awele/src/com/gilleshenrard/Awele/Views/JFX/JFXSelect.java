/****************************************************************************************************/
/*  Class JFXSelect                                                                                 */
/*  Implementation of the Strategy design pattern                                                   */
/*  Allows a player to wait for a click on a JavaFX slot and return its value                       */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 27/05/2020                                                                        */
/****************************************************************************************************/
package com.gilleshenrard.Awele.Views.JFX;

import com.gilleshenrard.Awele.Controllers.BoardController;
import com.gilleshenrard.Awele.Models.Point;
import com.gilleshenrard.Awele.Views.Selectable;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JFXSelect extends Selectable {

    /**
     * Create a new JFXSelect
     * @param controller Board controller to use
     * @param ID ID of the player selecting the slot
     */
    public JFXSelect(BoardController controller, int ID){
        super(controller, ID);
    }

    /**
     * Tell if the current player is an AI or not
     * @return false
     */
    @Override
    public boolean isAI() {
        return false;
    }

    /**
     * Select a slot by getting the last slot clicked by the user
     * @return -2 if no legal shot left, slot selected otherwise
     */
    @Override
    public int selectSlot() {
        //check if there are any legal slots left
        int ret = super.selectSlot();
        if (ret < 0)
            return ret;

        try {
            //wait for a slot to be clicked in JFXSelect
            synchronized (this.getController()){
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Player " + this.getController().getCurrentPlayer() + " : waiting for an event");
                this.getController().wait();
            }
        }
        catch (InterruptedException e){}

        //check if a choice has been made by the player
        Point p = this.getController().getLastSelected();
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Player " + this.getController().getCurrentPlayer() + " : last selected : " + p);
        if (p == null) {
            return 0;
        }
        else if (this.getID() == p.getY() + 1){
            //get the last slot chosen by the player
            ret = p.getX() + 1;

            //reset the last slot selected
            this.getController().setLastSelected(null);

            //return the choice (between 0 and 6)
            return ret;
        }
        else
            return 0;
    }
}
