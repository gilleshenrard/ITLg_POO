/****************************************************************************************************/
/*  Class GameController                                                                            */
/*  Deals with game indirect manipulations (getting the current state, ...), and acts as a buffer   */
/*      between the Game and the Game view. Must be primarily used in the Main method               */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 11/05/2020                                                                        */
/****************************************************************************************************/

package ITLg.POO.GillesHenrard.Awele.Controllers;

import ITLg.POO.GillesHenrard.Awele.Models.Game;
import ITLg.POO.GillesHenrard.Awele.Models.Player;
import ITLg.POO.GillesHenrard.Awele.Models.Point;
import ITLg.POO.GillesHenrard.Awele.Views.SystemMessage;

import java.security.InvalidParameterException;

public class GameController {
    private BoardController m_board;
    private SystemMessage m_view;
    private State m_currentState;
    private int m_currentPlayer;

    /**
     * Create a new Game Controller
     */
    public GameController(){
        this.m_board = new BoardController(Game.getInstance().getBoard());
        this.m_board.setGameController(this);
        this.m_view = null;
        this.m_currentPlayer = 1;
        this.m_currentState = State.PROMPTING;
    }

    /**
     * Set which will be the next state to join
     * @param nextState Next state to join
     */
    public void setNextState(State nextState){
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
    public int handleState(){
        return this.m_currentState.handleState(this);
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
    public void setView(SystemMessage gameView) throws NullPointerException{
        if(gameView == null)
            throw new NullPointerException("GameController.setView() : NULL instance of SystemMessage");
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
     * Display a message
     * @param msg Message to display
     * @throws NullPointerException
     */
    public void displayMessage(String msg) throws NullPointerException{
        if (this.m_board == null)
            throw new NullPointerException("GameController.sendMessage() : BoardController not instantiated");

        this.m_board.displayMessage(msg);
    }

    /**
     * Display a warning message
     * @param msg Message to display
     * @throws NullPointerException
     */
    public void displayWarning(String msg) throws NullPointerException{
        if (this.m_board == null)
            throw new NullPointerException("GameController.sendWarning() : BoardController not instantiated");

        this.m_board.displayMessage(msg);
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
     * Display the whole board and score of the two players
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public void updateObservers() throws InvalidParameterException, NullPointerException {
        this.getBoardController().updateObservers();
    }
}
