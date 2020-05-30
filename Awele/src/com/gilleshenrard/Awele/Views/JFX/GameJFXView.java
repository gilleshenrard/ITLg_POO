/****************************************************************************************************/
/*  Class GameJFXView                                                                               */
/*  Implements iNotifiable                                                                          */
/*  Provides game JavaFX system messages methods, and thus communicates with the game controller    */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 27/05/2020                                                                        */
/****************************************************************************************************/
package com.gilleshenrard.Awele.Views.JFX;

import com.gilleshenrard.Awele.App;
import com.gilleshenrard.Awele.Controllers.GameController;
import com.gilleshenrard.Awele.Views.iNotifiable;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameJFXView extends GridPane implements Initializable, iNotifiable {
    private Stage m_stage;
    private Scene m_menuscene = null;
    private Scene m_mainscene = null;
    private GameController m_controller;
    @FXML Button b_exit;

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
        this.b_exit.setOnMouseClicked(this::onExitButtonClicked);
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
        //switch the scenes to the menu pane
        Platform.runLater(() -> {
            Logger.getLogger(App.class.getName()).log(Level.FINE, "GameJFXView.DisplayMenu() : display the Menu pane");

            //switch the scenes to display the menu pane
            this.m_mainscene = this.m_stage.getScene();
            this.m_stage.setScene(this.m_menuscene);
        });

        //make the game loop thread wait
        synchronized (this.m_controller){
            try {
                Logger.getLogger(App.class.getName()).log(Level.FINE, "GameJFXView.DisplayMenu() : game loop thread waiting");
                this.m_controller.wait();
                Logger.getLogger(App.class.getName()).log(Level.FINE, "GameJFXView.DisplayMenu() : game loop resumed");
            }
            catch (InterruptedException e){}
        }
    }

    /**
     * Handle a click on the Exit Menu button
     * @param event JavaFX click event
     */
    private void onExitButtonClicked(Event event){
        Platform.runLater(() -> {
            Logger.getLogger(App.class.getName()).log(Level.FINE, "GameJFXView.onExitButton() : display Main screen");

            //display the game scene
            this.m_stage.setScene(this.m_mainscene);

            //notify the game loop thread to resume the game loop
            synchronized (this.m_controller) {
                this.m_controller.notify();
            }
        });
    }
}
