/**
 * CodeRed E-Commerce Application
 * This {@code ItemViewController} class is used to handle the item view of the application.
 * It contains methods to handle the item view functionality, including adding items to the cart, selecting colors, sizes, 
 * and materials, and navigating back to the homepage.
 * 
 * @authors CodeRed Team (Xavier, Miguel, Alfredo, Jesus)
 * @version 1.0
 * @see itemView.fxml
 */
package com.codered.ecomerce;

import java.io.IOException;
import java.util.*;

import com.codered.ecomerce.enums.*;
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
import javafx.scene.control.*;
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
public class ItemViewController extends App {

    @FXML
    private Label itemNameText, itemPriceText, itemStockText;
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
    @FXML
    private HBox materialHBox;
    
    private String selectedColor;
    private String selectedSize;
    private String selectedMaterial;
    private String itemName;
    private int itemID;
    private int productQuantity;
    private Variant currentVariant;

    private ArrayList<Color> itemColors = new ArrayList<>();
    private ArrayList<Size> itemSizes = new ArrayList<>();
    private ArrayList<Material> itemMaterials = new ArrayList<>();
    
    private CartManager cartItems = CartManager.getInstance();

    public void initialize() {
        itemImageView.fitWidthProperty().bind(imageStackPane.widthProperty());
        itemImageView.fitHeightProperty().bind(imageStackPane.heightProperty());
        createColorToggleButtons(itemColors);
        createSizeToggleButtons(itemSizes);
        createMaterialToggleButtons(itemMaterials);
    }

    @FXML
    public void AddtoCart() throws Exception {
        itemName = itemNameText.getText();
        productQuantity = Integer.parseInt(quantityTextField.getText());
        if (productQuantity != 0 && productQuantity <= currentVariant.getStock()) {
            System.out.println(quantityTextField.getText() + ": " + itemName + " added to cart! \n" +
                "Color: " + selectedColor + "\nSize: " + selectedSize + "\nMaterial: " + selectedMaterial);
            for (int i = 0; i < productQuantity; i++) {
                CartManager.addCartItem(currentVariant);
            }
        } else {
            System.out.println("Please select a valid quantity (1 to " + currentVariant.getStock() + ").");
        }
    }

    private void createSizeToggleButtons(ArrayList<Size> sizes) {
        if (sizes == null || sizes.isEmpty()) {
            System.out.println("No Sizes available");
            return;
        }
        ToggleGroup toggleGroupSize = new ToggleGroup();
        sizeHbox.getChildren().clear();

        List<String> sizeOrder = List.of("Small", "Medium", "Large", "X-Large");

        sizes.sort((size1, size2) -> {
            String sizeName1 = switch (size1.toString()) {
                case "S" -> "Small";
                case "M" -> "Medium";
                case "L" -> "Large";
                case "XL" -> "X-Large";
                default -> size1.toString();
            };
            String sizeName2 = switch (size2.toString()) {
                case "S" -> "Small";
                case "M" -> "Medium";
                case "L" -> "Large";
                case "XL" -> "X-Large";
                default -> size2.toString();
            };
            return Integer.compare(sizeOrder.indexOf(sizeName1), sizeOrder.indexOf(sizeName2));
        });

        // Get valid sizes for the current color
        Set<String> validSizes = getValidSizesForColor(selectedColor);

        Set<Size> generatedSizes = new HashSet<>();
        for (Size size : sizes) {
            if (size == null) {
                System.out.println("Skipping Null Size");
                continue;
            }
            if (generatedSizes.contains(size)) {
                System.out.println("Skipping already generated size: " + size);
                continue;
            }
            generatedSizes.add(size);
            String currentSize = switch (size.toString()) {
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

            // Disable if size is not valid for the current color
            sizeButton.setDisable(!validSizes.contains(currentSize));

            sizeButton.setOnAction(event -> {
                Toggle selectedToggle = toggleGroupSize.getSelectedToggle();
                if (selectedToggle != null) {
                    selectedSize = ((ToggleButton) selectedToggle).getText();
                    String enumSize = switch (selectedSize) {
                        case "Small" -> "S";
                        case "Medium" -> "M";
                        case "Large" -> "L";
                        case "X-Large" -> "XL";
                        default -> selectedSize;
                    };
                    ArrayList<Product> products = CentralShoppingSystem.getProducts();
                    Product product = products.get(currentVariant.getID());
                    Variant targetVariant = null;
                    // Prioritize size and color, adjust material if needed
                    for (Variant v : product.getVariants()) {
                        if (v != null &&
                            v.getColor() == currentVariant.getColor() &&
                            v.getSize() == Size.valueOf(enumSize.toUpperCase())) {
                            targetVariant = v;
                            break; // Take the first valid material for this color/size
                        }
                    }
                    if (targetVariant != null) {
                        this.setVariant(targetVariant);
                        System.out.println("Size " + selectedSize + " selected, material set to " + targetVariant.getMaterial());
                    }
                }
            });
            
            sizeHbox.getChildren().add(sizeButton);
        }
        sizeHbox.spacingProperty().set(15);
    }

    private void createColorToggleButtons(ArrayList<Color> colors) {
        if (colors == null || colors.isEmpty()) {
            System.out.println("No Colors available");
            return;
        }
        ToggleGroup toggleGroupColor = new ToggleGroup();
        double layoutX = 0;

        Set<Color> generatedColors = new HashSet<>();
        for (Color color : colors) {
            if (generatedColors.contains(color)) {
                System.out.println("Skipping already generated color: " + color);
                continue;
            }
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

            // Color buttons are always enabled
            colorButton.setDisable(false);

            colorButton.setOnAction(event -> {
                Toggle selectedToggle = toggleGroupColor.getSelectedToggle();
                if (selectedToggle != null) {
                    selectedColor = ((ToggleButton) selectedToggle).getText();
                    ArrayList<Product> products = CentralShoppingSystem.getProducts();
                    Product product = products.get(currentVariant.getID());
                    // Find a variant with the selected color, preferring current size
                    Variant targetVariant = null;
                    for (Variant v : product.getVariants()) {
                        if (v != null && v.getColor() == Color.valueOf(selectedColor.toUpperCase())) {
                            if (v.getSize() == currentVariant.getSize()) {
                                targetVariant = v; // Prefer current size
                                break;
                            } else if (targetVariant == null) {
                                targetVariant = v; // Fallback
                            }
                        }
                    }
                    if (targetVariant != null) {
                        this.setVariant(targetVariant);
                        System.out.println("Color " + selectedColor + " selected!");
                    }
                }
            });
            
            colorAnchorPane.getChildren().add(colorButton);
            layoutX += 60;
        }
    }

    private void createMaterialToggleButtons(ArrayList<Material> materials) {
        if (materials == null || materials.isEmpty()) {
            System.out.println("No Materials available");
            return;
        }
        ToggleGroup toggleGroupMaterial = new ToggleGroup();
        materialHBox.getChildren().clear();

        // Get valid materials for the current color and size
        Set<String> validMaterials = getValidMaterialsForColorAndSize(selectedColor, selectedSize);

        Set<Material> generatedMaterials = new HashSet<>();
        for (Material m : materials) {
            if (m == null) {
                System.out.println("Skipping Null Material");
                continue;
            }
            if (generatedMaterials.contains(m)) {
                System.out.println("Skipping generated material: " + m);
                continue;
            }
            generatedMaterials.add(m);

            ToggleButton materialButton = new ToggleButton(m.toString());
            materialButton.setStyle("-fx-text-fill: white; -fx-font-style: bold;");
            materialButton.setPrefHeight(99);
            materialButton.setPrefWidth(100);
            materialButton.setToggleGroup(toggleGroupMaterial);

            // Disable if material is not valid for the current color and size
            materialButton.setDisable(!validMaterials.contains(m.toString()));

            materialButton.setOnAction(event -> {
                Toggle selectedToggle = toggleGroupMaterial.getSelectedToggle();
                if (selectedToggle != null) {
                    selectedMaterial = ((ToggleButton) selectedToggle).getText();
                    ArrayList<Product> products = CentralShoppingSystem.getProducts();
                    Product product = products.get(currentVariant.getID());
                    for (Variant v : product.getVariants()) {
                        if (v != null &&
                            v.getColor() == currentVariant.getColor() &&
                            v.getSize() == currentVariant.getSize() &&
                            v.getMaterial() == Material.valueOf(selectedMaterial.toUpperCase())) {
                            this.setVariant(v);
                            System.out.println("Material " + selectedMaterial + " selected!");
                            break;
                        }
                    }
                }
            });

            materialHBox.getChildren().add(materialButton);
        }
        materialHBox.spacingProperty().set(15);
    }

    // Helper method to get valid sizes for a given color
    private Set<String> getValidSizesForColor(String color) {
        Set<String> validSizes = new HashSet<>();
        ArrayList<Product> products = CentralShoppingSystem.getProducts();
        Product product = products.get(currentVariant.getID());
        for (Variant v : product.getVariants()) {
            if (v != null && v.getColor().toString().equalsIgnoreCase(color)) {
                String sizeText = switch (v.getSize().toString()) {
                    case "S" -> "Small";
                    case "M" -> "Medium";
                    case "L" -> "Large";
                    case "XL" -> "X-Large";
                    default -> v.getSize().toString();
                };
                validSizes.add(sizeText);
            }
        }
        return validSizes;
    }

    // Helper method to get valid materials for a given color
    private Set<String> getValidMaterialsForColor(String color) {
        Set<String> validMaterials = new HashSet<>();
        ArrayList<Product> products = CentralShoppingSystem.getProducts();
        Product product = products.get(currentVariant.getID());
        for (Variant v : product.getVariants()) {
            if (v != null && v.getColor().toString().equalsIgnoreCase(color)) {
                validMaterials.add(v.getMaterial().toString());
            }
        }
        return validMaterials;
    }

    // Helper method to get valid materials for a given color and size
    private Set<String> getValidMaterialsForColorAndSize(String color, String size) {
        Set<String> validMaterials = new HashSet<>();
        ArrayList<Product> products = CentralShoppingSystem.getProducts();
        Product product = products.get(currentVariant.getID());
        String enumSize = switch (size) {
            case "Small" -> "S";
            case "Medium" -> "M";
            case "Large" -> "L";
            case "X-Large" -> "XL";
            default -> size;
        };
        for (Variant v : product.getVariants()) {
            if (v != null &&
                v.getColor().toString().equalsIgnoreCase(color) &&
                v.getSize().toString().equalsIgnoreCase(enumSize)) {
                validMaterials.add(v.getMaterial().toString());
            }
        }
        return validMaterials;
    }

    @FXML
    public void returnPrimary(MouseEvent event) throws IOException {
        App.switchScene("primary", event);
        System.out.println("Returning to homepage...");
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public void setItemImage(Image image) {
        itemImageView.setImage(image);
    }

    public void setImage(Image image) {
        if (image != null) {
            itemImageView.setImage(image);
        } else {
            System.out.println("Image is null!");
        }
    }

    public void setVariant(Variant variant) {
        currentVariant = variant;
        List<Product> products = CentralShoppingSystem.getProducts();
        Product product = products.get(variant.getID());
        itemNameText.setText(product.getName());
        itemPriceText.setText("$" + variant.getPrice());
        itemStockText.setText("Stock: " + variant.getStock());

        itemColors = product.getColors();
        itemSizes = product.getSizes();
        itemMaterials = product.getMaterials();

        setItemID(variant.getID());
        if (itemColors == null) {
            itemColors = new ArrayList<>();
            itemColors.add(Color.PINK);
        }
        if (itemMaterials == null) {
            itemMaterials = new ArrayList<>();
        }

        selectedColor = variant.getColor().toString();
        selectedSize = switch (variant.getSize().toString()) {
            case "S" -> "Small";
            case "M" -> "Medium";
            case "L" -> "Large";
            case "XL" -> "X-Large";
            default -> variant.getSize().toString();
        };
        selectedMaterial = variant.getMaterial().toString();

        // Recreate buttons with updated disabled states
        createColorToggleButtons(itemColors);
        createSizeToggleButtons(itemSizes);
        createMaterialToggleButtons(itemMaterials);

        // Apply black outline and selection
        for (Node node : colorAnchorPane.getChildren()) {
            if (node instanceof ToggleButton tb) {
                if (tb.getText().equals(selectedColor)) {
                    tb.setStyle("-fx-background-color: " + tb.getText() + "; -fx-text-fill: black; -fx-border-color: black; -fx-border-width: 2;");
                    tb.setSelected(true);
                } else {
                    tb.setStyle("-fx-background-color: " + tb.getText() + "; -fx-text-fill: black;");
                }
            }
        }
        for (Node node : sizeHbox.getChildren()) {
            if (node instanceof ToggleButton tb) {
                if (tb.getText().equals(selectedSize)) {
                    tb.setStyle("-fx-text-fill: white; -fx-font-style: bold; -fx-border-color: black; -fx-border-width: 2;");
                    tb.setSelected(true);
                } else {
                    tb.setStyle("-fx-text-fill: white; -fx-font-style: bold;");
                }
            }
        }
        for (Node node : materialHBox.getChildren()) {
            if (node instanceof ToggleButton tb) {
                if (tb.getText().equals(selectedMaterial)) {
                    tb.setStyle("-fx-text-fill: white; -fx-font-style: bold; -fx-border-color: black; -fx-border-width: 2;");
                    tb.setSelected(true);
                } else {
                    tb.setStyle("-fx-text-fill: white; -fx-font-style: bold;");
                }
            }
        }
    }

    public void addQuantity() throws Exception {
        quantityTextField.setText(String.valueOf(Integer.parseInt(quantityTextField.getText()) + 1));
    }

    public void subtractQuantity() throws Exception {
        if (Integer.parseInt(quantityTextField.getText()) > 0) {
            quantityTextField.setText(String.valueOf(Integer.parseInt(quantityTextField.getText()) - 1));
        } else {
            System.out.println("Quantity cannot be less than 0.");
        }
    }

    public void checkoutView(ActionEvent event) throws IOException {
        CartManager.getInstance();
        List<Variant> cartItems = CartManager.getCartItems();
        if(cartItems.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empty Cart");
            alert.setHeaderText(null);
            alert.setContentText("Your cart is empty. Please add items to your cart before proceeding to checkout.");
            alert.showAndWait();
        }
        else {
            System.out.println("Taking you to order checkout!");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("checkoutView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    @FXML
    public void cartView(ActionEvent event) throws IOException {
        System.out.println("Taking you to your cart!");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("cartView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

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
    public void search(ActionEvent event) throws IOException {
        String searchItem = searchTextBox.getText().trim();
        App.search(searchItem, event);
    }
}