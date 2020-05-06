package Views;

import Controllers.BoardController;
import Controllers.iObserver;

public class BoardJFXView implements iObserver{
    private BoardController m_controller;

    @Override
    public void update() {

    }

    @Override
    public void setController(Object controller) {
        this.m_controller = (BoardController) controller;
    }
}
