package Views;

import java.util.ArrayList;
import java.util.Random;

public class RandomSelect implements iSelectable {
    private ArrayList<Integer> m_playableSlots;

    /**
     * Create a new random slot selector
     */
    public RandomSelect(){
        this.m_playableSlots = null;
    }

    /**
     * Randomly select a slot (between 1 and 6)
     * @return Random slot number
     * @throws NullPointerException
     */
    @Override
    public int selectSlot() throws NullPointerException{
        if (this.m_playableSlots == null)
            throw new NullPointerException("RandomSelect.selectSlot() : null instance of ArrayList<Integer>");

        Random r = new Random();
        return 1 + this.m_playableSlots.get(r.nextInt(this.m_playableSlots.size()));
    }

    /**
     * Inform RandomSelect about the possible slots to play
     * @param playable Slot possible to be picked
     * @throws NullPointerException
     */
    public void setPlayableSlots(ArrayList<Integer> playable) throws NullPointerException {
        if (playable == null)
            throw new NullPointerException("RandomSelect.setPlayableSlots() : NULL instance of ArrayList<Integer>");
        this.m_playableSlots = playable;
    }
}
