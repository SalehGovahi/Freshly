package com.example.freshly;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Intro implements Initializable {

    @FXML
    private Label temp;

    private Timeline timeline;
    private int count = 0;

    public void time() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            count++;
            System.out.println(count);
            if (count == 8) {
                timeline.stop();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("Login Page");
                temp.getScene().getWindow().hide();

                stage.setScene(scene);
                stage.show();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        time();
    }
}