package Controllers;

import Controllers.iSelectable;

import java.util.Random;

public class RandomSelect implements iSelectable {
    private Random m_rand;

    /**
     * Create a new random slot selector
     */
    public RandomSelect(){
        this.m_rand = new Random();
    }

    /**
     * Randomly select a slot (between 0 and 6)
     * @return Random slot number
     */
    @Override
    public int selectSlot() {
        return this.m_rand.nextInt(6);
    }
}
