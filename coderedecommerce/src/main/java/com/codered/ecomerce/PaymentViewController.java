package com.codered.ecomerce;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

///*
/// PaymentViewController controls the payment view page of the application. It handles the payment process and user input for payment details.
/// @author(s) Miguel Alfaro
/// @version 1.0
/// 
///  */
public class PaymentViewController extends App implements Initializable{



    @FXML
    private ChoiceBox<String> cardTypeChoiceBox;
    @FXML
    private ChoiceBox<String> cardDateMonthChoiceBox;
    @FXML
    private ChoiceBox<String> cardDateYearChoiceBox;

    @FXML
    private TextField cardNumTextBox, securityNumTextBox, zipCodeTextBox, cardHolderTextBox;

    @FXML
    private String cardNumber, cardSecurityCode, cardHolderName, cardType, cardDateMonth, cardDateYear, zipCode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the ChoiceBox with card types
        cardTypeChoiceBox.getItems().addAll(
            "Credit", "Debit", "Prepaid", "Gift Card"
        );
        cardTypeChoiceBox.setValue("Select Card Type"); //Default Text

        //Initialize the ChoiceBox with months
        cardDateMonthChoiceBox.getItems().addAll(
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
        );
        cardDateMonthChoiceBox.setValue("MM"); 

        //Initialize the ChoiceBox with years
        cardDateYearChoiceBox.getItems().addAll(
            "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035"
        );
        cardDateYearChoiceBox.setValue("YYYY"); 

    }//end initialize


    
    public void confirmPayment(ActionEvent event) throws IOException {

        // Get the values from the text fields and choice boxes
        cardNumber = cardNumTextBox.getText();
        cardSecurityCode = securityNumTextBox.getText();
        cardHolderName = cardHolderTextBox.getText();
        cardType = cardTypeChoiceBox.getValue();
        cardDateMonth = cardDateMonthChoiceBox.getValue();
        cardDateYear = cardDateYearChoiceBox.getValue();
        zipCode = zipCodeTextBox.getText();

        try{
            // Check if any of the fields are empty or not selected, display an error message if so
            if(cardNumber.isEmpty() || cardSecurityCode.isEmpty() || cardHolderName.isEmpty() || cardType.equals("Select Card Type") || cardDateMonth.equals("Select Month") || cardDateYear.equals("Select Year")){
                System.out.println("Please fill out all fields.");
            }else{
                

                // Test (successful)
                System.out.println("Payment Successful! \n" +
                    "Card Holder Name: " + cardHolderName + "\n" +
                    "Card Number: " + cardNumber + "\n" +
                    "Card Security Code: " + cardSecurityCode + "\n" +
                    "Card Expiration Date: " + cardDateMonth + "/" + cardDateYear + "\n" +
                    "Card Type: " + cardType);
            
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

    }
    //* Click CodeRedLogo, return to homepage */
    public void returnPrimary(MouseEvent event) throws IOException {
        // Get the current stage
        App.switchScene("primary", event);
        // Test (successful)
        System.out.println("Returning to homepage...");
    }
    
}//end PaymentViewController
