package Views;

import Controllers.BoardController;

import java.util.ArrayList;
import java.util.Random;

public class RandomSelect implements iSelectable {
    private int m_id;
    private BoardController m_board;
    private ArrayList<Integer> m_playable;

    /**
     * Create a new RandomSelect
     * @param board Game board on which use this behaviour
     * @param ID ID of the player using this behaviour
     */
    public RandomSelect(BoardController board, int ID){
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

        //if there are non-empty slots left
        if (this.m_playable.size() > 0) {
            Random r = new Random();
            //randomly pick a slot
            Integer index = this.m_playable.get(r.nextInt(this.m_playable.size()));
            //remove it from the array so it can't be played again until the array is refreshed
            //  (avoids collisions : picking the same slots infinitely)
            this.m_playable.remove(index);
            return 1 + index.intValue();
        }
        else
            return 0;
    }

    /**
     * Refresh the array of non-empty slots
     */
    @Override
    public void refresh(){
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
