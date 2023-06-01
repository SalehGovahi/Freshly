package com.example.freshly;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ProductLabel {

    @FXML
    private ImageView AddToCartButton;

    @FXML
    private Label ProductName;

    @FXML
    private Label ProductPrice;

    @FXML
    private Label ProductRate;

    @FXML
    private ImageView ProductsImage;

    private String Brand = " ";

    public void setData(Product product){
        ProductName.setText(product.getName());
        ProductRate.setText(String.valueOf(product.getPoint()));
        ProductPrice.setText(String.valueOf(product.getPrice()));
        ProductsImage.setImage(product.getImage());
    }
}
