package com.example.freshly;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class ShowingProducts implements Initializable,LoginChecker,BackToMainPage {

    @FXML
    private ComboBox<?> TypeComboBoxFilter;

    @FXML
    private ComboBox<String> BrandComboBox;

    @FXML
    private Line line;

    @FXML
    private GridPane ProductsGridPane;

    @FXML
    private TextField SearchTextField;

    @FXML
    private Pane unLoginedPane;

    @FXML
    private Pane loginedPane;

    @FXML
    private Label MaxPriceButton;

    @FXML
    private Label MaxRateButton;

    @FXML
    private Label MinPriceButton;

    @FXML
    private Label MinRateButton;

    @FXML
    private Button usefilter;

    @FXML
    private ImageView Cart;


    @FXML
    private Button cancelFilter;

    private Connection connection;
    private PreparedStatement prepare;
    private ResultSet result;


    protected ObservableList<Product> cardListData = FXCollections.observableArrayList();
    protected ObservableList<Product> cardListDataTemp = FXCollections.observableArrayList();
    protected ObservableList<Product> allProducts = FXCollections.observableArrayList();


    public static String type="";
    public static String name = "";


    private Alert alert;


    private void SetBrandComboBox(){
        List<String> list = ReadBrandsOfProductFromDataBase.Brands();
        ObservableList<String> allBrands = FXCollections.observableArrayList(list);
        BrandComboBox.setItems(allBrands);
    }

    private void SetTypeComboBox() {
        String[] RoleList = {"کالاهای اساسی و خواروبار", "مواد پروتئینی", "نوشیدنی", "تنقلات", "لبنیات", "سایر"};
        List<String> ItemList = new ArrayList<>();

        for (String data : RoleList) {
            ItemList.add(data);
        }

        ObservableList DataList = FXCollections.observableArrayList(ItemList);
        TypeComboBoxFilter.setItems(DataList);
    }

    public ObservableList<Product> GetProductsList() throws IOException {
        String sql = "SELECT * FROM product";

        ObservableList<Product> listData = FXCollections.observableArrayList();
        connection = database.connectDB();

        try {
            if (connection != null) {
                prepare = connection.prepareStatement(sql);
            }
            result = prepare.executeQuery();

            Product prod;

            while (result.next()) {
                String productid = result.getString("productid");
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

                listData.add(prod);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }
    public  void search(String name) throws IOException {
        int size=cardListDataTemp.size();
        ObservableList<Product> cardListDataTemp2 = FXCollections.observableArrayList();
        for (int i = 0; i < size; i++) {
            if(cardListDataTemp.get(i).getName().equals(name)){
                cardListDataTemp2.add(cardListDataTemp.get(i));
            }
        }
        cardListDataTemp=cardListDataTemp2;
    }

    public void menuDisplayCard(boolean firstTime) throws IOException {
        if (firstTime) {
            cardListData.clear();
            cardListData.addAll(allProducts);
            if (!type.equals("")){
                switch (type) {
                    case "کالاهای اساسی و خواروبار" -> filterType("groceries");
                    case "نوشیدنی" -> filterType("drinks");
                    case "مواد پروتئینی" -> filterType("proteins");
                    case "تنقلات" -> filterType("nuts");
                    case "لبنیات" -> filterType("dairy");
                    case "سایر" -> filterType("other");
                }
                menuDisplayCard(false);
            }
            if (!name.equals("")){
                search(name);
                menuDisplayCard(false);
            }
        }

        else {
            cardListData.clear();
            cardListData.addAll(cardListDataTemp);
        }
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

                GridPane.setMargin(pane, new Insets(1));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void FixSearchTextField(MouseEvent event){
        SearchTextField.requestFocus();
    }

    public void openLoginPage(MouseEvent mouseEvent){
        System.out.println("going to login page");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setTitle("Login Page");
            loginedPane.getScene().getWindow().hide();

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void UseFilter(ActionEvent event) throws IOException {
        cardListDataTemp.removeAll();
        cardListDataTemp = allProducts;
        if (BrandComboBox.getSelectionModel().getSelectedItem() != null) {
            String brand = (String) BrandComboBox.getSelectionModel().getSelectedItem();
            filterBrand(brand);
        }
        if (TypeComboBoxFilter.getSelectionModel().getSelectedItem() != null ) {
            switch ((String) TypeComboBoxFilter.getSelectionModel().getSelectedItem()) {
                case "کالاهای اساسی و خواروبار" -> filterType("groceries");
                case "نوشیدنی" -> filterType("drinks");
                case "مواد پروتئینی" -> filterType("proteins");
                case "تنقلات" -> filterType("nuts");
                case "لبنیات" -> filterType("dairy");
                case "سایر" -> filterType("other");
            }
        }
        menuDisplayCard(false);

    }
    public void CancelFilter(ActionEvent event) throws IOException {
       cancel();
    }
    public void cancel() throws IOException {
        type="";
        name="";
        TypeComboBoxFilter.setValue(null);
        BrandComboBox.setValue(null);
        cardListDataTemp.clear();
        cardListDataTemp.addAll(allProducts);
        menuDisplayCard(true);
    }



    @Override
    public boolean isLogin() {
        return MainPage.customer != null;
    }
    public void changeHeaderIcon(){
        if (isLogin()){
            loginedPane.setVisible(true);
            unLoginedPane.setVisible(false);
        }else if (!isLogin()){
            loginedPane.setVisible(false);
            unLoginedPane.setVisible(true);
        }
    }
    public void reversingAllProducts(){
        ObservableList<Product> allProducts2 = FXCollections.observableArrayList();
        for (int i = allProducts.size()-1; i >=0 ; i--) {
            allProducts2.add(allProducts.get(i));

        }
        allProducts=allProducts2;
    }
    public void sortingByPriceMin(MouseEvent mouseEvent) throws IOException {
        //cardListDataTemp.removeAll();
        //cardListDataTemp = allProducts;

        sortedByPrice(false);
        menuDisplayCard(false);
    }
    public void sortingByPriceMax(MouseEvent mouseEvent) throws IOException {
        //cardListDataTemp.removeAll();
        //cardListDataTemp = allProducts;
        sortedByPrice(true);
        menuDisplayCard(false);
    }
    public void sortingByPointMin() throws IOException {
        //cardListDataTemp.removeAll();
        //cardListDataTemp = allProducts;
        sortedByPrice(true);
        sortedByPoint(false);
        menuDisplayCard(false);
    }
    public void sortingByPointMax() throws IOException {
        //cardListDataTemp.removeAll();
        //cardListDataTemp = allProducts;
        sortedByPrice(true);
        sortedByPoint(true);
        menuDisplayCard(false);
    }
    public void sortedByPrice(boolean ascending){
        if(!ascending)
            cardListDataTemp=cardListDataTemp.sorted();
        else {
            cardListDataTemp=cardListDataTemp.sorted();
            ObservableList<Product> cardListDataTemp2 = FXCollections.observableArrayList();
            for (int i = cardListDataTemp.size()-1; i >=0; i--) {
                cardListDataTemp2.add(cardListDataTemp.get(i));
            }
            cardListDataTemp=cardListDataTemp2;
        }
    }

    public void filterBrand(String brand){
        int size=cardListDataTemp.size();
        ObservableList<Product> cardListDataTemp2 = FXCollections.observableArrayList();
        for (int i = 0; i < size; i++) {
            if(cardListDataTemp.get(i).getBrand().equals(brand)){
                cardListDataTemp2.add(cardListDataTemp.get(i));
            }
        }
        cardListDataTemp=cardListDataTemp2;
    }
    public void filterType(String type){
        int size=cardListDataTemp.size();
        ObservableList<Product> cardListDataTemp2 = FXCollections.observableArrayList();
        for (int i = 0; i < size; i++) {
            if(cardListDataTemp.get(i).getType().equals(type)){
                cardListDataTemp2.add(cardListDataTemp.get(i));
            }
        }
        cardListDataTemp=cardListDataTemp2;
    }
    public void sortedByPoint(boolean ascending){
        Comparator<Product> pointComparator = new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getPoint()<p2.getPoint()? -1:(p1.getPoint()==p2.getPoint()?0 : +1);
            }
        };
        if(!ascending)
            Collections.sort(cardListDataTemp,pointComparator);
        else {
            Collections.sort(cardListDataTemp,pointComparator);
            ObservableList<Product> cardListDataTemp2 = FXCollections.observableArrayList();
            for (int i = cardListDataTemp.size()-1; i >=0; i--) {
                cardListDataTemp2.add(cardListDataTemp.get(i));
            }
            cardListDataTemp=cardListDataTemp2;
        }
    }
    @FXML
    void searching(KeyEvent event) {
        if (String.valueOf(event.getCode()).equals("ENTER")) {
            ShowingProducts.name = SearchTextField.getText();
            cardListDataTemp.clear();
            cardListDataTemp.addAll(allProducts);
            try {

                menuDisplayCard(true);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @FXML
    void openCart(MouseEvent event) {
        if (MainPage.customer == null){
            try {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("You didnt Login in to your account , Do you want to login ?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    loginedPane.getScene().getWindow().hide();

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
            Parent root = null;
            try {
                loginedPane.getScene().getWindow().hide();
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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TypeComboBoxFilter.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        TypeComboBoxFilter.getEditor().setStyle("-fx-alignment: baseline-right");
        try {
            allProducts.addAll(GetProductsList());
            cardListDataTemp=allProducts;
            reversingAllProducts();
            menuDisplayCard(true);
            SetBrandComboBox();
            SetTypeComboBox();
        } catch (IOException e) {
            e.printStackTrace();
        }
        changeHeaderIcon();
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
}
