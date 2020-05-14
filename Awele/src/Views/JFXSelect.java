package Views;

import Controllers.BoardController;
import Models.Point;

public class JFXSelect implements iSelectable {
    private int m_id;
    private BoardController m_controller;

    public JFXSelect(BoardController controller, int ID){
        this.m_id = ID;
        this.m_controller = controller;
    }

    @Override
    public boolean isAI() {
        return false;
    }

    @Override
    public int selectSlot() {
        try {
            //wait for a slot to be clicked in JFXSelect
            synchronized (this.m_controller){
                this.m_controller.wait();
            }
        }
        catch (InterruptedException e){}

        //check if a choice has been made by the player
        Point p = this.m_controller.getLastSelected();
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
