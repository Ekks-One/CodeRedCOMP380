/**
 * Codered E-Commerce System
 * This {@code PrimaryController} class is used to handle the primart view of the application
 * * It contains methods to handle the search functionality, menu bar functionality, and image click events.
 * * It also contains methods to initialize the view and set up the layout of the images in the grid.
 * * The class uses JavaFX to create the user interface and handle events.
 * 
 * @Author CodeRed Team (Miugel, Xavier, Alfredo)
 * @version 1.0
 * @see Primary.fxml
 * @see App.java
 * @see ItemViewController.java
 * @see checkoutView.java
 * @see searchView,java
 */
package com.codered.ecomerce;

import java.io.IOException;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PrimaryController extends App{

    //declaration of FXML elements
    @FXML
    private TextField searchTextBox;
    @FXML
    private Button searchButton, checkoutButton;
    @FXML
    private AnchorPane headerAnchorP;
    @FXML
    private GridPane productGridPane;
    @FXML
    private ImageView homeTitleCard, 
                      imageV00, imageV10, imageV20,
                      imageV01, imageV11, imageV21,
                      imageV02, imageV12, imageV22;
    @FXML
    private StackPane stackP00, stackP10, stackP20,
                      stackP01, stackP11, stackP21,
                      stackP02, stackP12, stackP22;
    @FXML
    private MenuItem mensTops, mensBottoms, 
                     womensTops, womensBottoms, 
                     kidsTops, kidsBottoms,
                     aboutUs, ordersMenuItem;
    

    /**
     * method to perform search from the search bar when the search button is clicked.
     * This method retrieves the text from the searchTextBox and prints it to the console.
     * @throws IOException if there is an error loading the fxml file
     */ 
    @FXML
    public void search() throws IOException
    {
        String searchItem = searchTextBox.getText();
        //test(successful)
        System.out.println("Searching for: " + searchItem);
    }

    /**
     * method to return to the primary view when the home title card is clicked
     * @param event the mouse even that triggers the method
     * @throws IOException if there is an error loading the fxml file
     */ 
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
    /** Method initialize is called automatically after fxml file loads
    * Can Reuse for other pages to switch fxml files  
    * Add all buttons, methods, etc that need to be implemented when loaded 
    */
    public void initialize() {
        /**
         * Ensure the checkout button has its functionality
         */ 
        checkoutButton.setOnAction(event -> {
            try {
                App.setRoot("checkoutView");
            } catch (IOException e) {
                e.printStackTrace();
            }  
        });
        
        headerAnchorP.setStyle("-fx-background-color: red;");

        /*
         * Declaring 2D array to map the respective image views to their corresponding 
         * stack panes
         */
        ImageView[][] imageViews =
        {
            {imageV00, imageV10, imageV20},
            {imageV01, imageV11, imageV21},
            {imageV02, imageV12, imageV22}
        };
        
        StackPane[][] stackPanes =
        {
            {stackP00, stackP10, stackP20},
            {stackP01, stackP11, stackP21},
            {stackP02, stackP12, stackP22}  
        };

        /*
         * Nested for loop to iterate through the grid of images and set their fitWidth
         * and fitHeight properties to the stack panes' which ensures that the grid of images
         * on the home page will scale dynamically when the window is resized
         */
        for(int i = 0; i < imageViews.length; i++)
        {
            for(int j = 0; j < stackPanes[i].length; j++)
            {
                ImageView imageView = imageViews[i][j];
                StackPane stackPane = stackPanes[i][j];

                stackPane.prefWidthProperty().bind(productGridPane.widthProperty().divide(3));
                stackPane.prefHeightProperty().bind(productGridPane.heightProperty().divide(3));

                imageView.fitWidthProperty().bind(stackPane.widthProperty());
                imageView.fitHeightProperty().bind(stackPane.heightProperty());
                imageView.setPreserveRatio(false);
                
            }
        } 
    }
    /*
     * Method to handle the click event on the images within the gridPane and returns the
     * itemView page of the selected item, passing the image from the homepage to the itemView page
     */
    public void itemView(MouseEvent event) throws IOException
    {
        ImageView clickedImage = (ImageView) event.getSource();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("itemView.fxml"));
        Parent root = loader.load();

        ItemViewController controller = loader.getController();
        controller.setItemImage(clickedImage.getImage());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Method to handle the click event on the checkout button and returns the 
     * checkoutView page
     * * @param event the mouse event that triggers the method
     * @throws IOException if there is an error loading the fxml file
     */
    public void checkoutView() throws IOException
    {
        //Linked to Checkout Button, but can change once we add something
        App.setRoot("checkoutView");
    }
}
