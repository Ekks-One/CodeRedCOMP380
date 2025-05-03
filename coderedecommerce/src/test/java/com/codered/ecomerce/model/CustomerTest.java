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

class CustomerTest {

    @Test 
    public void testCustomerConstrutor() {
        Customer customer = new Customer("John", "Doe", 1, "johndoe@my.csun.edu");

        System.out.println("Testing Customer Constructor:");
        System.out.println("Expected first name: John, Observed: " + customer.getFname());
        System.out.println("Expected last name: Doe, Observed: " + customer.getLname());
        System.out.println("Expected ID: 1, Observed: " + customer.getID());
        System.out.println("Expected email: johndoe@my.csun.edu, Observed: " + customer.getCustomerEmail());

        assertEquals("John", customer.getFname(), 
            "Expected first name: John, but got: " + customer.getFname());
        assertEquals("Doe", customer.getLname(), 
            "Expected last name: Doe, but got: " + customer.getLname());
        assertEquals(1, customer.getID(), 
            "Expected ID: 1, but got: " + customer.getID());
        assertEquals("johndoe@my.csun.edu", customer.getCustomerEmail(), 
            "Expected email: johndoe@my.csun.edu, but got: " + customer.getCustomerEmail());
    }
    
    @Test
    public void testUpdateInfo() {
        Customer customer = new Customer("Mike", "Smith", 4, "mikesmith@gmail.com");
        String[] newEmail = {"mikesmith@my.csun.edu"};
        customer.updateInfo(newEmail);

        System.out.println("Testing Update Info:");
        System.out.println("Expected updated email: mikesmith@my.csun.edu, Observed: " + customer.getCustomerEmail());

        assertEquals("mikesmith@my.csun.edu", customer.getCustomerEmail(), 
            "Expected updated email: mikesmith@my.csun.edu, but got: " + customer.getCustomerEmail());
    }

    @Test
    public void testSettersAndGetters() {
        Customer customer = new Customer("Jane", "Smith", 2, "janedoe@email.com");
        customer.setFirstName("Janet");
        customer.setLastName("Doe");
        customer.setID(3);
        customer.setCustomerEmail("janetdoe@email.com");

        System.out.println("Testing Setters and Getters:");
        System.out.println("Expected first name: Janet, Observed: " + customer.getFname());
        System.out.println("Expected last name: Doe, Observed: " + customer.getLname());
        System.out.println("Expected ID: 3, Observed: " + customer.getID());
        System.out.println("Expected email: janetdoe@email.com, Observed: " + customer.getCustomerEmail());

        assertEquals("Janet", customer.getFname(), 
            "Expected first name: Janet, but got: " + customer.getFname());
        assertEquals("Doe", customer.getLname(), 
            "Expected last name: Doe, but got: " + customer.getLname());
        assertEquals(3, customer.getID(), 
            "Expected ID: 3, but got: " + customer.getID());
        assertEquals("janetdoe@email.com", customer.getCustomerEmail(), 
            "Expected email: janetdoe@email.com, but got: " + customer.getCustomerEmail());
    }
}