package Controllers;

public interface iGameState {
    /**
     * Base handleState method. Defines the signature for all children
     * @param controller Game controller to use
     * @param input Input of a state
     * @return Output of the state
     */
    int handleState(GameController controller, int input);
}
