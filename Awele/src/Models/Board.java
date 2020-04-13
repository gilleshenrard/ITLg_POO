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
     * Get the amount of seeds stored by a player
     * @param ID ID of the player
     * @return Amount of seeds
     * @throws InvalidParameterException
     */
    public int getStoredSeeds(int ID) throws InvalidParameterException{
        return Game.getInstance().getSeeds(ID);
    }

    /**
     * Get the name of a player regarding its ID
     * @param ID ID of the player of which getting the name
     * @return Name of the player
     * @throws InvalidParameterException
     */
    public String getName(int ID) throws InvalidParameterException{
        return Game.getInstance().getName(ID);
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
     * Harvest the seeds from a slot and, if necessary, scatter them
     * @param id ID of the player harvesting
     * @param slot Slot being harvested
     * @return 0 if normal season (with or without capture), 1 if victory, 2 if starvation, 3 if empty slot selected
     * @throws InvalidParameterException
     */
    public int playSlot(int id, int slot) throws InvalidParameterException{
        Game.validateID(id, "Board.playSlot()");
        Board.validateCoordinates(slot - 1, id - 1, "Board.playSlot()");

        //get number of seeds in the slot harvested by the player
        Slot s = this.getSlot(slot-1, id-1);
        int nbseeds = s.getNbSeeds();

        if(nbseeds == 0)
            return 3;

        //
        //HARVEST PHASE
        //

        //backup seeds in the slot and empty it (it's been harvested)
        int backupseeds = s.getNbSeeds();
        s.emptySeeds();

        //save all the slots after the slot harvested in a buffer until there are no seeds left to scatter
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

        //
        //SCATTERING PHASE
        //

        //get the amount of seeds in the last slot of the buffer
        nbseeds = buffer.get(buffer.size()-1).getNbSeeds();
        this.updateRemainingSeeds();

        //if that amount is 2 or 3, a capture needs to be made
        if(nbseeds == 2 || nbseeds == 3){

            //check if this seasons risks to starve the opponent (remaining seeds = 0)
            int sumseeds = this.getSumCapturable(buffer);
            if(sumseeds >= this.getRemainingSeeds(3 - id)){
                //opponent starved, cancellation
                for (Slot tmp:buffer) {
                    tmp.decrementSeeds();
                }
                s.setNbSeeds(backupseeds);
                this.updateRemainingSeeds();

                //return code for starvation
                return 2;
            }
            else{
                //opponent not starved, capture
                // (collect all seeds from the slots in the buffer which contain 2 or 3 seeds)
                for (Slot tmp:buffer) {
                    if(tmp.getNbSeeds() == 2 || tmp.getNbSeeds() == 3) {
                        this.setRemainingSeeds(id, getRemainingSeeds(id) - tmp.getNbSeeds());
                        Game.getInstance().storeSeeds(id, tmp.nb_seeds);
                        tmp.emptySeeds();
                    }
                }
                this.updateRemainingSeeds();
            }

            //if stored seeds > 24, the current player won the game
            if(Game.getInstance().getSeeds(id) > 24)
                return 1;
        }

        //return code for normal end of season
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
            throw new NullPointerException("Board.getSumCapturable() : NULL instance of ArrayList<Slot>");

        //count the sum of the seeds in the capturable slots (containing 2 or 3 seeds after scattering)
        int total = 0;
        for (Slot s: buffer) {
            if(s.getNbSeeds() == 2 || s.getNbSeeds()==3)
                total += s.getNbSeeds();
        }

        return total;
    }

    /**
     * Refresh the amount of remaining seeds for both players
     */
    private void updateRemainingSeeds(){
        int total = 0;

        //update remaining seeds for player 1
        for (int i = 0 ; i < 6 ; i++)
            total += this.getSlotSeeds(i, 0);
        this.setRemainingSeeds(1, total);

        //update remaining seeds for player 2
        total = 0;
        for (int j = 0 ; j < 6 ; j++)
            total += this.getSlotSeeds(j, 1);
        this.setRemainingSeeds(2, total);
    }

    /**
     * Reset the board to an inial value
     */
    public void resetBoard(){
        this.setRemainingSeeds(1, 24);
        this.setRemainingSeeds(2, 24);

        for (int l = 0 ; l < 2 ; l++){
            for (int c = 0 ; c < 6 ; c++){
                this.getSlot(c, l).setNbSeeds(4);
            }
        }
    }

    /**
     * Get the X coordinate of the slots with at least 1 seed for a player
     * @param ID ID of the player for which retrieve the playable slots
     * @return Array of playable slots X coordinates
     * @throws InvalidParameterException
     */
    public ArrayList<Integer> getPlayableSlots(int ID) throws InvalidParameterException {
        Game.validateID(ID, "Board.getPlayableSlots()");

        ArrayList<Integer> array = new ArrayList<>();
        for(int i=0 ; i<6 ; i++){
            if(this.getSlotSeeds(i, ID-1) > 0)
                array.add(i+1);
        }

        return array;
    }
}
