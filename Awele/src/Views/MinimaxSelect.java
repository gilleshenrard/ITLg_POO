package Views;

import Controllers.BoardController;
import Models.Point;

public class MinimaxSelect implements iSelectable{
    private BoardController m_controller;
    private int m_id;
    private int m_maxDepth;
    private final static int INFINITE_POS = 200000;
    private final static int INFINITE_NEG = -INFINITE_POS;
    private final static int ERROR = Integer.MIN_VALUE;

    /**
     * Create a new Minimax selection behaviour
     * @param controller The board controller to use
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
     * @throws NullPointerException
     */
    @Override
    public int selectSlot() throws NullPointerException{
        if (this.m_controller == null)
            throw new NullPointerException("MinimaxSelect.SelectSlot() : Board controller is not instantiated");

        int bestVal = ERROR;
        int bestShot = -3;  //initialised with error code - 1

        try {
            //test each legal slots for the best value
            Point slot = new Point(0, 0);
            for (int x = 0; x < 6; x++) {
                slot.setCoordinates(x, this.m_id - 1);

                //if slot is legal, get its minimax value (AI is the maximiser, the opponent is the minimiser)
                if (this.m_controller.isLegal(slot)) {
                    int val = miniMax(slot, this.m_maxDepth, INFINITE_NEG, INFINITE_POS, true);

                    //if no error, update the best value and the best slot
                    if (val != ERROR && val > bestVal) {
                        bestVal = val;
                        bestShot = slot.getX();
                    }
                }
            }
        }
        catch (Exception e){
            //empty the BoardController stack, then rethrow the exception caught
            while (this.m_controller != null && this.m_controller.popStack() > 0){}
            throw e;
        }

        //return the best slot
        return bestShot + 1;
    }

    /**
     * Use the Minimax algorithm to determine the best move
     * @param slot Slot to play in this node
     * @param depth Depth of the current node
     * @param alpha Alpha value in this pruning state
     * @param beta Beta value in this pruning state
     * @param maximiser Flag determining if the current node concerns the maximiser or the minimiser (AI considers itself the maximiser)
     * @return Best Slot to select
     * @throws NullPointerException
     */
    private int miniMax(Point slot, int depth, int alpha, int beta, boolean maximiser) throws NullPointerException{
        if (this.m_controller == null)
            throw new NullPointerException("MinimaxSelect.miniMax() : Board controller is not instantiated");

        //save a copy of the parent node state
        this.m_controller.pushStack();

        //play the current node slot and, if error,
        //  restore the parent board and return error code
        if (this.m_controller.playSlot(slot) < 0 ) {
            this.m_controller.popStack();
            return ERROR;
        }

        //evaluate the current node and return its value if tree leaf reached or game won
        int evaluation = evaluateState();
        if (depth == 0 || evaluation == INFINITE_POS || evaluation == INFINITE_NEG) {
            this.m_controller.popStack();
            return evaluation;
        }

        //look for the best maximising value in all the minimising children, or the best minimising in each maximising children
        int bestEvaluation = (maximiser ? INFINITE_NEG : INFINITE_POS);
        int x = 0;
        do{
            //AI is the maximiser, opponent is the minimiser, each evaluate on its side
            Point childSlot = new Point(x, (maximiser ? this.m_id-1 : 2-this.m_id));
            evaluation = miniMax(childSlot, depth - 1, alpha, beta, !maximiser);

            //if no error, update the best evaluation, alpha and beta values
            if (evaluation != ERROR) {
                if (maximiser) {
                    bestEvaluation = Math.max(bestEvaluation, evaluation);
                    alpha = Math.max(alpha, evaluation);
                }
                else {
                    bestEvaluation = Math.min(bestEvaluation, evaluation);
                    beta = Math.min(beta, evaluation);
                }
            }
            x++;
        }while(x<6 && beta > alpha);

        //restore the parent status and return the current node best evaluation
        this.m_controller.popStack();
        return bestEvaluation;
    }

    /**
     * Generate an evaluation value for the current state of the game
     * @return Evaluation value
     * @throws NullPointerException
     */
    private int evaluateState() throws NullPointerException{
        if (this.m_controller == null)
            throw new NullPointerException("MinimaxSelect.evaluateState() : Board controller is not instantiated");

        int[] eval = new int[2];
        Point p = new Point(0, 0);

        //if minimiser (opponent) won the game
        if (this.m_controller.getStoredSeeds(3 - this.m_id) > 24)
            return INFINITE_NEG;

        //if maximiser (AI) won the game
        if (this.m_controller.getStoredSeeds(this.m_id) > 24)
            return INFINITE_POS;

        //process the evaluation for both players
        for (int player=0 ; player < 2 ; player++){
            //initialise the evaluation with the amount of seeds captured and add a lot of weight to it
            eval[player] = 300 * this.m_controller.getStoredSeeds(player + 1);

            //add the content of the player's slots, each weighted depending on their place (right more weighted)
            for (int slot=0 ; slot < 6 ; slot++) {
                p.setCoordinates(slot, player);
                eval[player] += this.m_controller.getSlotSeeds(p) * (slot+1);
            }
        }

        //return the evaluation code
        return eval[this.m_id - 1] - eval[2 - this.m_id];
    }
}
