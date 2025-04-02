package com.codered.ecomerce.model;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

//customer class in the ecommerce system representing an individual customer with
//their respective details and attributes.
public class Customer 
{
    private String customerName;
    private int customerID;
    private String shippingAddress;
    private String emailAddress;
    private String phoneNumber;
    private LocalDateTime createdAt;

    //constructor to initialize customer with the appropriate details.
    public Customer(String customerName, int customerID, String shippingAddress, String emailAddress,
                    String phoneNumber, LocalDateTime createdAt)
    {
        this.customerName = customerName;
        this.customerID = customerID;
        this.shippingAddress = shippingAddress;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
    }

    public void updateInfo(String newShippingAddress, String newEmailAddress, String newPhoneNumber)
    {
        this.shippingAddress = newShippingAddress;
        this.emailAddress = newEmailAddress;
        this.phoneNumber = newPhoneNumber;
    }

    public void browseProducts()
    {
        //leaving void for now until database gets implemented 
        //it'll require a List<Product> to be passed in as a parameter and then
        //return a List<Product> of the products that are available for the customer to browse.
    }    

    public void displayOrders()
    {
        //leaving void for now until database gets implemented 
        //it'll require a List<Order> to be passed in as a parameter and then
        //return a List<Order> of the orders that were placed by the customer.
    }


}
