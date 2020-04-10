package Models;

import java.security.InvalidParameterException;

public class Game {
    private int m_seedpl1;
    private int m_seedpl2;
    private static Game m_instance;
    private Player m_player1;
    private Player m_player2;
    private Board m_board;

    /**
     * Create a new Game
     */
    public Game() {
        this.m_seedpl1 = 0;
        this.m_seedpl2 = 0;
        this.m_instance = null;
        this.m_player1 = null;
        this.m_player2 = null;
        this.m_board = null;
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
    public void setPlayer(int ID, Player player) throws InvalidParameterException, NullPointerException{
        if (ID != 1 && ID != 2)
            throw new InvalidParameterException("ID must be 1 or 2");

        if(player == null)
            throw new NullPointerException("Player cannot be NULL");

        if (ID == 1)
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
        if(ID != 1 && ID != 2)
            throw new InvalidParameterException("ID must be 1 or 2");

        if(ID == 1)
            return this.m_player1;
        else
            return this.m_player2;
    }

    /**
     * Set the board on which to play the current game
     * @param board Board to set
     * @throws NullPointerException
     */
    public void setBoard(Board board) throws NullPointerException{
        if(board == null)
            throw new NullPointerException("Board cannot be NULL");
        this.m_board = board;
    }

    /**
     * Return the board set in the current game
     * @return Board to return
     */
    public Board getBoard(){
        return this.m_board;
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

        if (ID == 1)
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

        if(ID == 1)
            return m_seedpl1;
        else
            return m_seedpl2;
    }

    /**
     * Fetch the name of a player via its ID
     * @param ID ID of the player
     * @return Name of the player
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public String getName(int ID) throws InvalidParameterException, NullPointerException{
        if(ID != 1 && ID != 2)
            throw new InvalidParameterException("ID must be 1 or 2");

        if(ID == 1) {
            if (this.m_player1 == null)
                throw new NullPointerException("Player 1 must be instantiated first (cannot be NULL)");
            else
                return this.m_player1.getName();
        }
        else{
            if (this.m_player2 == null)
                throw new NullPointerException("Player 2 must be instantiated first (cannot be NULL)");
            else
                return this.m_player2.getName();
        }
    }
}
