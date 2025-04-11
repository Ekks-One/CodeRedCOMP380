package com.codered.ecomerce;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.event.ActionEvent;

public class CheckoutController implements Initializable {


	//Population of the Choice Box
    @FXML
    private ChoiceBox<String> statesChoiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        statesChoiceBox.getItems().addAll(
            "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI",
            "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MN", "MS",
            "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK",
            "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV",
            "WI", "WY"
        );
        statesChoiceBox.setValue("Select State"); //Default Text
    }

    // Example event handler
    @FXML
    private void handleSomething(ActionEvent event) {
        String selectedState = statesChoiceBox.getValue();
        System.out.println("Selected State: " + selectedState);
    }
}