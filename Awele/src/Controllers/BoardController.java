package Controllers;

import Models.Board;
import Models.Point;
import Views.BoardView;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class BoardController {
    Board m_board;
    BoardView m_boardView;

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
     * @return -1 if starvation, -2 if empty, positive if the play is legal
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public int isLegal(Point p) throws InvalidParameterException, NullPointerException{
        if (p == null)
            throw new NullPointerException("BoardController.isLegal() : NULL instance of Point");
        Board.validateID(p.getY() + 1, "BoardController.isLegal()");
        Board.validateCoordinates(p, "Board.isLegal()");

        //recover the amount of seeds it the slot tested
        int nbseeds = this.getSlotSeeds(p);

        //if slot empty, return the proper code
        if (nbseeds == 0)
            return -2;

        //backup the amount of seeds in the slot harvested by the player
        int[] backup = new int[2];
        backup[0] = (p.getY() == 0 ? nbseeds : 0);
        backup[1] = (p.getY() == 1 ? nbseeds : 0);

        //compute the amount of seeds which would be captured in each player row
        //  (1 or 2 in each slot in which the tested slot would be scattered)
        Point tmp = new Point(p);
        int[] capturable = new int[2];
        java.util.Arrays.fill(capturable, 0);
        do {
            tmp = this.m_board.getNext(tmp);
            if (this.getSlotSeeds(tmp) == 1 || this.getSlotSeeds(tmp) == 2)
                capturable[tmp.getY()] += getSlotSeeds(tmp);
            nbseeds--;
        }while (nbseeds > 0);

        //starvation case for the player 1
        if(backup[0] + (this.getSlotSeeds(tmp) == 1 || this.getSlotSeeds(tmp) == 2 ? capturable[0] : 0) == this.m_board.getRemainingSeeds(1))
            return -1;

        //starvation case for the player 2
        if(backup[1] + (this.getSlotSeeds(tmp) == 1 || this.getSlotSeeds(tmp) == 2 ? capturable[1] : 0) == this.m_board.getRemainingSeeds(2))
            return -1;

        return 1;
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
        int ret = this.isLegal(p);
        if (ret <= 0)
            return ret;

        //Scatter the selected slot + create a scattering buffer
        ArrayList<Point> buffer = this.processScattering(p);

        //get the amount of seeds in the last slot of the buffer
        ret = this.m_board.getSlotSeeds(buffer.get(buffer.size()-1));

        //if that amount is 2 or 3, a capture needs to be made
        if(ret == 2 || ret == 3)
            ret = this.processCapture(buffer);
        else
            ret = 0;

        return ret;
    }

    /**
     * Process the scattering of a slot : empty it and scatter it one seed at a time in the next slots
     * @param point Coordinates of the slot to harvest and scatter
     * @return Array of slots coordinates in which the scattering occurred
     * @throws NullPointerException
     */
    private ArrayList<Point> processScattering(Point point) throws NullPointerException {
        if (point == null)
            throw new NullPointerException("BoardController.processScattering() : NULL instance of Point");

        //get the number of seeds in the slot to harvest and empty it + update remaining seeds
        int nbseeds = this.m_board.getSlotSeeds(point);
        this.m_board.removeRemainingSeeds(point.getY()+1, nbseeds);
        this.m_board.emptySlotSeeds(point);

        //save all the slots after the slot harvested in a buffer until there are no seeds left to scatter
        Point pNext = this.m_board.getNext(point);
        ArrayList<Point> buffer = new ArrayList<>();
        while (nbseeds > 0){
            //make sure not to add the slot harvested
            if(!pNext.equals(point)) {
                //increment the seeds in all the slot, update remaining seeds and add to the buffer
                this.m_board.incrementSlotSeeds(pNext);
                this.m_board.addRemainingSeeds(pNext.getY()+1, 1);
                buffer.add(pNext); //a slot can be added several times in the buffer, by design
                nbseeds--;
            }

            pNext = this.m_board.getNext(pNext);
        }

        return buffer;
    }

    /**
     * Capture all the slots in the array : all those containing 2 or 3 seeds are emptied and their content is stored
     * @param ID ID of the player performing the capture
     * @param buffer Array of slots in which the scattering occurred
     * @return Amount of seeds to be captured
     * @throws NullPointerException
     */
    private int processCapture(ArrayList<Point> buffer) throws NullPointerException{
        if (buffer == null)
            throw new NullPointerException("BoardController.processCapture() : NULL instance of ArrayList<Point>");

        int nb_stored = 0;

        //for each slot in the buffer containing 2 or 3 seeds, store its amount, update remaining seeds and empty the slot
        for (Point tmp:buffer) {
            if(this.m_board.getSlotSeeds(tmp) == 2 || this.m_board.getSlotSeeds(tmp) == 3) {
                nb_stored += this.m_board.getSlotSeeds(tmp);
                this.m_board.removeRemainingSeeds(tmp.getY()+1, this.m_board.getSlotSeeds(tmp));
                this.m_board.emptySlotSeeds(tmp);
            }
        }

        return nb_stored;
    }

    /**
     * Reset the board to an inial value
     */
    public void resetBoard(){
        this.m_board.reset();
    }
}
