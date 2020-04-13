package Models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class Game {
    private int m_seedpl1;
    private int m_seedpl2;
    private static Game m_instance;
    private Player m_player1;
    private Player m_player2;
    private Board m_board;
    private int m_lastSlotPlayed;

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
        this.m_lastSlotPlayed = 0;
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
    public void setPlayer(int ID, Player player) throws InvalidParameterException, NullPointerException{
        if(player == null)
            throw new NullPointerException("Game.setPlayer() : NULL instance of Player");
        Game.validateID(ID, "Game.setPlayer()");

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
        Game.validateID(ID, "Game.getPlayer()");

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
            throw new NullPointerException("Game.setBoard() : NULL instance of Board");
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
        Game.validateID(ID, "Game.storeSeeds()");

        if(nb_seeds < 0 || nb_seeds > 23)
            throw new InvalidParameterException("Game.storeSeeds() : incorrect amount of seeds (value : " + nb_seeds + ")");

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
        Game.validateID(ID, "Game.getSeeds()");

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
     * Return the last slot played by a player
     * @return Last slot played by a player
     */
    public int getLastSlotPlayed(){
        return this.m_lastSlotPlayed;
    }

    /**
     * Save the player's selection + Harvest the seeds from a slot and, if necessary, scatter them
     * @param id ID of the player harvesting
     * @param slot Slot being harvested
     * @return 0 if no further action, 1 if victory, 2 if season cancelled
     * @throws InvalidParameterException
     */
    public int playSlot(int id, int slot) throws InvalidParameterException{
        this.m_lastSlotPlayed = slot;
        return this.m_board.playSlot(id, slot);
    }

    /**
     * Reset the Game to an inial value
     */
    public void resetGame(){
        this.m_seedpl1 = 0;
        this.m_seedpl2 = 0;
        this.m_lastSlotPlayed = 0;

        this.m_board.resetBoard();
    }

    /**
     * Get the X coordinate of the slots with at least 1 seed for a player
     * @param ID ID of the player for which retrieve the playable slots
     * @return Array of playable slots X coordinates
     * @throws InvalidParameterException
     */
    public ArrayList<Integer> getPlayableSlots(int ID) throws InvalidParameterException {
        return this.m_board.getPlayableSlots(ID);
    }
}
