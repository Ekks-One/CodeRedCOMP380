package com.codered.ecomerce.model;

import java.util.List;
import java.util.LinkedList;
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
    public Customer(String customerName, int customerID, String shippingAddress, String emailAddress, String phoneNumber, LocalDateTime createdAt)
    {
        this.customerName = customerName;
        this.customerID = customerID;
        this.shippingAddress = shippingAddress;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
    }

    //getters
    public String getCustomerName()
    {
        return customerName;
    }

    public int getCustoemrID()
    {
        return customerID;
    }

    public String getShippingAddress()
    [
        return shippingAddress;
    ]

    public String getCustomerEmail()
    {
        return emailAddress;
    }

    public String getCustomerPhoneNumber()
    {
        return phoneNumber;
    }

    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }

    //setters
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public void setCustomerID(int customerID)
    {
        this.customerID = customerID;
    }

    public void setShippingAddress(String shippingAddress)
    {
        this.shippingAddress = shippingAddress;
    }

    public void setCustomerEmail(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public void updateInfo(String newShippingAddress, String newEmailAddress, String newPhoneNumber)
    {
        setShippingAddress(newShippingAddress);
        setCustomerEmail(newEmailAddress);
        setPhoneNumber(newPhoneNumber);
    }

    public void addCustomer(String customerName, int customerID, String shippingAddress, String emailAddress,String phoneNumber, LocalDateTime createdAt)
    {
        Customer newCustomer = new Customer(customerName, customerID, shippingAddress, emailAddress, phoneNumber, createdAt);
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
