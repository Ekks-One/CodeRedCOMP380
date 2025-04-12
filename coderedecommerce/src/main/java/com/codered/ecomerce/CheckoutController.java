package com.codered.ecomerce;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

public class CheckoutController extends App implements Initializable {

    @FXML
    private TextField fnameTextBox, lnameTextBox, addressTextBox, cityTextBox, zipTextBox, emailTextBox, phoneTextBox;

	//Population of the Choice Box
    @FXML
    private ChoiceBox<String> statesChoiceBox;
    @FXML
    private Button placeOrderButton;

    private String selectedState;
    private String firstName;
    private String lastName;
    private String address;
    private String email;

    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        placeOrderButton.setOnAction(event -> {
            try {
                App.setRoot("paymentView");
            } catch (IOException e) {
                e.printStackTrace();
            }  
        });
        statesChoiceBox.getItems().addAll(
            "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI",
            "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MN", "MS",
            "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK",
            "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV",
            "WI", "WY"
        );
        statesChoiceBox.setValue("Select State"); //Default Text
    }

    //* Gathers all info inputed from the page */

    /* Places order if all required fields are filled out
    public void placeOrder() throws Exception {
        firstName = fnameTextBox.getText();
        lastName = lnameTextBox.getText();
        address = addressTextBox.getText();
        selectedState = statesChoiceBox.getValue();
        email = emailTextBox.getText();
        
        if(!firstName.isEmpty() && !lastName.isEmpty() && !address.isEmpty() && 
            !selectedState.equals("Select State") && !email.isEmpty()) {
            System.out.println("Checkout Successful! \n" +
                "Name: " + firstName + " " + lastName + "\n" +
                "Address: " + address + ", " + selectedState + "\n" +
                "Email: " + email);
        } else {
            System.out.println("Please fill in all required fields.");
        }
    } */

    //* Returns to primary(HomePage) */
    public void returnPrimary(MouseEvent event) throws IOException {
        // Get the current stage
        App.switchScene("primary", event);
        // Test (successful)
        System.out.println("Returning to homepage...");
    }

    public void returnPayment(MouseEvent event) throws IOException {
        // Get the current stage
        App.switchScene("paymentView", event);
        // Test (successful)
        System.out.println("Proceeding to payment...");
    }


    // Example event handler
    @FXML
    private void getSelectedState(ActionEvent event) {
        String selectedState = statesChoiceBox.getValue();
        System.out.println("Selected State: " + selectedState);
    }

    public void paymentView() throws IOException
    {
        //Linked to Place Order Button
        FXMLLoader Loader = new FXMLLoader(getClass().getResource("paymentView.fxml"));
        Parent root = Loader.load();
        Stage stage = (Stage) placeOrderButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}