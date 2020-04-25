package Models;

import java.security.InvalidParameterException;

public class Game {
    private Player m_player1;
    private Player m_player2;
    private int m_seedpl1;
    private int m_seedpl2;
    private static Game m_instance;

    /**
     * Create a new Game
     */
    public Game() {
        this.m_seedpl1 = 0;
        this.m_seedpl2 = 0;
        this.m_player1 = null;
        this.m_player2 = null;
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
     * Set the instance of the player with regards to its ID
     * @param ID ID of the player
     * @param player Player to set
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public void setPlayer(Player player) throws InvalidParameterException, NullPointerException{
        if(player == null)
            throw new NullPointerException("Game.setPlayer() : NULL instance of Player");
        Game.validateID(player.getID(), "Game.setPlayer()");

        if (player.getID() == 1)
            this.m_player1 = player;
        else
            this.m_player2 = player;
    }

    /**
     * Return a player according to its ID
     * @param ID ID of the player to return
     * @return Player to return
     * @throws InvalidParameterException
     */
    public Player getPlayer(int ID) throws InvalidParameterException{
        Game.validateID(ID, "Game.getPlayer()");

        if(ID == 1)
            return this.m_player1;
        else
            return this.m_player2;
    }

    /**
     * Tell if the player is an AI or not (instance of RandomSelect)
     * @param ID ID of the player to check
     * @return true if RandomSelect, false otherwise
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public boolean isPlayerAI(int ID) throws InvalidParameterException, NullPointerException {
        Game.getInstance().validateID(ID, "Game.isPlayerAI()");
        if (Game.getInstance().getPlayer(ID) == null)
            throw new NullPointerException("Game.isPlayerAI() : NULL instance of Player");

        return Game.getInstance().getPlayer(ID).isPlayerAI();
    }

    /**
     * Return the amount of playable shots left
     * @return Number of playable shots left
     * @throws InvalidParameterException
     */
    public int getShotsLeft(int ID) throws InvalidParameterException{
        Game.validateID(ID, "Game.getShotsLeft()");

        return Game.getInstance().getPlayer(ID).getShotsLeft();
    }

    /**
     * Fetch the name of a player via its ID
     * @param ID ID of the player
     * @return Name of the player
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public String getName(int ID) throws InvalidParameterException, NullPointerException{
        Game.validateID(ID, "Game.getName()");

        if(ID == 1) {
            if (this.m_player1 == null)
                throw new NullPointerException("Game.getName() : Player 1 not instantiated");
            else
                return this.m_player1.getName();
        }
        else{
            if (this.m_player2 == null)
                throw new NullPointerException("Game.getName() : Player 2 not instantiated");
            else
                return this.m_player2.getName();
        }
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
     * Make a player select a slot
     * @param ID ID of the player to play the slot
     * @return Slot selected
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public int selectSlot(int ID) throws InvalidParameterException, NullPointerException{
        Game.validateID(ID, "Game.selectSlot()");
        if (Game.getInstance().getPlayer(ID) == null)
            throw new NullPointerException("Game.selectSlot() : NULL instance of Player");

        return Game.getInstance().getPlayer(ID).selectSlot();
    }

    /**
     * Make a player select a slot
     * @param ID ID of the player to play the slot
     * @return Slot selected
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public void refresh(int ID) throws InvalidParameterException, NullPointerException{
        Game.validateID(ID, "Game.refresh()");
        if (Game.getInstance().getPlayer(ID) == null)
            throw new NullPointerException("Game.refresh() : NULL instance of Player");

        Game.getInstance().getPlayer(ID).refresh();
    }

    /**
     * Reset the Game to an inial value
     */
    public void resetGame(){
        this.m_seedpl1 = 0;
        this.m_seedpl2 = 0;
    }
}
