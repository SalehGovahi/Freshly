package com.example.freshly;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductPage implements Initializable ,CreatingCartOfCostumerAndUpdateDatabase , LoginChecker,BackToMainPage,SearchInProducts{


    @FXML
    private Label BrandName;

    @FXML
    private Label BrandProduct;

    @FXML
    private Label BrandProductUnder;

    @FXML
    private HBox CommentHBox;

    @FXML
    private TextField CommentTextField;

    @FXML
    private TextFlow CommentTextFlow;

    @FXML
    private VBox CommentVBox;

    @FXML
    private ImageView FreshlyHeader;

    @FXML
    private ImageView ProductImage;

    @FXML
    private Label ProductPrice;

    @FXML
    private Label ProductRate;

    @FXML
    private TextField SearchTextField;

    @FXML
    private Button SendComment;

    @FXML
    private Label TypeProduct;

    @FXML
    private Button addToCart;

    @FXML
    private Label commentsCount;

    @FXML
    private Pane loginedPane;

    @FXML
    private Label productSeller;

    @FXML
    private AnchorPane scrollPane;

    @FXML
    private Pane unLoginedPane;

    @FXML
    private ImageView Star1;

    @FXML
    private ImageView Star2;

    @FXML
    private ImageView Star3;

    @FXML
    private ImageView Star4;

    @FXML
    private ImageView Star5;

    @FXML
    private ScrollPane scroll;

    private Connection connection;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet resultSet;

    public static Product product ;
    private ObservableList<String> comments = FXCollections.observableArrayList();
    private Image image;
    private Alert alert;
    private Tooltip tooltip;
    private int pointDatabase;
    public void FixSearchTextField(MouseEvent event){

    }
    public void openLoginPage(MouseEvent event){
        System.out.println("going to login page");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setTitle("Login Page");
            BrandProduct.getScene().getWindow().hide();

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setData(Product product){
        this.product=product;
        BrandName.setText(product.getName());
        BrandProduct.setText(product.getBrand());
        BrandProductUnder.setText(product.getBrand());
        TypeProduct.setText(product.getType());
        ProductRate.setText(String.valueOf(product.getPoint()));
        ProductPrice.setText(String.valueOf(product.getPrice()));
        String path = "File:" + product.getImage();
        image = new Image(path, 246, 223, false, true);
        ProductImage.setImage(image);
        productSeller.setText(product.getProductSeller());
        commentsCount.setText("دیدگاه "+product.getComments().size());
    }

    @FXML
    void AddingToCostumerCart(ActionEvent event) {
        if (MainPage.customer == null){
            try {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("You didnt Login in to your account , Do you want to login ?");
                Optional<ButtonType> option = alert.showAndWait();

                FreshlyController.p_temp = product;

                if (option.get().equals(ButtonType.OK)) {
                    addToCart.getScene().getWindow().hide();

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
    public boolean isLogin() {
        return MainPage.customer != null;
    }
    public void changeHeaderPage(){
        if (isLogin()){
            unLoginedPane.setVisible(false);
            loginedPane.setVisible(true);
        }else {
            unLoginedPane.setVisible(true);
            loginedPane.setVisible(false);
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
                loginedPane.getScene().getWindow().hide();

                stage.setScene(scene);
                stage.show();
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

    public static void recieveMessage(String messageFromServer, VBox vBox) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(messageFromServer);
        TextFlow textFlow = new TextFlow(text);

        textFlow.setStyle(
                "-fx-background-color: rgb(233, 233, 235);" +
                        "-fx-background-radius: 20px;");

        textFlow.setPadding(new Insets(5, 10, 5, 10));
        hBox.getChildren().add(textFlow);
        vBox.getChildren().add(hBox);

    }



    public void sendComment(ActionEvent event){
            if (MainPage.customer != null) {
                String messageToSend = CommentTextField.getText();
                if (!messageToSend.isBlank()) {
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER);
                    hBox.setPadding(new Insets(5, 5, 5, 10));

                    //int spaceIndex2 = messageToSend.indexOf(" ");
                    //String message2 = messageToSend.substring(spaceIndex2 + 1);

                    Text text = new Text(messageToSend);
                    TextFlow textFlow = new TextFlow(text);

                    textFlow.setStyle(
                            "-fx-color: rgb(239, 242, 255);" +
                                    "-fx-background-color: rgb(15, 125, 242);" +
                                    "-fx-background-radius: 20px;");

                    textFlow.setPadding(new Insets(5, 10, 5, 10));
                    text.setFill(Color.color(0.934, 0.925, 0.996));

                    hBox.getChildren().add(textFlow);
                    CommentVBox.getChildren().add(hBox);
                    connection = database.connectDB();
                    try {
                        statement = connection.createStatement();
                        statement.executeUpdate("INSERT INTO comments (Text,productid,Username,isbought) VALUES ('" + messageToSend + "','" + product.getProductId() + "','" + MainPage.customer.getUsername() + "','Yes')");
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }else {
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
            }


            CommentTextField.clear();
        }
    
    public void showComments(){
        connection = database.connectDB();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM comments");
            while (resultSet.next()){
                String productCommentsUsername = resultSet.getString("Username");
                String text = resultSet.getString("Text");
                if (resultSet.getString("productid").equals(String.valueOf(product.getProductId()))) {
                    recieveMessage(resultSet.getString("Username") + " : " + resultSet.getString("Text"), CommentVBox);
                    System.out.println(resultSet.getString("Username") + " : " + text);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void vote(int pointDatabase){
        connection = database.connectDB();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT point , numberofpoints FROM product WHERE productid = '"+String.valueOf(product.getProductId())+"'");
            double point = 0;
            int numberOfPoints = 0;
            if (resultSet.next()) {
                point = Double.parseDouble(resultSet.getString("point"));
                numberOfPoints = Integer.parseInt(resultSet.getString("numberofpoints"));
                point = ((point * numberOfPoints) + pointDatabase) / (numberOfPoints + 1);
                double point1 = Math.floor(point*10);
                point = point1/10;
                System.out.println(point);
                numberOfPoints++;

            }
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE product SET point = '"+point+"' , numberofpoints = '"+numberOfPoints+"'WHERE productid = '"+String.valueOf(product.getProductId())+"'");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information message");
        alert.setHeaderText(null);
        alert.setContentText("your vote confirmed");
        alert.showAndWait();
    }
    public void point1() {
        if (isLogin())
            vote(1);
        else {
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
        }
    }
    public void point2(){
        if (isLogin())
            vote(2);
        else {
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
        }
    }
    public void point3(){
        if (isLogin())
            vote(3);
        else {
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
        }
    }
    public void point4(){
        if (isLogin())
            vote(4);
        else {
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
        }
    }
    public void point5(){
        if (isLogin())
            vote(5);
        else {
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
        }
    }
    public void Star1Hover(){
        Image image = new Image(getClass().getResource("/com/example/freshly/Assets/FilledStar.png").toString());
        Star1.setImage(image);
        Star1.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 100, 0, 0, 0);");
        tooltip = new Tooltip("1 امتیاز");
        tooltip.show(Star1, 1017, 635);
    }
    public void Star1AfterHover(){
        Image image = new Image(getClass().getResource("/com/example/freshly/Assets/EmptyStar.png").toString());
        Star1.setImage(image);
        Star1.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 0, 0, 0, 0);");
        tooltip.hide();
        tooltip.setStyle("-fx-background-color: rgba(0,0,0,0.1);");
    }

    public void setPointStar1(){
//        connection = database.connectDB();
//        try {
//            statement = connection.createStatement();
//            statement.executeUpdate("INSERT INTO comments (Text,productid,Username,isbought) VALUES ('" + messageToSend + "','" + product.getProductId() + "','" + MainPage.customer.getUsername() + "','Yes')");
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
    }

    private void smoothScroll(ScrollPane scrollPane, double targetVvalue) {
        double duration = 200; // مدت زمان اسکرول (به میلی‌ثانیه)
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(duration), new KeyValue(scrollPane.vvalueProperty(), targetVvalue, Interpolator.EASE_BOTH))
        );
        timeline.play();
    }

    public void scrollToComment(){
        double commentsSectionHeight = 635;
        double previousSectionHeight = 200;
        double vValue = commentsSectionHeight / (commentsSectionHeight + previousSectionHeight);
        smoothScroll(scroll, vValue);
    }

    public void Star2Hover(){
        Image image = new Image(getClass().getResource("/com/example/freshly/Assets/FilledStar.png").toString());
        Star1.setImage(image);
        Star1.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 100, 0, 0, 0);");
        Star2.setImage(image);
        Star2.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 100, 0, 0, 0);");
        tooltip = new Tooltip("2 امتیاز");
        tooltip.show(Star2, 1052, 635);
    }
    public void Star2AfterHover(){
        Image image = new Image(getClass().getResource("/com/example/freshly/Assets/EmptyStar.png").toString());
        Star1.setImage(image);
        Star1.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 0, 0, 0, 0);");
        Star2.setImage(image);
        Star2.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 0, 0, 0, 0);");
        tooltip.hide();
    }

    public void Star3Hover(){
        Image image = new Image(getClass().getResource("/com/example/freshly/Assets/FilledStar.png").toString());
        Star1.setImage(image);
        Star1.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 100, 0, 0, 0);");
        Star2.setImage(image);
        Star2.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 100, 0, 0, 0);");
        Star3.setImage(image);
        Star3.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 100, 0, 0, 0);");
        tooltip = new Tooltip("3 امتیاز");
        tooltip.show(Star2, 1087, 635);
    }
    public void Star3AfterHover(){
        Image image = new Image(getClass().getResource("/com/example/freshly/Assets/EmptyStar.png").toString());
        Star1.setImage(image);
        Star1.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 0, 0, 0, 0);");
        Star2.setImage(image);
        Star2.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 0, 0, 0, 0);");
        Star3.setImage(image);
        Star3.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 0, 0, 0, 0);");
        tooltip.hide();
    }


    public void Star4Hover(){
        Image image = new Image(getClass().getResource("/com/example/freshly/Assets/FilledStar.png").toString());
        Star1.setImage(image);
        Star1.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 100, 0, 0, 0);");
        Star2.setImage(image);
        Star2.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 100, 0, 0, 0);");
        Star3.setImage(image);
        Star3.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 100, 0, 0, 0);");
        Star4.setImage(image);
        Star4.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 100, 0, 0, 0);");
        tooltip = new Tooltip("4 امتیاز");
        tooltip.show(Star2, 1122, 635);
    }
    public void Star4AfterHover(){
        Image image = new Image(getClass().getResource("/com/example/freshly/Assets/EmptyStar.png").toString());
        Star1.setImage(image);
        Star1.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 0, 0, 0, 0);");
        Star2.setImage(image);
        Star2.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 0, 0, 0, 0);");
        Star3.setImage(image);
        Star3.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 0, 0, 0, 0);");
        Star4.setImage(image);
        Star4.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 0, 0, 0, 0);");
        tooltip.hide();
    }

    public void Star5Hover(){
        Image image = new Image(getClass().getResource("/com/example/freshly/Assets/FilledStar.png").toString());
        Star1.setImage(image);
        Star1.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 100, 0, 0, 0);");
        Star2.setImage(image);
        Star2.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 100, 0, 0, 0);");
        Star3.setImage(image);
        Star3.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 100, 0, 0, 0);");
        Star4.setImage(image);
        Star4.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 100, 0, 0, 0);");
        Star5.setImage(image);
        Star5.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 100, 0, 0, 0);");
        tooltip = new Tooltip("5 امتیاز");
        tooltip.show(Star2, 1155, 635);
    }
    public void Star5AfterHover(){
        Image image = new Image(getClass().getResource("/com/example/freshly/Assets/EmptyStar.png").toString());
        Star1.setImage(image);
        Star1.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 0, 0, 0, 0);");
        Star2.setImage(image);
        Star2.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 0, 0, 0, 0);");
        Star3.setImage(image);
        Star3.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 0, 0, 0, 0);");
        Star4.setImage(image);
        Star4.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 0, 0, 0, 0);");
        Star5.setImage(image);
        Star5.setStyle("-fx-background-color: dropshadow(three-pass-box, rgba(0,0,0,1), 0, 0, 0, 0);");
        tooltip.hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setData(product);
        changeHeaderPage();
        showComments();
    }
}
