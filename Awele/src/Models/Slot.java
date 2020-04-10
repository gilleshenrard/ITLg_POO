package Models;

import java.security.InvalidParameterException;
import java.util.Objects;

public class Slot {
    private int m_x;
    private int m_y;
    byte nb_seeds;

    /**
     * Create a new Slot
     * @param x The X coordinate of the slot
     * @param y The Y coordinate of the slot
     */
    public Slot(int x, int y){
        this.nb_seeds = 4;
        this.setCoordinates(x, y);
    }

    /**
     * Return the amout of seeds in the slot
     * @return Amount of seeds
     */
    public int getNbSeeds(){
        return this.nb_seeds;
    }

    /**
     * Set the X and Y coordinates of the Slot
     * @param x X coordinate of the slot
     * @param y Y coordinate of the slot
     * @throws InvalidParameterException
     */
    public void setCoordinates(int x, int y) throws InvalidParameterException{
        if (x < 0 || x>5)
            throw new InvalidParameterException("X must be between 0 and 5");

        if (y != 0 && y!=1)
            throw new InvalidParameterException("Y must be between 0 or 1");

        this.m_x = x;
        this.m_y = y;
    }

    /**
     * Set the amount of seeds to 0
     */
    public void emptySeeds(){
        this.nb_seeds = 0;
    }

    /**
     * Add one seed in the slot
     * @throws IllegalStateException
     */
    public void incrementSeeds() throws IllegalStateException{
        if(this.nb_seeds >= 48)
            throw new IllegalStateException("A slot can have maximum 24 seeds");

        this.nb_seeds += 1;
    }

    /**
     * Remove one seed from the slot
     * @throws IllegalStateException
     */
    public void decrementSeeds() throws IllegalStateException{
        if (this.nb_seeds <= 0)
            throw new IllegalStateException("The amount of seeds in a slot must be >= 0");

        this.nb_seeds -= 1;
    }

    /**
     * Check if o is equal to the current Slot
     * @param o Object to check
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slot slot = (Slot) o;
        return m_x == slot.m_x &&
                m_y == slot.m_y &&
                nb_seeds == slot.nb_seeds;
    }

    /**
     * Return the hashcode of the current Slot
     * @return Hashcode of the current slot
     */
    @Override
    public int hashCode() {
        return Objects.hash(m_x, m_y, nb_seeds);
    }
}
