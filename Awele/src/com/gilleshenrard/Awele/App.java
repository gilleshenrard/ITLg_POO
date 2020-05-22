package com.gilleshenrard.Awele;

import com.gilleshenrard.Awele.Views.AI.MinimaxSelect;
import com.gilleshenrard.Awele.Views.Console.BoardConsoleView;
import com.gilleshenrard.Awele.Views.Console.GameConsoleView;
import com.gilleshenrard.Awele.Views.Console.KeyboardSelect;
import com.gilleshenrard.Awele.Views.JFX.BoardJFXView;
import com.gilleshenrard.Awele.Views.JFX.GameJFXView;
import com.gilleshenrard.Awele.Views.JFX.JFXSelect;
import com.gilleshenrard.Awele.Controllers.GameController;
import javafx.application.Application;
import com.gilleshenrard.Awele.Models.Player;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.stage.Stage;
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

        if (args.length > 0 && args[0].equals("console")) {
            cHandler.setLevel(Level.OFF);
            startConsole();
        }
        else{
            cHandler.setLevel(Level.FINE);
            launch(args);
        }
    }

    /**
     * Start the game with a JavaFX UI
     * @param primaryStage Primary JavaFX stage
     */
    @Override
    public void start(Stage primaryStage) {
        //game setup
        GameController game = new GameController();
        GameJFXView gameView = new GameJFXView(game);

        //Board setup
        BoardJFXView bv = new BoardJFXView();
        game.getBoardController().attach(bv);

        //players setup
        game.setPlayer(new Player(1, "Gilles", new JFXSelect(game.getBoardController(), 1)));
        game.setPlayer(new Player(2, "AI", new MinimaxSelect(game.getBoardController(), 2)));

        //primary Stage setup
        primaryStage.setTitle("POO - App");
        primaryStage.setScene((Scene)bv.getContent());
        game.displayGame();
        primaryStage.setResizable(false);
        primaryStage.setWidth(1024);
        primaryStage.setHeight(576);
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

    /**
     * Start the game with a console UI
     */
    public static void startConsole() {
        //game setup
        GameController game = new GameController();
        GameConsoleView gameConsoleView = new GameConsoleView(game);

        //Board setup
        BoardConsoleView bv = new BoardConsoleView();
        game.getBoardController().attach(bv);

        //players setup
        game.setPlayer(new Player(1, "Gilles", new KeyboardSelect(game.getBoardController(), 1)));
        game.setPlayer(new Player(2, "AI", new MinimaxSelect(game.getBoardController(), 2)));

        //display the board
        game.displayGame();

        //launch the game loop
        game.gameLoop();
    }
}
