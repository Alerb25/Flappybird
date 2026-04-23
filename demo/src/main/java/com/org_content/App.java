package com.org_content;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 * Flappy bird just for fun 
 */
public class App extends Application {

    //first the app measures
    public static final int WIDTH = 480;
    public static final int HEIGHT = 640;
    
    @Override
    public void start(Stage stage) {
        GamePane game = new GamePane(); 
        Scene scene = new Scene(game, WIDTH, HEIGHT);
        
        stage.setTitle("Flappy App");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        game.requestFocus();

    }

        public static void main(String[] args) {
        launch(args);
    }
}