/**
 * CodRed E-Commerce System
 * This {@code CreditCard} class represents a credit card payment method.
 * It extends the {@code PayMethod} class and contains additional attributes
 * for credit card details.
 * 
 * @author CodeRed Team (Jesus)
 * @version 1.0
 * @created 01/12/2025
 */
package com.codered.ecomerce.model;

/**
 * This class represent a credit card payment method extended from the PayMethod class
 */
public class CreditCard extends PayMethod {
    int cvc;
    int expire;
    String name;
    String address;
    
}
