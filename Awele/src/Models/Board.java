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
     * Harvest the seeds from a slot and, if necessary, scatter them
     * @param id ID of the player harvesting
     * @param slot Slot being harvested
     * @return 0 if normal season (with or without capture), 1 if victory, 2 if starvation, 3 if empty slot selected
     * @throws InvalidParameterException
     */
    public int playSlot(int id, int slot) throws InvalidParameterException{
        Game.validateID(id, "Board.playSlot()");
        Board.validateCoordinates(slot - 1, id - 1, "Board.playSlot()");

        //
        //SCATTERING PHASE
        //

        //get number of seeds in the slot harvested by the player + backup
        Slot s = this.getSlot(slot-1, id-1);
        int backupseeds = s.getNbSeeds();

        //if the slot is empty, return empty slot code
        if(backupseeds == 0)
            return 3;

        //Scatter the selected slot + create a scattering buffer
        ArrayList<Slot> buffer = this.processScattering(s);

        //if any side of the board is now empty, revert the scattering and return starvation code
        if (this.getRemainingSeeds(1) == 0 || this.getRemainingSeeds(2) == 0) {
            this.revertScattering(s, buffer, backupseeds);
            return 2;
        }

        //
        //CAPTURE PHASE
        //

        //get the amount of seeds in the last slot of the buffer
        int nbseeds = buffer.get(buffer.size()-1).getNbSeeds();

        //if that amount is 2 or 3, a capture needs to be made
        if(nbseeds == 2 || nbseeds == 3){

            //check if this seasons risks to starve a player (remaining seeds = 0)
            nbseeds = this.getSumCapturable(buffer);
            if(nbseeds >= this.getRemainingSeeds(1) || nbseeds >= this.getRemainingSeeds(2)){
                //player starved, revert the scattering and return starvation code
                this.revertScattering(s, buffer, backupseeds);
                return 2;
            }
            else{
                //opponent not starved, capture
                // (collect all seeds from the slots in the buffer which contain 2 or 3 seeds)
                this.processCapture(id, buffer);
            }

            //if stored seeds > 24, the current player won the game
            if(Game.getInstance().getSeeds(id) > 24)
                return 1;
        }

        //return code for normal end of season
        return 0;
    }

    /**
     * Process the scattering of a slot : empty it and scatter it one seed at a time in the next slots
     * @param s Slot to harvest and scatter
     * @return Array of slots in which the scattering occurred
     * @throws NullPointerException
     */
    private ArrayList<Slot> processScattering(Slot s) throws NullPointerException {
        if (s == null)
            throw new NullPointerException("Board.processScattering() : NULL instance of Slot");

        //get the number of seeds in the slot to harvest and empty it + update remaining seeds
        int nbseeds = s.getNbSeeds();
        this.removeRemainingSeeds(s.getY()+1, nbseeds);
        s.emptySeeds();

        //save all the slots after the slot harvested in a buffer until there are no seeds left to scatter
        Slot sNext = this.getNext(s);
        ArrayList<Slot> buffer = new ArrayList<Slot>();
        while (nbseeds > 0){
            //make sure not to add the slot harvested
            if(!sNext.equals(s)) {
                //increment the seeds in all the slot, update remaining seeds and add to the buffer
                sNext.incrementSeeds();
                this.addRemainingSeeds(sNext.getY()+1, 1);
                buffer.add(sNext); //a slot can be added several times in the buffer, by design
                nbseeds--;
            }

            sNext = this.getNext(sNext);
        }

        return buffer;
    }

    /**
     * Revert a scattering previously made
     * @param buffer Buffer containing the slots involved in the scattering
     * @param backupseeds Amount of seeds to recover in the original node
     * @throws NullPointerException
     */
    private void revertScattering(Slot s, ArrayList<Slot> buffer, int backupseeds) throws NullPointerException {
        if (buffer == null)
            throw new NullPointerException("Board.revertScattering() : NULL instance of ArrayList<Slot>");
        if (s == null)
            throw new NullPointerException("Board.revertScattering() : NULL instance of Slot");

        //decrement the amount of seeds in each slot of the buffer
        for (Slot tmp:buffer) {
            tmp.decrementSeeds();
            this.removeRemainingSeeds(tmp.getY()+1, 1);
        }

        //restore the original seed count in the slot selected by the player
        s.setNbSeeds(backupseeds);
        this.addRemainingSeeds(s.getY()+1, backupseeds);
    }

    /**
     * Capture all the slots in the array : all those containing 2 or 3 seeds are emptied and their content is stored
     * @param ID ID of the player performing the capture
     * @param buffer Array of slots in which the scattering occurred
     * @throws NullPointerException
     */
    private void processCapture(int ID, ArrayList<Slot> buffer) throws NullPointerException{
        if (buffer == null)
            throw new NullPointerException("Board.processCapture() : NULL instance of ArrayList<Slot>");

        //for each slot in the buffer containing 2 or 3 seeds, store its amount, update remaining seeds and empty the slot
        for (Slot tmp:buffer) {
            if(tmp.getNbSeeds() == 2 || tmp.getNbSeeds() == 3) {
                Game.getInstance().storeSeeds(ID, tmp.getNbSeeds());
                this.removeRemainingSeeds(tmp.getY()+1, tmp.getNbSeeds());
                tmp.emptySeeds();
            }
        }
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
