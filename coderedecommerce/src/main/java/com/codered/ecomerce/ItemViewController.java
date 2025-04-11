package com.codered.ecomerce;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class ItemViewController {

    @FXML
    private Text itemNameText;

    @FXML
    private Text itemPriceText;

    @FXML
    private TextField quantityTextField;

    
    private String selectedColor;
    private String selectedSize;
    private String itemName;
    private int quantityAmmount;

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

}
