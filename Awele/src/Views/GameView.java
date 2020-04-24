package Views;

import Controllers.GameController;

import java.security.InvalidParameterException;

public class GameView {
    private BoardView m_board;
    private GameController m_controller;

    /**
     * Create a new GameView
     */
    public GameView(){
        this.m_controller = null;
        this.m_board = null;
    }

    /**
     * Set the BoardView used by the game view
     * @param board Board view to use
     */
    public void setBoardView(BoardView board){
        this.m_board = board;
    }

    /**
     * Set the game controller used by the game view
     * @param controller Controller to use
     */
    public void setController(GameController controller){
        this.m_controller = controller;
    }

    /**
     * Display a message in the out channel
     * @param msg Message to display
     */
    public void displayMessage(String msg){
        System.out.println(msg);
    }

    /**
     * Display a warning message in the out channel
     * @param msg Message to display
     */
    public void displayWarning(String msg){
        this.displayMessage(msg);
    }

    /**
     * Display an error message in the err channel
     * @param msg Message to display
     */
    public void displayError(String msg){
        System.err.println(msg);
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
        System.out.println(this.m_controller.getName(2));
        this.m_board.displaySlot(this.m_controller.getSeeds(2));
        System.out.println();
        this.m_board.displayRow(2, true);

        //display the player side of the board
        //|  1 ||  2 ||  3 ||  4 ||  5 ||  6 |
        //|  0 |
        //PLAYER
        this.m_board.displayRow(1, false);
        this.m_board.displaySlot(this.m_controller.getSeeds(1));
        System.out.println();
        System.out.println(this.m_controller.getName(1));
    }
}
