/**
 * CodeRed E-Commerce System
 * The {@code PayMethod} class represents a payment method in the e-commerce system.
 * It contains all pertinent information about the payment method such as the account ID,
 * customer ID, and payment type.
 * 
 * @author CodeRed Team (Jesus)
 * @version 1.0
 * @created on 04/12/2025
 */
package com.codered.ecomerce.model;

import com.codered.ecomerce.enums.*;

/**
 * This class represents a payment method object in the e-commerce system.
 * It contains methods to retrieve and update payment method information.
 */
public class PayMethod {
    double accountID; // this is the cc# for credit cards
    int customerID;
    PaymentType type;
    
}
