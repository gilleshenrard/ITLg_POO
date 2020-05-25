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
import Views.BoardView;
import java.security.InvalidParameterException;
import java.util.Stack;

public class BoardController {
    Board m_board;
    BoardView m_boardView;
    Stack<Board> m_stack;

    /**
     * Create a new Board controller
     * @param b Board to assign to the controller
     * @throws NullPointerException
     */
    public BoardController(Board b) throws NullPointerException{
        this.setBoard(b);
        this.m_boardView = null;
        this.m_stack = new Stack<>();
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
     * Set the board view on which to play the current game
     * @param board Board view to set
     * @throws NullPointerException
     */
    public void setBoardView(BoardView board) throws NullPointerException{
        if(board == null)
            throw new NullPointerException("BoardController.setBoardView() : NULL instance of Board");
        this.m_boardView = board;
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
     * Get how many seeds will be in a slot after scattering
     * @param start Slot from which the scattering starts
     * @param p Slot for which to compute the final seed count
     * @param nbseeds Amount of seeds in the start slot
     * @return Total amount of seeds in the slot after scattering
     */
    public int getFinalSeeds(Point start, Point p, int nbseeds){
        //if p is the starting slot, amount of seeds should be 0
        if (p.equals(start))
            return 0;

        //get how many seeds in total will be scattered in p, starting from start
        int addedSeeds = ((start.getX() + 1 + nbseeds) / 6);
        addedSeeds /= 2;

        //get the final slot to be scattered to
        Point finalSlot = this.m_board.getNext(start, nbseeds);

        //rectify amount of seeds in p (before or after last slot scattered)
        if (finalSlot.getY() != start.getY()){
            if ((p.getY() == finalSlot.getY() && p.getX() <= finalSlot.getX())
                    || p.getY() == start.getY() && p.getX() > start.getX())
                addedSeeds++;
        }
        else {
            //is p between start and last slot?
            if (p.getY() == start.getY() && p.getX() <= finalSlot.getX() && p.getX() > start.getX())
                addedSeeds++;
        }

        return this.getSlotSeeds(p) + addedSeeds;
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

        //if last slot scattered not the opponent's or doesn't get to be captured, stop there
        Point tmp = this.m_board.getNext(p, nbseeds);
        int finalSeeds = this.getFinalSeeds(p, tmp, nbseeds);
        if (tmp.getY() == p.getY() || (finalSeeds != 2 && finalSeeds != 3)) {
            //if the opponent has no seed left and player doesn't scatter on the opponent's side, starvation
            if (this.m_board.getRemainingSeeds(2 - p.getY()) == 0 && (p.getX() + nbseeds / 6) <= 0)
                return -1;
            else
                return 0;
        }

        //prepare buffer variables
        tmp = new Point(p);
        int backup = nbseeds;
        int capturable = 0;
        int scattered = 0;

        //compute the amount of seeds which would be captured in each player row
        //  (1 or 2 in each slot in which the tested slot would be scattered)
        do {
            tmp = this.m_board.getNext(tmp);
            if (!tmp.equals(p)) {
                if (tmp.getY() != p.getY()) {
                    finalSeeds = this.getFinalSeeds(p, tmp, backup);
                    if (finalSeeds == 2 || finalSeeds == 3)
                        capturable += finalSeeds;
                    else
                        capturable = 0;
                    scattered++;
                }
                else
                    capturable = 0;
                nbseeds--;
            }
        }while (nbseeds > 0);

        //starvation occurring during a capture
        if(this.getSlotSeeds(tmp) == 1 || this.getSlotSeeds(tmp) == 2){
            if(capturable - scattered == this.m_board.getRemainingSeeds(2 - p.getY()))
                return -1;
            else
                return capturable;
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
        Point pNext = new Point(p);
        do {
            //get the next slot to treat, except for the one played by the player
            pNext = this.m_board.getNext(pNext);
            if (!pNext.equals(p)){
                int tmp = this.getSlotSeeds(pNext);

                //if capture case, store the seeds, empty the slot and update remaining
                if (ret > 0 && (tmp == 1 || tmp == 2)) {
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
        return ret;
    }

    /**
     * Reset the board to an inial value
     */
    public void resetBoard(){
        this.m_board.reset();
    }

    /**
     * Display all the slots of the board
     * @param ID ID of the current player
     * @throws NullPointerException
     */
    public void displayBoard(int ID) throws NullPointerException{
        if (this.m_boardView == null)
            throw new NullPointerException("BoardController.displayRow() : NULL instance of BoardView");

        this.m_boardView.displayBoard(ID);
    }
}
