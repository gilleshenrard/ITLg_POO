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
        this.m_maxDepth = 10;
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
        int bestVal = ERROR;
        int bestShot = -3;

        //test each legal shot for the best value
        Point p = new Point(0, 0);
        for(int i=0 ; i<6 ; i++){
            p.setCoordinates(i, this.m_id - 1);
            if (this.m_controller.isLegal(p)) {

                //use minimax to find the best value in the current slot
                int val = miniMax(p, this.m_maxDepth, NEGINFINITE, POSINFINITE, true);

                //if no error, update the best value and the best slot
                if (val != ERROR && val > bestVal) {
                    bestVal = val;
                    bestShot = p.getX();
                }
            }
        }

        //return the best slot
        return bestShot + 1;
    }

    /**
     * Use the Minimax algorithm to determine the best move
     * @param p Slot to select in this node
     * @param depth Depth of the current node
     * @param alpha Alpha value in this pruning state
     * @param beta Beta value in this pruning state
     * @param maximiser Flag determining if the current node concerns the maximiser or the minimiser (player 2 is the maximiser by default)
     * @return Best Slot to select
     */
    private int miniMax(Point p, int depth, int alpha, int beta, boolean maximiser){
        //save a copy of the current node
        this.m_controller.pushStack();

        //test its outcome and return an error if not legal
        if (this.m_controller.playSlot(p) < 0 ) {
            this.m_controller.popStack();
            return ERROR;
        }

        //evaluate the current node and return its value if leaf or game won
        int outcome = evaluateState();
        if (depth == 0 || outcome == POSINFINITE || outcome == NEGINFINITE ) {
            this.m_controller.popStack();
            return outcome;
        }

        //if maximiser
        if (maximiser){
            //look for the best value in the subchildren
            int maxEval = NEGINFINITE;
            int i = 0;
            do{
                Point tmp = new Point(i, this.m_id - 1);
                int eval = miniMax(tmp, depth - 1, alpha, beta, false);

                //if no error, update the max value in the subchildren and the alpha value
                if (eval != ERROR) {
                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);
                }
                i++;
            }while(i<6 && beta > alpha);

            //restore the parent status and return the current node best evaluation
            this.m_controller.popStack();
            return maxEval;
        }
        //if minimiser
        else {
            //look for the best value in the subchildren
            int minEval = POSINFINITE;
            int i = 0;
            do{
                Point tmp = new Point(i, 2 - this.m_id);
                int eval = miniMax(tmp, depth - 1, alpha, beta, true);

                //if no error, update the min value in the subchildren and the beta value
                if (eval != ERROR) {
                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);
                }
                i++;
            }while(i<6 && beta > alpha);

            //restore the parent status and return the current node best evaluation
            this.m_controller.popStack();
            return minEval;
        }
    }

    /**
     * Generate an evaluation value for the current state of the game
     * @return Evaluation value
     */
    private int evaluateState(){
        int[] eval = new int[2];
        Point p = new Point(0, 0);

        //if minimiser won the game
        if (this.m_controller.getStoredSeeds(3 - this.m_id) > 24)
            return NEGINFINITE;

        //if maximiser won the game
        if (this.m_controller.getStoredSeeds(this.m_id) > 24)
            return POSINFINITE;

        //process the evaluation for both players
        for (int player=0 ; player < 2 ; player++){
            //initialise the evaluation with the amount of seeds captured with a lot of weight
            eval[player] = 300 * this.m_controller.getStoredSeeds(player + 1);

            //add the content of the player's slots, each weighted
            for (int slot=0 ; slot < 6 ; slot++) {
                p.setCoordinates(slot, player);
                eval[player] += this.m_controller.getSlotSeeds(p) * (slot+1);
            }
        }

        //return the evaluation code
        return eval[this.m_id - 1] - eval[2 - this.m_id];
    }
}
