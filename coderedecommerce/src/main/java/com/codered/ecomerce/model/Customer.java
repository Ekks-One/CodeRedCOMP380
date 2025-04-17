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
 * @created on 04-01-2025
 */
package com.codered.ecomerce.model;

import java.util.*;
import java.time.LocalDateTime;

/**
 * customer class in the ecommerce system representing an individual customer with
 * their respective details and attributes.
 */
public class Customer 
{
    private String firstName;
    private String lastName;
    private Account account;
    private int id;
    private String[] shippingAddress;
    private String emailAddress;
    private String phoneNumber;
    private List<Order> orders;
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

    public Customer(String firstName, String lastName, String address, String state, String emailAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.shippingAddress = new String[]{address, state};
    }

    //public Customer(String firstName2, String lastName2, String address, String selectedState, String email) {
        //TODO Auto-generated constructor stub
    //}

    /**
     * getters returning all pertinent information about the customer.
     */ 
    public String getLname()
    {
        return lastName;
    }

    public String getFname(){
        return firstName;
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

    public boolean HasAccount(){
        return !this.account.equals(null);
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

    public void setID(int id){
        this.id = id;
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
