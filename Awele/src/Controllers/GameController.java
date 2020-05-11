/****************************************************************************************************/
/*  Class GameController                                                                            */
/*  Deals with game indirect manipulations (getting the current state, ...), and acts as a buffer   */
/*      between the Game and the Game view. Must be primarily used in the Main method               */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 11/05/2020                                                                        */
/****************************************************************************************************/
package Controllers;

import Models.Game;
import Models.Player;
import Models.Point;
import Views.GameView;

import java.security.InvalidParameterException;

public class GameController {
    private BoardController m_board;
    private GameView m_gameView;
    private iGameState m_currentState;
    private int m_currentPlayer;
    public static SwitchingPlayerState m_switching = new SwitchingPlayerState();
    public static PromptingState m_prompting = new PromptingState();
    public static PlayingState m_playing = new PlayingState();
    public static StoringState m_storing = new StoringState();

    /**
     * Create a new Game Controller
     */
    public GameController(){
        this.m_board = new BoardController(Game.getInstance().getBoard());
        this.m_gameView = null;
        this.m_currentPlayer = 1;
        this.m_currentState = GameController.m_prompting;
    }

    /**
     * Set which will be the next state to join
     * @param nextState Next state to join
     */
    public void setNextState(iGameState nextState){
        this.m_currentState = nextState;
    }

    /**
     * Get the next game state
     * @return Next game state to reach
     */
    public iGameState getNextState(){
        return this.m_currentState;
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
     * @param input Input for the current state
     * @return Output of the current state
     */
    public int handleState(int input){
        return this.m_currentState.handleState(this, input);
    };

    /**
     * Return the board set in the current game
     * @return Board to return
     */
    public BoardController getBoardController(){
        return this.m_board;
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
    public void setView(GameView gameView) throws NullPointerException{
        if(gameView == null)
            throw new NullPointerException("GameController.setView() : NULL instance of GameView");
        this.m_gameView = gameView;
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
     * Reset the Game and its related board to an inital value
     */
    public void resetGame(){
        Game.getInstance().reset();
        this.getBoardController().resetBoard();
    }
    /**
     * Display a message in the out channel
     * @param msg Message to display
     * @throws NullPointerException
     */
    public void displayMessage(String msg) throws NullPointerException{
        if (this.m_gameView == null)
            throw new NullPointerException("GameController.displayMessage() : GameView not instantiated");

        this.m_gameView.displayMessage(msg);
    }

    /**
     * Display a warning message in the out channel
     * @param msg Message to display
     * @throws NullPointerException
     */
    public void displayWarning(String msg) throws NullPointerException{
        if (this.m_gameView == null)
            throw new NullPointerException("GameController.displayWarning() : GameView not instantiated");

        this.m_gameView.displayWarning(msg);
    }

    /**
     * Display an error message in the err channel
     * @param msg Message to display
     * @throws NullPointerException
     */
    public void displayError(String msg) throws NullPointerException{
        if (this.m_gameView == null)
            throw new NullPointerException("GameController.displayError() : GameView not instantiated");

        this.m_gameView.displayError(msg);
    }

    /**
     * Display the whole board and score of the two players
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public void displayGame() throws InvalidParameterException, NullPointerException {
        if (this.m_gameView == null)
            throw new NullPointerException("GameController.displayGame() : GameView not instantiated");

        this.m_gameView.displayGame();
    }

    /**
     * Display all the slots of the board
     * @throws NullPointerException
     */
    public void displayBoard() throws NullPointerException{
        this.getBoardController().displayBoard(this.m_currentPlayer);
    }

    /**
     * Display a separator in the console
     */
    public void displaySeparator(){
        if (this.m_gameView == null)
            throw new NullPointerException("GameController.displayGame() : GameView not instantiated");

        this.m_gameView.displaySeparator();
    }
}
