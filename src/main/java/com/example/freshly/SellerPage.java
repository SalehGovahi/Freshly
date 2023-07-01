package com.example.freshly;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//Todo جدول در کلاس باید فیلد هایش متناسب شود &&&&&& نحوه حذف کردن باید تفاوت کند

public class SellerPage implements Initializable,LoginChecker {

    @FXML
    private Button AddButton;

    @FXML
    private Button AddButton1;

    @FXML
    private ImageView AddPaneImage;

    @FXML
    private ImageView AddPaneImage1;

    @FXML
    private TextField BrandTextfield;

    @FXML
    private TextField BrandTextfield1;

    @FXML
    private ComboBox<?> ChooseTypeComboBox;

    @FXML
    private ComboBox<?> ChooseTypeComboBox1;

    @FXML
    private DatePicker ExpertDatePicker;

    @FXML
    private DatePicker ExpertDatePicker1;

    @FXML
    private Label ExpertDateText;

    @FXML
    private Label ExpertDateText1;

    @FXML
    private Label HealthValueText;

    @FXML
    private Label HealthValueText1;

    @FXML
    private TextField HealthValueTextField;

    @FXML
    private TextField HealthValueTextField1;

    @FXML
    private TableColumn<Product, String> IdCol;

    @FXML
    private Pane MainPane;

    @FXML
    private TableColumn<Product, String> NameCol;

    @FXML
    private TextField NameTextfield;

    @FXML
    private TextField NameTextfield1;

    @FXML
    private TableColumn<Product, String> PriceCol;

    @FXML
    private TextField PriceTextfield;

    @FXML
    private TextField PriceTextfield1;

    @FXML
    private TableView<Product> ProductsTable;

    @FXML
    private TableColumn<Product, String> StockCol;

    @FXML
    private TextField StockTextfield;

    @FXML
    private TextField StockTextfield1;

    @FXML
    private TableColumn<Product, String> TypeCol;

    @FXML
    private Label WeightText;

    @FXML
    private Label WeightText1;

    @FXML
    private TextField WeightTextField;

    @FXML
    private TextField WeightTextField1;

    @FXML
    private Label sellerUsername;

    private Alert alert;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private String path;
    public static Seller seller = null;
    String pattern = "^[0-9]+$";

    public void SetChooseRole() {
        String[] RoleList = {"کالاهای اساسی و خواروبار", "مواد پروتئینی", "نوشیدنی", "تنقلات", "لبنیات", "سایر"};
        List<String> ItemList = new ArrayList<>();

        for (String data : RoleList) {
            ItemList.add(data);
        }

        ObservableList DataList = FXCollections.observableArrayList(ItemList);
        ChooseTypeComboBox.setItems(DataList);
    }

    public void showRelatedField() {
        if (ChooseTypeComboBox.getValue().equals("لبنیات") || ChooseTypeComboBox.getValue().equals("مواد پروتئینی")) {
            ExpertDateText.setVisible(true);
            ExpertDatePicker.setVisible(true);
            HealthValueText.setVisible(false);
            HealthValueTextField.setVisible(false);
            WeightText.setVisible(false);
            WeightTextField.setVisible(false);
        }
        if (ChooseTypeComboBox.getValue().equals("تنقلات")) {
            HealthValueText.setVisible(true);
            HealthValueTextField.setVisible(true);
            WeightText.setVisible(false);
            WeightTextField.setVisible(false);
            ExpertDatePicker.setVisible(false);
            ExpertDateText.setVisible(false);
        }
        if (ChooseTypeComboBox.getValue().equals("کالاهای اساسی و خواروبار")) {
            WeightText.setVisible(true);
            WeightTextField.setVisible(true);
            ExpertDateText.setVisible(false);
            ExpertDatePicker.setVisible(false);
            HealthValueTextField.setVisible(false);
            HealthValueText.setVisible(false);
        }
        if (ChooseTypeComboBox.getValue().equals("نوشیدنی")) {
            WeightText.setVisible(false);
            WeightTextField.setVisible(false);
            ExpertDateText.setVisible(false);
            ExpertDatePicker.setVisible(false);
            HealthValueTextField.setVisible(false);
            HealthValueText.setVisible(false);
        }
        if (ChooseTypeComboBox.getValue().equals("سایر")) {
            WeightText.setVisible(false);
            WeightTextField.setVisible(false);
            ExpertDateText.setVisible(false);
            ExpertDatePicker.setVisible(false);
            HealthValueTextField.setVisible(false);
            HealthValueText.setVisible(false);
        }
    }

    public void selectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image file", "*png", "*jpg"));

        File file = fileChooser.showOpenDialog(MainPane.getScene().getWindow());
        path = file.getAbsolutePath();
        if (file != null) {
            Image image = new Image(file.toURI().toString(), 110, 121, false, true);
            AddPaneImage.setImage(image);
        }
    }
    private void clearTextFields(){
        NameTextfield.setText("");
        BrandTextfield.setText("");
        PriceTextfield.setText("");
        PriceTextfield.setText("");
        StockTextfield.setText("");
        //ChooseTypeComboBox.getSelectionModel().clearSelection();
        path="";
        AddPaneImage.setImage(null);
    }

    public void addBtn() {
        if (checkEmptyFields()) {
            if (stockAndPriceRegex(PriceTextfield.getText()) || stockAndPriceRegex(StockTextfield.getText())) {
                AddProductToDataBase();
                clearTextFields();
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Incorrect Data");
                alert.showAndWait();
            }
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }
    }

    private boolean checkEmptyFields() {
        boolean result;
        if (NameTextfield.getText().isEmpty() ||
                BrandTextfield.getText().isEmpty() ||
                PriceTextfield.getText().isEmpty() ||
                StockTextfield.getText().isEmpty() ||
                ChooseTypeComboBox.getSelectionModel().getSelectedItem() == null) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }

    private boolean stockAndPriceRegex(String string) {
        Pattern regex = Pattern.compile(pattern);

        Matcher matcher = regex.matcher(string);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /***/
    private boolean checkProductExist() {
        boolean result = false;
        connection = database.connectDB();
        try {
            if (connection != null) {
                statement = connection.prepareStatement("SELECT productSeller , name FROM Product WHERE productSeller = '" + seller.getUsername() + "' AND name='" + NameTextfield.getText() + "'");
            }
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = true;
            } else {
                result = false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    /***/
    private void AddProductToDataBase() {
        connection = database.connectDB();
        try {
            if (connection != null) {
                statement = connection.prepareStatement("INSERT INTO product (name,productid,price,point,numberofpoints,brand,uniqueproperties,image,information,type,stock,productSeller) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
            }
            statement.setString(1, NameTextfield.getText());
            String id = String.valueOf(MakeProductId.productIdMaker());
            statement.setString(2, id);
            statement.setString(3, PriceTextfield.getText());
            statement.setString(4, String.valueOf(0));
            statement.setString(5, String.valueOf(0));
            statement.setString(6, BrandTextfield.getText());
            statement.setString(7, "");
            String nativePath = ImageCopy.copyfile(path, id);
            statement.setString(8, nativePath);
            statement.setString(9, "");
            if (ChooseTypeComboBox.getSelectionModel().getSelectedItem() == "کالاهای اساسی و خواروبار") {
                statement.setString(10, "groceries");
            }
            if (ChooseTypeComboBox.getSelectionModel().getSelectedItem() == "مواد پروتئینی"){
                statement.setString(10, "proteins");
            }
            if (ChooseTypeComboBox.getSelectionModel().getSelectedItem() == "نوشیدنی"){
                statement.setString(10, "drinks");
            }
            if (ChooseTypeComboBox.getSelectionModel().getSelectedItem() == "تنقلات"){
                statement.setString(10, "nuts");
            }
            if (ChooseTypeComboBox.getSelectionModel().getSelectedItem() == "لبنیات"){
                statement.setString(10, "dairy");
            }
            if (ChooseTypeComboBox.getSelectionModel().getSelectedItem() == "سایر"){
                statement.setString(10, "other");
            }
            statement.setInt(11, Integer.parseInt(StockTextfield.getText()));
            statement.setString(12, seller.getUsername());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteButton() {
        if (checkEmptyFields()) {
            if (stockAndPriceRegex(PriceTextfield.getText()) || stockAndPriceRegex(StockTextfield.getText())) {
                deletingProductFromDatabase();
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Incorrect Data");
                alert.showAndWait();
            }
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }
    }

    private void deletingProductFromDatabase() {
        connection = database.connectDB();
        try {
            statement = connection.prepareStatement("DELETE FROM Product WHERE name='" + NameTextfield.getText() + "' AND productseller='" + seller.getUsername() + "'");
            statement.executeUpdate();
            System.out.println("delete successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateButton(){
        if (checkEmptyFields()){
            if (checkProductExist()){
                AddProductToDataBase();
            }else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("There is no product by this information");
                alert.showAndWait();
            }
        }else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }
    }

    public void setProductsTable() throws IOException {
        ShowingProducts showingProducts = new ShowingProducts();
        ObservableList<Product> productsList = showingProducts.GetProductsList();
        System.out.println(productsList);
        IdCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        StockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        ProductsTable.setItems(productsList);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setProductsTable();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SetChooseRole();
    }

    @Override
    public boolean isLogin() {
        return seller != null;
    }
    public void changeSellerUsernameTextField(){
        if (isLogin()){
            sellerUsername.setText(seller.getUsername());
        }else {
            sellerUsername.setText("");
        }
    }
}
