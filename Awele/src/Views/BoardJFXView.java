package Views;

import Controllers.BoardController;
import Controllers.iObserver;

public class BoardJFXView implements iObserver{
    private BoardController m_controller;

    /**
     * Initialise all nodes in the Scene
     */
    @Override
    public void init() {}

    /**
     * Update nodes values in the scene
     */
    @Override
    public void update() {}

    /**
     * Set the board controller to use in this scene
     * @param controller Board controller to use
     */
    @Override
    public void setController(Object controller) {
        this.m_controller = (BoardController) controller;
    }
}
