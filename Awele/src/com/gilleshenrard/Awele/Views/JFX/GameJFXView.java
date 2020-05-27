/****************************************************************************************************/
/*  Class GameJFXView                                                                               */
/*  Implements SystemMessage                                                                        */
/*  Provides game JavaFX system messages methods, and thus communicates with the game controller    */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 27/05/2020                                                                        */
/****************************************************************************************************/
package com.gilleshenrard.Awele.Views.JFX;

import com.gilleshenrard.Awele.App;
import com.gilleshenrard.Awele.Controllers.GameController;
import com.gilleshenrard.Awele.Views.SystemMessage;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameJFXView extends SystemMessage {
    /**
     * Create a new Game Java FX view
     * @param controller Game controller to use
     * @throws NullPointerException
     */
    public GameJFXView(GameController controller) throws NullPointerException {
        super(controller);
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
