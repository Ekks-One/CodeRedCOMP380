/**
 * Codered E-Commece System
 * this {@code PaymentViewController} class is used to handle the payment view of the application.
 * it contains methods to handle the payment functionality, including
 * initializing the view, confirming the payment, and returning to the homepage.
 * 
 * @Author CodeRed Team (Miguel, Xavier)
 * @version 1.0
 */
package com.codered.ecomerce;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.codered.ecomerce.model.Customer;
import com.codered.ecomerce.model.CustomerManager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

/**
 * PaymentViewController controls the operations of the payment view page
 */
public class PaymentViewController extends App implements Initializable{

    @FXML
    private ChoiceBox<String> cardTypeChoiceBox;
    @FXML
    private ChoiceBox<String> cardDateMonthChoiceBox;
    @FXML
    private ChoiceBox<String> cardDateYearChoiceBox;

    @FXML
    private TextField cardNumTextBox, securityNumTextBox, zipCodeTextBox, cardHolderTextBox,
                      searchTextBox;

    @FXML
    private String cardNumber, cardSecurityCode, cardHolderName, cardType, cardDateMonth, cardDateYear, zipCode;

    /**
     * Method to initialize the PaymentViewController class
     * @param location the URL location, 
     * @param resources the ResourceBundle
     */
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


/**
 * Method to confirm the payment and display the payment details
 * @param ActionEvent event that triggers the method
 * @throws IOException 
 */    
    public void confirmPayment(ActionEvent event) throws IOException {

        // Get the current customer from the CustomerManager
        Customer customer = CustomerManager.getCustomer();

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
                //Display an alert to inform the user that all fields are required
                Alert missingInfoAlert = new Alert(Alert.AlertType.ERROR); // Create an error alert
                missingInfoAlert.setTitle("Something went wrong");               
                missingInfoAlert.setHeaderText("Missing required fields!");      
                missingInfoAlert.setContentText("Please fill in all fields.");
                missingInfoAlert.showAndWait();
            }else{
                
                // Test (successful)
                    System.out.println("Payment Successful! \n" +
                    "Card Holder Name: " + cardHolderName + "\n" +
                    "Card Number: " + cardNumber + "\n" +
                    "Card Security Code: " + cardSecurityCode + "\n" +
                    "Card Expiration Date: " + cardDateMonth + "/" + cardDateYear + "\n" +
                    "Card Type: " + cardType);

                    //Retrieves customer from Customer Class and formats the customer information to be displayed in the alert
                    String customerInfo = "Customer Name: " + customer.getFname() + " " + customer.getLname() + "\n" +
                    "Email: " + customer.getCustomerEmail() + "\n" +
                    "Shipping Address: " + String.join(", ", customer.getShippingAddress()) + "\n\n";

                    // Combines the customer information and payment details into a single string
                    // to be displayed in the alert
                    String confirmationText = customerInfo +
                    "Card Holder Name: " + cardHolderName + "\n" +
                    "Card Number: " + cardNumber + "\n" +
                    "Card Security Code: " + cardSecurityCode + "\n" +
                    "Card Expiration Date: " + cardDateMonth + "/" + cardDateYear + "\n" +
                    "Card Type: " + cardType;

                //Display an alert to confirm the order creation containing the payment details, order details
                    Alert OrderCreatedAlert = new Alert(Alert.AlertType.INFORMATION); // Create an information alert
                    OrderCreatedAlert.setTitle("Order Created Successfully!");
                    OrderCreatedAlert.setHeaderText("Order Details");
                    OrderCreatedAlert.setContentText(confirmationText); // Set the content text
                    OrderCreatedAlert.showAndWait();
            }//end else
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
             
        }

    }
    /**
     *  Method that returns the user to the homepage by clicking on the home title card
     * @param MouseEvent event that triggers the method
     * @throws IOException if there is an error loading the fxml file
     */
    public void returnPrimary(MouseEvent event) throws IOException {
        // Get the current stage
        App.switchScene("primary", event);
        // Test (successful)
        System.out.println("Returning to homepage...");
    }

    /**
     * method to perform search from the search bar when the search button is clicked.
     * This method retrieves the text from the searchTextBox and prints it to the console.
     * Brings you to searchResults Page
     * @throws IOException if there is an error loading the fxml file
     */ 
    @FXML
    public void search(ActionEvent event) throws IOException
    {
        if(!searchTextBox.getText().isEmpty()) {
            System.out.println("Taking you to Search Results!");
            String searchItem = searchTextBox.getText();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("searchResultsView.fxml"));
                Parent root = loader.load();

                // Get the current stage
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                // Set the new scene
                stage.setScene(new Scene(root));
                stage.setTitle("Checkout Page");
                stage.show();
                //test(successful)
            System.out.println("Searching for: " + searchItem);
        }
        else{
            System.out.println("Please enter a search term.");
        }
    }

     /**
     * method to perform a filtered search from the menu bar corresponding to the selected menu item
     * @param event the mouse event that triggers the method
     * @throws IOException if there is an error loading the fxml file
     */ 
    @FXML
    public void menuSearch(ActionEvent event) throws IOException
    {
        String searchItem = ((MenuItem)event.getSource()).getText();
        //test (successful)
        System.out.println("Searching for: " + searchItem);
    }
    
}
