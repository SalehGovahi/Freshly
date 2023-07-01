package com.example.freshly;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CostumerPage implements Initializable , BackToMainPage{

    @FXML
    private GridPane AuctionGridPane;

    @FXML
    private ImageView AuctionLine;

    @FXML
    private ScrollPane AuctionScroll;

    @FXML
    private GridPane BuyingGridPane;

    @FXML
    private Label BuyingHistoryButton;

    @FXML
    private ScrollPane BuyingHistoryScrollPane;

    @FXML
    private ImageView CartLine;

    @FXML
    private Pane CartPane;

    @FXML
    private ImageView FreshlyHeader;

    @FXML
    private ImageView HistoryLine;

    @FXML
    private ImageView OffCode;

    @FXML
    private Label OffCodeButton;

    @FXML
    private GridPane OffCodeGrid;

    @FXML
    private ScrollPane OffCodesScroll;

    @FXML
    private Pane PaymentOpening;

    @FXML
    private Label PersonalInformationButton;

    @FXML
    private Label PersonalInformationEmail;

    @FXML
    private Label PersonalInformationFamily;

    @FXML
    private Label PersonalInformationName;

    @FXML
    private Pane PersonalInformationPane;

    @FXML
    private Label PersonalInformationPassword;

    @FXML
    private Label PersonalInformationPhoneNumber;

    @FXML
    private Label PersonalInformationUsername;

    @FXML
    private ImageView PersonalLine;

    @FXML
    private TextField SearchTextField;

    @FXML
    private Pane loginedPane;

    @FXML
    private ScrollPane scroll;

    @FXML
    private AnchorPane scrollPane;

    @FXML
    private Pane unLoginedPane;


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

    public void returnClicked(MouseEvent event){
        Return();
    }


    public void setBuyingGridPane(){
        try {
            Parent buyingLabels = FXMLLoader.load(getClass().getResource("BuyingLabel.fxml"));
            BuyingGridPane.add(buyingLabels, 0, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setOffCodesGridpane(){
        try {
            Parent buyingLabels = FXMLLoader.load(getClass().getResource("OffCodeLabel.fxml"));
            OffCodeGrid.add(buyingLabels,0,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAuctionGridPane(){
        try {
            Parent buyingLabels = FXMLLoader.load(getClass().getResource("AuctionLabel.fxml"));
            AuctionGridPane.add(buyingLabels,0,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void searching(KeyEvent event) {

    }

    @FXML
    void openCart(MouseEvent event) {
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

    public void ShowPersonalInformation(){
        BuyingHistoryScrollPane.setVisible(false);
        PersonalInformationPane.setVisible(true);
        PersonalLine.setVisible(true);
        HistoryLine.setVisible(false);
        OffCode.setVisible(false);
        OffCodesScroll.setVisible(false);
        AuctionScroll.setVisible(false);
        AuctionLine.setVisible(false);
        CartPane.setVisible(false);
        CartLine.setVisible(false);
    }

    public void ShowBuyingHistory(){
        BuyingHistoryScrollPane.setVisible(true);
        PersonalInformationPane.setVisible(false);
        HistoryLine.setVisible(true);
        PersonalLine.setVisible(false);
        OffCode.setVisible(false);
        OffCodesScroll.setVisible(false);
        AuctionScroll.setVisible(false);
        AuctionLine.setVisible(false);
        CartPane.setVisible(false);
        CartLine.setVisible(false);
    }

    public void showOffCodes(){
        BuyingHistoryScrollPane.setVisible(false);
        PersonalInformationPane.setVisible(false);
        HistoryLine.setVisible(false);
        PersonalLine.setVisible(false);
        OffCode.setVisible(true);
        OffCodesScroll.setVisible(true);
        AuctionScroll.setVisible(false);
        AuctionLine.setVisible(false);
        CartPane.setVisible(false);
        CartLine.setVisible(false);
    }

    public void showAuctions(){
        BuyingHistoryScrollPane.setVisible(false);
        PersonalInformationPane.setVisible(false);
        HistoryLine.setVisible(false);
        PersonalLine.setVisible(false);
        OffCode.setVisible(false);
        OffCodesScroll.setVisible(false);
        AuctionScroll.setVisible(true);
        AuctionLine.setVisible(true);
        CartPane.setVisible(false);
        CartLine.setVisible(false);
    }

    public void showCart(){
        BuyingHistoryScrollPane.setVisible(false);
        PersonalInformationPane.setVisible(false);
        HistoryLine.setVisible(false);
        PersonalLine.setVisible(false);
        OffCode.setVisible(false);
        OffCodesScroll.setVisible(false);
        AuctionScroll.setVisible(false);
        AuctionLine.setVisible(false);
        CartPane.setVisible(true);
        CartLine.setVisible(true);
    }

    public void showPaymentPane(){
        FadeTransition transition = new FadeTransition(Duration.seconds(1), PaymentOpening);
        transition.setFromValue(0.0);
        transition.setToValue(1.0);

        PaymentOpening.setOpacity(0.0);
        PaymentOpening.setVisible(true);

        transition.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OffCodesScroll.setStyle("-fx-background-color: white");
        OffCodeGrid.setStyle("-fx-background-color: white");
        BuyingHistoryScrollPane.setStyle("-fx-background-color: white");
        BuyingGridPane.setStyle("-fx-background-color: white");
        AuctionScroll.setStyle("-fx-background-color: white");
        AuctionGridPane.setStyle("-fx-background-color: white");
        setAuctionGridPane();
        setBuyingGridPane();
        setOffCodesGridpane();
    }
}
