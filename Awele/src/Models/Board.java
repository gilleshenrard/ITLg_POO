package Models;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class Board {
    private Slot[][] m_slots;
    private int m_remSeedsPl1;
    private int m_remSeedsPl2;

    /**
     * Create new Board
     */
    public Board(){
        this.setRemainingSeeds(1, 24);
        this.setRemainingSeeds(2, 24);
        
        this.m_slots = new Slot[2][6];
        for (int l = 0 ; l < 2 ; l++){
            for (int c = 0 ; c < 6 ; c++){
                this.m_slots[l][c] = new Slot(c, l);
            }
        }
    }

    /**
     * Throw an exception if not 0 <= x <= 5 of y != 1,2
     * @param x X coordinate to validate
     * @param y Y coordinate to validate
     * @param msg Name of the method in which the validation occurs
     * @throws InvalidParameterException
     */
    public static void validateCoordinates(int x, int y, String msg) throws InvalidParameterException{
        if(x < 0 || x > 5 || (y != 0 && y != 1))
            throw new InvalidParameterException(msg + " : incorrect coordinates (values : " + x + "," + y + ")");
    }

    /**
     * Return the remaining amount of seeds for a player
     * @param ID ID of the player
     * @return Amount of seeds remaining
     * @throws InvalidParameterException
     */
    public int getRemainingSeeds(int ID) throws InvalidParameterException{
        Game.validateID(ID,"Board.getRemainingSeeds()");

        if (ID == 1)
            return this.m_remSeedsPl1;
        else
            return this.m_remSeedsPl2;
    }

    /**
     * Assigns a new value to m_remainingseeds regarding the player ID
     * @param ID ID of the player for which assign the new value
     * @param value New amount of remaining seeds for the player
     * @throws InvalidParameterException
     */
    public void setRemainingSeeds(int ID, int value) throws InvalidParameterException{
        Game.validateID(ID, "Board.setRemainingSeeds()");
        Slot.validateNbSeeds(value, "Board.setRemainingSeeds()");

        if (ID == 1)
            this.m_remSeedsPl1 = value;
        else
            this.m_remSeedsPl2 = value;
    }

    /**
     * Add remaining seeds to a player
     * @param ID ID of the player
     * @param value Amount to add
     * @throws InvalidParameterException
     */
    public void addRemainingSeeds(int ID, int value) throws InvalidParameterException {
        this.setRemainingSeeds(ID, this.getRemainingSeeds(ID) + value);
    }

    /**
     * Remove remaining seeds from a player
     * @param ID ID of the player
     * @param value Amount to remove
     * @throws InvalidParameterException
     */
    public void removeRemainingSeeds(int ID, int value) throws InvalidParameterException {
        this.setRemainingSeeds(ID, this.getRemainingSeeds(ID) - value);
    }

    /**
     * Get the slot located at X,Y
     * @param x X coordinate of the slot
     * @param y Y coordinate of the slot
     * @return Slot requested
     * @throws InvalidParameterException
     */
    public Slot getSlot(int x, int y) throws InvalidParameterException{
        Board.validateCoordinates(x, y, "Board.getSlot()");
        return this.m_slots[y][x];
    }

    /**
     * Get the amount of seeds contained in the slot located at X,Y
     * @param x X coordinate of the slot
     * @param y Y coordinate of the slot
     * @return Amount of seeds
     * @throws InvalidParameterException
     */
    public int getSlotSeeds(int x, int y) throws InvalidParameterException{
        return this.getSlot(x, y).getNbSeeds();
    }

    /**
     * Get the next slot (increment x, and roll y when reached the end)
     * @param s Slot of which to find the next
     * @return Next slot to s
     * @throws NullPointerException
     */
    public Slot getNext(Slot s) throws NullPointerException{
        if (s == null)
            throw new NullPointerException("Board.getNext() : NULL instance of Slot");

        //retrieve current coordinates
        int x = s.getX();
        int y = s.getY();

        //increment X
        //if end of row (X rolled back to 0), increment Y
        // if end of column, roll Y back to 0
        x++;
        if((x %= 6) == 0){
            y++;
            y %= 2;
        }

        return this.getSlot(x, y);
    }

    /**
     * Get a buffer with all the non-empty slots
     * @param ID ID of the player for which getting the slots
     * @return Buffer
     * @throws InvalidParameterException
     */
    public ArrayList<Integer> getNonEmpty(int ID) throws InvalidParameterException{
        Game.validateID(ID, "Board.getNonEmpty()");

        ArrayList<Integer> array = new ArrayList<>();

        for(Slot s : this.m_slots[ID-1]){
            if (s.getNbSeeds() > 0)
                array.add(s.getX());
        }

        return array;
    }
}
