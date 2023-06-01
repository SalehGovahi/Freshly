package com.example.freshly;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FreshlyApplication extends Application {
    @Override
<<<<<<< Updated upstream
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FreshlyApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
=======
    public void start(Stage primaryStage) throws IOException {
        // Load the FXML file for the new scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SellerPage.fxml"));
        Parent root = loader.load();

        // Create a new scene with the loaded FXML file as the root node
        Scene scene = new Scene(root);

        // Set the new scene as the root of the primary stage
        primaryStage.setScene(scene);

        // Show the primary stage with the new scene
        primaryStage.show();
>>>>>>> Stashed changes
    }

    public static void main(String[] args) {
        launch();
    }
}