package Controllers;

import Models.Board;
import Models.Point;
import Views.BoardView;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class BoardController {
    Board m_board;
    BoardView m_boardView;
    GameController m_game;

    /**
     * Create a new Board controller
     * @param b Board to assign to the controller
     * @throws NullPointerException
     */
    public BoardController(Board b, GameController g) throws NullPointerException{
        if(b == null)
            throw new NullPointerException("NULL instance of Board");

        if(b == null)
            throw new NullPointerException("NULL instance of GameController");

        this.m_board = b;
        this.m_game = g;
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
     * Harvest the seeds from a slot and, if necessary, scatter them
     * @param id ID of the player harvesting
     * @param slot Slot being harvested
     * @return -1 if starvation, -2 if empty slot selected, amount of seeds captured otherwise
     * @throws InvalidParameterException
     */
    public int playSlot(int id, int slot) throws InvalidParameterException {
        Board.validateID(id, "BoardController.playSlot()");
        Board.validateCoordinates(new Point(slot - 1, id - 1), "Board.playSlot()");

        //
        //SCATTERING PHASE
        //

        //get number of seeds in the slot harvested by the player + backup
        Point p = new Point(slot - 1, id -1);
        int backupseeds = this.m_board.getSlotSeeds(p);

        //if the slot is empty, return empty slot code
        if(backupseeds == 0)
            return -2;

        //Scatter the selected slot + create a scattering buffer
        ArrayList<Point> buffer = this.processScattering(p);

        //if any side of the board is now empty, revert the scattering and return starvation code
        if (this.m_board.getRemainingSeeds(1) == 0 || this.m_board.getRemainingSeeds(2) == 0) {
            this.revertScattering(p, buffer, backupseeds);
            return -1;
        }

        //
        //CAPTURE PHASE
        //

        //get the amount of seeds in the last slot of the buffer
        int nbseeds = this.m_board.getSlotSeeds(buffer.get(buffer.size()-1));

        //if that amount is 2 or 3, a capture needs to be made
        if(nbseeds == 2 || nbseeds == 3){

            //check if this seasons risks to starve a player (remaining seeds = 0)
            nbseeds = this.getSumCapturable(buffer);
            if(nbseeds >= this.m_board.getRemainingSeeds(1) || nbseeds >= this.m_board.getRemainingSeeds(2)){
                //player starved, revert the scattering and return starvation code
                this.revertScattering(p, buffer, backupseeds);
                return -1;
            }
            else{
                //opponent not starved, capture
                // (collect all seeds from the slots in the buffer which contain 2 or 3 seeds)
                nbseeds = this.processCapture(buffer);
            }
        }
        else
            nbseeds = 0;

        //return code for normal end of season
        return nbseeds;
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
     * Revert a scattering previously made
     * @param buffer Buffer containing the slots involved in the scattering
     * @param backupseeds Amount of seeds to recover in the original node
     * @throws NullPointerException
     */
    private void revertScattering(Point point, ArrayList<Point> buffer, int backupseeds) throws NullPointerException {
        if (buffer == null)
            throw new NullPointerException("BoardController.revertScattering() : NULL instance of ArrayList<Point>");
        if (point == null)
            throw new NullPointerException("BoardController.revertScattering() : NULL instance of Point");

        //decrement the amount of seeds in each slot of the buffer
        for (Point tmp:buffer) {
            this.m_board.decrementSlotSeeds(tmp);
            this.m_board.removeRemainingSeeds(tmp.getY()+1, 1);
        }

        //restore the original seed count in the slot selected by the player
        this.m_board.setSlotSeeds(point, backupseeds);
        this.m_board.addRemainingSeeds(point.getY()+1, backupseeds);
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
     * Get the sum of seeds which can be captured in the buffer
     * @param buffer Buffer from which get the sum
     * @return Sum of the seeds which can be captured
     * @throws NullPointerException
     */
    private int getSumCapturable(ArrayList<Point> buffer) throws NullPointerException{
        if(buffer == null)
            throw new NullPointerException("BoardController.getSumCapturable() : NULL instance of ArrayList<Point>");

        //count the sum of the seeds in the capturable slots (containing 2 or 3 seeds after scattering)
        int total = 0;
        for (Point p: buffer) {
            if(this.m_board.getSlotSeeds(p) == 2 || this.m_board.getSlotSeeds(p) == 3)
                total += this.m_board.getSlotSeeds(p);
        }

        return total;
    }

    /**
     * Reset the board to an inial value
     */
    public void resetBoard(){
        this.m_board.setRemainingSeeds(1, 24);
        this.m_board.setRemainingSeeds(2, 24);

        Point p = new Point(0, 0);
        for (int l = 0 ; l < 2 ; l++){
            for (int c = 0 ; c < 6 ; c++){
                p.setCoordinates(c, l);
                this.m_board.setSlotSeeds(p, 4);
            }
        }
    }

    /**
     * Get a buffer with all the non-empty slots
     * @param ID ID of the player for which getting the slots
     * @return Buffer
     * @throws InvalidParameterException
     */
    public ArrayList<Integer> getNonEmpty(int ID) throws InvalidParameterException{
        return this.m_board.getNonEmpty(ID);
    }
}
