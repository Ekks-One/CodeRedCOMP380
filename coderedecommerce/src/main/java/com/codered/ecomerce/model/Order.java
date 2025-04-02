package com.codered.ecomerce.model;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;


public class Order 
{
    private int orderID;
    private int customerID;
    private List<Product> orderItems;
    private enum OrderStatus{PENDING, SHIPPED, DELIVERED, CANCELLED, DELAYED};
    private int orderCount;
    private LocalDateTime orderDate;

    //contrusctor to initialize order with the approriate details
    public Order(int orderID, int customerID, List<Product> orderItems, int orderCount, LocalDateTime orderDate)
    {
        this.orderID = orderID;
        this.customerID = customerID;
        this.orderItems = orderItems;
        this.orderCount = orderCount;
        this.orderDate = orderDate;
    }

    public boolean generateOrderID()
    {
        //returning true for now until database gets implemented
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
