package Models;

import java.security.InvalidParameterException;

public class Game {
    int m_seedpl1;
    int m_seedpl2;
    Game m_instance;

    /**
     * Create a new Game
     */
    public Game() {
        this.m_seedpl1 = 0;
        this.m_seedpl2 = 0;
        this.m_instance = null;
    }

    /**
     * Create or return this instance of Game (DP singleton)
     * @return This instance of Game
     */
    public Game getInstance(){
        //if instance does not exist, create it
        if (this.m_instance == null)
            this.m_instance = new Game();

        return this.m_instance;
    }

    /**
     * Add nb_seeds to the seeds reserve of Player 1 or Player 2
     * @param ID ID of the player who receives the seeds
     * @param nb_seeds  Amount of seeds to store
     * @throws InvalidParameterException
     */
    public void storeSeeds(int ID, int nb_seeds) throws InvalidParameterException{
        if(ID != 1 && ID != 2)
            throw new InvalidParameterException("ID must be 1 or 2");

        if(nb_seeds < 0 || nb_seeds > 23)
            throw new InvalidParameterException("nb_seeds must be a positive number between 0 and 23");

        if (ID == 0)
            this.m_seedpl1 += nb_seeds;
        else
            this.m_seedpl2 += nb_seeds;
    }

    /**
     * Return the amount of stored seeds for a Player
     * @param ID ID of the player
     * @return Amount of seeds stored
     * @throws InvalidParameterException
     */
    public int getSeeds(int ID) throws InvalidParameterException {
        if(ID != 1 && ID != 2)
            throw new InvalidParameterException("ID must be 1 or 2");

        if(ID == 0)
            return m_seedpl1;
        else
            return m_seedpl2;
    }
}
