package Models;

import java.security.InvalidParameterException;

public class Board {
    private Slot[][] m_slots;
    private int remSeedsPl1;
    private int remSeedsPl2;
    private Game m_game;

    /**
     * Create new Board
     */
    public Board(){
        this.m_slots = new Slot[2][6];
        for (int l = 0 ; l < 2 ; l++){
            for (int c = 0 ; c < 6 ; c++){
                this.m_slots[l][c] = new Slot(c, l);
            }
        }

        this.remSeedsPl1 = 24;
        this.remSeedsPl2 = 24;
        this.m_game = Game.getInstance();
    }

    /**
     * Set the Game instance
     */
    public void setGame(){
        this.m_game = Game.getInstance();
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

    /**
     * Get the slot located at X,Y
     * @param x X coordinate of the slot
     * @param y Y coordinate of the slot
     * @return Slot requested
     * @throws InvalidParameterException
     */
    public Slot getSlot(int x, int y) throws InvalidParameterException{
        if(x < 0 || x > 5)
            throw new InvalidParameterException("X value must be 0 <= x < 6");
        if(y != 0 && y != 1)
            throw new InvalidParameterException("Y value must be 0 or 1");

        return this.m_slots[y][x];
    }

    /**
     * Get the next slot (increment x, and roll y when reached the end)
     * @param s Slot of which to find the next
     * @return Next slot to s
     * @throws NullPointerException
     */
    public Slot getNext(Slot s) throws NullPointerException{
        if (s == null)
            throw new NullPointerException("Slot must not be null");

        int x = s.getX();
        int y = s.getY();

        x++;
        x %= 6;
        if(x == 0){
            y++;
            y %= 2;
        }

        return this.getSlot(x, y);
    }
}
