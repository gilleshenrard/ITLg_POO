/****************************************************************************************************/
/*  Class BoardJFXView                                                                              */
/*  Implements the Observer design pattern                                                          */
/*  Provides game board JavaFX display methods, and thus communicates with the board controller     */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 11/05/2020                                                                        */
/****************************************************************************************************/
package ITLg.POO.GillesHenrard.Awele.Views;

import ITLg.POO.GillesHenrard.Awele.Controllers.BoardController;
import ITLg.POO.GillesHenrard.Awele.Controllers.iObserver;
import ITLg.POO.GillesHenrard.Awele.Models.Point;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoardJFXView extends BorderPane implements iObserver, Initializable {
    private Scene m_scene = null;
    private BoardController m_controller;
    private int[][] m_slots;
    private int[] m_scores;
    @FXML GridPane m_grid;
    @FXML Label l_message;
    @FXML Label l_namePl1;
    @FXML Label l_namePl2;
    @FXML Label l_scorePl1;
    @FXML Label l_scorePl2;

    /**
     * Create a new Board Java FX view
     */
    public BoardJFXView() {
        try {
            this.m_slots = new int[2][6];
            this.m_scores = new int[2];

            //load the FXML document
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Layouts/MainSceneGrid.fxml"));
            loader.setController(this);
            GridPane graph = loader.load();

            //load custom fonts
            Font.loadFont(this.getClass().getResource("Styles/FTY_DELIRIUM_NEON_NCV.otf").toExternalForm(), 12);          //-fx-font-family: 'FTY DELIRIUM NEON NCV'

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
        //log the main scene initialisation
        Logger.getLogger(this.getClass().getName()).log(Level.FINE, "Main scene initialisation");

        //set the click handler to the grid panel
        this.m_grid.setOnMouseClicked(this::onSlotClicked);

        //set a default value for the message
        this.l_message.setText("");

        //set a default value to score labels
        this.l_scorePl1.setText("0");
        this.l_scorePl2.setText("0");

        //set the default value for labels as "0"
        for (int l=0 ; l<2 ; l++) {
            for (int c = 0; c < 6; c++) {
                //retrieve the proper GridView element and its label child
                int index = (l == 0 ? 8 + c : 7 - c);
                StackPane tmp = (StackPane) this.m_grid.getChildren().get(index);
                Label tmplabel = (Label) tmp.getChildren().get(1);

                tmplabel.setText("4");
            }
        }
    }

    /**
     * Update nodes values in the scene
     */
    @Override
    public void update() {
        Logger.getLogger(this.getClass().getName()).log(Level.FINE, "Player " + this.m_controller.getCurrentPlayer() + " updates the buffers");

        //update the slots seeds and stored seeds count buffers
        Point p = new Point(0, 0);
        for (int l=0 ; l<2 ; l++) {
            for (int c = 0; c < 6; c++) {
                p.setCoordinates(c, l);
                this.m_slots[l][c] = this.m_controller.getSlotSeeds(p);
            }
            this.m_scores[l] = this.m_controller.getStoredSeeds(l + 1);
        }

        //update the JavaFX scene
        updateScene();
    }

    /**
     * Update the main scene's labels in a runLater procedure
     */
    private void updateScene(){
        //launch scene update as a runlater task to avoid concurrency issues
        Platform.runLater(() -> {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, "Player " + this.m_controller.getCurrentPlayer() + " updates the main scene");

            //update all the slots with the values from the Board
            Point p = new Point(0, 0);
            for (int l=0 ; l<2 ; l++){
                for (int c=0 ; c<6 ; c++) {
                    //retrieve the proper GridView element and its label child
                    int index = (l == 0 ? 8+c : 7-c);
                    StackPane tmp = (StackPane) this.m_grid.getChildren().get(index);
                    Label tmplabel = (Label) tmp.getChildren().get(1);

                    //update the label's text
                    if (this.m_slots[l][c] != Integer.parseInt(tmplabel.getText())) {
                        tmplabel.setText(Integer.toString(this.m_slots[l][c]));
                        animate(tmp);
                    }

                    //update the CSS class of the whole Stackpane element
                    p.setCoordinates(c, l);
                    if (this.m_controller.isOwner(this.m_controller.getCurrentPlayer(), p) && this.m_controller.isLegal(p)){
                        tmp.getStyleClass().remove("illegal");
                        tmp.getStyleClass().add("legal");
                    }
                    else {
                        tmp.getStyleClass().remove("legal");
                        tmp.getStyleClass().add("illegal");
                    }
                }
            }

            //update the stored seeds count for both players
            this.l_scorePl1.setText(Integer.toString(this.m_scores[0]));
            this.l_scorePl2.setText(Integer.toString(this.m_scores[1]));

            //update the name of both players
            this.l_namePl1.setText(this.m_controller.getName(1));
            this.l_namePl2.setText(this.m_controller.getName(2));

            Logger.getLogger(this.getClass().getName()).log(Level.FINE, "Player " + this.m_controller.getCurrentPlayer() + " finished updating the scene");
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
        //click is higher than top 15% height line (title bar and message label)
        if (mouseEvent.getY() < this.m_grid.getHeight()*0.2) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, "Player " + this.m_controller.getCurrentPlayer() + " : click too high in the scene");
            return;
        }

        //click is lower than bottom 15% height line (scoreboards)
        if (mouseEvent.getY() >= this.m_grid.getHeight()*0.8) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, "Player " + this.m_controller.getCurrentPlayer() + " : click too low in the scene");
            return;
        }

        //compute the coordinates of the click on the slots area
        double coordY = (this.m_grid.getHeight() * 0.6) / 2.0;
        coordY = (mouseEvent.getY() - this.m_grid.getHeight() * 0.15) / coordY;

        //retrieve the slot coordinates from the mouse click coordinates
        Point p = new Point((int)(mouseEvent.getX()/(this.m_grid.getWidth()/6)), 1 - (int)coordY);

        //check if the current player is owner of the slot clicked
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Player " + this.m_controller.getCurrentPlayer() + " : clicked on " + p);
        if (this.m_controller.isOwner(this.m_controller.getCurrentPlayer(), p)) {
            //set the coordinates selected by the player, and play its season
            this.m_controller.setLastSelected(p);

            //notify JFXSelect that a slot has been clicked
            synchronized (this.m_controller){
                Logger.getLogger(this.getClass().getName()).log(Level.FINE, "Player " + this.m_controller.getCurrentPlayer() + " : sends a notification");
                this.m_controller.notify();
            }
        }
    }

    /**
     * Display a message on the message label
     * @param msg Message to display
     */
    @Override
    public void sendMessage(String msg){
        //launch as a runlater task to avoid concurrency issues
        Platform.runLater(() -> {
            this.l_message.setText(msg);
        });
    }

    /**
     * Animate a pane by fading it for 1s from 1.0 to 0.2 once, then return to normal
     * @param pane Pane to animate
     */
    private void animate(StackPane pane) {
        //fade out transition
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(900));
        fade.setFromValue(1.0);
        fade.setToValue(0.2);
        fade.setCycleCount(1);
        fade.setAutoReverse(false);

        //fade in transition
        FadeTransition unfade = new FadeTransition();
        unfade.setDuration(Duration.millis(100));
        unfade.setFromValue(0.2);
        unfade.setToValue(1.0);
        unfade.setCycleCount(1);
        unfade.setAutoReverse(false);

        //set a sequence of both transition on the pane
        SequentialTransition sequence = new SequentialTransition(pane, fade, unfade);

        //play the sequence
        sequence.play();
    }
}
