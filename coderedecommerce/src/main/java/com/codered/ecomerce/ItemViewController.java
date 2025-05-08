/**
 * CodeRed E-Commerce Application
 * This {@code ItemViewController} class is used to handle the item view of the application.
 * It contains methods to handle the item view functionality, including adding items to the cart, selecting colors and sizes, 
 * and navigating back to the homepage.
 * 
 * @authors CodeRed Team (Xavier, Miguel, Alfredo)
 * @version 1.0
 * @see itemView.fxml
 */
package com.codered.ecomerce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.codered.ecomerce.enums.Color;
import com.codered.ecomerce.enums.Size;
import com.codered.ecomerce.model.CartManager;
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
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * ItemViewController controls the item view page of the application. It handles the item view functionality
 */
public class ItemViewController extends App{

    @FXML
    private Label itemNameText, itemPriceText;
    @FXML
    private Button searchButton, checkoutButton; 
    @FXML
    private TextField quantityTextField, searchTextBox;
    @FXML
    private TextArea productDescriptionBox;
    @FXML 
    private ImageView itemImageView;
    @FXML 
    private StackPane imageStackPane;
    @FXML 
    private AnchorPane leftAnchorPane, colorAnchorPane;
    @FXML 
    private MenuBar menuBar;
    @FXML
    private HBox sizeHbox;
    
    
    private String selectedColor;
    private String selectedSize;
    private String itemName;
    private int itemID;

    private ArrayList<Color> itemColors = new ArrayList<>();
    private ArrayList<Size> itemSizes = new ArrayList<>();
    
    private CartManager cartItems = CartManager.getInstance();

    /*
     * Method to initialize the page and set the default values for the item view page
     */
    public void initialize()
    {
        
        itemImageView.fitWidthProperty().bind(imageStackPane.widthProperty());
        itemImageView.fitHeightProperty().bind(imageStackPane.heightProperty());
        createColorToggleButtons(itemColors);
        createSizeToggleButtons(itemSizes);
        
    }

    /**
     * Method to add the selected item to the customers cart
     * @throws IOException if there is an error loading the fxml file
     */
    @FXML
    public void AddtoCart() throws Exception {
        
        
        itemName = itemNameText.getText();
        productQuantity = Integer.parseInt(quantityTextField.getText());
        if(selectedColor != null && selectedSize != null && productQuantity != 0 && productQuantity <= currentVariant.getStock()) {
            System.out.println(quantityTextField.getText() +": "+ itemName + " added to cart! \n"+ "Color:"+ selectedColor + "\nSize: " + selectedSize);
            for(int i = 0; i < productQuantity; i++) {

                CartManager.addCartItem(currentVariant);
            }
        } else {
            System.out.println("Please select a color and size before adding to cart.");
        }
    }


    private void createSizeToggleButtons(ArrayList<Size> sizes) {
        if(sizes == null || sizes.isEmpty()) {
            System.out.println("No Sizes available"); // Default color if none are available
            return;
        }
        // Create a ToggleGroup for the size buttons
        // This ToggleGroup allows only one button to be selected at a time.
        ToggleGroup toggleGroupSize = new ToggleGroup();
        sizeHbox.getChildren().clear();

         // Define the custom order for sizes
        List<String> sizeOrder = List.of("Small", "Medium", "Large", "X-Large");

        // Sort the sizes based on the custom order
        sizes.sort((size1, size2) -> {
            String sizeName1 = switch (size1.toString()) {
                case "S" -> "Small";
                case "M" -> "Medium";
                case "L" -> "Large";
                case "XL" -> "X-Large";
                default -> size1.toString();
            };

            // Use a switch expression to map the size to its name
            // This is a more concise way to handle the mapping.
            String sizeName2 = switch (size2.toString()) {
                case "S" -> "Small";
                case "M" -> "Medium";
                case "L" -> "Large";
                case "XL" -> "X-Large";
                default -> size2.toString();
            };

            return Integer.compare(sizeOrder.indexOf(sizeName1), sizeOrder.indexOf(sizeName2));
        });

        // Create a Set to keep track of generated sizes
        Set<Size> generatedSizes = new HashSet<>();

        // Iterate through the sorted sizes and create buttons for each size
        for (Size size : sizes) {
            if(size == null) {
                System.out.println("Skipping Null Size"); // Default color if none are available
                continue;
            }
            if(generatedSizes.contains(size)) {
                System.out.println("Skipping already generated size: " + size);
                continue;
            }
            generatedSizes.add(size);
            String currentSize =  
                switch (size.toString()) {
                case "S" -> "Small";
                case "M" -> "Medium";
                case "L" -> "Large";
                case "XL" -> "X-Large";
                default -> size.toString();
            };
            
            ToggleButton sizeButton = new ToggleButton(currentSize);
            sizeButton.setStyle("-fx-text-fill: white; -fx-font-style: bold;");
            sizeButton.setPrefHeight(99);
            sizeButton.setPrefWidth(100);
            sizeButton.setToggleGroup(toggleGroupSize);

            
            sizeButton.setOnAction(event -> {
                Toggle selectedToggle = toggleGroupSize.getSelectedToggle();
                if (selectedToggle != null) {
                    selectedSize = ((ToggleButton) selectedToggle).getText();
                    System.out.println("Size " + selectedSize + " selected!");
                }
            });
            
            sizeHbox.getChildren().add(sizeButton);
        }
        sizeHbox.spacingProperty().set(15);
    }
    /**
     * Method to create color toggle buttons for the item view page
     * @param colors the list of colors to be displayed as toggle buttons
     */
    // This method creates toggle buttons for each color in the provided list and adds them to the colorAnchorPane.
    private void createColorToggleButtons(ArrayList<Color> colors) {
        
        ToggleGroup toggleGroupColor = new ToggleGroup();
        double layoutX = 0;

        Set<Color> generatedColors = new HashSet<>();
        for (Color color : colors) {
            if(colors == null || colors.isEmpty()) {
                System.out.println("No Colors available"); // Default color if none are available
                break;
            }
            if(generatedColors.contains(color)) {
                System.out.println("Skipping already generated color: " + color);
                continue;
            }
            //Takes care of case with rainbow color
            if (color.toString().equalsIgnoreCase("RAINBOW")) {
                System.out.println("Skipping color: RAINBOW");
                continue;
            }
            generatedColors.add(color);

           
            ToggleButton colorButton = new ToggleButton(color.toString());
            colorButton.setStyle("-fx-background-color: " + color + "; -fx-text-fill: black;");
            colorButton.setPrefHeight(52);
            colorButton.setPrefWidth(52);
            colorButton.setLayoutX(layoutX);
            colorButton.setLayoutY(0);
            colorButton.setToggleGroup(toggleGroupColor);

            
            colorButton.setOnAction(event -> {

                Toggle selectedToggle = toggleGroupColor.getSelectedToggle();
                if (selectedToggle != null) {
                    selectedColor = ((ToggleButton) selectedToggle).getText();
                    System.out.println("Color " + selectedColor + " selected!");
                }
            });
            
            colorAnchorPane.getChildren().add(colorButton);
            layoutX += 60;
        }
    }

    /**
     *  Method that returns the user to the homepage by clicking on the home title card
     * @param MouseEvent "click" event that triggers the method
     * @throws IOException if there is an error loading the fxml file
     */
    @FXML
    public void returnPrimary(MouseEvent event) throws IOException {
        // Get the current stage
        App.switchScene("primary", event);
        // Test (successful)
        System.out.println("Returning to homepage...");
    }

    /*
     * Helper method to switch to the itemView of a particular item selected from the homepage 
     * and to return the image of the selected item
     */
    public void setItemID(int itemID)
    {
        this.itemID = itemID;
    }

    public void setItemImage(Image image)
    {
        itemImageView.setImage(image);
    }

    public void setImage(Image image) {
        if (image != null) {
            itemImageView.setImage(image);
        } else {
            System.out.println("Image is null!");
        }
    }

    /**
     * Method to set the variant of the item selected from the homepage
     * @param variant the variant of the item selected
     */
    // This method sets the item name and price based on the selected variant.
    public void setVariant(Variant variant) {
        currentVariant = variant;
        List<Product> products = CentralShoppingSystem.getProducts();
        itemNameText.setText(products.get(variant.getID()).getName());
        itemPriceText.setText("$" + variant.getPrice());

        itemColors = products.get(variant.getID()).getColors();
        itemSizes = products.get(variant.getID()).getSizes();
        setItemID(variant.getID());
        if(itemColors == null) {
            itemColors = new ArrayList<>();
            itemColors.add(Color.PINK); // Default color if none are available
        }
        createColorToggleButtons(itemColors);
        createSizeToggleButtons(itemSizes);
    }

    /** 
     * Method to add 1 to the current quantity in the TextField
     * @throws IOException if there is an error loading the fxml file 
     */
    public void addQuantity() throws Exception {
        quantityTextField.setText(String.valueOf(Integer.parseInt(quantityTextField.getText()) + 1));
    }

    /**
     * Method to subtract 1 from the current quantity in the TextField
     * @throws IOException if there is an error loading the fxml file
     */
    public void subtractQuantity() throws Exception {
        if(Integer.parseInt(quantityTextField.getText()) > 0) {
            quantityTextField.setText(String.valueOf(Integer.parseInt(quantityTextField.getText()) - 1));
        } else {
            System.out.println("Quantity cannot be less than 0.");
        }
    }

    /**
     * Method to add functionality of the checkout button within the itemView page
     * @param event the mouse event that triggers the method
     * @throws IOException if there is an error loading the fxml file
     * @see checkoutView.fxml
     */
    public void checkoutView(ActionEvent event) throws IOException
    {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("checkoutView.fxml"));
                Parent root = loader.load();

                // Get the current stage
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                // Set the new scene
                stage.setScene(new Scene(root));
                stage.setTitle("Checkout Page");
                stage.show();
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

    /**
     * Method to add functionality of the menu serch bar within the itemView page
     * @param even the mouse event that triggers the method
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
    
}
