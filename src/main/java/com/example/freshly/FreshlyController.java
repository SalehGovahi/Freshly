package com.example.freshly;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.scene.control.PasswordField;

import javax.mail.MessagingException;
import java.net.URL;
import java.sql.*;
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

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    private Alert alert;

    private final String EMAIL_REGEX =
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public void SetChooseRole(){
        String[]RoleList = {"خریدار" , "فروشنده"};
        List<String> ItemList = new ArrayList<>();

        for (String data : RoleList){
            ItemList.add(data);
        }

        ObservableList DataList= FXCollections.observableArrayList(ItemList);
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



    public void FadeIn(ImageView imageView){
        FadeTransition fd = new FadeTransition();

        //set the duration for the Fade transition
        fd.setDuration(Duration.millis(2000));
        fd.setFromValue(0);
        fd.setToValue(100);

        //the transition will set to be auto reversed by setting this to true
        fd.setAutoReverse(false);

        //set Circle as the node onto which the transition will be applied
        fd.setNode(imageView);

        //playing the transition
        fd.play();
    }

    public void FadeOut(ImageView imageView){
        FadeTransition fd = new FadeTransition();

        //set the duration for the Fade transition
        fd.setDuration(Duration.millis(2000));
        fd.setFromValue(100);
        fd.setToValue(0);

        //the transition will set to be auto reversed by setting this to true
        fd.setAutoReverse(false);

        //set Circle as the node onto which the transition will be applied
        fd.setNode(imageView);

        //playing the transition
        fd.play();
    }
    public void getLogin(ActionEvent e){
        if (e.getSource()==LoginButton){
            if (CheckFieldsLoginPane()){
                connect = database.connectDB();
                try {
                    statement=connect.createStatement();
                    result = statement.executeQuery("SELECT Username , Password FROM costumer  WHERE Username = '"+UsenameTextFieldLoginPane.getText()+"' AND Password='"+PasswordfTextFieldLoginPane.getText()+"'");
                    if (result.next()){
                        //Todo Login Works
                        System.out.println("Login Successfully");
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information");
                        alert.setHeaderText(null);
                        alert.setContentText("Login Successfully");
                        alert.showAndWait();
                    }else {
                        //Todo Incorrect Pass/User
                        System.out.println("Incorrect Pass/User");
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Incorrect Pass/User");
                        alert.showAndWait();
                    }
                }catch (Exception exception){
                    System.out.println(exception.getMessage());
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
                PasswordfTextFieldLoginPane.getText().isEmpty()){
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
                    if (ChooseRoleComboBox.getSelectionModel().getSelectedItem()=="خریدار") {
                        result = statement.executeQuery("SELECT Username FROM costumer WHERE UserName = '"+UsernameTextField.getText()+"'");
                        if (result.next()){
                            //Todo Username has taken
                            System.out.println("already taken");
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Username has already taken");
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
                                statement.executeUpdate("INSERT INTO costumer (Username,Password,FirstName,LastName,PhoneNumber,EmailAddress) VALUES ('" + UsernameTextField.getText() + "','" + PasswordTextField.getText() + "','" + NameTextField.getText() + "','"+FamilyTextField.getText()+"','"+PhoneNumberTextField.getText()+"','"+EmailTextField.getText()+"')");
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
                        result = statement.executeQuery("SELECT Username FROM seller WHERE UserName = '"+UsernameTextField.getText()+"'");
                        if (result.next()){
                            //Todo Username has taken
                            System.out.println("already taken");
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Username has already taken");
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
                                statement.executeUpdate("INSERT INTO seller (Username,Password,FirstName,LastName,PhoneNumber,EmailAddress) VALUES ('" + UsernameTextField.getText() + "','" + PasswordTextField.getText() + "','" + NameTextField.getText() + "','"+FamilyTextField.getText()+"','"+PhoneNumberTextField.getText()+"','"+EmailTextField.getText()+"')");
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
        Boolean result=false;
        if (UsernameTextField.getText().isEmpty() ||
                PasswordTextField.getText().isEmpty() ||
                NameTextField.getText().isEmpty() ||
                FamilyTextField.getText().isEmpty() ||
                PhoneNumberTextField.getText().isEmpty()||
                ChooseRoleComboBox.getSelectionModel().getSelectedItem()==null||
                EmailTextField.getText().isEmpty()){
            result=false;
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
        ChooseRoleComboBox.setSelectionModel(null);
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
                        result = statement.executeQuery("SELECT Password FROM costumer WHERE EmailAddress = '" + VerificationٍEmailTextField.getText() + "'");
                        if (result.next()) {
                            String pass = result.getString("Password");
                            int successfulEmailSend = 0;
                            successfulEmailSend = EmailSender.sendEmail(VerificationٍEmailTextField.getText(),pass);
                            if (successfulEmailSend == 1) {
                                System.out.println("Done");
                            } else if (successfulEmailSend == 0) {
                                System.out.println("fail");
                            }
                        }else {
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
                } catch (MessagingException e) {
                    System.out.println(e.getMessage());
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