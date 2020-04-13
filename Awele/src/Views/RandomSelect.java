package Views;

import Models.Board;

import java.util.ArrayList;
import java.util.Random;

public class RandomSelect implements iSelectable {
    private int m_id;
    private Board m_board;
    private ArrayList<Integer> m_playable;

    public RandomSelect(Board board, int ID){
        this.m_id = ID;
        this.m_board = board;
        this.m_playable = null;
    }

    /**
     * Randomly select a slot (between 1 and 6)
     * @return Random slot number
     * @throws NullPointerException
     */
    @Override
    public int selectSlot() throws NullPointerException{
        if (this.m_playable == null)
            throw new NullPointerException("RandomSelect.selectSlot() : null instance of ArrayList<Integer>");

        if (this.m_playable.size() > 0) {
            Random r = new Random();
            Integer index = this.m_playable.get(r.nextInt(this.m_playable.size()));
            this.m_playable.remove(index);
            return 1 + index.intValue();
        }
        else
            return 0;
    }

    /**
     * Refresh the array of non-empty slots
     */
    public void reset(){
        this.m_playable = this.m_board.getNonEmpty(this.m_id);
    }

    /**
     * Return the amount of slots not tried yet
     * @return Amount of slots left
     * @throws NullPointerException
     */
    public int getShotsLeft() throws NullPointerException{
        if (this.m_playable == null)
            throw new NullPointerException("RandomSelect.selectSlot() : null instance of ArrayList<Integer>");

        return this.m_playable.size();
    }
}
