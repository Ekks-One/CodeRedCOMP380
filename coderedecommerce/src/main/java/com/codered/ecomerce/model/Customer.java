/**
 * Codered E-Commerce System
 * The {@code Customer} class represents a customer in the e-commerce system.
 * It contains all pertinent information about the customer such as their name,
 * email, phone number, shipping address, and the date they were created.
 * 
 * <p> This class provides methods to retrieve and update customer information.</p>
 * 
 * @author CodeRed Team (Jesus, Xavier, Alfredo)
 * @version 1.0
 * Created on 04-01-2025
 */
package com.codered.ecomerce.model;

import java.util.List;
import java.util.LinkedList;
import java.time.LocalDateTime;
import java.sql.*;

/**
 * customer class in the ecommerce system representing an individual customer with
 * their respective details and attributes.
 */
public class Customer 
{
    private String firstName;
    private String lastName;
    private int id;
    private String[] shippingAddress;
    private String emailAddress;
    private String phoneNumber;
    private LocalDateTime createdAt;

    /**
     * constructor to initialize customer with the appropriate details.
     * 
     */
    public Customer(String firstName, String lastName, int id, String emailAddress)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.emailAddress = emailAddress;
    }

    /**
     * getters returning all pertinent information about the customer.
     */ 
    public String getCustomerName()
    {
        return firstName + " " + lastName;
    }

    public int getID()
    {
        return id;
    }

    public String[] getShippingAddress()
    {
        return shippingAddress;
    }

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

    /**
     * setters to set the customer details after the customer has been created.
     * This is useful for updating customer information.
     */ 
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setShippingAddress(String[] shippingAddress)
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

    /**
     * method to print the customer details to the console for debugging purposes
     * and to verify the customer information.
     */
    public void print(){
        System.out.println("Customer id"+id+" fname= "+firstName+" lname = "+lastName+" email= "+emailAddress);
    }

    /**
     * Method to update the customer information. This method takes in the new
     * @param newShippingAddress
     * @param newEmailAddress
     * @param newPhoneNumber
     */
    public void updateInfo(String[] newShippingAddress, String newEmailAddress, String newPhoneNumber)
    {
        setShippingAddress(newShippingAddress);
        setCustomerEmail(newEmailAddress);
        setPhoneNumber(newPhoneNumber);
    }

    /**
     * method to display the orders placed by the customer.
     */
    public void displayOrders()
    {
        //leaving void for now until database gets implemented 
        //it'll require a List Order to be passed in as a parameter and then
        //return a List Order of the orders that were placed by the customer.
    }


}
