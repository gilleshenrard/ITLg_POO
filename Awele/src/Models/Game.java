package Models;

import java.security.InvalidParameterException;

public class Game {
    private int m_seedpl1;
    private int m_seedpl2;
    private static Game m_instance;

    /**
     * Create a new Game
     */
    public Game() {
        this.m_seedpl1 = 0;
        this.m_seedpl2 = 0;
        this.m_instance = null;
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
     * Create or return this instance of Game (DP singleton)
     * @return This instance of Game
     */
    public static synchronized Game getInstance(){
        //if instance does not exist, create it
        if (Game.m_instance == null)
            Game.m_instance = new Game();

        return Game.m_instance;
    }

    /**
     * Add nb_seeds to the seeds reserve of Player 1 or Player 2
     * @param ID ID of the player who receives the seeds
     * @param nb_seeds  Amount of seeds to store
     * @throws InvalidParameterException
     */
    public void setSeeds(int ID, int nb_seeds) throws InvalidParameterException{
        Game.validateID(ID, "Game.storeSeeds()");

        if(nb_seeds < 0 || nb_seeds > 48)
            throw new InvalidParameterException("Game.storeSeeds() : incorrect amount of seeds (value : " + nb_seeds + ")");

        if (ID == 1)
            this.m_seedpl1 = nb_seeds;
        else
            this.m_seedpl2 = nb_seeds;
    }

    /**
     * Return the amount of stored seeds for a Player
     * @param ID ID of the player
     * @return Amount of seeds stored
     * @throws InvalidParameterException
     */
    public int getSeeds(int ID) throws InvalidParameterException {
        Game.validateID(ID, "Game.getSeeds()");

        if(ID == 1)
            return m_seedpl1;
        else
            return m_seedpl2;
    }

    /**
     * Reset the Game to an inial value
     */
    public void resetGame(){
        this.m_seedpl1 = 0;
        this.m_seedpl2 = 0;
    }
}
