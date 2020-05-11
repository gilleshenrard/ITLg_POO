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
        Point p = this.m_controller.getLastSelected();
        if (p == null)
            return 0;
        else if (this.m_id == p.getY() + 1){
            return p.getX() + 1;
        }
        else
            return 0;
    }
}
