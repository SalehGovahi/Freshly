package com.example.demo67;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.sql.*;

public class Card {
    @FXML
    private Button b1;

    @FXML
    private Label brand;

    @FXML
    private Label name;

    @FXML
    private Label point;
    @FXML
    private ImageView imageview;
    @FXML
    private Label price;

    private Image image;

    public void setData(product product){
        name.setText(product.getName());
        brand.setText(product.getBrand());
        point.setText(String.valueOf(product.getPoint()));
        price.setText(String.valueOf(product.getPrice()));
        String path= "File:" + product.getImage();
        image = new Image(path,170,115,false,true);
        imageview.setImage(image);
    }
    public void print(){
        System.out.println("salam");
    }
}
