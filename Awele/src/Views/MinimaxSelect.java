package Views;

import Controllers.BoardController;
import Models.Point;

import java.util.ArrayList;

public class MinimaxSelect implements iSelectable{
    private BoardController m_board;
    private int m_id;

    /**
     * Create a new Minimax selection behaviour
     * @param board The board controller to use
     * @param ID The ID of the player to which set the behaviour
     * @throws NullPointerException
     */
    public MinimaxSelect(BoardController board, int ID) throws NullPointerException{
        if(board == null)
            throw new NullPointerException("MinimaxSelect() : NULL instance of BoardController");

        this.m_board = board;
        this.m_id = ID;
    }

    /**
     * Return a flag stating the player is an AI
     * @return true
     */
    @Override
    public boolean isAI() {
        return true;
    }

    /**
     * Select a slot using the Minimax algorithm
     * @return Selection
     */
    @Override
    public int selectSlot() {
        ArrayList<Integer> legalShots = new ArrayList<>();
        Point p = new Point(0, 0);

        //create an array with all the legal shots the player can take
        for(int c = 0 ; c < 6 ; c++){
            p.setCoordinates(c, this.m_id - 1);
            if (this.m_board.isLegal(p))
                legalShots.add(p.getX());
        }


        return 0;
    }
}
