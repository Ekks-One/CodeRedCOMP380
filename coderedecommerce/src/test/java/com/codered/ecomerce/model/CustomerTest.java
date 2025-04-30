/**
 * CodeRed E-Commerce System
 * This {@code CustomerTest} class is a test class for the {@code Customer} class
 * and contains unit tests to verify the functionality of the Customer class.
 * It includes test for the constructor, updateInfo, getters, and setters of the Customer class.
 * 
 * @author CodeRed Team (Alfredo, Xavier)
 * @version 1.0
 * @see customer
 * created 04/29/2025
 */
package com.codered.ecomerce.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

public class CustomerTest 
{
    @Test 
    public void testCustomerConstrutor()
    {
        Customer customer = new Customer("John", "Doe", 001, "johndoe@my.csun.edu");
        assertEquals("John", customer.getFname());
        assertEquals("Doe", customer.getLname());
        assertEquals(001, customer.getID());
        assertEquals("johndoe@my.csun.edu", customer.getCustomerEmail());
    }
    
    @Test
    public void testUpdateInfo()
    {
        Customer customer = new Customer("Mike", "Smith", 004, "mikesmith@gmail.com");
        String[] newEmail = {"mikesmith@my.csun.edu"};
        customer.updateInfo(newEmail);    
    }

    @Test
    public void testSettersAndGetters()
    {
        Customer customer = new Customer("Jane", "Smith", 002, "janedoe@email.com");
        customer.setFirstName("Janet");
        customer.setLastName("Doe");
        customer.setID(003);
        customer.setCustomerEmail("janetdoe@email.com");

        assertEquals("Janet", customer.getFname());
        assertEquals("Doe", customer.getLname());
        assertEquals(3, customer.getID());
        assertEquals("janetdoe@email.com", customer.getCustomerEmail());

    }
}
