package com.codered.ecomerce.model;

public class CustomerManager {

    /**
     * The {@code CustomerManager} class is a singleton that manages the current customer
     * in the application. It provides methods to set, get, and clear the current customer.
     * This class is used to store the current customer information during the checkout process.
     * 
     * @author CodeRed Team (Miguel)
     * @version 1.0
     */
    private static Customer currentCustomer;

    public static void setCustomer(Customer customer) {
        currentCustomer = customer;
    }

    public static Customer getCustomer() {
        return currentCustomer;
    }

    public static void clearCustomer() {
        currentCustomer = null;
    }
}
