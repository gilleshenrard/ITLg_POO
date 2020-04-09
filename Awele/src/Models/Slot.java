package Models;

public class Slot {
    byte nb_seeds;

    /**
     * Create a new Slot
     */
    public Slot(){
        this.nb_seeds = 4;
    }

    /**
     * Return the amout of seeds in the slot
     * @return Amount of seeds
     */
    public int getNbSeeds(){
        return this.nb_seeds;
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
}
