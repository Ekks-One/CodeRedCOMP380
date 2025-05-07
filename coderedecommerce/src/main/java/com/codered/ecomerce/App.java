/**
 * CodeRed E-Commerce System
 * This {@code App} class is the main entry point of the application.
 * It initializes the JavaFX application, sets up the primary stage,
 * and provides methods for switching scenes and loading FXML files.
 * 
 * @author CodeRed Team (Xavier, Alfredo, Miguel)
 * @version 1.0
 * @see primary.fxml
 */
package com.codered.ecomerce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.codered.ecomerce.model.CartManager;
import com.codered.ecomerce.model.CentralShoppingSystem;
import com.codered.ecomerce.model.Variant;
import com.codered.ecomerce.sql.SearchProducts;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;



/**
 * JavaFX App
 */
public class App extends Application {
    public static ArrayList<Variant> searchResults;
    public static int productQuantity;
    public static Variant currentVariant;

    private static Scene scene;
    private static Parent primaryRoot; 


    /** 
     * Returns the primaryRoot (top element of fxml file) of the application 
     */

    public static Parent getPrimaryRoot() {
        return primaryRoot; // Return the primaryRoot
    }

    /** 
     * Returns current displayed Scene 
     */
    public static Scene getScene() {
        return scene; 
    }

    /**
     * This method is used to set the primary root of the application
     */
    @Override
    public void start(Stage stage) throws IOException {
        CartManager.getInstance(); // Initialize the CartManager
        CentralShoppingSystem.getInstance(); // Initialize the CentralShoppingSystem
        FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
        primaryRoot = loader.load(); // Initialize primaryRoot with the loaded FXML
        scene = new Scene(primaryRoot); // Use primaryRoot for the scene

        javafx.geometry.Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();

        stage.setTitle("Code Red E-Commerce System");
        stage.getIcons().add(new Image(App.class.getResourceAsStream("images/Code Red Logo (Mockup).png")));

        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Added a method for switching Scenes to be used in other controllers 
     * This method is used to switch scenes when a button is clicked.
     */  
    public static void switchScene(String fxml, MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource(fxml + ".fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

public static void search(String searchItem, ActionEvent event) throws IOException {
    if (searchItem == null || searchItem.trim().isEmpty()) {
        System.out.println("Please enter a valid search term.");
        return;
    }

    System.out.println("Taking you to Search Results!");
    searchItem = searchItem.toLowerCase(); // Normalize the search term

    // Perform the search
    List<Variant> searchResults = SearchProducts.Search(searchItem);

    if (searchResults == null || searchResults.isEmpty()) {
        System.out.println("No results found for: " + searchItem);
        // Optionally, show an alert or message to the user
        return;
    }

    try {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("searchResultsView.fxml"));
        Parent root = loader.load();

        // Get the controller instance
        searchResultsController controller = loader.getController();

        // Pass the searchItem and searchResults to the controller
        controller.setSearchItem(searchItem);
        controller.setSearchResults(searchResults);

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene
        stage.setScene(new Scene(root));
        stage.show();
        System.out.println("Searching for: " + searchItem);
    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Error loading search results page.");
    }
}

    public static void main(String[] args) {
        launch();
    }

}