package com.codered.ecomerce.model;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

//customer class in the ecommerce system representing an individual customer with
//their respective details and attributes.
public class Customer 
{
    private String firstName;
    private String lastName;
    private int id;
    private String shippingAddress;
    private String emailAddress;
    private String phoneNumber;
    private LocalDateTime createdAt;

    //constructor to initialize customer with the appropriate details.
    public Customer(String firstName, String lastName, int id, String emailAddress)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.emailAddress = emailAddress;
    }

    //getters
    public String getCustomerName()
    {
        return firstName + " " + lastName;
    }

    public int getID()
    {
        return id;
    }

    public String getShippingAddress()
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

    //setters
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
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

    public void print(){
        System.out.println("Customer fname= "+firstName+" lname = "+lastName+" email= "+emailAddress+" address= "+shippingAddress);
    }

    public void updateInfo(String newShippingAddress, String newEmailAddress, String newPhoneNumber)
    {
        setShippingAddress(newShippingAddress);
        setCustomerEmail(newEmailAddress);
        setPhoneNumber(newPhoneNumber);
    }

    public void browseProducts()
    {
        //leaving void for now until database gets implemented 
        //it'll require a List Product  to be passed in as a parameter and then
        //return a List Product of the products that are available for the customer to browse.
    }    

    public void displayOrders()
    {
        //leaving void for now until database gets implemented 
        //it'll require a List Order to be passed in as a parameter and then
        //return a List Order of the orders that were placed by the customer.
    }


}
