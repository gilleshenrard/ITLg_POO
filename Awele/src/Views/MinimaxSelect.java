package Views;

import Controllers.GameController;
import Models.Board;
import Models.Point;

public class MinimaxSelect implements iSelectable{
    private GameController m_game;
    private int m_id;
    private int m_maxDepth;
    private static int ERROR = -100000;

    /**
     * Create a new Minimax selection behaviour
     * @param game The game controller to use
     * @param ID The ID of the player to which set the behaviour
     * @throws NullPointerException
     */
    public MinimaxSelect(GameController game, int ID) throws NullPointerException{
        if(game == null)
            throw new NullPointerException("MinimaxSelect() : NULL instance of GameController");

        this.m_game = game;
        this.m_id = ID;
        this.m_maxDepth = 6;
    }

    /**
     * Return a flag stating the player is an AI
     * @return true
     */
    @Override
    public boolean isAI() {
        return true;
    }

    /**
     * Select a slot using the Minimax algorithm
     * @return Selection
     */
    @Override
    public int selectSlot() {
        return bestMove();
    }

    /**
     * Get the best possible move starting from the current state as a tree root
     * @return X coordinate of the best move found
     */
    private int bestMove(){
        int best_eval = ERROR;
        int best = ERROR;
        Point p = new Point(0, 0);

        //for each of the root children, find the best move
        for (int slot=0 ; slot<6 ; slot++){
            p.setCoordinates(slot, this.m_id - 1);
            Board b = new Board(this.m_game.getBoardController().getBoard());
            int val = evaluate(b, 0, this.m_id, p, -ERROR);
            if (val == ERROR)
                continue;

            //if the current tree is the best yet, set it as the best
            if (val > best_eval) {
                best_eval = val;
                best = p.getX();
            }
        }
        return best + 1;
    }

    /**
     * Evaluate each possible states (tree nodes) recursively, and return the best move
     * @param depth Depth of the current tree evaluated
     * @param ID ID of the current player evaluated (maximiser or minimiser)
     * @param move Coordinates of the current tree
     * @param alpha Alpha value (Alpha-Beta Pruning)
     * @return Best move evaluated in the current tree
     */
    private int evaluate(Board parent, int depth, int ID, Point move, int alpha){
        //return error if move is not possible
        if (this.m_game.playSlot(move) < 0)
            return ERROR;

        //if a leaf has been reached, evaluate the game status
        if (++depth >= this.m_maxDepth)
            return evaluateState();

        //invert the ID and prepare variables
        ID = (ID == 1 ? 2 : 1);
        int best = ERROR;
        int val = 0;
        Point p = new Point(0, 0);

        //for each slot (child nodes), make a copy of the board and evaluate it
        for(int slot=0 ; slot<6 ; slot++){
            Board b = new Board(parent);
            p.setCoordinates(slot, ID-1);
            val = evaluate(b, depth, ID, p, alpha);

            //if the evaluation return an error, process the next child
            if (val == ERROR)
                continue;

            //determine if current node is the best one
            val = -val;
            if (val > best)
                best = val;
            if (val > alpha)
                break;
        }
        return best;
    }

    /**
     * Generate an evaluation value for the current state of the game
     * @return Evaluation value
     */
    private int evaluateState(){
        int[] eval = new int[2];
        Point p = new Point(0, 0);

        //process the evaluation for both players
        for (int player=0 ; player < 2 ; player++){
            //initialise the evaluation with the amount of seeds captured with a lot of weight
            eval[player] = 300 * this.m_game.getSeeds(player + 1);

            //add the content of the player's slots, each weighted
            for (int slot=0 ; slot < 6 ; slot++) {
                p.setCoordinates(slot, player);
                eval[player] += this.m_game.getBoardController().getSlotSeeds(p) * (slot+1);
            }
        }

        //return the delta between both evaluations, the order depending on which player is evaluated
        return eval[this.m_id - 1] - eval[2 - this.m_id];
    }
}
