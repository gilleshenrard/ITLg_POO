package Views;

import Controllers.BoardController;
import Models.Point;

public class MinimaxSelect implements iSelectable{
    private BoardController m_controller;
    private int m_id;
    private int m_maxDepth;
    private final static int POSINFINITE = 200000;
    private final static int NEGINFINITE = -POSINFINITE;
    private final static int ERROR = Integer.MIN_VALUE;

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
     * @return Selection, or -2 if error
     */
    @Override
    public int selectSlot() {
        int find = findBest();
        return find + 1;
    }

    /**
     * Find the value in the current game state
     * @return best shot possible, or -3 if error
     */
    private int findBest(){
        int bestVal = ERROR;
        int bestShot = -3;

        Point p = new Point(0, 0);
        for(int i=0 ; i<6 ; i++){
            p.setCoordinates(i, this.m_id - 1);
            if (this.m_controller.isLegal(p)) {

                int val = miniMax(this.m_controller, p, this.m_maxDepth, NEGINFINITE, POSINFINITE, true);
                if (val == ERROR)
                    continue;

                if (val > bestVal) {
                    bestVal = val;
                    bestShot = p.getX();
                }
            }
        }

        return bestShot;
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
        parent.pushStack();
        if (parent.playSlot(p) < 0 ) {
            parent.popStack();
            return ERROR;
        }

        int outcome = evaluateState(parent);
        if (depth == 0 || outcome == POSINFINITE || outcome == NEGINFINITE ) {
            return outcome;
        }

        Point tmp = new Point(0, 0);
        if (maximiser){
            int maxEval = NEGINFINITE;
            for(int i=0 ; i<6 ; i++){
                tmp.setCoordinates(i, 1);
                int eval = miniMax(parent, tmp, depth - 1, alpha, beta, false);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha)
                    break;
            }
            parent.popStack();
            return maxEval;
        }
        else {
            int minEval = POSINFINITE;
            for(int i=0 ; i<6 ; i++){
                p.setCoordinates(i, 0);
                int eval = miniMax(parent, tmp, depth - 1, alpha, beta, true);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha)
                    break;
            }
            parent.popStack();
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
            return NEGINFINITE;

        //if maximiser (player 2) won the game
        if (this.m_id == 2 && bc.getStoredSeeds(2) > 24)
            return POSINFINITE;

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
