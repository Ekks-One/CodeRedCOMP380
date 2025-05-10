/**
 * Codered E-Commerce System
 * The {@code Order} class represents an order in the e-commerce system.
 * It contains all pertinent information about the order such as the order ID,
 * customer details, order items, order status, order count, and the date the order was placed.
 * 
 * <p> This class provides methods to retrieve and update order information.</p>
 * 
 * @author CodeRed Team (Alfredo, Xavier)
 * @version 1.0
 * @created on 04-01-2025
 */
package com.codered.ecomerce.model;

import com.codered.ecomerce.model.*;
import java.util.List;
import java.util.LinkedList;
import java.time.LocalDateTime;

/**
 * This class represents an order object in the e-commerce system.
 */
public class Order 
{
    private int orderID;
    private Customer customer;
    private LinkedList<Variant> orderItems;
    private enum OrderStatus {PENDING, SHIPPED, DELIVERED, CANCELLED, DELAYED};
    private OrderStatus orderStatus = OrderStatus.PENDING;
    private int orderCount = 0;
    private LocalDateTime orderDate;

    /**
    * constructor to initialize order with the appropriate details
    */
    public Order(Customer customer, List<Variant> orderItems, int orderCount, LocalDateTime orderDate)
    {
        this.customer = customer;
        this.orderItems = new LinkedList<Variant>(orderItems);
        this.orderCount = orderCount;
        this.orderDate = orderDate;
    }

        // No-argument constructor
        public Order() {
            this.customer = null; // Default to null
            this.orderItems = new LinkedList<>(); // Initialize an empty list
            this.orderCount = 0; // Default to 0
            this.orderDate = LocalDateTime.now(); // Default to the current date and time
        }

    /**
     * getters responsible for retrieving all pertinent info about the order.
     * @return orderID, customerID, orderItems, orderCount, orderStatus,
     * and orderDate.
     */
    public int getOrderID()
    {
        return orderID;
    }

    public int getCustomerID()
    {
        return customer.getID();
    }

    public List<Variant> getOrderItems()
    {
        return orderItems;
    }

    public int getOrderCount()
    {
        return orderCount;
    }

    public OrderStatus getOrderStatus()
    {
        return orderStatus;
    }

    public LocalDateTime getOrderDate()
    {
        return orderDate;
    }

    /**
     * setters responsible for updating order details after the order has 
     * been created
     * @param orderID, 
     * @param customerID,
     * @param orderItems,
     * @param orderCount,
     * @param orderStatus,
     * @param orderDate
     */
    public void setOrderID(int orderID)
    {
        this.orderID = orderID;
    }

    public void setCustomerID(Customer customer)
    {
        this.customer = customer;    
    }

    public void setOrderItems(List<Variant> orderItems)
    {
        this.orderItems = new LinkedList<>(orderItems);
    }

    public void setOrderStatus(OrderStatus orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    public void setOrderCount(int orderCount)
    {
        this.orderCount = orderCount;
    }

    public void setOrderDate(LocalDateTime orderDate)
    {
        this.orderDate = orderDate;
    }
        
    /**
     * method to generate order ID for a customer once they place an order
     * @param customer
     * @return order object with all the details of the order
     */
    public Order generateOrderID(Customer customer)
    {
        orderCount++;
        //test
        System.out.println("Generating orderID for customer: " + customer.getID());
        return new Order( customer, new LinkedList<>(), orderCount, LocalDateTime.now());
    }

    /**
     * method to check if an item is in stock before placing an order
     * @return true if the item is in stock, false otherwise
     */
    public boolean checkStock()
    {
        //returning true for now until database gets implemented
        return true;
    }

    /**
     * method to retrieve order details for a customer
     * 
     */
    public void retrieveOrderDetails()
    {
        //leaving emptyf for now
    }

    /**
     * method to retrive order history for a particular customer
     * @param customerID
     */
    public void orderhistory(int customerID)
    {
        //leaving empty for now
    }

    /**
     * method to retrieve all system orders
     * @return list of all orders in the system
     */
    public void allSystemOrders()
    {
        /*leaving empty for now, im thinking it might be better to implement this
        in a future sprint.*/
    }

    /**
     * method to update the status of an order
     * @param orderStatus the new status to set for the order
     */
    public void updateOrderStatus(OrderStatus orderStatus)
    {
        this.orderStatus = orderStatus; // Update the order status
    }
}
