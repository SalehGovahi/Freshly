package com.example.freshly;

import com.example.freshly.Exception.NoProductInTheDatabase;
import com.example.freshly.Exception.OutOfNumberToDecrease;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ProductLabelInCard implements Initializable , CreatingCartOfCostumerAndUpdateDatabase{

    @FXML
    private ImageView ImageProduct;

    @FXML
    private Pane PaneAfterClickingBuyingButton;

    @FXML
    private Label PriceLabel;

    @FXML
    private Label ProductLabel;

    @FXML
    private ImageView minusProduct;

    @FXML
    private ImageView plusProduct;

    @FXML
    private Label CountOfProduct;

    private Product product;
    private Image image;

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public void setData(Product product,String countOfProduct){
        this.product=product;
        ProductLabel.setText(product.getName());
        PriceLabel.setText(String.valueOf(product.getPrice()));
        String path = "File:" + product.getImage();
        image = new Image(path, 246, 223, false, true);
        ImageProduct.setImage(image);
        CountOfProduct.setText(countOfProduct);
    }
    public void plusProduct(MouseEvent event){
        CountOfProduct.setText(String.valueOf(Integer.parseInt(CountOfProduct.getText())+1));
        String cur = createCart(String.valueOf(product.getProductId()), 1);
        System.out.println(cur);
        addToDataBase(cur);
        Parent root = null;
        try {
            PriceLabel.getScene().getWindow().hide();
            root = FXMLLoader.load(getClass().getResource("CartPage.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setTitle("Login Page");


            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void minusProduct(MouseEvent event){
        int t = Integer.parseInt(CountOfProduct.getText());
        t--;
        try {
            DeleteProductOfDataBase.deleteAProductFromDatabase(MainPage.customer.getUsername(), String.valueOf(product.getProductId()), 1);
            CountOfProduct.setText(String.valueOf(Integer.parseInt(CountOfProduct.getText())-1));
            Parent root = null;
            try {
                PriceLabel.getScene().getWindow().hide();
                root = FXMLLoader.load(getClass().getResource("CartPage.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("Login Page");


                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (NoProductInTheDatabase e) {
            throw new RuntimeException(e);
        } catch (OutOfNumberToDecrease e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public String createCart(String prodId, int count) {
        ObservableList<String> data = readCartAndDiscountCodeOfCostumerFromDatabase.cartOfCostumer(MainPage.customer.getUsername());
        HashMap<String,String> cart = new HashMap<>();
        for (String datum : data) {
            String[] infos = datum.split(separator());
            cart.put(infos[0], infos[1]);
        }
        int sw = 0;
        for (String id:
                cart.keySet()) {
            if (prodId.equals(id)){
                sw = 1;
                int num = Integer.parseInt(cart.get(prodId));
                num++;
                cart.put(id,String.valueOf(num));
                break;
            }
        }
        data.clear();
        for (String id:
                cart.keySet()) {
            data.add(id+"_"+cart.get(id));
        }
        if (sw == 0) {
            data.add(prodId + "_" + String.valueOf(count));
        }
        StringBuilder result = new StringBuilder(data.get(0));
        for (int i = 1; i < data.size(); i++) {
            result.append("@").append(data.get(i));
        }
        return result.toString();
    }
    private static String separator(){
        return "_";
    }

    @Override
    public void addToDataBase(String cart) {
        connection = database.connectDB();
        try {
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE costumer SET cart = '"+cart+"' WHERE Username = '"+MainPage.customer.getUsername()+"'");
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
