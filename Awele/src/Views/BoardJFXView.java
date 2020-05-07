package Views;

import Controllers.BoardController;
import Controllers.iObserver;
import Models.Point;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BoardJFXView extends BorderPane implements iObserver, Initializable {
    private Scene m_scene = null;
    private BoardController m_controller;
    private SimpleIntegerProperty[][] m_slots;
    private SimpleIntegerProperty m_storedPlayer1;
    private SimpleIntegerProperty m_storedPlayer2;
    private SimpleStringProperty m_namePlayer1;
    private SimpleStringProperty m_namePlayer2;
    @FXML Button m_menuButton;
    @FXML GridPane m_grid;
    @FXML Label l_namePl1;
    @FXML Label l_namePl2;
    @FXML Label l_scorePl1;
    @FXML Label l_scorePl2;

    /**
     * Create a new Board Java FX view
     */
    public BoardJFXView() {
        //intantiate all the non-FXML properties
        this.m_slots = new SimpleIntegerProperty[2][6];
        this.m_storedPlayer1 = new SimpleIntegerProperty();
        this.m_storedPlayer2 = new SimpleIntegerProperty();
        this.m_namePlayer1 = new SimpleStringProperty();
        this.m_namePlayer2 = new SimpleStringProperty();

        try {
            //load the FXML document
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Layouts/mainScene.fxml"));
            loader.setController(this);
            BorderPane graph = loader.load();

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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialize the elements in the central gridpane
        //  + bind them and add them to said gridpane
        for (int l = 0; l < 2; l++) {
            for (int c = 0; c < 6; c++) {
                //get the grid pane child corresponding to the proper element in the array
                int index = (l == 0 ? 6 : 0) + (l == 0 ? c : 5-c);
                Label tmp = (Label) this.m_grid.getChildren().get(index);

                //make sure to instantiate the Property buffer and bind it to the grid pane child
                this.m_slots[l][c] = new SimpleIntegerProperty();
                tmp.textProperty().bind(this.m_slots[l][c].asString());
            }
        }

        //bind labels holding players' names and give them a default value
        l_namePl1.textProperty().bind(this.m_namePlayer1);
        this.m_namePlayer1.setValue("Player 1");
        l_namePl2.textProperty().bind(this.m_namePlayer2);
        this.m_namePlayer2.setValue("Player 2");

        //bind labels holding players' scores
        l_scorePl1.textProperty().bind(this.m_storedPlayer1.asString());
        l_scorePl2.textProperty().bind(this.m_storedPlayer2.asString());
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
        this.m_namePlayer1.setValue(this.m_controller.getName(1));
        this.m_namePlayer2.setValue(this.m_controller.getName(2));
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
