package com.codered.ecomerce.model;

import com.codered.ecomerce.model.Product;
import com.codered.ecomerce.model.Customer;
import java.util.List;
import java.util.LinkedList;
import java.time.LocalDateTime;


public class Order 
{
    private int orderID;
    private Customer customer;
    private LinkedList<Product> orderItems;
    private enum OrderStatus {PENDING, SHIPPED, DELIVERED, CANCELLED, DELAYED};
    private OrderStatus orderStatus;
    private int orderCount = 0;
    private LocalDateTime orderDate;

    //constructor to initialize order with the appropriate details
    public Order(int orderID, Customer customer, List<Product> orderItems, OrderStatus orderStatus, int orderCount, LocalDateTime orderDate)
    {
        this.orderID = orderID;
        this.customer = customer;
        this.orderItems = new LinkedList<>(orderItems);
        this.orderStatus = orderStatus;
        this.orderCount = orderCount;
        this.orderDate = orderDate;
    }

    //getters
    public int getOrderID()
    {
        return orderID;
    }

    public int getCustomerID()
    {
        return customer.getID();
    }

    public List<Product> getOrderItems()
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

    //setters
    public void setOrderID(int orderID)
    {
        this.orderID = orderID;
    }

    public void setCustomerID(Customer customer)
    {
        this.customer = customer;    
    }

    public void setOrderItems(List<Product> orderItems)
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
        
    //method to generate order ID
    public Order generateOrderID(Customer customer)
    {
        orderCount++;
        //test
        System.out.println("Generating orderID for customer: " + customer.getID());
        return new Order(orderCount, customer, new LinkedList<>(), OrderStatus.PENDING, orderCount, LocalDateTime.now());
    }

    public boolean checkStock()
    {
        //returning true for now until database gets implemented
        return true;
    }

    public void retrieveOrderDetails()
    {
        //leaving emptyf for now
    }

    public void orderhistory()
    {
        //leaving empty for now
    }

    public void allSystemOrders()
    {
        /*leaving empty for now, im thinking it might be better to implement this
        in a future sprint.*/
    }

    public void updateOrderStatus()
    {
        //leaving empty for now
    }

    public void sendConfirmationEmail()
    {
        //leaving empty for now
    }
}
