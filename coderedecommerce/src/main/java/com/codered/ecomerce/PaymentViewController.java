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


public class PaymentViewController extends App implements Initializable{

    @FXML
    private ChoiceBox<String> cardTypeChoiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cardTypeChoiceBox.getItems().addAll(
            "Credit", "Debit", "Prepaid", "Gift Card"
        );
        cardTypeChoiceBox.setValue("Select Card Type"); //Default Text

    }//end initialize

    public void returnPrimary(MouseEvent event) throws IOException {
        // Get the current stage
        App.switchScene("primary", event);
        // Test (successful)
        System.out.println("Returning to homepage...");
    }
    
}//end PaymentViewController
