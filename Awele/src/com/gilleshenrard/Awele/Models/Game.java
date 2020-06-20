/****************************************************************************************************/
/*  Class Game                                                                                      */
/*  Encloses the game manipulations                                                                 */
/*  The game consists of a Board and two players. It is a Singleton, as only one instance           */
/*      is playable                                                                                 */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 11/05/2020                                                                        */
/****************************************************************************************************/
package com.gilleshenrard.Awele.Models;

import com.gilleshenrard.Awele.Views.Selectable;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;

public class Game {
    private Board m_board;
    private Player[] m_player;
    private LocalDateTime m_time;
    private static Game m_instance = null;

    /**
     * Create a new Game
     */
    private Game() {
        this.m_board = new Board();
        this.m_player = new Player[2];
        this.m_player[0] = null;
        this.m_player[1] = null;
        this.m_time = null;
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
     * Get the board attached to the current game
     * @return Current board
     */
    public Board getBoard(){
        return this.m_board;
    }

    /**
     * Set an instance of Player
     * @param player Player to set
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public void setPlayer(Player player) throws InvalidParameterException, NullPointerException{
        if(player == null)
            throw new NullPointerException("Game.setPlayer() : NULL instance of Player");
        Game.validateID(player.getID(), "Game.setPlayer()");

        this.m_player[player.getID() - 1] = player;
    }

    /**
     * Return a player according to its ID
     * @param ID ID of the player to return
     * @return Player to return
     * @throws InvalidParameterException
     */
    public Player getPlayer(int ID) throws InvalidParameterException{
        Game.validateID(ID, "Game.getPlayer()");

        return this.m_player[ID - 1];
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
            throw new NullPointerException("Game.isPlayerAI() : Player " + ID + " not instantiated");

        return Game.getInstance().getPlayer(ID).isPlayerAI();
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
        if(Game.getInstance().getPlayer(ID) == null)
            throw new NullPointerException("Game.getName() : Player " + ID + " not instantiated");

            return Game.getInstance().getPlayer(ID).getName();
    }

    /**
     * Set the name of a player
     * @param ID ID of the player to which assign the name
     * @param name Name to assign to the player
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public void setName(int ID, String name) throws InvalidParameterException, NullPointerException {
        Game.validateID(ID, "Game.setName()");
        if(Game.getInstance().getPlayer(ID) == null)
            throw new NullPointerException("Game.setName() : Player " + ID + " not instantiated");

        Game.getInstance().getPlayer(ID).setName(name);
    }

    /**
     * Set the behaviour of a Player
     * @param ID ID of the player to which assign the behaviour
     * @param behaviour Behaviour to assign to the Player
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public void setBehaviour(int ID, Selectable behaviour) throws InvalidParameterException, NullPointerException {
        Game.validateID(ID, "Game.setBehaviour()");
        if(Game.getInstance().getPlayer(ID) == null)
            throw new NullPointerException("Game.setBehaviour() : Player " + ID + " not instantiated");
        if(behaviour == null)
            throw new NullPointerException("Game.setBehaviour() : NULL instance of Selectable");

        Game.getInstance().getPlayer(ID).setBehaviour(behaviour);
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
            throw new NullPointerException("Game.selectSlot() : Player " + ID + " not instantiated");

        return Game.getInstance().getPlayer(ID).selectSlot();
    }

    /**
     * Reset the Game to an initial value
     */
    public void reset(){
        this.m_board.reset();
    }

    /**
     * Set the date and time of the beginning of the game
     */
    public void setTimeNow() {
        this.m_time = LocalDateTime.now();
    }

    /**
     * Get the date and time of the beginning of the game
     * @return Date and time of the beginning of the game
     */
    public LocalDateTime getTime() {
        return this.m_time;
    }
}
