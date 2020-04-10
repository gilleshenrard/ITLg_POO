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

    /**
     * Harvest the seeds from a slot and, if necessary, scatter them
     * @param id ID of the player harvesting
     * @param slot Slot being harvested
     * @return 0 if no futher action, 1 if victory, 2 if season cancelled
     * @throws InvalidParameterException
     */
    public int playSlot(int id, int slot) throws InvalidParameterException{
        if(id != 1 && id != 2)
            throw new InvalidParameterException("The ID must be 1 or 2");

        if(slot < 1 || slot > 6)
            throw new InvalidParameterException("The slot chosen must be between 1 and 6 included");

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
                buffer.add(sNext);
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
            if((id == 1 && sumseeds == this.remSeedsPl2) || (id == 2 && sumseeds == this.remSeedsPl1)){
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
                    this.m_game.storeSeeds(id, tmp.nb_seeds);
                    tmp.emptySeeds();
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
            throw new NullPointerException("buffer must be instantiated");

        int total = 0;
        for (Slot s: buffer) {
            if(s.getNbSeeds() == 2 || s.getNbSeeds()==3)
                total += s.getNbSeeds();
        }

        return total;
    }
}
