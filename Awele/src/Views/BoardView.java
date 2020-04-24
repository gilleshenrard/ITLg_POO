package Views;
import Controllers.BoardController;

import java.security.InvalidParameterException;

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
     * Display the whole board and score of the two players
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public void displayBoard() throws InvalidParameterException, NullPointerException {
        //display the opponent side of the board (slots are inverted)
        //OPPONENT
        //|  0 |
        //|  6 ||  5 ||  4 ||  3 ||  2 ||  1 |
        System.out.println(this.m_board.getName(2));
        this.displaySlot(this.m_board.getStoredSeeds(2));
        System.out.println();
        displayRow(2, true);

        //display the player side of the board
        //|  1 ||  2 ||  3 ||  4 ||  5 ||  6 |
        //|  0 |
        //PLAYER
        displayRow(1, false);
        this.displaySlot(this.m_board.getStoredSeeds(1));
        System.out.println();
        System.out.println(this.m_board.getName(1));
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
        for(int i=0 ; i<6 ; i++)
            this.displaySlot(this.m_board.getSlotSeeds((invert ? 5-i : i), ID-1));
        System.out.println();
    }
}
