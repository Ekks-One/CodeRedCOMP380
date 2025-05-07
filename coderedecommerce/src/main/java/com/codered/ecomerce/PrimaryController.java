/**
 * Codered E-Commerce System
 * This {@code PrimaryController} class is used to handle the primart view of the application
 * * It contains methods to handle the search functionality, menu bar functionality, and image click events.
 * * It also contains methods to initialize the view and set up the layout of the images in the grid.
 * * The class uses JavaFX to create the user interface and handle events.
 * 
 * @Author CodeRed Team (Miguel, Xavier, Alfredo, jesus)
 * @version 1.0
 * @see Primary.fxml
 * @see App.java
 * @see ItemViewController.java
 * @see checkoutView.java
 * @see searchView,java
 */
package com.codered.ecomerce;

import java.io.IOException;
import java.util.ArrayList;

import com.codered.ecomerce.model.CentralShoppingSystem;
import com.codered.ecomerce.model.Product;
import com.codered.ecomerce.model.Variant;
import com.codered.ecomerce.sql.SearchProducts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

///* PrimaryController controls the primary page of the application. It handles the search functionality and image grid display.
/// @author(s) Miguel Alfaro, Alfredo Catzin, Xavier Ramos
/// @version 1.0
/// */
public class PrimaryController extends App{

    //declaration of FXML elements
    @FXML
    private TextField searchTextBox;
    @FXML
    private Button searchButton, cartViewButton;
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

    @FXML private MenuBar menuBar;

    

    /**
     * method to perform search from the search bar when the search button is clicked.
     * This method retrieves the text from the searchTextBox and prints it to the console.
     * Brings you to searchResults Page
     * @throws IOException if there is an error loading the fxml file
     */ 
    @FXML
    public void search(ActionEvent event) throws IOException {
        String searchItem = searchTextBox.getText().trim();
        App.search(searchItem, event);
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

    /**
     * method to perform a filtered search from the menu bar corresponding to the selected menu item
     * @param event the mouse event that triggers the method
     * @throws IOException if there is an error loading the fxml file
     */ 
    @FXML
    public void menuSearch(ActionEvent event) throws IOException {
        String searchItem = ((MenuItem)event.getSource()).getText();
        System.out.println("Searching for: " + searchItem);
    
        if (searchItem.equals("Tops")) {
            searchResults = SearchProducts.Search(searchItem);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("topsSearchView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) menuBar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Tops Search Results");
            stage.show();
    
        } else if (searchItem.equals("Bottoms")) {
            searchResults = SearchProducts.Search(searchItem);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("bottomsSearchView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) menuBar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Bottoms Search Results");
            stage.show();
        }
    }

    @FXML
    /** Method initialize is called automatically after fxml file loads
    * Can Reuse for other pages to switch fxml files  
    * Add all buttons, methods, etc that need to be implemented when loaded 
    */
    public void initialize() throws IOException {
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

        Image clickedImageContent = clickedImage.getImage();
        controller.setImage(clickedImageContent);


        ArrayList<Product> products = CentralShoppingSystem.getProducts();
        ArrayList<Variant> variants = new ArrayList<>();



        if (products == null) {
            System.out.println("the database was not reached");
            return;
        }
        for (Product p : products) {
                if (p != null) {
                    ArrayList<Variant> var = p.getVariants();
                try {
                    for (Variant v : var) {
                        if (v != null) {
                            variants.add(v);
                        }
                    }
                
                if(clickedImage == imageV00)
                {
                    controller.setVariant(variants.get(p.getID()));
                }
                else if(clickedImage == imageV10)
                {
                    controller.setVariant(variants.get(1));
                }
                else if(clickedImage == imageV20)
                {
                    controller.setVariant(variants.get(0));
                }
                else if(clickedImage == imageV01)
                {
                    controller.setVariant(variants.get(0));
                }
                else if(clickedImage == imageV11)
                {
                    controller.setVariant(variants.get(0));
                }
                else if(clickedImage == imageV21)
                {
                    controller.setVariant(variants.get(0));
                }
                else if(clickedImage == imageV02)
                {
                    controller.setVariant(variants.get(0));
                }
                else if(clickedImage == imageV12)
                {
                    controller.setVariant(variants.get(0));
                }
                else if(clickedImage == imageV22)
                {
                    controller.setVariant(variants.get(0));
                }
            } catch (NullPointerException e) {
                System.out.println("Null pointer exception: " + e.getMessage());
            }
        }
    

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        }
    }

    
    /**
     * Method to handle the click event on the view cart button and returns the 
     * cart page
     * @param event the mouse event that triggers the method
     * @throws IOException if there is an error loading the fxml file
     */
    @FXML
    public void cartView(ActionEvent event) throws IOException
    {
        System.out.println("Taking you to your cart!");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("cartView.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    //TO DO: Add the functionality to the menu items: Tops and Bottoms
    //fxid: topsMenuItem, bottomsMenuItem
}
