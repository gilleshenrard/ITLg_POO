package Controllers;

import Models.Game;
import Models.Player;
import Views.GameView;

import java.security.InvalidParameterException;

public class GameController {
    private Player m_player1;
    private Player m_player2;
    private BoardController m_board;
    private GameView m_gameView;

    public GameController(GameView view){
        this.m_player1 = null;
        this.m_player1 = null;
        this.m_board = null;
        this.m_gameView = view;
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
            throw new NullPointerException("GameController.setPlayer() : NULL instance of Player");
        Game.validateID(ID, "GameController.setPlayer()");

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
        Game.validateID(ID, "GameController.getPlayer()");

        if(ID == 1)
            return this.m_player1;
        else
            return this.m_player2;
    }

    /**
     * Fetch the name of a player via its ID
     * @param ID ID of the player
     * @return Name of the player
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public String getName(int ID) throws InvalidParameterException, NullPointerException{
        Game.validateID(ID, "GameController.getName()");

        if(ID == 1) {
            if (this.m_player1 == null)
                throw new NullPointerException("GameController.getName() : Player 1 not instantiated");
            else
                return this.m_player1.getName();
        }
        else{
            if (this.m_player2 == null)
                throw new NullPointerException("GameController.getName() : Player 2 not instantiated");
            else
                return this.m_player2.getName();
        }
    }

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
     * Add nb_seeds to the seeds reserve of Player 1 or Player 2
     * @param ID ID of the player who receives the seeds
     * @param nb_seeds  Amount of seeds to store
     * @throws InvalidParameterException
     */
    public void storeSeeds(int ID, int nb_seeds) throws InvalidParameterException{
        Game.getInstance().setSeeds(ID, Game.getInstance().getSeeds(ID) + nb_seeds);
    }

    /**
     * Select a slot to play, depending on a player's ID
     * @param ID ID of the player for which to select the slot
     * @return Slot selected
     * @throws InvalidParameterException
     */
    public int selectSlot(int ID) throws InvalidParameterException{
        Game.validateID(ID, "GameController.selectSlot()");

        return this.getPlayer(ID).selectSlot();
    }

    /**
     * Reset the array of playable slots for the player with the id ID
     * @param ID ID of the player for which reset the array
     * @throws InvalidParameterException
     */
    public void reset(int ID) throws InvalidParameterException {
        Game.validateID(ID, "GameController.reset()");

        this.getPlayer(ID).reset();
    }

    /**
     * Save the player's selection + Harvest the seeds from a slot and, if necessary, scatter them
     * @param id ID of the player harvesting
     * @param slot Slot being harvested
     * @return 0 if no further action, 1 if victory, 2 if season cancelled
     * @throws InvalidParameterException
     */
    public int playSlot(int id, int slot) throws InvalidParameterException{
        return this.m_board.playSlot(id, slot);
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
}
