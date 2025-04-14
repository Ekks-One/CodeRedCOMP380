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

import javafx.application.Application;
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

    public static void main(String[] args) {
        launch();
    }

}