package com.example.freshly;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductLabel implements Initializable , LoginChecker , CreatingCartOfCostumerAndUpdateDatabase {

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

    @FXML
    private Pane productPane;

    private String Brand = " ";
    private Image image;
    private Product product;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private Alert alert;

    public void setData(Product product){
        this.product=product;
        ProductName.setText(product.getName());
        ProductRate.setText(String.valueOf(product.getPoint()));
        ProductPrice.setText(String.valueOf(product.getPrice()));
        String path = "File:" + product.getImage();
        image = new Image(path, 246, 223, false, true);
        ProductsImage.setImage(image);
    }
    public void addToCart(MouseEvent event){
        /*System.out.println("product that you chose : "+product.getName());
        System.out.println(product.getName());
        System.out.println(product.getBrand());
        System.out.println(product.getProductId());
        System.out.println(product.getInformation());
        System.out.println(product.getPrice());
        System.out.println(product.getPoint());
        System.out.println(product.getType());
        System.out.println(product.getImage());
        System.out.println(product.getUniqueProperties());
        for (int i = 0; i < product.getComments().size(); i++) {
            System.out.print(product.getComments().get(i)+" ");
        }
        System.out.println();
        System.out.println(product.getProductSeller());
        System.out.println(product.getStock());
        System.out.println();
        System.out.println();*/
        if (MainPage.customer == null){
            try {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("You didnt Login in to your account , Do you want to login ?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    AddToCartButton.getScene().getWindow().hide();

                    // LINK YOUR LOGIN FORM AND SHOW IT
                    Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.setTitle("Login Page");
                    /*stage.setOnHiding(new EventHandler<WindowEvent>() {
                        public void handle(WindowEvent we) {
                            stage.hide();
                            // نمایش پنجره قبلی
                            stage.getOwner().showingProperty();
                        }
                    });*/


                    stage.setScene(scene);
                    stage.show();

                }
                if (option.get().equals(ButtonType.NO)){
                    alert.close();
                }
                /*
*/
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            String cur = createCart(String.valueOf(product.getProductId()), 1);
            System.out.println(cur);
            addToDataBase(cur);
            System.out.println("this prod add to your cart");
        }
    }
    public void addCart(ActionEvent event){
        /*System.out.println("product that you chose : "+product.getName());
        System.out.println(product.getName());
        System.out.println(product.getBrand());
        System.out.println(product.getProductId());
        System.out.println(product.getInformation());
        System.out.println(product.getPrice());
        System.out.println(product.getPoint());
        System.out.println(product.getType());
        System.out.println(product.getImage());
        System.out.println(product.getUniqueProperties());
        for (int i = 0; i < product.getComments().size(); i++) {
            System.out.print(product.getComments().get(i)+" ");
        }
        System.out.println();
        System.out.println(product.getProductSeller());
        System.out.println(product.getStock());
        System.out.println();
        System.out.println();*/
        if (MainPage.customer == null){
            try {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("You didnt Login in to your account , Do you want to login ?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    AddToCartButton.getScene().getWindow().hide();

                    // LINK YOUR LOGIN FORM AND SHOW IT
                    Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.setTitle("Login Page");
                    /*stage.setOnHiding(new EventHandler<WindowEvent>() {
                        public void handle(WindowEvent we) {
                            stage.hide();
                            // نمایش پنجره قبلی
                            stage.getOwner().showingProperty();
                        }
                    });*/


                    stage.setScene(scene);
                    stage.show();

                }
                if (option.get().equals(ButtonType.NO)){
                    alert.close();
                }
                /*
                 */
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            String cur = createCart(String.valueOf(product.getProductId()), 1);
            System.out.println(cur);
            addToDataBase(cur);
            System.out.println("this prod add to your cart");
        }
    }

    public void openProductInformation(MouseEvent event){
        //ProductPage productPage = new ProductPage(this.product);
        ProductPage.product=product;
        ProductRate.getScene().getWindow().hide();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductPage.fxml"));
            Parent root = loader.load();

            Stage primaryStage = new Stage();

            // Create a new scene with the loaded FXML file as the root node
            Scene scene = new Scene(root);

            // Set the new scene as the root of the primary stage
            primaryStage.setScene(scene);

            // Show the primary stage with the new scene
            primaryStage.show();


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public boolean isLogin() {
        return MainPage.customer!=null;
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

    private static String separator(){
        return "_";
    }
}
