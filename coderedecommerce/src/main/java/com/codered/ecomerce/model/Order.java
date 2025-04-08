package com.codered.ecomerce.model;

import com.codered.ecomerce.model.Product;
import com.codered.ecomerce.model.Customer;
import java.util.List;
import java.util.LinkedList;
import java.time.LocalDateTime;


public class Order 
{
    private int orderID;
    private int customerID;
    private LinkedList<Product> orderItems;
    private enum OrderStatus {PENDING, SHIPPED, DELIVERED, CANCELLED, DELAYED};
    private OrderStatus orderStatus;
    private int orderCount;
    private LocalDateTime orderDate;

    //constructor to initialize order with the appropriate details
    public Order(int orderID, int customerID, LinkedList<Product> orderItems, int orderCount, LocalDateTime orderDate)
    {
        this.orderID = orderID;
        this.customerID = customerID;
        this.orderItems = orderItems;
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
        return customerID;
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

    public void setCustomerID(int customerID)
    {
        this.customerID = customerID;
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
    public boolean generateOrderID()
    {
        
        return true;
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
