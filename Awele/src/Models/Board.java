package Models;

import java.security.InvalidParameterException;
import java.util.ArrayList;

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
        Game.validateID(ID,"getRemainingSeeds()");

        if (ID == 1)
            return this.remSeedsPl1;
        else
            return this.remSeedsPl2;
    }

    /**
     * Get the name of a player regarding its ID
     * @param ID ID of the player of which getting the name
     * @return Name of the player
     * @throws InvalidParameterException
     */
    public String getName(int ID) throws InvalidParameterException{
        return this.m_game.getName(ID);
    }

    /**
     * Assigns a new value to m_remainingseeds regarding the player ID
     * @param ID ID of the player for which assign the new value
     * @param value New amount of remaining seeds for the player
     * @throws InvalidParameterException
     */
    public void setRemainingSeeds(int ID, int value) throws InvalidParameterException{
        Game.validateID(ID, "setRemainingSeeds");

        if(value < 0 || value > 24)
            throw new InvalidParameterException("setRemainingSeeds() : incorrect amount of seeds (value : " + value + ")");

        if (ID == 1)
            this.remSeedsPl1 = value;
        else
            this.remSeedsPl2 = value;
    }

    /**
     * Get the slot located at X,Y
     * @param x X coordinate of the slot
     * @param y Y coordinate of the slot
     * @return Slot requested
     * @throws InvalidParameterException
     */
    public Slot getSlot(int x, int y) throws InvalidParameterException{
        Board.validateCoordinates(x, y, "getSlot()");
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
            throw new NullPointerException("getNext() : NULL instance of Slot");

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
     * Harvest the seeds from a slot and, if necessary, scatter them
     * @param id ID of the player harvesting
     * @param slot Slot being harvested
     * @return 0 if no futher action, 1 if victory, 2 if season cancelled
     * @throws InvalidParameterException
     */
    public int playSlot(int id, int slot) throws InvalidParameterException{
        Game.validateID(id, "playSlot()");
        Board.validateCoordinates(slot - 1, id - 1, "playSlot()");

        //get number of seeds in the slot harvested by the player
        Slot s = this.getSlot(slot-1, id-1);
        int nbseeds = s.getNbSeeds();
        int backupseeds = s.getNbSeeds();

        if(nbseeds == 0)
            return 2;

        //empty the slot (it's been harvested)
        s.emptySeeds();

        //save all the slots after the slot harvested in a buffer until there are no seeds left
        Slot sNext = this.getNext(s);
        ArrayList<Slot> buffer = new ArrayList<Slot>();
        while (nbseeds > 0){
            //make sure not to add the slot harvested
            if(!sNext.equals(s)) {
                sNext.incrementSeeds();
                buffer.add(sNext); //a slot can be added several times in the buffer
                nbseeds--;
            }

            sNext = this.getNext(sNext);
        }

        //get the amount of seeds in the last slot of the buffer
        int seeds = buffer.get(buffer.size()-1).getNbSeeds();

        //if that amount is 2 or 3, a capture needs to be made
        if(seeds == 2 || seeds == 3){
            //check if this seasons risks to starve the opponent
            int sumseeds = this.getSumCapturable(buffer);
            if(sumseeds == this.getRemainingSeeds(3 - id)){
                //opponent starved, cancellation
                for (Slot tmp:buffer) {
                    tmp.decrementSeeds();
                }
                s.setNbSeeds(backupseeds);

                return 2;
            }
            else{
                //opponent not starved, capture
                for (Slot tmp:buffer) {
                    if(tmp.getNbSeeds() == 2 || tmp.getNbSeeds() == 3) {
                        this.setRemainingSeeds(id, getRemainingSeeds(id) - tmp.getNbSeeds());
                        this.m_game.storeSeeds(id, tmp.nb_seeds);
                        tmp.emptySeeds();
                    }
                }
            }

            if(this.m_game.getSeeds(id) > 24)
                return 1;
        }

        return 0;
    }

    /**
     * Get the sum of seeds which can be captured in the buffer
     * @param buffer Buffer from which get the sum
     * @return Sum of the seeds which can be captured
     * @throws NullPointerException
     */
    private int getSumCapturable(ArrayList<Slot> buffer) throws NullPointerException{
        if(buffer == null)
            throw new NullPointerException("getSumCapturable() : NULL instance of ArrayList<Slot>");

        int total = 0;
        for (Slot s: buffer) {
            if(s.getNbSeeds() == 2 || s.getNbSeeds()==3)
                total += s.getNbSeeds();
        }

        return total;
    }
}
