package com.codered.ecomerce;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class PrimaryController extends App{



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

    //Uses method from App to swtich Scenes
    public void returnPrimary(MouseEvent event) throws IOException
    {
        App.switchScene("primary", event);
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

    @FXML
    //* Method initialize is called automatically after fxml file loads */
    //* Can Reuse for other pages to switch fxml files  */
    //* Add all buttons, methods, etc that need to be implemetned when loaded */
    public void initialize() {
        // Ensure the checkout button has its functionality
        checkoutButton.setOnAction(event -> {
            try {
                App.setRoot("itemView");
            } catch (IOException e) {
                e.printStackTrace();
            }  
        });
    }


    public void checkoutView() throws IOException
    {
        //Linked to Checkout Button, but can change once we add something
        App.setRoot("checkoutView");
    }
}
