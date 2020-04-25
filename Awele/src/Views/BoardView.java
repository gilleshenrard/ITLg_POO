package Views;
import Controllers.BoardController;
import Models.Point;

public class BoardView {
    private BoardController m_board;

    /**
     * create a new Board View
     * @param board Board to view
     * @throws NullPointerException
     */
    public BoardView(BoardController board) throws NullPointerException{
        if(board == null)
            throw new NullPointerException("BoardView() : NULL instance of Board");

        this.m_board = board;
    }

    /**
     * Display a fixed size slot
     * @param amount Amount to display in the slot
     */
    public void displaySlot(int amount){
        System.out.format("| %1$2d |", amount);
    }

    /**
     * Display all the slots a player owns
     * @param ID ID of the player
     * @param invert Order of the slots : false = left-right, true = right-left
     */
    public void displayRow(int ID, boolean invert){
        Point p = new Point(0, 0);
        for(int i=0 ; i<6 ; i++) {
            p.setCoordinates((invert ? 5 - i : i), ID - 1);
            this.displaySlot(this.m_board.getSlotSeeds(p));
        }
        System.out.println();
    }
}
