package com.codered.ecomerce;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PrimaryController {



    @FXML
    private TextField searchTextBox;
    @FXML
    private Button searchButton;
    @FXML
    private Button checkoutButton;
    @FXML
    private ImageView homeTitleCard;
    @FXML
    private MenuItem mensTops;
    @FXML
    private MenuItem mensBottoms;
    @FXML
    private MenuItem womensTops;
    @FXML
    private MenuItem womensBottoms;
    @FXML
    private MenuItem kidsTops;
    @FXML
    private MenuItem kidsBottoms;
    @FXML
    private MenuItem aboutUs;
    @FXML 
    private MenuItem ordersMenuItem;
    
    

    /*@FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    */

    //method to perform search from the search when the search button is clicked.
    @FXML
    public void search() throws IOException
    {
        String searchItem = searchTextBox.getText();
        //test(successful)
        System.out.println("Searching for: " + searchItem);
    }

    //method to return to homepage when the home title card is clicked
    public void returnPrimary(MouseEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("primary.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        //test (successful)
        System.out.println("Returning to homepage...");
    }

    //method to perform a filtered search from the menu bar corresponding to the selected menu item
    @FXML
    public void menuSearch(ActionEvent event) throws IOException
    {
        String searchItem = ((MenuItem)event.getSource()).getText();
        //test (successful)
        System.out.println("Searching for: " + searchItem);
    }


    public void checkoutView() throws IOException
    {
        //Linked to Checkout Button, but can change once we add something
        App.setRoot("checkoutView");
    }
}
