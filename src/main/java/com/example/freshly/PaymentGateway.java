package com.example.freshly;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class PaymentGateway implements Initializable {

    @FXML
    private Button BuyingButton;

    @FXML
    private Button CancelButton;

    @FXML
    private TextField CaptchaCodeTextField;

    @FXML
    private ImageView CaptchaPicture;

    @FXML
    private TextField CardNumber1;

    @FXML
    private TextField CardNumber2;

    @FXML
    private TextField CardNumber3;

    @FXML
    private TextField CardNumber4;

    @FXML
    private Label CardNumberData1;

    @FXML
    private Label CardNumberData2;

    @FXML
    private Label CardNumberData3;

    @FXML
    private Label CardNumberData4;

    @FXML
    private Label Cvv2Number;

    @FXML
    private TextField Cvv2TextField;

    @FXML
    private Label ExpertDate;

    @FXML
    private TextField ExpertDateTextField;

    @FXML
    private TextField ExpertMonthTextField;

    @FXML
    private Label ExpertYear;

    @FXML
    private TextField PasswordTextField;

    @FXML
    private Button SendPassword;

    @FXML
    private ImageView BankLogo;

    private String CardNumberString;

    public void ChangeTextFields(){
        CardNumber1.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.length() == 4) {
                CardNumber2.requestFocus();
                CardNumberString = CardNumber1.getText();
                System.out.println(CardNumberString);
            }
        });
        CardNumber2.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.length() == 4) {
                CardNumber3.requestFocus();
                CardNumberString = CardNumberString + CardNumber2.getText();
                System.out.println(CardNumberString);
                if (CardNumberString.startsWith("603799")){
                    Image image = new Image(getClass().getResource("/com/example/freshly/Assets/BankMelli.png").toString());
                    BankLogo.setImage(image);
                }
                if (CardNumberString.startsWith("589210")){
                    Image image = new Image(getClass().getResource("/com/example/freshly/Assets/BankSepah.png").toString());
                    BankLogo.setImage(image);
                }
                if (CardNumberString.startsWith("627353")){
                    Image image = new Image(getClass().getResource("/com/example/freshly/Assets/BankTejarat.png").toString());
                    BankLogo.setImage(image);
                }
                if (CardNumberString.startsWith("621986")){
                    Image image = new Image(getClass().getResource("/com/example/freshly/Assets/BankSaman.png").toString());
                    BankLogo.setImage(image);
                }
                if (CardNumberString.startsWith("610433")){
                    Image image = new Image(getClass().getResource("/com/example/freshly/Assets/BankMellat.png").toString());
                    BankLogo.setImage(image);
                }
            }
        });

        CardNumber3.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.length() == 4) {
                CardNumber4.requestFocus();
                CardNumberString = CardNumberString + CardNumber3.getText();
            }
        });

        CardNumber4.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.length() == 4) {
                Cvv2TextField.requestFocus();
                CardNumberString = CardNumberString + CardNumber4.getText();
            }
        });

        Cvv2TextField.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.length() == 4) {
                ExpertMonthTextField.requestFocus();
            }
        });

        ExpertMonthTextField.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.length() == 2) {
                ExpertDateTextField.requestFocus();
            }
        });

        ExpertDateTextField.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.length() == 2) {
                CaptchaCodeTextField.requestFocus();
            }
        });

        CaptchaCodeTextField.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.length() == 4) {
                PasswordTextField.requestFocus();
            }
        });
    }

    public void setCardData(){
        CardNumber1.textProperty().addListener((obs, oldText, newText) -> {
            CardNumberData1.setText(newText);
        });
        CardNumber2.textProperty().addListener((obs, oldText, newText) -> {
            CardNumberData2.setText(newText);
        });
        CardNumber3.textProperty().addListener((obs, oldText, newText) -> {
            CardNumberData3.setText(newText);
        });
        CardNumber4.textProperty().addListener((obs, oldText, newText) -> {
            CardNumberData4.setText(newText);
        });
        Cvv2TextField.textProperty().addListener((obs, oldText, newText) -> {
            Cvv2Number.setText(newText);
        });
        ExpertMonthTextField.textProperty().addListener((obs, oldText, newText) -> {
            ExpertDate.setText(newText);
        });
        ExpertDateTextField.textProperty().addListener((obs, oldText, newText) -> {
            ExpertYear.setText(newText);
        });
    }

    public boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public void checkFields(){
        if (!isNumeric(CardNumber1.getText()) || !isNumeric(CardNumber2.getText()) || !isNumeric(CardNumber3.getText()) ||!isNumeric(CardNumber4.getText()) ||
                !isNumeric(Cvv2TextField.getText()) || !isNumeric(ExpertMonthTextField.getText()) || !isNumeric(ExpertDateTextField.getText())||
                !isNumeric(PasswordTextField.getText())){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Input");
            alert.showAndWait();
        }
    }

    public void setTextFields(){
        CardNumber1.setFocusTraversable(true);
    }

    public void PayButton(){
        checkFields();
    }

    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTextFields();
        ChangeTextFields();
        setCardData();
    }
}
