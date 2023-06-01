package com.example.freshly;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
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
    private Label WeightText;

    @FXML
    private TextField WeightTextField;

    @FXML
    private Pane MainPane;

    @FXML
    private ImageView AddPaneImage;

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
    }

    public void selectImage(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image file" , "*png" , "*jpg"));

        File file = fileChooser.showOpenDialog(MainPane.getScene().getWindow());

        if (file != null){
            Image image = new Image(file.toURI().toString(),110,121,false,true);
            AddPaneImage.setImage(image);
        }
    }

    public void addBtn(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SetChooseRole();
    }
}
