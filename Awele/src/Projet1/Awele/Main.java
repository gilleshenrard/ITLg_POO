package Projet1.Awele;

import Views.*;
import javafx.application.Application;
import Controllers.GameController;
import Models.Player;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
            game.displayError(e.getMessage());
            System.exit(-1);
        }
        //system error, exit with an error
        if (outcome == -1)
            System.exit(outcome);
    }
}
