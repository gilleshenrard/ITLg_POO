package Views;

import java.util.Random;

public class RandomSelect implements iSelectable {
    /**
     * Randomly select a slot (between 1 and 6)
     * @return Random slot number
     */
    @Override
    public int selectSlot(){

        Random r = new Random();
        return 1 + r.nextInt(6);
    }
}
