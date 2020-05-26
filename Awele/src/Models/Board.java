/****************************************************************************************************/
/*  Class Board                                                                                     */
/*  Encloses the game board manipulations                                                           */
/*  The board contains a static array of slots, a players' remaining seeds count                    */
/*      and a players' stored (saved) seeds count                                                   */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 11/05/2020                                                                        */
/****************************************************************************************************/
package Models;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class Board {
    private Slot[][] m_slots;
    private int[] m_remSeedsPlayer;
    private int[] m_storedSeeds;

    /**
     * Create new Board
     */
    public Board(){
        this.m_remSeedsPlayer = new int[2];
        java.util.Arrays.fill(m_remSeedsPlayer, 24);
        this.m_storedSeeds = new int[2];
        java.util.Arrays.fill(m_storedSeeds, 0);
        
        this.m_slots = new Slot[2][6];
        for (int l = 0 ; l < 2 ; l++){
            for (int c = 0 ; c < 6 ; c++){
                this.m_slots[l][c] = new Slot(c, l);
            }
        }
    }

    /**
     * Copy a Board from another Board
     * @param board Board to copy
     */
    public Board(Board board){
        this.m_remSeedsPlayer = new int[2];
        this.setRemainingSeeds(1, board.getRemainingSeeds(1));
        this.setRemainingSeeds(2, board.getRemainingSeeds(2));

        this.m_storedSeeds = new int[2];
        this.setStoredSeeds(1, board.getStoredSeeds(1));
        this.setStoredSeeds(2, board.getStoredSeeds(2));

        Point p = new Point(0, 0);
        this.m_slots = new Slot[2][6];
        for (int l = 0 ; l < 2 ; l++){
            for (int c = 0 ; c < 6 ; c++){
                p.setCoordinates(c, l);
                this.m_slots[l][c] = new Slot(p);
                this.m_slots[l][c].setNbSeeds(board.getSlot(p).getNbSeeds());
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

        return this.m_remSeedsPlayer[ID - 1];
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

        this.m_remSeedsPlayer[ID - 1] = value;
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
     * Add nb_seeds to the seeds reserve of Player 1 or Player 2
     * @param ID ID of the player who receives the seeds
     * @param nb_seeds  Amount of seeds to store
     * @throws InvalidParameterException
     */
    public void setStoredSeeds(int ID, int nb_seeds) throws InvalidParameterException{
        Board.validateID(ID, "Board.storeSeeds()");

        if(nb_seeds < 0 || nb_seeds > 48)
            throw new InvalidParameterException("Board.storeSeeds() : incorrect amount of seeds (value : " + nb_seeds + ")");

        this.m_storedSeeds[ID - 1] = nb_seeds;
    }

    /**
     * Return the amount of stored seeds for a Player
     * @param ID ID of the player
     * @return Amount of seeds stored
     * @throws InvalidParameterException
     */
    public int getStoredSeeds(int ID) throws InvalidParameterException {
        Board.validateID(ID, "Board.getSeeds()");

        return this.m_storedSeeds[ID - 1];
    }

    /**
     * Get the slot located at point coordinates
     * @param point Coordinates of the slot
     * @return Slot requested
     * @throws InvalidParameterException
     */
    private Slot getSlot(Point point) throws InvalidParameterException{
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
     * Get the next slot in which a seed can be scattered
     * @param point Point of which find the next
     * @return Next slot to point
     * @throws NullPointerException
     * @throws InvalidParameterException
     */
    public Point getNext(Point point) throws NullPointerException, InvalidParameterException{
        return this.getSubsequent(point, 1);
    }

    /**
     * Get the Xth slot after a point in which a seed can be scattered (skip said point each turn)
     * @param point Point of which find the next
     * @param subsequent Which one of the subsequent seeds to get
     * @return Next slot to point
     * @throws NullPointerException
     * @throws InvalidParameterException
     */
    public Point getSubsequent(Point point, int subsequent) throws NullPointerException, InvalidParameterException{
        validateCoordinates(point, "Board.getSubsequent()");
        if (point == null)
            throw new NullPointerException("Board.getSubsequent() : NULL instance of Point");
        if (subsequent < 0)
            throw new InvalidParameterException("Board.getSubsequent() : negative subsequent number");

        //compute new x (start X + amount of seeds + amount of times the starting point is reached, then get the remainder of / 6)
        int x = (point.getX() + subsequent + (subsequent/12)) % 6;

        //compute new y (amount of back and forth the scattering would take + take start slot into account, then divide by 2)
        int y = ((point.getX() + subsequent + (subsequent/12)) / 6) % 2;

        //rectify the new Y if point is on opponent's side
        if (point.getY() == 1)
            y = 1 - y;

        return new Point(x, y);
    }

    /**
     * Get the previous slot coordinates (decrement x, and roll y when reached the beginning)
     * @param point Point of which find the next
     * @return Previous slot from point
     * @throws NullPointerException
     * @throws InvalidParameterException
     */
    public Point getPrevious(Point point) throws NullPointerException, InvalidParameterException{
        validateCoordinates(point, "Board.getSubsequent()");
        if (point == null)
            throw new NullPointerException("Board.getSubsequent() : NULL instance of Point");

        int x = point.getX();
        int y = point.getY();

        //decrement X
        x--;

        //if beginning of row, roll X and decrement Y
        if (x < 0){
            x = 5;
            y--;
        }

        //rectify Y
        if (y < 0)
            y = 1;

        return new Point(x, y);
    }

    /**
     * Reset the board to an inial value
     */
    public void reset(){
        this.setRemainingSeeds(1, 24);
        this.setRemainingSeeds(2, 24);

        this.setStoredSeeds(1, 0);
        this.setStoredSeeds(2, 0);

        Point p = new Point(0, 0);
        for (int l = 0 ; l < 2 ; l++){
            for (int c = 0 ; c < 6 ; c++){
                p.setCoordinates(c, l);
                this.setSlotSeeds(p, 4);
            }
        }
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
