package com.example.freshly;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainPage implements Initializable,LoginChecker,SearchInProducts {


    @FXML
    private TextField SearchTextField;

    @FXML
    private Button allProducts;

    @FXML
    private Button dairy;

    @FXML
    private Button drinks;

    @FXML
    private Button groceries;

    @FXML
    private Pane headerMainPage;

    @FXML
    private Pane headerMainPageUnlogined;

    @FXML
    private Button nuts;

    @FXML
    private Button proteins;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Pane Koll1;

    Client client;

    private Alert alert;
    public static Customer customer=null;
    private Socket socket;
    private int port = 1234;
    private String hostIp = "localhost";

    private Connection connection;
    private PreparedStatement prepare;
    private ResultSet result;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //socket = new Socket(hostIp,port);
        //System.out.println("Connected to Server");
        //client = new Client(socket);


        scrollPane.fitToWidthProperty();
        scrollPane.fitToHeightProperty();
        changeHeaderOfLogin();
        Refresh.delete();
        try {
            Refresh.deleteMessages();
        }catch (Exception e){
            System.out.println("successfully loaded");
        }
        showPopup();

    }

    public void showPopup() {
        // ایجاد Popup جدید
        Popup popup = new Popup();

        // ایجاد ImageView
        Image image = new Image(getClass().getResource("/com/example/freshly/Assets/BankSepah.png").toString());
        ImageView imageView = new ImageView(image);

        // ایجاد StackPane جدید برای قرار دادن ImageView روی آن
        StackPane content = new StackPane();
        content.getChildren().add(imageView);

        // تنظیم رنگ پس زمینه StackPane به مشکی با نیمه شفاف
        content.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");

        // اعمال افکت Blur بر روی StackPane
        GaussianBlur blur = new GaussianBlur(10);
        content.setEffect(blur);

        // تنظیم محل نمایش Popup در مرکز پنجره
        double centerXPosition = 600;
        double centerYPosition = 350;
        popup.setX(centerXPosition - content.getWidth() / 2);
        popup.setY(centerYPosition - content.getHeight() / 2);

        // تنظیم محتوای Popup به StackPane ایجاد شده
        popup.getContent().add(content);

        // نمایش Popup
        //popup.show(.getScene().getWindow());
    }

    @Override
    public boolean isLogin() {
        return customer != null;
    }
    public void changeHeaderOfLogin(){
        if (isLogin()){
            headerMainPage.setVisible(true);
            headerMainPageUnlogined.setVisible(false);
        }else {
            headerMainPage.setVisible(false);
            headerMainPageUnlogined.setVisible(true);
        }
    }

    public void onFilterButtonsClicked(ActionEvent event){
        try {
            if (event.getSource() == proteins) {
                ShowingProducts.type = "مواد پروتئینی";
                changeScene();
            }
            if (event.getSource() == drinks) {
                ShowingProducts.type = "نوشیدنی";
                changeScene();
            }
            if (event.getSource() == nuts) {
                ShowingProducts.type = "تنقلات";
                changeScene();
            }
            if (event.getSource() == dairy) {
                ShowingProducts.type = "لبنیات";
                changeScene();
            }
            if (event.getSource() == groceries) {
                ShowingProducts.type = "کالاهای اساسی و خواروبار";
                changeScene();
            }
            if (event.getSource() == allProducts) {
                //ShowingProducts.type = "";
                changeScene();
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public void changeScene() throws IOException {
        proteins.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("ShowingProducts.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        //stage.setTitle("Login Page");
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
                headerMainPage.getScene().getWindow().hide();

                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public void openLoginPage(MouseEvent mouseEvent){
        System.out.println("going to login page");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setTitle("Login Page");
            dairy.getScene().getWindow().hide();

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void goToCostumerPage(){
        headerMainPageUnlogined.getScene().getWindow().hide();
        GoToCostumerPage.goToCostumerPage();
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
                    dairy.getScene().getWindow().hide();

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
                dairy.getScene().getWindow().hide();
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
}
