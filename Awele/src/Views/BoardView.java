/****************************************************************************************************/
/*  Class BoardView                                                                                 */
/*  Provides game board display methods, and thus communicates with the board controller            */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 11/05/2020                                                                        */
/****************************************************************************************************/
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
        this.m_board.setBoardView(this);
    }

    /**
     * Display a fixed size slot
     * @param amount Amount to display in the slot
     * @param highlight Flag to indicate whether the slot should be highlighted
     */
    public void displaySlot(int amount, boolean highlight){
            System.out.format((highlight ? "\033[47;30m| %1$2d |\033[0m" : "| %1$2d |"), amount);
    }

    /**
     * Display all the slots of the board
     * @param ID ID of the current player
     */
    public void displayBoard(int ID){
        //display the board
        //|  0 |
        //|  6 ||  5 ||  4 ||  3 ||  2 ||  1 |
        //|  1 ||  2 ||  3 ||  4 ||  5 ||  6 |
        //|  0 |

        this.displaySlot(this.m_board.getStoredSeeds(2), false);
        System.out.println();

        Point p = new Point(0, 0);
        for(int l=1 ; l>=0 ; l--) {
            for (int c = 0; c < 6; c++) {
                p.setCoordinates((l > 0 ? 5 - c : c), l);
                this.displaySlot(this.m_board.getSlotSeeds(p),  l == ID-1 && this.m_board.isLegal(p));
            }
            System.out.println();
        }

        this.displaySlot(this.m_board.getStoredSeeds(1), false);
        System.out.println();

    }
}
