/**
 * Codered E-Commerce System
 * The {@code Payment} class represents a payment in the e-commerce system.
 * It contains all pertinent information about the payment such as the payment ID,
 * total amount, payment method, product amount, shipping amount, tax amount,
 * and discount amount.
 * 
 * <p> This class provides methods to retrieve and update payment information.</p>
 * 
 * @author CodeRed Team (Xavier, Alfredo)
 * @version 1.0
 * @created on 04-01-2025
 */
package com.codered.ecomerce.model;

/**
 * This class represents a payment object in the e-commerce system.
 * It contains methods to retrieve and update payment information.
 */
public class Payment {
    private int paymentID;
    private double totalAmount;
    private enum paymentMethod {CreditCard, Paypal, DebitCard, ApplePay} 
    private double productAmount;
    private double shippingAmount;
    private final double taxAmount;
    private double discountAmount;

    /**
     * constructor method to initialize payment with the appropriate details.
     * @param paymentID, @param totalAmount, @param paymentMethod, @param productAmount, 
     * @param shippingAmount, @param taxAmount, @param discountAmount
     */
    public Payment(int paymentID, double totalAmount, String paymentMethod, double productAmount, double shippingAmount, double taxAmount, double discountAmount) {
        this.paymentID = paymentID;
        this.totalAmount = totalAmount;
        this.productAmount = productAmount;
        this.shippingAmount = shippingAmount; 
        this.taxAmount = 0.11 * totalAmount; // Assuming tax is 11% of total amount
        this.discountAmount = discountAmount;
    }
   

    /**
     * method to perform calculation of the total amount of the payment
     * @param productAmount, @param shippingAmount, @param taxAmount, @param discountAmount
     * 
     * @return total amount of the payment
     */ 
    public double getTotalAmount(int productAmount, int shippingAmount, int taxAmount, int discountAmount) {
        return productAmount + shippingAmount + taxAmount - discountAmount;
    }


    /*
     * Getter methods responsible for retrieving all pertinent information about the payment
     * @return paymentID, totalAmount, productAmount, shippingAmount,
     * taxAmount, and discountAmount.
     */
    public int getPaymentID() {
        return paymentID;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getProductAmount() {
        return productAmount;
    }

    public double getTaxAmount() {
        return taxAmount;
    }
    
    public double getDiscountAmount() {
        return discountAmount;
    }

    public double getShippingAmount() {
        return shippingAmount;
    }


    /**
     * Setter methods responsible for updating payment details after the payment has been created
     * @param paymentID, @param totalAmount, @param productAmount,
     * @param shippingAmount, @param taxAmount, @param discountAmount
     */ 
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }
   
    public void setProductAmount(double productAmount) {
        this.productAmount = productAmount;
    }

    public void setShippingAmount(double shippingAmount) {
        this.shippingAmount = shippingAmount;
    }
    
    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }
    


}
