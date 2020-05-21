/****************************************************************************************************/
/*  Class JFXSelect                                                                                 */
/*  Implementation of the Strategy design pattern                                                   */
/*  Allows a player to wait for a click on a JavaFX slot and return its value                       */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 20/05/2020                                                                        */
/****************************************************************************************************/
package ITLg.POO.GillesHenrard.Awele.Views.JFX;

import ITLg.POO.GillesHenrard.Awele.Controllers.BoardController;
import ITLg.POO.GillesHenrard.Awele.Models.Point;
import ITLg.POO.GillesHenrard.Awele.Views.iSelectable;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JFXSelect implements iSelectable {
    private int m_id;
    private BoardController m_controller;

    /**
     * Create a new JFXSelect
     * @param controller Board controller to use
     * @param ID ID of the player selecting the slot
     */
    public JFXSelect(BoardController controller, int ID){
        this.m_id = ID;
        this.m_controller = controller;
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
        //fill an array with all the possible shots
        ArrayList<Integer> legalArray = new ArrayList<>();
        Point p = new Point(0, this.m_id-1);
        for(int i=0 ; i<6 ; i++){
            p.setX(i);
            if (this.m_controller.isLegal(p))
                legalArray.add(i);
        }

        //if no legal shots left, return code
        if (legalArray.size() == 0)
            return -2;

        try {
            //wait for a slot to be clicked in JFXSelect
            synchronized (this.m_controller){
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Player " + this.m_controller.getCurrentPlayer() + " : waiting for an event");
                this.m_controller.wait();
            }
        }
        catch (InterruptedException e){}

        //check if a choice has been made by the player
        p = this.m_controller.getLastSelected();
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Player " + this.m_controller.getCurrentPlayer() + " : last selected : " + p);
        if (p == null) {
            return 0;
        }
        else if (this.m_id == p.getY() + 1){
            //get the last slot chosen by the player
            int choice = p.getX() + 1;

            //reset the last slot selected
            this.m_controller.setLastSelected(null);

            //return the choice (between 0 and 6)
            return choice;
        }
        else
            return 0;
    }
}
