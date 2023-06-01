package com.example.freshly;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SellerPage implements Initializable {

    @FXML
    private ComboBox<?> ChooseTypeComboBox;

    @FXML
    private DatePicker ExpertDatePicker;

    @FXML
    private Label ExpertDateText;

    @FXML
    private Label HealthValueText;

    @FXML
    private TextField HealthValueTextField;

    @FXML
    private TextField NameTextfield;

    @FXML
    private TextField BrandTextfield;

    @FXML
    private TextField PriceTextfield;

    @FXML
    private TextField StockTextfield;

    @FXML
    private Label WeightText;

    @FXML
    private TextField WeightTextField;

    @FXML
    private Pane MainPane;

    @FXML
    private ImageView AddPaneImage;

    private Alert alert;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private String path;
    public static Seller seller=null;

    public void SetChooseRole(){
        String[]RoleList = { "کالاهای اساسی و خواروبار", "مواد پروتئینی" ,  "نوشیدنی" , "تنقلات","لبنیات", "سایر"};
        List<String> ItemList = new ArrayList<>();

        for (String data : RoleList){
            ItemList.add(data);
        }

        ObservableList DataList= FXCollections.observableArrayList(ItemList);
        ChooseTypeComboBox.setItems(DataList);
    }

    public void showRelatedField(){
        if (ChooseTypeComboBox.getValue().equals("لبنیات") || ChooseTypeComboBox.getValue().equals("مواد پروتئینی")){
            ExpertDateText.setVisible(true);
            ExpertDatePicker.setVisible(true);
            HealthValueText.setVisible(false);
            HealthValueTextField.setVisible(false);
            WeightText.setVisible(false);
            WeightTextField.setVisible(false);
        }
        if (ChooseTypeComboBox.getValue().equals("تنقلات")){
            HealthValueText.setVisible(true);
            HealthValueTextField.setVisible(true);
            WeightText.setVisible(false);
            WeightTextField.setVisible(false);
            ExpertDatePicker.setVisible(false);
            ExpertDateText.setVisible(false);
        }
        if (ChooseTypeComboBox.getValue().equals("کالاهای اساسی و خواروبار")){
            WeightText.setVisible(true);
            WeightTextField.setVisible(true);
            ExpertDateText.setVisible(false);
            ExpertDatePicker.setVisible(false);
            HealthValueTextField.setVisible(false);
            HealthValueText.setVisible(false);
        }
        if (ChooseTypeComboBox.getValue().equals("نوشیدنی")){
            WeightText.setVisible(false);
            WeightTextField.setVisible(false);
            ExpertDateText.setVisible(false);
            ExpertDatePicker.setVisible(false);
            HealthValueTextField.setVisible(false);
            HealthValueText.setVisible(false);
        }
        if (ChooseTypeComboBox.getValue().equals("سایر")){
            WeightText.setVisible(false);
            WeightTextField.setVisible(false);
            ExpertDateText.setVisible(false);
            ExpertDatePicker.setVisible(false);
            HealthValueTextField.setVisible(false);
            HealthValueText.setVisible(false);
        }
    }

    public void selectImage(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image file" , "*png" , "*jpg"));

        File file = fileChooser.showOpenDialog(MainPane.getScene().getWindow());
        path = file.getAbsolutePath();
        if (file != null){
            Image image = new Image(file.toURI().toString(),110,121,false,true);
            AddPaneImage.setImage(image);
        }
    }

    public void addBtn(){
        if (checkEmptyFields()){
            AddProductToDataBase();
        }else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }
    }
    private boolean checkEmptyFields(){
        boolean result;
        if (NameTextfield.getText().isEmpty()||
            BrandTextfield.getText().isEmpty()||
            PriceTextfield.getText().isEmpty()||
            StockTextfield.getText().isEmpty()||
            ChooseTypeComboBox.getSelectionModel().getSelectedItem()==null){
            result=false;
        }else {
            result=true;
        }
        return result;
    }
    private void AddProductToDataBase(){
        connection = database.connectDB();
        try {
            if (connection != null) {
                statement = connection.prepareStatement("INSERT INTO product (name,productid,price,point,numberofpoints,brand,uniqueproperties,image,information,type,stock,productSeller) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
            }
            statement.setString(1,NameTextfield.getText());
            String id = String.valueOf(MakeProductId.productIdMaker());
            statement.setString(2,id);
            statement.setString(3,PriceTextfield.getText());
            statement.setString(4,String.valueOf(0));
            statement.setString(5,String.valueOf(0));
            statement.setString(6,BrandTextfield.getText());
            statement.setString(7,"");
            String nativePath = ImageCopy.copyfile(path,id);
            statement.setString(8,nativePath);
            statement.setString(9,"");
            statement.setString(10,(String) ChooseTypeComboBox.getSelectionModel().getSelectedItem());
            statement.setInt(11,Integer.parseInt(StockTextfield.getText()));
            seller=new Seller("mamali","","","","","","");
            statement.setString(12,seller.getUsername());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteButton(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SetChooseRole();
    }
}
