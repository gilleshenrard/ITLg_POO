/****************************************************************************************************/
/*  Class GameJFXView                                                                               */
/*  Implements iNotifiable                                                                          */
/*  Provides game JavaFX system messages methods, and thus communicates with the game controller    */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 27/05/2020                                                                        */
/****************************************************************************************************/
package com.gilleshenrard.Awele.Views.JFX;

import com.gilleshenrard.Awele.Controllers.GameController;
import com.gilleshenrard.Awele.Views.AI.MinimaxSelect;
import com.gilleshenrard.Awele.Views.AI.RandomSelect;
import com.gilleshenrard.Awele.Views.iNotifiable;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameJFXView extends GridPane implements Initializable, iNotifiable {
    private Stage m_stage;
    private Scene m_menuscene = null;
    private GameController m_controller;
    @FXML Button b_ok;
    @FXML Button b_exit;
    @FXML Button b_reset;
    @FXML TextField tf_name1;
    @FXML TextField tf_name2;
    @FXML ToggleGroup tg_pl1AI;
    @FXML ToggleGroup tg_pl2AI;

    /**
     * Create a new Game Java FX view
     * @param controller Game controller to use
     * @param stage Primary stage hosting the JavaFX scenes
     * @throws NullPointerException
     */
    public GameJFXView(GameController controller, Stage stage) throws NullPointerException {
        this.m_controller = controller;
        this.m_controller.setView(this);
        this.m_stage = stage;

        try {
            //load the FXML document
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Layouts/MenuSceneGrid.fxml"));
            loader.setController(this);
            GridPane graph = loader.load();

            //load custom fonts
            Font.loadFont(this.getClass().getResource("Styles/FTY_DELIRIUM_NEON_NCV.otf").toExternalForm(), 12);          //-fx-font-family: 'FTY DELIRIUM NEON NCV'

            //create a new scene from the graph
            this.m_menuscene = new Scene(graph);
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * Initialise the JavaFX elements of the Menu pane
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //log the main scene initialisation
        Logger.getLogger(this.getClass().getName()).log(Level.FINE, "Menu scene initialisation");
        this.b_ok.setOnMouseClicked(this::onOKButtonClicked);
        this.b_exit.setOnMouseClicked(this::onExitButtonClicked);
        this.b_reset.setOnMouseClicked(this::onResetButtonClicked);
    }

    /**
     * Display an Alert pane with an error message
     * @param msg Message to display
     */
    @Override
    public void displayError(String msg) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(msg);
            alert.showAndWait();
        });
    }

    /**
     * Display a menu JavaFX pane
     */
    @Override
    public void displayMenu() {
        //switch the scenes to the menu pane (in separate thread)
        Platform.runLater(() -> {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, "GameJFXView.DisplayMenu() : display the Menu pane");

            //switch the scenes to display the menu pane
            this.m_stage.setScene(this.m_menuscene);
            this.m_stage.sizeToScene();
        });

        //make the game loop thread wait
        synchronized (this.m_controller){
            try {
                Logger.getLogger(this.getClass().getName()).log(Level.FINE, "GameJFXView.DisplayMenu() : game loop thread waiting");
                this.m_controller.wait();
                Logger.getLogger(this.getClass().getName()).log(Level.FINE, "GameJFXView.DisplayMenu() : game loop resumed");
            }
            catch (InterruptedException e){}
        }
    }

    /**
     * Handle a click on the OK button
     * @param event JavaFX click event
     */
    private void onOKButtonClicked(Event event){
        Logger.getLogger(this.getClass().getName()).log(Level.FINE, "GameJFXView.onOKButton() : display Main screen");

        //get the players' names written in the options pane
        this.m_controller.setName(1, this.tf_name1.getText());
        this.m_controller.setName(2, this.tf_name2.getText());

        //get the behaviour selected for the player 1
        RadioButton tmp_radio = (RadioButton) this.tg_pl1AI.getSelectedToggle();
        this.setBehaviour(1, tmp_radio.getText());

        //get the behaviour selected for the player 2
        tmp_radio = (RadioButton) this.tg_pl2AI.getSelectedToggle();
        this.setBehaviour(2, tmp_radio.getText());

        //notify the game loop thread to resume the game loop
        synchronized (this.m_controller) {
            this.m_controller.notify();
        }
    }

    /**
     * Handle a click on the Exit Game button
     * @param event JavaFX click event
     */
    private void onExitButtonClicked(Event event){
        Logger.getLogger(this.getClass().getName()).log(Level.FINE, "GameJFXView.onExitButton() : flag the main loop as not running");

        //flag the game loop as stopped and close the main stage
        this.m_controller.setRunning(false);
        this.m_stage.close();

        //notify the game loop thread to resume the game loop
        synchronized (this.m_controller) {
            this.m_controller.notify();
        }
    }

    /**
     * Handle a click on the Exit Game button
     * @param event JavaFX click event
     */
    private void onResetButtonClicked(Event event){
        Logger.getLogger(this.getClass().getName()).log(Level.FINE, "GameJFXView.onExitButton() : flag the main loop as not running");

        //reset the game
        this.m_controller.resetGame();

        //update all the game fields
        this.onOKButtonClicked(event);
    }

    /**
     * Assign a behaviour to a player
     * @param ID ID of the player to which assign the behaviour
     * @param behaviour Behaviour to assign to the player
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    private void setBehaviour(int ID, String behaviour) throws InvalidParameterException, NullPointerException {
        switch (behaviour) {
            case "None":
                this.m_controller.setBehaviour(ID, new JFXSelect(this.m_controller.getBoardController()));
                Logger.getLogger(this.getClass().getName()).log(Level.FINE, "Player " + ID + " : JFXSelect behaviour set");
                break;

            case "Easy":
                this.m_controller.setBehaviour(ID, new RandomSelect(this.m_controller.getBoardController()));
                Logger.getLogger(this.getClass().getName()).log(Level.FINE, "Player " + ID + " : RandomSelect behaviour set");
                break;

            case "Medium":
                MinimaxSelect tmp = new MinimaxSelect(this.m_controller.getBoardController());
                tmp.setMaxDepth(4);
                this.m_controller.setBehaviour(ID, tmp);
                Logger.getLogger(this.getClass().getName()).log(Level.FINE, "Player " + ID + " : Minimax (depth 4) behaviour set");
                break;

            case "Hard":
                tmp = new MinimaxSelect(this.m_controller.getBoardController());
                tmp.setMaxDepth(10);
                this.m_controller.setBehaviour(ID, tmp);
                Logger.getLogger(this.getClass().getName()).log(Level.FINE, "Player " + ID + " : Minimax (depth 10) behaviour set");
                break;

            default:
                throw new InvalidParameterException("GameJFXView.setBehaviour() : invalid value of behaviour : " + behaviour);
        }
    }
}
