package Projet1.Awele;

import Views.*;
import javafx.application.Application;
import Controllers.GameController;
import Models.Player;
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
        GameConsoleView gameConsoleView = new GameConsoleView(game);

        //Board setup
        BoardJFXView bv = new BoardJFXView();
        game.getBoardController().attach(bv);

        //players setup
        game.setPlayer(new Player(1, "Gilles", new JFXSelect(game.getBoardController(), 1)));
        game.setPlayer(new Player(2, "AI", new MinimaxSelect(game.getBoardController(), 2)));

        //primary Stage setup
        primaryStage.setTitle("POO - Awele");
        primaryStage.setScene((Scene)bv.getContent());
        primaryStage.show();
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
                outcome = game.handleState(outcome);
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
