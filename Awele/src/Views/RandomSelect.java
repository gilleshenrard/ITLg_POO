package Views;

import Controllers.BoardController;
import Models.Point;

import java.util.ArrayList;
import java.util.Random;

public class RandomSelect implements iSelectable {
    private int m_id;
    private BoardController m_board;
    private ArrayList<Integer> m_legal;

    /**
     * Create a new RandomSelect
     * @param board Game board on which use this behaviour
     * @param ID ID of the player using this behaviour
     */
    public RandomSelect(BoardController board, int ID){
        this.m_id = ID;
        this.m_board = board;
        this.m_legal = new ArrayList<>();
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
        //create an array with all the legal shots the player can take
        this.m_legal.clear();
        Point tmp = new Point(0, 0);
        for(int i = 0 ; i<6 ; i++){
            tmp.setCoordinates(i, this.m_id - 1);
            if (this.m_board.isLegal(tmp))
                this.m_legal.add(i);
        }

        //if there are any legal shots
        if (this.m_legal.size() > 0) {
            Random r = new Random();
            //randomly pick a slot
            Integer index = this.m_legal.get(r.nextInt(this.m_legal.size()));
            return 1 + index.intValue();
        }
        else
            return -2;
    }
}
