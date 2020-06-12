package com.gilleshenrard.Awele;

import com.gilleshenrard.Awele.Views.AI.MinimaxSelect;
import com.gilleshenrard.Awele.Views.JFX.BoardJFXView;
import com.gilleshenrard.Awele.Views.JFX.GameJFXView;
import com.gilleshenrard.Awele.Views.JFX.JFXSelect;
import com.gilleshenrard.Awele.Controllers.GameController;
import javafx.application.Application;
import com.gilleshenrard.Awele.Models.Player;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App extends Application{

    /**
     * Main method
     * @param args Program arguments ('console' launches the game in console mode)
     */
    public static void main(String[] args) {
        // create the main logger, and enable all logs
        Logger logger = Logger.getLogger(App.class.getPackageName());
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);

        //assign a Console handler to the logger
        ConsoleHandler cHandler = new ConsoleHandler();
        logger.addHandler(cHandler);

        //setup the logger console handler and launch the application
        cHandler.setLevel(Level.FINE);
        launch(args);
    }

    /**
     * Start the game with a JavaFX UI
     * @param primaryStage Primary JavaFX stage
     */
    @Override
    public void start(Stage primaryStage) {
        //game setup
        GameController game = new GameController();
        GameJFXView gameView = new GameJFXView(game, primaryStage);

        //Board setup
        BoardJFXView bv = new BoardJFXView(primaryStage);
        game.getBoardController().attach(bv);

        //players setup
        game.setPlayer(new Player(1, "Player 1", new JFXSelect(game.getBoardController())));
        game.setPlayer(new Player(2, "AI", new MinimaxSelect(game.getBoardController())));

        //primary Stage setup
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);
        primaryStage.show();

        //make closing the primary stage exit the game
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            System.exit(0);
        });

        //Parallel Task holding the game loop
        Task<Void> mainLoop = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                game.gameLoop();
                return null;
            }
        };

        //Launch the game loop thread
        new Thread(mainLoop).start();
    }
}
