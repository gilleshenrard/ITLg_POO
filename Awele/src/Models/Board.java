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
     * Throw an exception if ID != 1 or ID != 2
     * @param ID ID of the player
     * @param msg Name of the method in which the validation occurs
     * @throws InvalidParameterException
     */
    public static void validateID(int ID, String msg) throws InvalidParameterException{
        if (ID != 1 && ID != 2)
            throw new InvalidParameterException(msg + ": invalid ID provided (value : " + ID + ")");
    }

    /**
     * Throw an exception if not 0 <= x <= 5 of y != 1,2
     * @param point Coordinates to validate
     * @param msg Name of the method in which the validation occurs
     * @throws InvalidParameterException
     */
    public static void validateCoordinates(Point point, String msg) throws InvalidParameterException{
        if(point.getX() < 0 || point.getX() > 5 || (point.getY() != 0 && point.getY() != 1))
            throw new InvalidParameterException(msg + " : incorrect coordinates (values : " + point.getX() + "," + point.getY() + ")");
    }

    /**
     * Return the remaining amount of seeds for a player
     * @param ID ID of the player
     * @return Amount of seeds remaining
     * @throws InvalidParameterException
     */
    public int getRemainingSeeds(int ID) throws InvalidParameterException{
        Board.validateID(ID,"Board.getRemainingSeeds()");

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
        Board.validateID(ID, "Board.setRemainingSeeds()");
        Slot.validateNbSeeds(value, "Board.setRemainingSeeds()");

        if (ID == 1)
            this.m_remSeedsPl1 = value;
        else
            this.m_remSeedsPl2 = value;
    }

    /**
     * Get the slot located at point coordinates
     * @param point Coordinates of the slot
     * @return Slot requested
     * @throws InvalidParameterException
     */
    public Slot getSlot(Point point) throws InvalidParameterException{
        Board.validateCoordinates(point, "Board.getSlot()");
        return this.m_slots[point.getY()][point.getX()];
    }

    /**
     * Set the amount of seeds contained in the slot located at X,Y
     * @param point Coordinates of the slot
     * @param nbSeeds Amount of seeds to set to the slot
     * @throws InvalidParameterException
     */
    public void setSlotSeeds(Point point, int nbSeeds) throws InvalidParameterException{
        this.getSlot(point).setNbSeeds(nbSeeds);
    }

    /**
     * Get the amount of seeds contained in the slot located at X,Y
     * @param point Coordinates of the slot
     * @return Amount of seeds
     * @throws InvalidParameterException
     */
    public int getSlotSeeds(Point point) throws InvalidParameterException{
        return this.getSlot(point).getNbSeeds();
    }

    /**
     * Empty a slot located at point
     * @param point Coordinates of the slot to empty
     * @throws InvalidParameterException
     */
    public void emptySlotSeeds(Point point) throws InvalidParameterException{
        this.getSlot(point).emptySeeds();
    }

    /**
     * Increment the amount of seeds in a Slot located at point
     * @param point Coordinates of the Slot of which increment the seeds
     * @throws InvalidParameterException
     */
    public void incrementSlotSeeds(Point point) throws InvalidParameterException{
        this.getSlot(point).incrementSeeds();
    }

    /**
     * Decrement the amount of seeds in a Slot located at point
     * @param point Coordinates of the Slot of which decrement the seeds
     * @throws InvalidParameterException
     */
    public void decrementSlotSeeds(Point point) throws InvalidParameterException{
        this.getSlot(point).decrementSeeds();
    }

    /**
     * Get the next slot coordinates (increment x, and roll y when reached the end)
     * @param point Point of which find the next
     * @return Next slot to point
     * @throws NullPointerException
     */
    public Point getNext(Point point) throws NullPointerException{
        validateCoordinates(point, "Board.getNext()");
        if (point == null)
            throw new NullPointerException("Board.getNext() : NULL instance of Point");

        //retrieve current coordinates
        int x = point.getX();
        int y = point.getY();

        //increment X
        //if end of row (X rolled back to 0), increment Y
        // if end of column, roll Y back to 0
        x++;
        if((x %= 6) == 0){
            y++;
            y %= 2;
        }

        return new Point(x, y);
    }

    /**
     * Get a buffer with all the non-empty slots
     * @param ID ID of the player for which getting the slots
     * @return Buffer
     * @throws InvalidParameterException
     */
    public ArrayList<Integer> getNonEmpty(int ID) throws InvalidParameterException{
        Board.validateID(ID, "Board.getNonEmpty()");

        ArrayList<Integer> array = new ArrayList<>();

        for(Slot s : this.m_slots[ID-1]){
            if (s.getNbSeeds() > 0)
                array.add(s.getCoordinates().getX());
        }

        return array;
    }
}
