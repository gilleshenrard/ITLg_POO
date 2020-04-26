package Controllers;

public abstract class GameState {
    /**
     * Base handleState method. Defines the signature for all children
     * @param controller Game controller to use
     * @param input Input of a state
     * @return Output of the state
     */
    public int handleState(GameController controller, int input){return 0;};
}
