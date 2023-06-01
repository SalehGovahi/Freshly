package com.example.freshly;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.w3c.dom.events.Event;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class ShowingProducts implements Initializable {

    @FXML
    private ComboBox<?> TypeComboBoxFilter;

    @FXML
    private Line line;

    @FXML
    private GridPane ProductsGridPane;

    @FXML
    private TextField SearchTextField;

    ObservableList<Product> cardListData = FXCollections.observableArrayList();

    public ObservableList<Product> GetProductsList() throws IOException {
        ObservableList<Product> listData = FXCollections.observableArrayList();

        File file=new File("E:\\Freshly\\Assets\\neshanLogoFooter.png");
        byte[] imagedata = Files.readAllBytes(file.toPath());
        Image image = new Image(new ByteArrayInputStream(imagedata));

        try {
            Product prod = new Product("chips","tan",10,image,10);
            Product product3 = new Product("peste","tan",20,image,20);
            listData.add(prod);
            listData.add(product3);
            Product product1 = new Product("badam","tan",30,image,30);
            Product product5 = new Product("tokhme","tan",40,image,40);
            listData.add(product1);
            listData.add(product5);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    public void menuDisplayCard() throws IOException {

        cardListData.clear();
        cardListData.addAll(GetProductsList());

        int row = 0;
        int column = 0;

        ProductsGridPane.getChildren().clear();
        ProductsGridPane.getRowConstraints().clear();
        ProductsGridPane.getColumnConstraints().clear();

        for (int q = 0; q < cardListData.size(); q++) {

            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("ProductLabel.fxml"));
                Pane pane = load.load();
                ProductLabel Pl = load.getController();
                Pl.setData(cardListData.get(q));

                if (column == 3) {
                    column = 0;
                    row += 1;
                }

                ProductsGridPane.add(pane, column++, row);

                GridPane.setMargin(pane, new Insets(0));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void FixSearchTextField(MouseEvent event){
        SearchTextField.requestFocus();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TypeComboBoxFilter.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        TypeComboBoxFilter.getEditor().setStyle("-fx-alignment: baseline-right");
        try {
            menuDisplayCard();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
