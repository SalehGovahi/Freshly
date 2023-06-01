package com.example.demo67;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;

public class GridTemp implements Initializable {
    @FXML
    private AnchorPane a;

    @FXML
    private GridPane g;

    @FXML
    private ScrollPane s;
    private Connection connection;
    private Statement statement;
    private PreparedStatement statement1;
    private ResultSet resultSet;
    Image image;
    ObservableList<product> cardListData = FXCollections.observableArrayList();
    File file;

    public ObservableList<product> menuGetData() {

        ObservableList<product> listData = FXCollections.observableArrayList();
        connection=database.connectDB();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Person");
            while (resultSet.next()){
                product product= new product(resultSet.getString("name"),resultSet.getString("lastname"),resultSet.getInt("id"),resultSet.getString("Image"));
                listData.add(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }
    public void menuDisplayCard() {

        cardListData.clear();
        cardListData.addAll(menuGetData());

        int row = 0;
        int column = 0;

        g.getChildren().clear();
        g.getRowConstraints().clear();
        g.getColumnConstraints().clear();

        for (int q = 0; q < cardListData.size(); q++) {

            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("card.fxml"));
                AnchorPane pane = load.load();
                Card cardC = load.getController();
                cardC.setData(cardListData.get(q));

                if (column == 3) {
                    column = 0;
                    row += 1;
                }

                g.add(pane, column++, row);

                GridPane.setMargin(pane, new Insets(10));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuDisplayCard();
    }
}
