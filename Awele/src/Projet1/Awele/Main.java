package Projet1.Awele;

import javafx.application.Application;
import Controllers.GameController;
import Models.Player;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Views.KeyboardSelect;
import Views.GameConsoleView;
import Views.MinimaxSelect;
import Views.BoardJFXView;

public class Main extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //game setup
        GameController game = new GameController();
        GameConsoleView gameConsoleView = new GameConsoleView(game);

        //Board setup
        BoardJFXView bv = new BoardJFXView();
        game.getBoardController().attach(bv);

        //players setup
        game.setPlayer(new Player(1, "Gilles", new KeyboardSelect()));
        game.setPlayer(new Player(2, "AI", new MinimaxSelect(game.getBoardController(), 2)));

        //primary Stage setup
        primaryStage.setTitle("POO - Awele");
        primaryStage.setScene((Scene)bv.getContent());
        primaryStage.show();
/*
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
*/
    }
}
