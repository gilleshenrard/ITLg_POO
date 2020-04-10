package Views;
import Models.Board;
import Models.Game;

public class BoardView {
    private Board m_board;

    /**
     * create a new Board View
     * @param board Board to view
     * @throws NullPointerException
     */
    public BoardView(Board board) throws NullPointerException{
        if(board == null)
            throw new NullPointerException("Board cannot be NULL");

        this.m_board = board;
    }

    /**
     * Display the whole board and score of the two players
     */
    public void displayBoard(){
        //display the opponent side of the board (slots are inverted)
        //OPPONENT
        //|  0 |
        //|  6 ||  5 ||  4 ||  3 ||  2 ||  1 |
        System.out.println("OPPONENT");
        this.displaySlot(Game.getInstance().getSeeds(2));
        System.out.println();
        for(int i=0 ; i<6 ; i++)
            this.displaySlot(this.m_board.getSlots()[1][5-i].getNbSeeds());
        System.out.println();

        //display the player side of the board
        //|  1 ||  2 ||  3 ||  4 ||  5 ||  6 |
        //|  0 |
        //PLAYER
        for(int i=0 ; i<6 ; i++)
            this.displaySlot(this.m_board.getSlots()[0][i].getNbSeeds());
        System.out.println();
        this.displaySlot(Game.getInstance().getSeeds(1));
        System.out.println();
        System.out.println("PLAYER");
    }

    /**
     * Display a fixed size slot
     * @param amount Amount to display in the slot
     */
    public void displaySlot(int amount){
        System.out.format("| %1$2d |", amount);
    }
}
