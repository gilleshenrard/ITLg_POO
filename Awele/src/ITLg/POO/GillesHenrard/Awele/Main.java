package ITLg.POO.GillesHenrard.Awele;

import ITLg.POO.GillesHenrard.Awele.Views.*;
import javafx.application.Application;
import ITLg.POO.GillesHenrard.Awele.Controllers.GameController;
import ITLg.POO.GillesHenrard.Awele.Models.Player;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application{

    /**
     * Main method
     * @param args Program arguments ('console' launches the game in console mode)
     */
    public static void main(String[] args) {
        if (args[0] == "console")
                startConsole();
        else
            launch(args);
    }

    /**
     * Start the game with a JavaFX UI
     * @param primaryStage Primary JavaFX stage
     */
    @Override
    public void start(Stage primaryStage) {
        // create the main logger, and enable all logs
        Logger logger = Logger.getLogger(this.getClass().getClass().getName());
        logger.setLevel(Level.ALL);

        //assign a Console handler to the logger, and handle FINE level logs
        ConsoleHandler cHandler = new ConsoleHandler();
        cHandler.setLevel(Level.FINE);
        logger.addHandler(cHandler);
        logger.setUseParentHandlers(false);

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
        primaryStage.setTitle("POO - Awele");
        primaryStage.setScene((Scene)bv.getContent());
        game.updateObservers();
        primaryStage.setResizable(false);
        primaryStage.setWidth(1920);
        primaryStage.setHeight(1080);
        primaryStage.show();

        //Parallel Task holding the game loop
        Task<Void> mainLoop = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                gameLoop(game);
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
        game.setPlayer(new Player(1, "Gilles", new KeyboardSelect()));
        game.setPlayer(new Player(2, "AI", new MinimaxSelect(game.getBoardController(), 2)));

        //launch the game loop
        gameLoop(game);
    }

    /**
     * Process the game loop using the Game Finite State Machine
     * @param game Game controller to use
     */
    public static void gameLoop(GameController game){
        //state variables
        int outcome = 0;

        try {
            //main game loop, while no victory
            while (outcome != -2 && outcome != -1) {
                outcome = game.handleState();
            }
        }
        catch (Exception e){
            Logger.getLogger("Awele").log(Level.SEVERE, e.getMessage());
            game.displayError(e.getMessage());
            System.exit(-1);
        }
        //system error, exit with an error
        if (outcome == -1)
            System.exit(outcome);
    }
}
