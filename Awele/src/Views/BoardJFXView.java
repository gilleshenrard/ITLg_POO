/****************************************************************************************************/
/*  Class BoardJFXView                                                                              */
/*  Implements the Observer design pattern                                                          */
/*  Provides game board JavaFX display methods, and thus communicates with the board controller     */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 11/05/2020                                                                        */
/****************************************************************************************************/
package Views;

import Controllers.BoardController;
import Controllers.iObserver;
import Models.Point;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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

            //load custom fonts
            Font.loadFont(this.getClass().getResource("Styles/AirstreamNF.ttf").toExternalForm(), 12);          //-fx-font-family: 'Airstream NF'
            Font.loadFont(this.getClass().getResource("Styles/modern_sans_serif_7.ttf").toExternalForm(), 12);  //-fx-font-family: 'Modern Sans Serif 7'
            Font.loadFont(this.getClass().getResource("Styles/marquee-moon.ttf").toExternalForm(), 12);         //-fx-font-family: 'Marquee Moon'
            Font.loadFont(this.getClass().getResource("Styles/MedulaOne-Regular.ttf").toExternalForm(), 12);    //-fx-font-family: 'Medula One'

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
        //set the click handler to the grid panel
        this.m_grid.setOnMouseClicked(this::onSlotClicked);

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
        //launch as a runlater task to avoid concurrency issues
        Logger.getLogger("Awele").log(Level.FINE, "Player " + this.m_controller.getCurrentPlayer() + " updates the main scene");
        Platform.runLater(() -> {
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
        });
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

    /**
     * When a slot is clicked, play a non-AI player season.
     * If the next player is an AI, play its season as well
     * @param mouseEvent Slot click event
     */
    public void onSlotClicked(MouseEvent mouseEvent) {
        //retrieve the slot coordinates from the mouse click coordinates
        Point p = new Point((int)(mouseEvent.getX()/(this.m_grid.getWidth()/6)), 1 - (int)(mouseEvent.getY()/(this.m_grid.getHeight()/2)));

        //check if the current player is owner of the slot clicked
        Logger.getLogger("Awele").log(Level.INFO, "Player " + this.m_controller.getCurrentPlayer() + " : clicked on " + p);
        if (this.m_controller.isOwner(this.m_controller.getCurrentPlayer(), p)) {
            //set the coordinates selected by the player, and play its season
            this.m_controller.setLastSelected(p);

            //notify JFXSelect that a slot has been clicked
            synchronized (this.m_controller){
                Logger.getLogger("Awele").log(Level.FINE, "Player " + this.m_controller.getCurrentPlayer() + " : sends a notification");
                this.m_controller.notify();
            }
        }
    }
}
