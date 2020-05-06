package Views;

import Controllers.BoardController;
import Controllers.iObserver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class BoardJFXView implements iObserver{
    private Scene m_scene = null;
    private BoardController m_controller;
    @FXML Button m_menuButton;
    @FXML Label m_title;

    /**
     * Initialise all nodes in the Scene
     */
    @Override
    public void init() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Layouts/mainScene.fxml"));
        try {
            Parent graph = loader.load();
            this.m_scene = new Scene(graph);
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
     * Get the current Board scene
     * @return Current scene
     */
    @Override
    public Object getContent() {
        return this.m_scene;
    }

    /**
     * Set the board controller to use in this scene
     * @param controller Board controller to use
     */
    @Override
    public void setController(BoardController controller) {
        this.m_controller = controller;
    }
}
