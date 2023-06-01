package com.example.freshly;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPage implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrollPane.fitToWidthProperty();
        scrollPane.fitToHeightProperty();
    }
}
