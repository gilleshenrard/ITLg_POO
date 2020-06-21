/****************************************************************************************************/
/*  Class GameController                                                                            */
/*  Deals with game indirect manipulations (getting the current state, ...), and acts as a buffer   */
/*      between the Game and the Game view. Must be primarily used in the Main method               */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 27/05/2020                                                                        */
/****************************************************************************************************/

package com.gilleshenrard.Awele.Controllers;

import com.gilleshenrard.Awele.App;
import com.gilleshenrard.Awele.FSM.State;
import com.gilleshenrard.Awele.Models.*;
import com.gilleshenrard.Awele.Views.Selectable;
import com.gilleshenrard.Awele.Views.iNotifiable;

import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameController {
    private BoardController m_boardctrl;
    private iNotifiable m_view;
    private State m_currentState;
    private DBSQLite m_database;
    private int m_currentPlayer;
    private boolean m_running;
    private boolean m_menu;
    private boolean m_pause;

    /**
     * Create a new Game Controller
     */
    public GameController(){
        this.m_boardctrl = new BoardController(Game.getInstance().getBoard());
        this.m_boardctrl.setGameController(this);
        this.m_view = null;
        this.m_currentPlayer = 1;
        this.m_currentState = State.MENU;
        this.m_menu = true;
        this.m_pause = false;

        //connect to the database
        this.m_database = new DBSQLite();
    }

    /**
     * Set the game loop as running or not
     * @param running flag to tell if the main loop is running
     */
    public void setRunning(boolean running) {
        Logger.getLogger(App.class.getName()).log(Level.FINE, "Game loop set as " + (running ? "running" : "stopped"));
        this.m_running = running;
    }

    /**
     * Tell if the main loop is running
     * @return main loop running or not
     */
    public boolean isRunning() {
        return this.m_running;
    }

    /**
     * Set which will be the next state to join
     * @param nextState Next state to join
     */
    public void setNextState(State nextState){
        Logger.getLogger(this.getClass().getName()).log(Level.FINE, "Player " + this.getCurrentPlayer() + " : next state -> " + nextState);
        this.m_currentState = nextState;
    }

    /**
     * Get the next game state
     * @return Next game state to reach
     */
    public State getNextState(){
        return this.m_currentState;
    }

    /**
     * Tell if the menu state has been requested
     * @return true if requested, false otherwise
     */
    public boolean isMenuRequested() {
        return this.m_menu;
    }

    /**
     * Set the flag telling if the menu state is requested
     * @param flag true if menu requested, false otherwise
     */
    public void setMenuRequested(boolean flag) {
        this.m_menu = flag;
    }

    /**
     * Tell if a pause has been requested
     * @return true if requested, false otherwise
     */
    public boolean isPauseRequested() {
        return this.m_pause;
    }

    /**
     * Set the flag telling if a pause is requested
     * @param flag true if pause requested, false otherwise
     */
    public void setPauseRequested(boolean flag) {
        this.m_pause = flag;
    }

    /**
     * Set the ID of the current player
     * @param ID Next state to join
     * @throws InvalidParameterException
     */
    public void setCurrentPlayer(int ID) throws InvalidParameterException{
        Game.validateID(ID, "GameController.setCurrentPlayer()");
        this.m_currentPlayer = ID;
    }

    /**
     * Get the ID of the current player
     * @return ID of the current player
     */
    public int getCurrentPlayer(){
        return this.m_currentPlayer;
    }

    /**
     * Get the ID of the opponent of the current player
     * @return ID of the opponent
     */
    public int getOpponent(){
        return (this.getCurrentPlayer() == 1 ? 2 : 1);
    }

    /**
     * Indicate whether the player is owner of the Point or not
     * @param ID ID of the player to test
     * @param p Point of which to test the ownership
     * @return true if the player owns the Point, false otherwise
     */
    public boolean isOwner(int ID, Point p){
        return ID == p.getY() + 1;
    }

    /**
     * Set an instance of Player
     * @param player Player to set
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public void setPlayer(Player player) throws InvalidParameterException, NullPointerException{
        Game.getInstance().setPlayer(player);
    }

    /**
     * Handle the current game state
     * @return Output of the current state
     */
    public void handleState(){
        this.m_currentState.handleState(this);
    };

    /**
     * Process the game loop using the Game Finite State Machine
     */
    public void gameLoop(){
        try {
            //flag the main loop as running
            this.m_database.connect();
            this.setRunning(true);

            //main game loop, while no victory
            while (this.isRunning()) {
                Logger.getLogger(App.class.getName()).log(Level.FINE, "Player " + this.getCurrentPlayer() + " : entering {0} state", this.getNextState());
                this.handleState();
            }
        }
        catch (Exception e){
            Logger.getLogger("App").log(Level.SEVERE, e.getMessage());
            this.displayError(e.getMessage());
            System.exit(-1);
        }

        this.m_database.close();
        Logger.getLogger(App.class.getName()).log(Level.INFO, "Game loop exited");
    }

    /**
     * Return the board set in the current game
     * @return Board to return
     */
    public BoardController getBoardController(){
        return this.m_boardctrl;
    }

    /**
     * Return the amount of stored seeds for a Player
     * @param ID ID of the player
     * @return Amount of seeds stored
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public int getStoredSeeds(int ID) throws InvalidParameterException, NullPointerException {
        if(this.getBoardController() == null)
            throw new NullPointerException("GameController.setBoard() : BoardController not instantiated");

        return this.getBoardController().getStoredSeeds(ID);
    }

    /**
     * Set the Game View used
     * @param gameView Game view to use
     * @throws NullPointerException
     */
    public void setView(iNotifiable gameView) throws NullPointerException{
        if(gameView == null)
            throw new NullPointerException("GameController.setView() : NULL instance of iNotifiable");
        this.m_view = gameView;
    }

    /**
     * Fetch the name of a player via its ID
     * @param ID ID of the player
     * @return Name of the player
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public String getName(int ID) throws InvalidParameterException, NullPointerException{
        return Game.getInstance().getName(ID);
    }

    /**
     * Assign a name to a player
     * @param ID ID of the player to which assign the name
     * @param name Name to assign
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public void setName(int ID, String name) throws InvalidParameterException, NullPointerException {
        Game.validateID(ID, "GameController.setName()");

        Game.getInstance().setName(ID, name);
    }

    /**
     * Set the behaviour of a Player
     * @param ID ID of the player to which assign the behaviour
     * @param behaviour Behaviour to assign to the Player
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public void setBehaviour(int ID, Selectable behaviour) throws InvalidParameterException, NullPointerException {
        Game.validateID(ID, "GameController.setBehaviour()");
        if (behaviour == null)
            throw new NullPointerException("GameController.setBehaviour() : NULL instance of Selectable");

        Game.getInstance().setBehaviour(ID, behaviour);
    }

    /**
     * Tell if the player is an AI or not (instance of RandomSelect)
     * @param ID ID of the player to check
     * @return true if RandomSelect, false otherwise
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public boolean isPlayerAI(int ID) throws InvalidParameterException, NullPointerException {
        return Game.getInstance().isPlayerAI(ID);
    }

    /**
     * Select a slot to play, depending on a player's ID
     * @param ID ID of the player for which to select the slot
     * @return Slot selected
     * @throws InvalidParameterException
     */
    public int selectSlot(int ID) throws InvalidParameterException{
        Game.validateID(ID, "GameController.selectSlot()");

        return Game.getInstance().selectSlot(ID);
    }

    /**
     * Save the player's selection + Harvest the seeds from a slot and, if necessary, scatter them
     * @param id ID of the player harvesting
     * @param slot Slot being harvested
     * @return -1 if starvation, -2 if empty slot selected, amount of seeds captured otherwise
     * @throws InvalidParameterException
     */
    public int playSlot(Point p) throws InvalidParameterException{
      return this.getBoardController().playSlot(p);
    }

    /**
     * Save the current game in the database
     */
    public void saveGame(int ID) {
        this.m_database.saveGame(ID);
    }

    /**
     * Reset the Game and its related board to an inital value
     */
    public void resetGame(){
        Game.getInstance().reset();
        this.setCurrentPlayer(1);
        this.getBoardController().resetBoard();
    }

    /**
     * Set the path to the DB file
     * @param path Path to the DB file
     */
    public void setDBPath(String path) {
        this.m_database.setPath(path);
    }

    /**
     * Display a message
     * @param msg Message to display
     * @throws NullPointerException
     */
    public void displayMessage(String msg) throws NullPointerException{
        if (this.m_boardctrl == null)
            throw new NullPointerException("GameController.sendMessage() : BoardController not instantiated");

        this.m_boardctrl.displayMessage(msg);
    }

    /**
     * Do a pause in the game
     * @throws NullPointerException
     */
    public void pauseSeason() throws NullPointerException {
        if (this.m_boardctrl == null)
            throw new NullPointerException("GameController.pauseSeason() : BoardController not instantiated");

        this.m_boardctrl.pauseSeason();
    }

    /**
     * Set the time member to Now
     */
    public void setTimeNow() {
        Game.getInstance().setTimeNow();
    }

    /**
     * Get the date and time of the beginning of the game
     * @return Date and time of the beginning of the game
     */
    public LocalDateTime getTime() {
        return Game.getInstance().getTime();
    }

    /**
     * (re-)Start the game timer
     */
    public void startClock() {
        Game.getInstance().startClock();
    }

    /**
     * Add the elapsed time between now and the start timer to the final clock
     */
    public void stopClock() {
        Game.getInstance().stopClock();
    }

    /**
     * Reset the final game clock to 00:00:00
     */
    public void resetClock() {
        Game.getInstance().resetClock();
    }


    /**
     * Fill the local Result Set with all the games saved in the DB
     */
    public void selectGames() {
        this.m_database.selectGames();
    }

    /**
     * Select the next row in the database
     * @return true if row has been selected, false if no more rows
     */
    public boolean selectNext() {
        return this.m_database.selectNext();
    }

    /**
     * Get the value of the current database row at the column specified
     * @param column Column from which get the value
     * @return Value of the field located in the current database row and the column specified
     */
    public String getField(DBFields column) {
        return this.m_database.getField(column);
    }

    /**
     * Display a warning message
     * @param msg Message to display
     * @throws NullPointerException
     */
    public void displayWarning(String msg) throws NullPointerException{
        if (this.m_boardctrl == null)
            throw new NullPointerException("GameController.sendWarning() : BoardController not instantiated");

        this.m_boardctrl.displayMessage(msg);
    }

    /**
     * Display an error message in the err channel
     * @param msg Message to display
     * @throws NullPointerException
     */
    public void displayError(String msg) throws NullPointerException{
        if (this.m_view == null)
            throw new NullPointerException("GameController.displayError() : GameView not instantiated");

        this.m_view.displayError(msg);
    }

    /**
     * Display the menu screen
     * @throws NullPointerException
     */
    public void displayMenu() throws NullPointerException {
        if (this.m_view == null)
            throw new NullPointerException("GameController.displayMenu() : GameView not instantiated");

        this.m_view.displayMenu();
    }

    /**
     * Display the whole board and score of the two players
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public void displayGame() throws InvalidParameterException, NullPointerException {
        this.getBoardController().updateObservers();
    }
}
