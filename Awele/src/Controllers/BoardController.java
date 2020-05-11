/****************************************************************************************************/
/*  Class BoardController                                                                           */
/*  Deals with game board indirect manipulations (playing a slot, saving the current board, ...),   */
/*      and acts as a buffer between the Board and the Board view. Must not be used directly in     */
/*      the main method.                                                                            */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 11/05/2020                                                                        */
/****************************************************************************************************/
package Controllers;

import Models.Board;
import Models.Point;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Stack;

public class BoardController {
    private GameController m_game;
    private Board m_board;
    private Stack<Board> m_stack;
    private ArrayList<iObserver> m_observers;
    private Point m_lastSelected;

    /**
     * Create a new Board controller
     * @param b Board to assign to the controller
     * @throws NullPointerException
     */
    public BoardController(Board b) throws NullPointerException{
        this.setBoard(b);
        this.m_stack = new Stack<>();
        this.m_observers = new ArrayList<>();
        this.m_lastSelected = null;
    }

    /**
     * Set the Game controller linked to this board controller
     * @param controller Game controller to use
     * @throws NullPointerException
     */
    public void setGameController(GameController controller) throws NullPointerException {
        if (controller == null)
            throw new NullPointerException("BoardController.setGameController() : NULL instance of GameController");

        this.m_game = controller;
    }

    /**
     * Return the amount of stored seeds for a Player
     * @param ID ID of the player
     * @return Amount of seeds stored
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public int getStoredSeeds(int ID) throws InvalidParameterException, NullPointerException {
        if(this.m_board == null)
            throw new NullPointerException("BoardController.setBoard() : Board not instantiated");

        return this.m_board.getStoredSeeds(ID);
    }

    /**
     * Add nb_seeds to the seeds reserve of Player 1 or Player 2
     * @param ID ID of the player who receives the seeds
     * @param nb_seeds  Amount of seeds to store
     * @throws InvalidParameterException
     */
    public void storeSeeds(int ID, int nb_seeds) throws InvalidParameterException{
        Board.validateID(ID, "BoardController.storeSeeds()");
        if (nb_seeds < 0)
            throw new InvalidParameterException("BoardController.storeSeeds() : negative amount of seeds");

        Board b = this.getBoard();
        b.setStoredSeeds(ID, b.getStoredSeeds(ID) + nb_seeds);
    }

    /**
     * Set the board on which to play the current game
     * @param board Board to set
     * @throws NullPointerException
     */
    public void setBoard(Board board) throws NullPointerException{
        if(board == null)
            throw new NullPointerException("BoardController.setBoard() : NULL instance of Board");
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
     * Push a copy of the current board in a stack
     * @throws NullPointerException
     */
    public void pushStack() throws NullPointerException {
        if(this.m_stack == null)
            throw new NullPointerException("BoardController.setBoardView() : Stack not instantiated");

        this.m_stack.push(new Board(this.getBoard()));
    }

    /**
     * Pop a Board from the stack and assign it as the current one
     * @return 1 if a board has been popped, 0 otherwise
     * @throws NullPointerException
     */
    public int popStack() throws NullPointerException {
        if(this.m_stack == null)
            throw new NullPointerException("BoardController.setBoardView() : Stack not instantiated");

        if (this.m_stack.size() > 0) {
            this.setBoard(this.m_stack.pop());
            return 1;
        }
        else
            return 0;
    }

    /**
     * Get the amount of seeds contained in the slot located at X,Y
     * @param point Coordinates of the slot
     * @return Amount of seeds
     * @throws InvalidParameterException
     */
    public int getSlotSeeds(Point point) throws InvalidParameterException{
        return this.m_board.getSlotSeeds(point);
    }

    /**
     * Fetch the name of a player via its ID
     * @param ID ID of the player
     * @return Name of the player
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public String getName(int ID) throws InvalidParameterException, NullPointerException{
        return this.m_game.getName(ID);
    }

    /**
     * Get the ID of the current player
     * @return ID of the current player
     */
    public int getCurrentPlayer(){
        return this.m_game.getCurrentPlayer();
    }

    /**
     * Handle the current game state
     * @return Output of the current state
     */
    public int handleState(){
        return this.m_game.handleState();
    }

    /**
     * Play a whole player's season
     * @return 0
     */
    public int playSeason(){
        return this.m_game.playSeason();
    }

    /**
     * Indicate whether a player is an AI
     * @param ID ID of the player to enquire
     * @return true if AI, false otherwise
     */
    public boolean isPlayerIA(int ID){
        return this.m_game.isPlayerAI(ID);
    }

    /**
     * Check if playing p is legal
     * @param p The slot to test
     * @return true = legal, false otherwise
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public boolean isLegal(Point p) throws InvalidParameterException, NullPointerException{
        if (p == null)
            throw new NullPointerException("BoardController.checkOutcome() : NULL instance of Point");
        Board.validateID(p.getY() + 1, "BoardController.checkOutcome()");
        Board.validateCoordinates(p, "Board.checkOutcome()");

        return this.checkOutcome(p) >= 0;
    }

    /**
     * Test a slot for an outcome code
     * @param p The slot to test
     * @return -2 if empty, -1 if starvation, amount captured otherwise
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public int checkOutcome(Point p) throws InvalidParameterException, NullPointerException{
        if (p == null)
            throw new NullPointerException("BoardController.checkOutcome() : NULL instance of Point");
        Board.validateID(p.getY() + 1, "BoardController.checkOutcome()");
        Board.validateCoordinates(p, "Board.checkOutcome()");

        //recover the amount of seeds it the slot tested
        int nbseeds = this.getSlotSeeds(p);

        //if slot empty, return the proper code
        if (nbseeds == 0)
            return -2;

        //prepare buffer variables
        Point tmp = new Point(p);
        int[] backup = new int[2];
        int[] capturable = new int[2];
        int[] scattered = new int[2];

        //backup the amount of seeds in the slot harvested by the player
        backup[0] = (p.getY() == 0 ? nbseeds : 0);
        backup[1] = (p.getY() == 1 ? nbseeds : 0);

        //compute the amount of seeds which would be captured in each player row
        //  (1 or 2 in each slot in which the tested slot would be scattered)
        java.util.Arrays.fill(scattered, 0);
        java.util.Arrays.fill(capturable, 0);
        do {
            tmp = this.m_board.getNext(tmp);
            if (!tmp.equals(p)) {
                if (this.getSlotSeeds(tmp) == 1 || this.getSlotSeeds(tmp) == 2)
                    capturable[tmp.getY()] += getSlotSeeds(tmp) + 1;
                scattered[tmp.getY()]++;
                nbseeds--;
            }
        }while (nbseeds > 0);

        //starvation occurring during a capture
        if(this.getSlotSeeds(tmp) == 1 || this.getSlotSeeds(tmp) == 2){
            if(capturable[1 - p.getY()] - scattered[1 - p.getY()] == this.m_board.getRemainingSeeds(2 - p.getY()))
                return -1;
            else
                return capturable[0] + capturable[1];
        }
        //simple scattering
        else {
            return 0;
        }
    }

    /**
     * Harvest the seeds from a slot and, if necessary, scatter them
     * @param id ID of the player harvesting
     * @param slot Slot being harvested
     * @return -1 if starvation, -2 if empty slot selected, amount of seeds captured otherwise
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public int playSlot(Point p) throws InvalidParameterException, NullPointerException {
        if (p == null)
            throw new NullPointerException("BoardController.playSlot() : NULL instance of Point");
        Board.validateID(p.getY() + 1, "BoardController.playSlot()");
        Board.validateCoordinates(p, "Board.playSlot()");

        //Check if the play is legal, and return ad hoc code if not
        int ret = this.checkOutcome(p);
        if (ret < 0)
            return ret;


        //get the number of seeds in the slot to harvest and empty it + update remaining seeds
        int nbseeds = this.getSlotSeeds(p);
        this.m_board.removeRemainingSeeds(p.getY()+1, nbseeds);
        this.m_board.emptySlotSeeds(p);

        //until all the seeds have been scattered
        int total = 0;
        Point pNext = new Point(p);
        do {
            //get the next slot to treat, except for the one played by the player
            pNext = this.m_board.getNext(pNext);
            if (!pNext.equals(p)){
                int tmp = this.getSlotSeeds(pNext);

                //if capture case, store the seeds, empty the slot and update remaining
                if (ret > 0 && (tmp == 1 || tmp == 2)) {
                    total += tmp + 1;
                    this.storeSeeds(p.getY() + 1, tmp + 1);
                    this.m_board.emptySlotSeeds(pNext);
                    this.m_board.removeRemainingSeeds(pNext.getY()+1, tmp);
                }
                //otherwise just increment the amount of seeds in each slot
                else {
                    this.m_board.incrementSlotSeeds(pNext);
                    this.m_board.addRemainingSeeds(pNext.getY()+1, 1);
                }
                nbseeds--;
            }
        }while (nbseeds > 0);

        //return the total captured (0 if no capture occurrence)
        return total;
    }

    /**
     * Reset the board to an inial value
     */
    public void resetBoard(){
        this.m_board.reset();
    }

    /**
     * Attach a new observer to the board controller
     * @param observer Observer to attach
     * @throws NullPointerException
     */
    public void attach(iObserver observer) throws NullPointerException{
        if (observer == null)
            throw new NullPointerException("BoardController.attach() : NULL instance of iObserver");

        this.m_observers.add(observer);
        observer.setController(this);
    }

    /**
     * Update all the attached observers
     * @param ID ID of the current player
     */
    public void updateObservers(){
        for (iObserver o:this.m_observers) {
            o.update();
        }
    }

    /**
     * Get the last slot selected by a player
     * @return Last Slot selected
     */
    public Point getLastSelected() {
        return this.m_lastSelected;
    }

    /**
     * Set the last slot selected by a player
     * @param last Last slot selected
     */
    public void setLastSelected(Point last) {
        this.m_lastSelected = last;
    }
}
