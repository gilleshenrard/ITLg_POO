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
     * @param end Final slot in which the scattering ends
     * @param p Slot for which to compute the final seed count
     * @param nbseeds Amount of seeds in the start slot
     * @return Total amount of seeds in the slot after scattering
     */
    public int getFinalSeeds(Point start, Point end, Point p, int nbseeds){
        //if p is the starting slot, amount of seeds should be 0
        if (p.equals(start))
            return 0;

        //get how many times the scattering makes a whole board turn (translated from {0, x})
        int addedSeeds = ((nbseeds) / 12);

        //compare slot count between start, final and p to know if an additional seed is to be added
        if (this.m_board.getDistance(start, p) <= this.m_board.getDistance(start, end))
            addedSeeds++;

        //return the final amount of seeds
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

        //prepare buffer variables
        Point tmp = this.m_board.getSubsequent(p, nbseeds);
        Point finalSlot = new Point(tmp);
        int finalSeeds = this.getFinalSeeds(p, finalSlot, tmp, nbseeds);
        int capturable = 0;
        boolean capturing = true;

        //get the total amount of seeds the opponent will have after scattering
        int i = 5;
        int total = 0;
        do {
            tmp.setCoordinates(i, 1 - p.getY());
            total += this.getFinalSeeds(p, finalSlot, tmp, nbseeds);
            i--;
        }while (i >= 0);

        //if the final slot scattered is not on opponent's side or the slot is not capturable
        if (finalSlot.getY() == p.getY() || (finalSeeds != 2 && finalSeeds != 3)) {
            return (total == 0 ? -1 : 0);
        }
        //if the slot is capturable
        else {
            i = finalSlot.getX();

            //scroll through all the capturable slots (until end of row or not capturable)
            //      and substract their seeds from the total and set them as capturable
            while (i >= 0 && capturing){
                tmp.setCoordinates(i, 1 - p.getY());
                finalSeeds = this.getFinalSeeds(p, finalSlot, tmp, nbseeds);
                if ((finalSeeds == 2 || finalSeeds == 3)){
                    total -= finalSeeds;
                    capturable += finalSeeds;
                }
                else
                    capturing = false;
                i--;
            }
        }

        //if seeds left at the opponent's, return the capturable seeds count. Otherwise, return an error
        if (total == 0)
            return -1;
        else
            return capturable;
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
        int backup = nbseeds;
        int finalSeeds = 0;
        Point pPrev = this.m_board.getSubsequent(p, backup);
        Point finalSlot = new Point(pPrev);
        boolean capturing = true;


        //empty the start slot and update the remaining seeds count
        this.m_board.removeRemainingSeeds(p.getY() + 1, this.getSlotSeeds(p));
        this.m_board.emptySlotSeeds(p);

        //
        //  CAPTURING PHASE
        //

        //capturing cycle (while only capturable slots encountered and on the opponent's side)
        while (ret > 0 && capturing && nbseeds >= 0){
            finalSeeds = this.getFinalSeeds(p, finalSlot, pPrev, backup);

            //if still capturable, empty the slot and update remaining seeds, otherwise stop capture
            if(pPrev.getY() == 1 - p.getY() && (finalSeeds == 2 || finalSeeds == 3)){
                this.storeSeeds(p.getY() + 1, finalSeeds);
                this.m_board.removeRemainingSeeds(pPrev.getY() + 1, this.getSlotSeeds(pPrev));
                this.m_board.emptySlotSeeds(pPrev);

                //get next slot
                nbseeds--;
                pPrev = this.m_board.getPrevious(pPrev);
            }
            else
                capturing = false;
        }

        //
        //  SCATTERING PHASE
        //

        //scattering cycle (while seeds left or scattering made a whole turn)
        while (nbseeds >= 0 && backup - nbseeds < 11){
            //if current slot is not the one played, update the total amount of seeds
            if (!pPrev.equals(p)){
                finalSeeds = this.getFinalSeeds(p, finalSlot, pPrev, backup);
                this.m_board.addRemainingSeeds(pPrev.getY() + 1, finalSeeds - this.getSlotSeeds(pPrev));
                this.m_board.setSlotSeeds(pPrev, finalSeeds);
                nbseeds--;
            }

            pPrev = this.m_board.getPrevious(pPrev);
        }

        //return the total captured
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
