package Views;

import Controllers.BoardController;
import Controllers.iObserver;
import Models.Point;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class BoardJFXView implements iObserver {
    private Scene m_scene = null;
    private BoardController m_controller;
    private SimpleIntegerProperty[][] m_slots;
    private SimpleIntegerProperty m_storedPlayer1;
    private SimpleIntegerProperty m_storedPlayer2;
    private SimpleStringProperty m_namePlayer1;
    private SimpleStringProperty m_namePlayer2;
    @FXML Button m_menuButton;
    @FXML GridPane m_grid;

    /**
     * Create a new Board Java FX view
     */
    public BoardJFXView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Layouts/mainScene.fxml"));
            loader.setController(this);
            BorderPane graph = loader.load();

            //intantiate all the properties
            this.m_slots = new SimpleIntegerProperty[2][6];
            this.m_storedPlayer1 = new SimpleIntegerProperty();
            this.m_storedPlayer1 = new SimpleIntegerProperty();
            this.m_namePlayer1 = new SimpleStringProperty();
            this.m_namePlayer2 = new SimpleStringProperty();

            //call the initialisation procedure
            this.init();

            //create a new scene from the graph
            this.m_scene = new Scene(graph);
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * Perform all the bindings and configurations necessary
     */
    private void init() {
        //initialize the elements in the central gridpane
        //  + bind them and add them to said gridpane
        for (int l = 0; l < 2; l++) {
            for (int c = 0; c < 6; c++) {
                Label tmp = new Label();
                this.m_slots[l][c] = new SimpleIntegerProperty();
                tmp.textProperty().bind(this.m_slots[l][c].asString());
                this.m_grid.add(tmp, (l==0 ? c : 5-c), 1-l);
            }
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
        this.m_storedPlayer1.set(this.m_controller.getStoredSeeds(1));
        this.m_storedPlayer2.set(this.m_controller.getStoredSeeds(2));

        //update the name of both players
        this.m_namePlayer1.set(this.m_controller.getName(1));
        this.m_namePlayer2.set(this.m_controller.getName(2));
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
