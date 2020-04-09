package Models;

import java.security.InvalidParameterException;

public class Board {
    private Slot[][] m_slots;
    private int remSeedsPl1;
    private int remSeedsPl2;

    /**
     * Create new Board
     */
    public Board(){
        this.m_slots = new Slot[2][6];
        for (int l = 0 ; l < 1 ; l++){
            for (int c = 0 ; c < 6 ; c++){
                this.m_slots[l][c] = new Slot(c, l);
            }
        }

        this.remSeedsPl1 = 24;
        this.remSeedsPl2 = 24;
    }

    /**
     * Return the board slots
     * @return Board slots
     */
    public Slot[][] getSlots(){
        return this.m_slots;
    }

    /**
     * Return the remaining amount of seeds for a player
     * @param ID ID of the player
     * @return Amount of seeds remaining
     * @throws InvalidParameterException
     */
    public int getRemainingSeeds(int ID) throws InvalidParameterException{
        if (ID != 1 && ID != 2)
            throw new InvalidParameterException("ID must be 1 or 2");

        if (ID == 1)
            return this.remSeedsPl1;
        else
            return this.remSeedsPl2;
    }
}
