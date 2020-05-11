package Projet1.Awele;

import Controllers.State;
import Views.*;
import javafx.application.Application;
import Controllers.GameController;
import Models.Player;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    public static void main(String[] args) {
        switch (args[0]){
            case "console":
                startConsole();
                break;
            case "jfx":
                launch(args);
                break;
            default:
                System.err.println("Argument 1 not recognised. (console|jfx)");
                System.exit(-1);
                break;
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
        primaryStage.setTitle("POO - Awele");
        primaryStage.setScene((Scene)bv.getContent());
        game.updateObservers();
        primaryStage.show();

        //launch the main game loop in a separate thread
        Task<Void> gameLoop = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                //reference the main loop thread to the board controller
                bv.setSelectThread(Thread.currentThread());

                //state variables
                int outcome = 0;

                try {
                    //main game loop, while no victory
                    do {
                        outcome = game.handleState();
                        System.out.println("main loop output : " + outcome);
                    }while (outcome != -2 && outcome != -1);
                }
                catch (Exception e){
                    game.displayError(e.getMessage());
                    System.exit(-1);
                }
                //system error, exit with an error
                if (outcome == -1)
                    System.exit(outcome);

                return null;
            }
        };

        //start the separate main loop thread
        new Thread(gameLoop).start();
    }

    /**
     * Start the game with a console UI
     */
    public static void startConsole(){
        //game setup
        GameController game = new GameController();
        GameConsoleView gameConsoleView = new GameConsoleView(game);

        //Board setup
        BoardConsoleView bv = new BoardConsoleView();
        game.getBoardController().attach(bv);

        //players setup
        game.setPlayer(new Player(1, "Gilles", new KeyboardSelect()));
        game.setPlayer(new Player(2, "AI", new MinimaxSelect(game.getBoardController(), 2)));

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
