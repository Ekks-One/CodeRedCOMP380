package com.codered.ecomerce;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class ItemViewController {
    
    @FXML
    private ImageView imageView;

    @FXML
    private AnchorPane parentPane;

    public void initialize() {
        imageView.fitWidthProperty().bind(parentPane.widthProperty());
        imageView.fitHeightProperty().bind(parentPane.heightProperty());
    }
}
