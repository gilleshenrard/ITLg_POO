package Views;

import Controllers.BoardController;
import Models.Board;
import Models.Point;

import java.util.ArrayList;

public class MinimaxSelect implements iSelectable{
    private BoardController m_controller;
    private int m_id;
    private int m_maxDepth;
    private static int ERROR = -100000;

    /**
     * Create a new Minimax selection behaviour
     * @param game The game controller to use
     * @param ID The ID of the player to which set the behaviour
     * @throws NullPointerException
     */
    public MinimaxSelect(BoardController controller, int ID) throws NullPointerException{
        if(controller == null)
            throw new NullPointerException("MinimaxSelect() : NULL instance of BoardController");

        this.m_controller = controller;
        this.m_id = ID;
        this.m_maxDepth = 3;
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
        return findBest() + 1;
    }

    /**
     * Find the value in the current game state
     * @return best value
     */
    private int findBest(){
        ArrayList<Point> legalShots = new ArrayList<>();
        Point p = new Point(0, 0);
        for(int i=0 ; i<6 ; i++){
            p.setCoordinates(i, this.m_id - 1);
            if (this.m_controller.isLegal(p))
                legalShots.add(p);
        }

        if (legalShots.size() > 0){
            int bestVal = (this.m_id == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE);
            for (Point tmp : legalShots){
                int val = miniMax(this.m_controller, tmp, this.m_maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, (this.m_id == 1 ? false : true));
                if(this.m_id == 1 && val > bestVal)
                    bestVal = val;

                if (this.m_id == 2 && val < bestVal)
                    bestVal = val;
            }
            return bestVal;
        }
        else
            return -2;
    }

    /**
     * Use the Minimax algorithm to determine the best move
     * @param parent BoardController hosting a Board representing the current node of the tree
     * @param p Slot to select in this node
     * @param depth Depth of the current node
     * @param alpha Alpha value in this pruning state
     * @param beta Beta value in this pruning state
     * @param maximiser Flag determining if the current node concerns the maximiser or the minimiser (player 2 is the maximiser by default)
     * @return Best Slot to select
     */
    private int miniMax(BoardController parent, Point p, int depth, int alpha, int beta, boolean maximiser){
        Board buffer = new Board(parent.getBoard());
        if (parent.playSlot(p) < 0 ) {
            parent.setBoard(buffer);
            return ERROR;
        }

        int outcome = evaluateState(parent);
        if (depth == 0 || outcome == Integer.MAX_VALUE || outcome == Integer.MIN_VALUE ) {
            return outcome;
        }

        Point tmp = new Point(0, 0);
        if (maximiser){
            int maxEval = Integer.MIN_VALUE;
            for(int i=0 ; i<6 ; i++){
                tmp.setCoordinates(i, 1);
                int eval = miniMax(parent, tmp, depth - 1, alpha, beta, false);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha)
                    break;
            }
            parent.setBoard(buffer);
            return maxEval;
        }
        else {
            int minEval = Integer.MAX_VALUE;
            for(int i=0 ; i<6 ; i++){
                p.setCoordinates(i, 0);
                int eval = miniMax(parent, tmp, depth - 1, alpha, beta, true);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha)
                    break;
            }
            parent.setBoard(buffer);
            return minEval;
        }
    }

    /**
     * Generate an evaluation value for the current state of the game
     * @return Evaluation value
     */
    private int evaluateState(BoardController bc){
        int[] eval = new int[2];
        Point p = new Point(0, 0);

        //if minimiser (player 1) won the game
        if (this.m_id == 1 && bc.getStoredSeeds(1) > 24)
            return Integer.MIN_VALUE;

        //if maximiser (player 2) won the game
        if (this.m_id == 2 && bc.getStoredSeeds(2) > 24)
            return Integer.MAX_VALUE;

        //process the evaluation for both players
        for (int player=0 ; player < 2 ; player++){
            //initialise the evaluation with the amount of seeds captured with a lot of weight
            eval[player] = 300 * bc.getStoredSeeds(player + 1);

            //add the content of the player's slots, each weighted
            for (int slot=0 ; slot < 6 ; slot++) {
                p.setCoordinates(slot, player);
                eval[player] += bc.getSlotSeeds(p) * (slot+1);
            }
        }

        if(this.m_id == 1)
            return eval[0] - eval[1];
        else
            return eval[1] - eval[0];
    }
}
