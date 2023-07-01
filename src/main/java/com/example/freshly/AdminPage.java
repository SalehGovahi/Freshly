package com.example.freshly;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminPage implements Initializable {

    @FXML
    private Pane ConfirmSeller;

    @FXML
    private Button ConfirmSellerButton;

    @FXML
    private Button FinancialButton;

    @FXML
    private Pane FinancialPane;

    @FXML
    private Pane MainPane;

    @FXML
    private Pane StoreRoom;

    @FXML
    private Button StoreRoomButton;

    @FXML
    private Pane ReportStoreRoom;

    @FXML
    private Pane inventoryStoreRoom;

    @FXML
    private Pane DefineStoreRoom;

    @FXML
    private GridPane ConfirmationGridPane;

    @FXML
    private Pane SendMassagePane;


    public void switchtoFinancial(){
        ConfirmSeller.setVisible(false);
        StoreRoom.setVisible(false);
        FinancialPane.setVisible(true);
        SendMassagePane.setVisible(false);
    }

    public void switchtoMassages(){
        ConfirmSeller.setVisible(false);
        StoreRoom.setVisible(false);
        FinancialPane.setVisible(false);
        SendMassagePane.setVisible(true);
    }

    public void switchtoStoreRoom(){
        ConfirmSeller.setVisible(false);
        StoreRoom.setVisible(true);
        FinancialPane.setVisible(false);
        SendMassagePane.setVisible(false);
    }

    public void switchtoComfirmSeller(){
        ConfirmSeller.setVisible(true);
        StoreRoom.setVisible(false);
        FinancialPane.setVisible(false);
        SendMassagePane.setVisible(false);
    }

    public void switchtoDefineStoreRoom(){
        ReportStoreRoom.setVisible(false);
        inventoryStoreRoom.setVisible(false);
        DefineStoreRoom.setVisible(true);
    }

    public void switchtoInventoryStoreRoom(){
        ReportStoreRoom.setVisible(false);
        inventoryStoreRoom.setVisible(true);
        DefineStoreRoom.setVisible(false);
    }

    public void switchtoReportStoreRoom(){
        ReportStoreRoom.setVisible(true);
        inventoryStoreRoom.setVisible(false);
        DefineStoreRoom.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
