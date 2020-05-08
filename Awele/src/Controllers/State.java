package Controllers;

public enum State{
    PROMPTING((iGameState) new PromptingState()),
    PLAYING((iGameState) new PlayingState()),
    STORING((iGameState) new StoringState()),
    SWITCHING((iGameState) new SwitchingPlayerState());

    private iGameState m_state;

    /**
     * Initialise the State assigned to an enumeration
     * @param state State to assign
     */
    State(iGameState state) {
        this.m_state = state;
    }

    /**
     * Get the state assigned to an enumeration
     * @return State
     */
    public iGameState getState(){
        return this.m_state;
    }
}
