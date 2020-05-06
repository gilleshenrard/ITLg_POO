package Views;

import Controllers.BoardController;
import Controllers.iObserver;
import Models.Point;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
    private SimpleIntegerProperty[][] m_slots;
    private SimpleIntegerProperty[] m_storedSeeds;
    private SimpleStringProperty[] m_names;
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
            this.m_slots = new SimpleIntegerProperty[2][6];
            this.m_storedSeeds = new SimpleIntegerProperty[2];
            this.m_names = new SimpleStringProperty[2];
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
    public void update() {
        //update all the slots with the values from the Board
        Point p = new Point(0, 0);
        for (int l=0 ; l<2 ; l++){
            for (int c=0 ; c<6 ; c++) {
                p.setCoordinates(c, l);
                this.m_slots[l][c].set(this.m_controller.getSlotSeeds(p));
            }
        }

        //update the stored seeds count for both players
        this.m_storedSeeds[0].set(this.m_controller.getStoredSeeds(1));
        this.m_storedSeeds[1].set(this.m_controller.getStoredSeeds(2));

        //update the name of both players
        this.m_names[0].set(this.m_controller.getName(1));
        this.m_names[1].set(this.m_controller.getName(2));
    }

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
