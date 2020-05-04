package Controllers;

import Models.Board;
import Models.Point;
import Views.BoardView;
import java.security.InvalidParameterException;

public class BoardController {
    Board m_board;
    BoardView m_boardView;

    /**
     * Create a new Board controller
     */
    public BoardController(){

        this.m_board = null;
    }

    /**
     * Create a new Board controller
     * @param b Board to assign to the controller
     * @throws NullPointerException
     */
    public BoardController(Board b) throws NullPointerException{
        if(b == null)
            throw new NullPointerException("NULL instance of Board");

        this.m_board = b;
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
     * Get the amount of seeds contained in the slot located at X,Y
     * @param point Coordinates of the slot
     * @return Amount of seeds
     * @throws InvalidParameterException
     */
    public int getSlotSeeds(Point point) throws InvalidParameterException{
        return this.m_board.getSlotSeeds(point);
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
                    capturable[tmp.getY()] += getSlotSeeds(tmp);
                else
                    scattered[tmp.getY()]++;
                nbseeds--;
            }
        }while (nbseeds > 0);

        //starvation occurring during a capture
        if(this.getSlotSeeds(tmp) == 1 || this.getSlotSeeds(tmp) == 2){
            if(capturable[0] + backup[0] == this.m_board.getRemainingSeeds(1)
               || capturable[1] + backup[1] == this.m_board.getRemainingSeeds(2))
                return -1;
            else
                return capturable[0] + capturable[1];
        }
        //starvation occurring during a scattering
        else {
            if(scattered[0] + backup[0] == this.m_board.getRemainingSeeds(1)
               || scattered[1] + backup[1] == this.m_board.getRemainingSeeds(2))
                return -1;
            else
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
     * Display all the slots of the board
     * @param ID ID of the current player
     * @throws NullPointerException
     */
    public void displayBoard(int ID) throws NullPointerException{
        if (this.m_boardView == null)
            throw new NullPointerException("BoardController.displayRow() : NULL instance of BoardView");

        this.m_boardView.displayBoard(ID);
    }

    /**
     * Display a fixed size slot
     * @param amount Amount to display in the slot
     * @param highlight Flag to indicate whether the slot should be highlighted
     */
    public void displaySlot(int amount, boolean highlight){
        if (this.m_boardView == null)
            throw new NullPointerException("BoardController.displaySlot() : NULL instance of BoardView");

        this.m_boardView.displaySlot(amount, highlight);
    }
}
