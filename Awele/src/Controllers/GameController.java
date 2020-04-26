package Controllers;

import Models.Game;
import Models.Point;
import Views.GameView;

import java.security.InvalidParameterException;

public class GameController {
    private BoardController m_board;
    private GameView m_gameView;
    private GameState m_currentState;
    private int m_currentPlayer;
    public static SwitchingPlayerState m_switching = new SwitchingPlayerState();
    public static PromptedState m_prompting = new PromptedState();
    public static PlayingState m_playing = new PlayingState();
    public static StoringState m_storing = new StoringState();

    /**
     * Create a new Game controller
     * @param view Game view to assign to the controller
     */
    public GameController(GameView view){
        this.m_board = null;
        this.m_gameView = view;
        this.m_currentPlayer = 1;
        this.m_currentState = GameController.m_prompting;
    }

    /**
     * Create a new Game Controller
     */
    public GameController(){
        this.m_board = null;
        this.m_gameView = null;
        this.m_currentPlayer = 1;
        this.m_currentState = GameController.m_prompting;
    }

    /**
     * Set which will be the next state to join
     * @param nextState Next state to join
     */
    public void setNextState(GameState nextState){
        this.m_currentState = nextState;
    }

    /**
     * Get the next game state
     * @return Next game state to reach
     */
    public GameState getNextState(){
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
     * Handle the current game state
     * @param input Input for the current state
     * @return Output of the current state
     */
    public int handleState(int input){
        return this.m_currentState.handleState(this, input);
    };

    /**
     * Set the board on which to play the current game
     * @param board Board to set
     * @throws NullPointerException
     */
    public void setBoardController(BoardController board) throws NullPointerException{
        if(board == null)
            throw new NullPointerException("GameController.setBoard() : NULL instance of Board");
        this.m_board = board;
    }

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
     */
    public int getSeeds(int ID) throws InvalidParameterException {
        return Game.getInstance().getSeeds(ID);
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
     * Return the amount of playable shots left
     * @return Number of playable shots left
     * @throws InvalidParameterException
     */
    public int getShotsLeft(int ID) throws InvalidParameterException{
        Game.validateID(ID, "Game.getShotsLeft()");

        return Game.getInstance().getShotsLeft(ID);
    }

    /**
     * Add nb_seeds to the seeds reserve of Player 1 or Player 2
     * @param ID ID of the player who receives the seeds
     * @param nb_seeds  Amount of seeds to store
     * @throws InvalidParameterException
     */
    public void storeSeeds(int ID, int nb_seeds) throws InvalidParameterException{
        if (nb_seeds < 0)
            throw new InvalidParameterException("Game.storeSeeds() : negative amount of seeds");

        Game g = Game.getInstance();
        g.setSeeds(ID, g.getSeeds(ID) + nb_seeds);
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
     * Reset the array of playable slots for the player with the id ID
     * @param ID ID of the player for which reset the array
     * @throws InvalidParameterException
     */
    public void refresh(int ID) throws InvalidParameterException {
        Game.validateID(ID, "GameController.refresh()");

        Game.getInstance().refresh(ID);
    }

    /**
     * Save the player's selection + Harvest the seeds from a slot and, if necessary, scatter them
     * @param id ID of the player harvesting
     * @param slot Slot being harvested
     * @return -1 if starvation, -2 if empty slot selected, amount of seeds captured otherwise
     * @throws InvalidParameterException
     */
    public int playSlot(Point p) throws InvalidParameterException{
/*        int ret = this.m_board.playSlot(p);
        if (ret > 0)
            this.storeSeeds(p.getY() + 1, ret);

        return ret;
*/      return this.m_board.playSlot(p);
    }

    /**
     * Reset the Game to an inial value
     */
    public void resetGame(){
        Game.getInstance().resetGame();
        this.m_board.resetBoard();
    }
    /**
     * Display a message in the out channel
     * @param msg Message to display
     */
    public void displayMessage(String msg){
        this.m_gameView.displayMessage(msg);
    }

    /**
     * Display a warning message in the out channel
     * @param msg Message to display
     */
    public void displayWarning(String msg){
        this.m_gameView.displayWarning(msg);
    }

    /**
     * Display an error message in the err channel
     * @param msg Message to display
     */
    public void displayError(String msg){
        this.m_gameView.displayError(msg);
    }

    /**
     * Display the whole board and score of the two players
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public void displayGame() throws InvalidParameterException, NullPointerException {
        this.m_gameView.displayGame();
    }
}
