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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameJFXView extends GridPane implements Initializable, iNotifiable {
    private Scene m_scene = null;
    private GameController m_controller;

    /**
     * Create a new Game Java FX view
     * @param controller Game controller to use
     * @throws NullPointerException
     */
    public GameJFXView(GameController controller) throws NullPointerException {
        this.m_controller = controller;
        this.m_controller.setView(this);

        try {
            //load the FXML document
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Layouts/MenuSceneGrid.fxml"));
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
     * Initialise the JavaFX elements of the Menu pane
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //log the main scene initialisation
        Logger.getLogger(this.getClass().getName()).log(Level.FINE, "Menu scene initialisation");
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
        Logger.getLogger(App.class.getName()).log(Level.FINE, "Displaying the menu pane");
    }
}
