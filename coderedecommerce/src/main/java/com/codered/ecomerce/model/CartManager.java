package com.codered.ecomerce.model;

import java.util.ArrayList;
import java.util.List;

/**
 * CartManager is a class that manages the shopping cart for the e-commerce application.
 * It provides methods to add, remove, and view items in the cart.
 * 
 * @author CodeRed Team (Miguel)
 * @version 1.0
 */

 
public class CartManager {
    private static List<Variant> cartItems = new ArrayList<Variant>();
    // Singleton instance of CartManager
    private static CartManager instance;


    /**
     * Singleton instance of CartManager
     * @return singleton instance
     */
    // This method returns the singleton instance of CartManager.
    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    // Private constructor to prevent instantiation
    // This ensures that the CartManager is a singleton and can only be accessed through getInstance()
    private CartManager () {

    }

    //adds item to the cart
    public static void addCartItem (Variant variant) {
        cartItems.add(variant);
    }

    //removes item from the cart
    public static void removeCartItem(Variant variant) {
        cartItems.remove(variant);
    }

    //contains all items in the cart
    public static List<Variant> getCartItems() {
        return cartItems;
    }
    

    public static void clearCart() {
        cartItems.clear();
    }


}
