package com.codered.ecomerce;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;



public class ItemViewController extends App{

    @FXML
    private Text itemNameText;
    @FXML
    private Text itemPriceText;
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
    private AnchorPane leftAnchorPane;
    
    private String selectedColor;
    private String selectedSize;
    private String itemName;
    private int quantityAmmount;
    private String itemID;

    /*
     * Method to initialize the page and set the default values for the item view page
     */
    public void initialize()
    {
        itemImageView.fitWidthProperty().bind(imageStackPane.widthProperty());
        itemImageView.fitHeightProperty().bind(imageStackPane.heightProperty());

    
    }

    //*Combine all item info on page together */

    public void AddtoCart() throws Exception {
        itemName = itemNameText.getText();
        quantityAmmount = Integer.parseInt(quantityTextField.getText());
        if(selectedColor != null && selectedSize != null && quantityAmmount != 0) {
            System.out.println(quantityTextField.getText() +": "+ itemName + " added to cart! \n"+ "Color:"+ selectedColor + "\nSize: " + selectedSize);
        } else {
            System.out.println("Please select a color and size before adding to cart.");
        }
    }

    //* Click CodeRedLogo, return to homepage */

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
    public void setItemID(String itemID)
    {
        this.itemID = itemID;
    }

    public void setItemImage(Image image)
    {
        itemImageView.setImage(image);
    }

    //* Returns Color Selected */

    public void colorSelect(ActionEvent event) throws Exception {
        ToggleButton selectedButton = (ToggleButton) event.getSource();
        selectedColor = selectedButton.getText();
        System.out.println("Color " + selectedColor + " selected!");
    }
    
    //* Returns Size Selected */

    public void sizeSelect(ActionEvent event) throws Exception {
        Button selctedButton = (Button) event.getSource();
        selectedSize = selctedButton.getText();
        System.out.println("Size " + selectedSize + " selected!");
    }

    //* " + Button " will add 1 to current Quantity in TextField  */
    public void addQuantity() throws Exception {
        quantityTextField.setText(String.valueOf(Integer.parseInt(quantityTextField.getText()) + 1));
    }

    //* " - Button " will subtract 1 to current Quantity in TextField  */
    public void subtractQuantity() throws Exception {
        if(Integer.parseInt(quantityTextField.getText()) > 0) {
            quantityTextField.setText(String.valueOf(Integer.parseInt(quantityTextField.getText()) - 1));
        } else {
            System.out.println("Quantity cannot be less than 0.");
        }
    }

    /*
     * Method to add functionality of the checkout button within the itemView page
     */
    public void checkoutView() throws IOException
    {
        //Linked to Checkout Button, but can change once we add something
        App.setRoot("checkoutView");
    }

    /*
     * Method to add functionality of the menu serch bar within the itemView page
     */
        public void menuSearch(ActionEvent event) throws IOException
    {
        String searchItem = ((MenuItem)event.getSource()).getText();
        //test (successful)
        System.out.println("Searching for: " + searchItem);
    }

    /*
     * Method to add functionality of the search button within the itemView page
     */
    public void search() throws IOException
    {
        String searchItem = searchTextBox.getText();
        //test(successful)
        System.out.println("Searching for: " + searchItem);
    }
}
