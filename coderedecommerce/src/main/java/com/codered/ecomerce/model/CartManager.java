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
    private static List<CartItem> cartItems = new ArrayList<>();


    //adds item to the cart
    public static void addCartItem (CartItem cartItem) {
        cartItems.add(cartItem);
    }

    //removes item from the cart
    public static void removeCartItem(CartItem cartItem) {
        cartItems.remove(cartItem);
    }

    //contains all items in the cart
    public static List<CartItem> getCartItems() {
        return cartItems;
    }
    

    public static void clearCart() {
        cartItems.clear();
    }


}
