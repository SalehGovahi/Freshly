package com.example.freshly;


import java.io.IOException;
import java.sql.*;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.PasswordField;

import javax.mail.MessagingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FreshlyController implements Initializable {

    @FXML
    private Hyperlink BackToLoginPage;

    @FXML
    private ComboBox<?> ChooseRoleComboBox;

    @FXML
    private Button ConfirmEmailButton;

    @FXML
    private Button CreateAccountButton;

    @FXML
    private Label CreateAccountText;

    @FXML
    private Hyperlink DontHaveAnAccount;

    @FXML
    private TextField EmailTextField;

    @FXML
    private Label EnterEmailText;

    @FXML
    private TextField FamilyTextField;

    @FXML
    private Pane ForgotPasswordPane;

    @FXML
    private Hyperlink HaveAnAccount;

    @FXML
    private Button LoginButton;

    @FXML
    private Pane LoginPane;

    @FXML
    private Label LoginText;

    @FXML
    private TextField NameTextField;

    @FXML
    private PasswordField PasswordTextField;

    @FXML
    private PasswordField PasswordfTextFieldLoginPane;

    @FXML
    private TextField PhoneNumberTextField;

    @FXML
    private Label RetrivalPasswordText;

    @FXML
    private Hyperlink SendAgain;

    @FXML
    private Pane SignupPane;

    @FXML
    private TextField UsenameTextFieldLoginPane;

    @FXML
    private TextField UsernameTextField;

    @FXML
    private TextField VerificationٍEmailTextField;

    @FXML
    private ComboBox<?> ChooseRoleComboBoxLoginPane;
    private Connection connect;
    private Statement statement;
    private Statement statement1;
    private ResultSet result;
    private ResultSet result1;
    private Alert alert;
    private final String EMAIL_REGEX =
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public FreshlyController() throws SQLException {
    }

    public void SetChooseRole(){
        String[]RoleList = {"خریدار" , "فروشنده"};
        List<String> ItemList = new ArrayList<>();

        for (String data : RoleList){
            ItemList.add(data);
        }

        ObservableList DataList= FXCollections.observableArrayList(ItemList);
        ChooseRoleComboBoxLoginPane.setItems(DataList);
        ChooseRoleComboBox.setItems(DataList);
    }

    @FXML
    public void SwitchToForgotPasswordPage(ActionEvent actionEvent) {
        LoginPane.setVisible(false);
        ForgotPasswordPane.setVisible(true);
    }

    @FXML
    public void SwitchToLoginPageFromForgotPasswordPage(ActionEvent actionEvent) {
        ForgotPasswordPane.setVisible(false);
        LoginPane.setVisible(true);
    }

    @FXML
    public void SwitchToForgotLoginPage(ActionEvent actionEvent) {
        SignupPane.setVisible(false);
        LoginPane.setVisible(true);
    }

    @FXML
    public void SwitchToForgotSignupPage(ActionEvent actionEvent) {
        LoginPane.setVisible(false);
        SignupPane.setVisible(true);
    }

    @FXML
    public void SwitchToConfirmationPage(ActionEvent actionEvent) {
        LoginPane.setVisible(false);
        SignupPane.setVisible(true);
    }
    public static Product p_temp;
    public void getLogin(ActionEvent e){
        if (e.getSource()==LoginButton){
            if (CheckFieldsLoginPane()) {
                connect = database.connectDB();
                if (ChooseRoleComboBoxLoginPane.getSelectionModel().getSelectedItem() == "خریدار") {

                    try {
                        statement = connect.createStatement();
                        statement1 = connect .createStatement();
                        result = statement.executeQuery("SELECT Username , Password FROM costumer  WHERE Username = '" + UsenameTextFieldLoginPane.getText() + "' AND Password='" + PasswordfTextFieldLoginPane.getText() + "'");
                        result1 = statement1.executeQuery("SELECT * FROM costumer  WHERE Username = '" + UsenameTextFieldLoginPane.getText() + "' AND Password='" + PasswordfTextFieldLoginPane.getText() + "'");
                        if (result.next() && result1.next()) {
                            //Todo Login Works
                            MainPage.customer=new Customer(result1.getString("Username"),
                                    result1.getString("Password"),
                                    result1.getString("FirstName"),
                                    result1.getString("LastName"),
                                    result1.getString("PhoneNumber"),
                                    result1.getString("EmailAddress"),
                                    readCartAndDiscountCodeOfCostumerFromDatabase.cartOfCostumer(result1.getString("Username")),
                                    Integer.parseInt(result1.getString("wallet")),
                                    readCartAndDiscountCodeOfCostumerFromDatabase.discountCodeOfCostumer(result1.getString("Username")));
                            System.out.println("Login Successfully");
                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information");
                            alert.setHeaderText(null);
                            alert.setContentText("Login Successfully");
                            alert.showAndWait();
                            if (p_temp==null) {
                                Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
                                Stage stage = new Stage();
                                Scene scene = new Scene(root);

                                stage.setTitle("Login Page");
                                LoginButton.getScene().getWindow().hide();

                                stage.setScene(scene);
                                stage.show();
                            }
                            if (p_temp!=null) {
                                ProductPage.product = p_temp;
                                LoginButton.getScene().getWindow().hide();
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
                                }
                                catch (IOException exception){
                                    System.out.println(exception.getMessage());
                                }
                                }
                        } else {
                            //Todo Incorrect Pass/User
                            System.out.println("Incorrect Pass/User");
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Incorrect Pass/User");
                            alert.showAndWait();
                        }
                    } catch (Exception exception) {
                        System.out.println(exception.getMessage());
                    }

                } else if (ChooseRoleComboBoxLoginPane.getSelectionModel().getSelectedItem()=="فروشنده") {
                    try {
                        statement = connect.createStatement();
                        statement1 = connect.createStatement();
                        result = statement.executeQuery("SELECT Username , Password FROM seller  WHERE Username = '" + UsenameTextFieldLoginPane.getText() + "' AND Password='" + PasswordfTextFieldLoginPane.getText() + "' AND varified = 'true'");
                        result1 = statement1.executeQuery("SELECT * FROM seller  WHERE Username = '" + UsenameTextFieldLoginPane.getText() + "' AND Password='" + PasswordfTextFieldLoginPane.getText() + "' AND varified = 'true'");
                        if (result.next() && result1.next()) {
                            SellerPage.seller=new Seller(result1.getString("Username"),
                                    result1.getString("Password"),
                                    result1.getString("FirstName"),
                                    result1.getString("LastName"),
                                    result1.getString("PhoneNumber"),
                                    result1.getString("EmailAddress"),
                                    "Seller",
                                    result1.getString("Company"));
                            //Todo Login Works
                            System.out.println("Login Successfully");
                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information");
                            alert.setHeaderText(null);
                            alert.setContentText("Login Successfully");
                            alert.showAndWait();

                            Parent root = FXMLLoader.load(getClass().getResource("SellerPage.fxml"));
                            Stage stage = new Stage();
                            Scene scene = new Scene(root);

                            stage.setTitle("Login Page");
                            LoginButton.getScene().getWindow().hide();

                            stage.setScene(scene);
                            stage.show();
                        } else {
                            //Todo Incorrect Pass/User
                            System.out.println("Incorrect Pass/User or admin has not varified you yet");
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Incorrect Pass/User or admin has not varified you yet");
                            alert.showAndWait();
                        }
                    } catch (Exception exception) {
                        System.out.println(exception.getMessage());
                    }
                }
            }else {
                //Todo anything is Empty
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Username Or Password is Empty");
                alert.showAndWait();
            }
        }
    }
    private Boolean CheckFieldsLoginPane(){
        boolean result ;
        if (UsenameTextFieldLoginPane.getText().isEmpty() ||
                PasswordfTextFieldLoginPane.getText().isEmpty()||
                ChooseRoleComboBoxLoginPane.getSelectionModel().getSelectedItem()==null){
            result = false;
        }else {
            result=true;
        }
        return result;
    }
    public void createAccount(ActionEvent actionEvent){
        if (actionEvent.getSource()==CreateAccountButton){
            if (checkFieldsSignUpPane()){
                connect=database.connectDB();
                try{
                    statement=connect.createStatement();
                    statement1 = connect.createStatement();
                    if (ChooseRoleComboBox.getSelectionModel().getSelectedItem()=="خریدار") {
                        result = statement.executeQuery("SELECT Username,EmailAddress FROM costumer WHERE UserName = '"+UsernameTextField.getText()+"' OR EmailAddress = '"+EmailTextField.getText()+"'");
                        result1 = statement1.executeQuery("SELECT EmailAddress FROM seller WHERE EmailAddress = '"+EmailTextField.getText()+"'");
                        if (result1.next() || result.next()){
                            System.out.println("already taken");
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Username/EmailAddress has already taken");
                            alert.showAndWait();
                        }
                        else {
                            System.out.println("valid Username");
                            String regex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                                    "[a-zA-Z0-9_+&*-]+)*@" +
                                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                                    "A-Z]{2,7}$";

                            Pattern pattern = Pattern.compile(regex);
                            Matcher matcher = pattern.matcher(EmailTextField.getText());

                            if (matcher.matches()) {
                                //Todo Account Successfully Created
                                System.out.println("Valid email address");
                                statement.executeUpdate("INSERT INTO costumer (Username,Password,FirstName,LastName,PhoneNumber,EmailAddress,cart,wallet,discountcode) VALUES ('" + UsernameTextField.getText() + "','" + PasswordTextField.getText() + "','" + NameTextField.getText() + "','"+FamilyTextField.getText()+"','"+PhoneNumberTextField.getText()+"','"+EmailTextField.getText()+"','8585_1@6969_1','0','freshly1402@freshly1383@freshly1382')");
                                alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Information Message");
                                alert.setHeaderText(null);
                                alert.setContentText("Account Successfully Created");
                                alert.showAndWait();
                                ClearLoginPaneTextFields();
                            } else {
                                //TODO Email Regex Is Invalid
                                System.out.println("Invalid email address");
                                alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error Message");
                                alert.setHeaderText(null);
                                alert.setContentText("Invalid Email Address");
                                alert.showAndWait();
                            }

                        }
                    }
                    if (ChooseRoleComboBox.getSelectionModel().getSelectedItem()=="فروشنده") {
                        result = statement.executeQuery("SELECT Username,EmailAddress FROM seller WHERE UserName = '"+UsernameTextField.getText()+"' OR EmailAddress = '"+EmailTextField.getText()+"'");
                        result1 = statement1.executeQuery("SELECT EmailAddress FROM costumer WHERE EmailAddress = '"+EmailTextField.getText()+"'");
                        if (result.next() || result1.next()){
                            //Todo Username has taken
                            System.out.println("already taken");
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Username/EmailAddress has already taken");
                            alert.showAndWait();
                        }
                        else {
                            System.out.println("valid Username");
                            String regex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                                    "[a-zA-Z0-9_+&*-]+)*@" +
                                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                                    "A-Z]{2,7}$";

                            Pattern pattern = Pattern.compile(regex);
                            Matcher matcher = pattern.matcher(EmailTextField.getText());

                            if (matcher.matches()) {
                                //Todo Account Successfully Created
                                System.out.println("Valid email address");
                                statement.executeUpdate("INSERT INTO seller (Username,Password,FirstName,LastName,PhoneNumber,EmailAddress,Company,varified) VALUES ('" + UsernameTextField.getText() + "','" + PasswordTextField.getText() + "','" + NameTextField.getText() + "','"+FamilyTextField.getText()+"','"+PhoneNumberTextField.getText()+"','"+EmailTextField.getText()+"','mohammad','false')");
                                alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Information Message");
                                alert.setHeaderText(null);
                                alert.setContentText("your request sent to admin");
                                alert.showAndWait();
                                ClearLoginPaneTextFields();
                            } else {
                                //TODO Email Regex Is Invalid
                                System.out.println("Invalid email address");
                                alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error Message");
                                alert.setHeaderText(null);
                                alert.setContentText("Invalid Email Address");
                                alert.showAndWait();
                            }

                        }
                    }
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }else {
                //TODO empty TextField
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("empty Username/Password");
                alert.showAndWait();
            }
        }
    }
    private Boolean checkFieldsSignUpPane(){
        boolean result=false;
        if (UsernameTextField.getText().isEmpty() ||
                PasswordTextField.getText().isEmpty() ||
                NameTextField.getText().isEmpty() ||
                FamilyTextField.getText().isEmpty() ||
                PhoneNumberTextField.getText().isEmpty()||
                ChooseRoleComboBox.getSelectionModel().getSelectedItem()==null||
                EmailTextField.getText().isEmpty()){

        }else {
            result=true;
        }
        return result;
    }
    public void ClearLoginPaneTextFields(){
        UsernameTextField.setText("");
        PasswordTextField.setText("");
        NameTextField.setText("");
        FamilyTextField.setText("");
        PhoneNumberTextField.setText("");
        EmailTextField.setText("");
    }
    public void rememberForgotPasswordToClient(ActionEvent actionEvent){
        if (actionEvent.getSource()==ConfirmEmailButton) {
            System.out.println("tayeed");
            if (checkEmailVerification()) {
                connect = database.connectDB();
                try {
                    if (isValidEmail(VerificationٍEmailTextField.getText())) {
                        statement = connect.createStatement();
                        statement1=connect.createStatement();
                        result = statement.executeQuery("SELECT Password FROM costumer WHERE EmailAddress = '" + VerificationٍEmailTextField.getText() + "'");
                        result1 = statement1.executeQuery("SELECT Password FROM seller WHERE EmailAddress = '" + VerificationٍEmailTextField.getText() + "'");
                        if (result.next()) {
                            String pass = result.getString("Password");
                            Thread thread = new Thread(new EmailSender(VerificationٍEmailTextField.getText(),"This is A Message From Freshly Team" +
                                    "Your Password is : "+pass+""));
                            thread.start();
                                alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Information Message");
                                alert.setHeaderText(null);
                                alert.setContentText("Your Password Sent");
                                alert.showAndWait();
                                System.out.println("Done");
                                VerificationٍEmailTextField.setText("");
                        } else if (result1.next()) {
                            String pass = result1.getString("Password");
                            int successfulEmailSend = 0;
                            Thread thread = new Thread(new EmailSender(VerificationٍEmailTextField.getText(),"This is A Message From Freshly Team" +
                                    "Your Password is : "+pass+""));
                            thread.start();
                                alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Information Message");
                                alert.setHeaderText(null);
                                alert.setContentText("Your Password Sent");
                                alert.showAndWait();
                                System.out.println("Done");
                                VerificationٍEmailTextField.setText("");
                        } else {
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Email Not Found");
                            alert.showAndWait();
                        }
                    }else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Incorrect Email Address");
                        alert.showAndWait();
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
    private boolean checkEmailVerification(){
        Boolean result = false;
        if (VerificationٍEmailTextField.getText().isEmpty()){
            result=false;
        }else {
            result=true;
        }
        return result;
    }
    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SetChooseRole();
    }
}