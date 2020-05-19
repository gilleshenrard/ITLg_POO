/****************************************************************************************************/
/*  Class GameJFXView                                                                               */
/*  Implements SystemMessage                                                                        */
/*  Provides game JavaFX system messages methods, and thus communicates with the game controller    */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 11/05/2020                                                                        */
/****************************************************************************************************/
package ITLg.POO.GillesHenrard.Awele.Views;

import ITLg.POO.GillesHenrard.Awele.Controllers.GameController;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public class GameJFXView extends SystemMessage {
    /**
     * Create a new Game Java FX view
     * @param controller Game controller to use
     * @throws NullPointerException
     */
    public GameJFXView(GameController controller) throws NullPointerException {
        super(controller);
    }

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
}
