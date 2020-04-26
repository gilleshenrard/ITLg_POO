package Models;

import java.security.InvalidParameterException;
import java.util.Objects;

//Slot is private-package : only accessible by classes in the Models package
class Slot {
    private Point m_coordinates;
    private int nb_seeds;

    /**
     * Create a new Slot
     * @param x The X coordinate of the slot
     * @param y The Y coordinate of the slot
     */
    public Slot(int x, int y){
        this.setNbSeeds(4);
        this.m_coordinates = new Point(x, y);
    }

    /**
     * Throw an exception if not 0 <= seeds <= 48
     * @param seeds Amount of seeds to validate
     * @param msg Name of the method in which the validation occurs
     * @throws InvalidParameterException
     */
    public static void validateNbSeeds(int seeds, String msg) throws InvalidParameterException{
        if (seeds < 0 || seeds > 48)
            throw new InvalidParameterException(msg + " : Incorrect amount of seeds (value : " + seeds + ")");
    }

    /**
     * Set the number of seeds in the current slot
     * @param seeds Number of seeds to set
     * @throws InvalidParameterException
     */
    public void setNbSeeds(int seeds) throws InvalidParameterException{
        Slot.validateNbSeeds(seeds, "Slot.setNbSeeds()");
        this.nb_seeds = seeds;
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
     */
    public void setCoordinates(int x, int y){
        this.m_coordinates.setCoordinates(x, y);
    }

    /**
     * Return the Coordinates of the slot
     * @return Coordinates of the slot
     */
    public Point getCoordinates(){
        return this.m_coordinates;
    }

    /**
     * Set the amount of seeds to 0
     */
    public void emptySeeds(){
        this.nb_seeds = 0;
    }

    /**
     * Add one seed in the slot
     * @throws InvalidParameterException
     */
    public void incrementSeeds() throws InvalidParameterException{
        Slot.validateNbSeeds(this.nb_seeds + 1, "Slot.incrementSeeds()");
        this.nb_seeds += 1;
    }

    /**
     * Remove one seed from the slot
     * @throws InvalidParameterException
     */
    public void decrementSeeds() throws InvalidParameterException{
        Slot.validateNbSeeds(this.nb_seeds - 1, "Slot.decrementSeeds()");
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
        return this.m_coordinates.getX() == slot.getCoordinates().getX() &&
                this.m_coordinates.getY() == slot.getCoordinates().getY() &&
                this.nb_seeds == slot.getNbSeeds();
    }

    /**
     * Return the hashcode of the current Slot
     * @return Hashcode of the current slot
     */
    @Override
    public int hashCode() {
        return Objects.hash(Objects.hash(this.getCoordinates()), this.getNbSeeds());
    }
}
