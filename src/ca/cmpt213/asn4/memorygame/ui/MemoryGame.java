package ca.cmpt213.asn4.memorygame.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The MemoryGame class is the main class for the memory-matching game.
 * It initializes the game and sets up the user interface.
 */
public class MemoryGame extends Application {
    @Override
    public void start(Stage primaryStage) {
        GameUI gameUI = new GameUI();
        Scene scene = new Scene(gameUI.getRoot(), 600, 600);

        primaryStage.setTitle("Memory Matching Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
