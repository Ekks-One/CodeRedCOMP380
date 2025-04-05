package com.codered.ecomerce.model;

public class Payment {
    private int paymentID;
    private double totalAmount;
    private enum paymentMethod {CreditCard, Paypal, DebitCard, ApplePay} 
    private double productAmount;
    private double shippingAmount;
    private final double taxAmount;
    private double discountAmount;

    public Payment(int paymentID, double totalAmount, String paymentMethod, double productAmount, double shippingAmount, double taxAmount, double discountAmount) {
        this.paymentID = paymentID;
        this.totalAmount = totalAmount;
        this.productAmount = productAmount;
        this.shippingAmount = shippingAmount; 
        this.taxAmount = 0.11 * totalAmount; // Assuming tax is 11% of total amount
        this.discountAmount = discountAmount;
    }
   

    //Getter Methods
    public double getTotalAmount(int productAmount, int shippingAmount, int taxAmount, int discountAmount) {
        return productAmount + shippingAmount + taxAmount - discountAmount;
    }

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


    //Setter Methods
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
