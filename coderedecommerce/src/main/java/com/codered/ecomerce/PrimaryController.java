package com.codered.ecomerce;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class PrimaryController {

    @FXML
    private TextField searchTextBox;
    @FXML
    private Button searchButton;
    @FXML
    private Button checkoutButton;
    
    String searchItem;

    /*@FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    */

    @FXML
    public void search() throws IOException
    {
        searchItem = searchTextBox.getText();
        //test
        System.out.println("Searching for: " + searchItem);
    }
}
