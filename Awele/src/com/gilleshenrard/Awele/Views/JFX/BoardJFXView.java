/****************************************************************************************************/
/*  Class BoardJFXView                                                                              */
/*  Implements the Observer design pattern                                                          */
/*  Provides game board JavaFX display methods, and thus communicates with the board controller     */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 27/05/2020                                                                        */
/****************************************************************************************************/
package com.gilleshenrard.Awele.Views.JFX;

import com.gilleshenrard.Awele.Controllers.BoardController;
import com.gilleshenrard.Awele.Views.iObserver;
import com.gilleshenrard.Awele.Models.Point;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoardJFXView extends GridPane implements iObserver, Initializable {
    private Stage m_stage = null;
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
    @FXML Button b_menu;

    /**
     * Create a new Board Java FX view
     */
    public BoardJFXView(Stage stage) {
        this.m_stage = stage;
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

        //set the click handler to the menu button
        this.b_menu.setOnMouseClicked(this::onMenuClicked);

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

            //display the game scene
            this.m_stage.setScene(this.m_scene);

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
                        animateSlot(tmp);
                    }

                    //update the CSS class of the whole Stackpane element
                    p.setCoordinates(c, l);
                    if (this.m_controller.isOwner(this.m_controller.getCurrentPlayer(), p) && this.m_controller.isLegal(p))
                        tmp.getStyleClass().add("legal");
                    else
                        tmp.getStyleClass().remove("legal");
                }
            }

            //update the stored seeds count for both players
            this.l_scorePl1.setText(Integer.toString(this.m_scores[0]));
            this.l_scorePl2.setText(Integer.toString(this.m_scores[1]));

            //update the name of both players
            this.l_namePl1.setText(this.m_controller.getName(1));
            this.l_namePl2.setText(this.m_controller.getName(2));

            //highlight the current player scoreboard with the same colour as legal slots
            if (this.m_controller.getCurrentPlayer() == 1){
                this.l_scorePl1.getStyleClass().add("legal");
                this.l_scorePl2.getStyleClass().remove("legal");
                this.l_namePl1.getStyleClass().add("legal");
                this.l_namePl2.getStyleClass().remove("legal");
            }
            else {
                this.l_scorePl2.getStyleClass().add("legal");
                this.l_scorePl1.getStyleClass().remove("legal");
                this.l_namePl2.getStyleClass().add("legal");
                this.l_namePl1.getStyleClass().remove("legal");
            }

            Logger.getLogger(this.getClass().getName()).log(Level.FINE, "Player " + this.m_controller.getCurrentPlayer() + " finished updating the scene");
        });
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
     * When the menu button is clicked, notify the Board controller that the menu is requested
     * @param mouseEvent Menu button click event
     */
    public void onMenuClicked(MouseEvent mouseEvent) {
        Logger.getLogger(this.getClass().getName()).log(Level.FINE, "Player " + this.m_controller.getCurrentPlayer() + " : click on the Menu button");

        //notify JFXSelect that a click has been handled
        synchronized (this.m_controller){
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, "Player " + this.m_controller.getCurrentPlayer() + " : sends a notification");
            this.m_controller.notify();
        }
    }

    /**
     * When a slot is clicked, notify the Board controller it can carry on with the player's season.
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

        //compute the Y coordinates of the click on the slots area
        double coordY = (this.m_grid.getHeight() * 0.6) / 2.0;
        coordY = (mouseEvent.getY() - this.m_grid.getHeight() * 0.15) / coordY;
        coordY = (double)(1 - (int)coordY);

        //compute the X coordinates of the click on the slots area
        double coordX = mouseEvent.getX()/(this.m_grid.getWidth()/6);
        if ((int)coordY == 1)
            coordX = (double)(5 - (int)coordX);

        //create the point from the click coordinates
        Point p = new Point((int)coordX, (int)coordY);

        //check if the current player is owner of the slot clicked
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Player " + this.m_controller.getCurrentPlayer() + " : clicked on " + p);
        if (this.m_controller.isOwner(this.m_controller.getCurrentPlayer(), p)) {
            //set the coordinates selected by the player, and play its season
            this.m_controller.setLastSelected(p);

            //notify JFXSelect that a click has been handled
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

            //animate the message (fade out during 5s)
            FadeTransition fade = new FadeTransition();
            fade.setDuration(Duration.millis(5000));
            fade.setFromValue(1.0);
            fade.setToValue(0.0);
            fade.setCycleCount(1);
            fade.setAutoReverse(false);
            fade.setNode(this.l_message);
            fade.play();
        });
    }

    /**
     * Animate a pane by fading the background as the stroke colour from 1.0 to 0 in 1 second
     * @param pane Pane to animate
     */
    private void animateSlot(StackPane pane) {
        //define an inline class
        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(1000));
                setInterpolator(Interpolator.EASE_OUT);
            }

            @Override
            protected void interpolate(double frac) {
                //get the colour of the label used (same as the circle stroke)
                Label label = (Label) pane.getChildren().get(1);
                String fill = label.getTextFill().toString();

                //fade it accordingly depending of the time (lower the alpha)
                Color vColor = Color.web(fill, 1.0 - frac);

                //set it as the background colour for the circle
                Circle circle = (Circle) pane.getChildren().get(0);
                circle.setFill(vColor);
            }
        };

        //play the animation
        animation.play();
    }
}
