package Views;

import Controllers.BoardController;
import Controllers.iObserver;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class BoardJFXView implements iObserver{
    private BoardController m_controller;

    /**
     * Initialise all nodes in the Scene
     */
    @Override
    public void init() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Views/Layouts/mainScene.fxml"));
        try {
            Parent graph = loader.load();
        }
        catch (IOException e){
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }

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
