package com.example.freshly;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CartPage implements Initializable,BackToMainPage {

    @FXML
    private Button ConfirmCart;

    @FXML
    private Label FinalPriceLabel;

    @FXML
    private Label FirstPriceLabel;

    @FXML
    private TextField OffCodeTextField;

    @FXML
    private GridPane ProductsGridPane;

    @FXML
    private TextField SearchTextField;

    @FXML
    private Pane loginedPane;

    @FXML
    private AnchorPane scrollPane;

    @FXML
    private Pane unLoginedPane;

    private Connection connection;
    private ResultSet result;
    private PreparedStatement prepare;

    public static String usedDiscountCode="";
    private Alert alert;
    private boolean sw;

    private ObservableList<Product> cardOfCart = FXCollections.observableArrayList();
    private HashMap<String,String> cart = new HashMap<>();

    @FXML
    void FixSearchTextField(MouseEvent event) {

    }

    @FXML
    void openLoginPage(MouseEvent event) {

    }

    public void setProductsGridPane() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductLabelInCard.fxml"));
        Object controller = loader.getController();
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

// Add the root node to the GridPane at column 0, row 0
        ProductsGridPane.add(root, 1, 1);
    }

    public ObservableList<String> GetCartList(){
        return readCartAndDiscountCodeOfCostumerFromDatabase.cartOfCostumer(MainPage.customer.getUsername());
    }
    private ObservableList<Product> IdToProduct(ObservableList<String> GetCartList){
        ObservableList<String> data = GetCartList;
        ObservableList<Product> products = FXCollections.observableArrayList();
        if (!data.isEmpty()) {
            for (String datum : data) {
                String[] infos = datum.split(separator());
                cart.put(infos[0], infos[1]);
            }
        }

        String sql = "SELECT * FROM product";
        connection = database.connectDB();

        try {
            if (connection != null) {
                prepare = connection.prepareStatement(sql);
            }
            result = prepare.executeQuery();

            Product prod;

            while (result.next()) {
                String productid = result.getString("productid");
                for (String cur:
                        cart.keySet()) {
                    if (cur.equals(productid)){
                        prod = new Product(result.getString("name"),
                                result.getString("brand"),
                                Double.parseDouble(result.getString("point")),
                                Integer.parseInt(result.getString("numberofpoints")),
                                Integer.parseInt(result.getString("productid")),
                                result.getString("information"),
                                Double.parseDouble(result.getString("price")),
                                result.getString("type"),
                                result.getString("image"),
                                Integer.parseInt(result.getString("stock")),
                                readCommentOfProductFromDataBase.commentOfProduct(productid),
                                result.getString("productSeller"));
                        products.add(prod);
                    }
                }



            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }
    private static String separator(){
        return "_";
    }

    public void CartDisplay(boolean firstTime){

        if (firstTime) {
            cardOfCart.clear();
            cardOfCart.addAll(IdToProduct(GetCartList()));
        }


        int row = 0;
        int column = 0;

        ProductsGridPane.getChildren().clear();
        ProductsGridPane.getRowConstraints().clear();
        ProductsGridPane.getColumnConstraints().clear();

        for (int q = 0; q < cardOfCart.size(); q++) {

            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("ProductLabelInCard.fxml"));
                Pane pane = load.load();
                ProductLabelInCard Pl = load.getController();
                Pl.setData(cardOfCart.get(q),cart.get(String.valueOf(cardOfCart.get(q).getProductId())));

                if (column == 1) {
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
    private void calculateCost(){
        double sum = 0;
        for (Product product : cardOfCart) {
            sum += (product.getPrice()*Integer.parseInt(cart.get(String.valueOf(product.getProductId()))));
        }
        FirstPriceLabel.setText(String.valueOf(sum));
        FinalPriceLabel.setText(String.valueOf(sum));
    }
    public void finalBuy(ActionEvent event){
        System.out.println(FinalPriceLabel.getText());
        if (MainPage.customer.getWallet() >= Double.parseDouble(FinalPriceLabel.getText())){
            int wallet = (int)(MainPage.customer.getWallet()-Double.parseDouble(FinalPriceLabel.getText()));
            MainPage.customer.setWallet(wallet);
            connection=database.connectDB();
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate("UPDATE costumer SET wallet = '"+wallet+"'WHERE username = '"+MainPage.customer.getUsername()+"'");
                statement=connection.createStatement();
                result=statement.executeQuery("SELECT cart , history FROM costumer WHERE username = '"+MainPage.customer.getUsername()+"'");
                String history = "";
                String cart = "";
                if(result.next()){
                    history = result.getString("history");
                    history = result.getString("cart") + history;
                }
                cart = "8585_1@6969_1";
                statement = connection.createStatement();
                statement.executeUpdate("UPDATE costumer SET cart='"+cart+"' , history = '"+history+"'  WHERE username = '"+MainPage.customer.getUsername()+"'");
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information message");
                alert.setHeaderText(null);
                alert.setContentText("successfully purchased");
                alert.showAndWait();
                cardOfCart.clear();
                CartDisplay(false);
                FinalPriceLabel.setText("0");
                FirstPriceLabel.setText("0");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }
    }

    @FXML
    void searching(KeyEvent event) {
        if(String.valueOf(event.getCode()).equals("ENTER")){
            ShowingProducts.name = SearchTextField.getText();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("ShowingProducts.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("Login Page");
                ConfirmCart.getScene().getWindow().hide();

                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void checkDiscountCode(MouseEvent mouseEvent){
        if (!OffCodeTextField.getText().isEmpty()) {
            ObservableList<String> discountCodes = readCartAndDiscountCodeOfCostumerFromDatabase.discountCodeOfCostumer(MainPage.customer.getUsername());
            usedDiscountCode = OffCodeTextField.getText();
            if (discountCodes.contains(usedDiscountCode)&&(!sw)){
                if (Double.parseDouble(FinalPriceLabel.getText()) >= 200_000){
                    sw=true;
                    FinalPriceLabel.setText(String.valueOf(Double.parseDouble(FinalPriceLabel.getText()) - 50000));
                }
                else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("حداقل مبلغ کل خرید برای استفاده از کد دویست هزار تومان میباشد");
                    alert.showAndWait();
                }
            }else {
                if (sw) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Used Discount Code");
                    alert.showAndWait();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Discount Code");
                    alert.showAndWait();
                }
            }
        }
    }

    public void returnClicked(MouseEvent event){
        Return();
    }

    @Override
    public void Return() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setTitle("Main Page");
            loginedPane.getScene().getWindow().hide();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setProductsGridPane();
            CartDisplay(true);
            loginedPane.setVisible(true);
            unLoginedPane.setVisible(false);
            calculateCost();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
