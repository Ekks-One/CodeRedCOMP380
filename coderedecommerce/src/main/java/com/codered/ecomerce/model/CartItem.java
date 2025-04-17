package com.codered.ecomerce.model;

/**
 * CartItem is a class that represents an item in the shopping cart.
 * It contains information about the product, quantity, selected color, and selected size.
 * 
 * @Author CodeRed Team (Miguel)
 * @version 1.0
 */

public class CartItem {
    private Product product;
    private int quantity;
    private String selectedColor;
    private String selectedSize;

    /// Constructor for CartItem
    public CartItem(Product product, int quantity, String selectedColor, String selectedSize) {
        this.product = product;
        this.quantity = quantity;
        this.selectedColor = selectedColor;
        this.selectedSize = selectedSize;
    }
    /// Getters and Setters

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getColor() {
        return selectedColor;
    }

    public String getSize() {
        return selectedSize;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}