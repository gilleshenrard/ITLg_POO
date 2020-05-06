package Views;
import Controllers.BoardController;
import Models.Point;
import Controllers.iObserver;

public class BoardConsoleView implements iObserver{
    private BoardController m_board;

    /**
     * create a new Board View
     * @param board Board to view
     * @throws NullPointerException
     */
    public BoardConsoleView() throws NullPointerException{
        this.m_board = null;
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
    public void displayBoard(){
        //display the board
        //OPPONENT
        //|  0 |
        //|  6 ||  5 ||  4 ||  3 ||  2 ||  1 |
        //|  1 ||  2 ||  3 ||  4 ||  5 ||  6 |
        //|  0 |
        //PLAYER

        System.out.println(this.m_board.getName(2));
        this.displaySlot(this.m_board.getStoredSeeds(2), false);
        System.out.println();

        Point p = new Point(0, 0);
        for(int l=1 ; l>=0 ; l--) {
            for (int c = 0; c < 6; c++) {
                p.setCoordinates((l > 0 ? 5 - c : c), l);
                this.displaySlot(this.m_board.getSlotSeeds(p),  l == this.m_board.getCurrentPlayer()-1 && this.m_board.isLegal(p));
            }
            System.out.println();
        }

        this.displaySlot(this.m_board.getStoredSeeds(1), false);
        System.out.println();
        System.out.println(this.m_board.getName(1));

    }

    @Override
    public void update() {
        this.displayBoard();
    }

    @Override
    public void setController(Object controller) {
        this.m_board = (BoardController) controller;
    }
}
