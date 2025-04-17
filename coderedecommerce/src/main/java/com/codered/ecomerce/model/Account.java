/**
 * CodeRed E-Commerce System
 * This {@code Account} class represents a user account in the e-commerce system.
 * It contains attributes such as customer ID, password, username, email,
 * and a list of payment methods.
 * 
 * @author CodeRed Team (Jesus)
 * @version 1.0
 * @created 01/12/2025
 */
package com.codered.ecomerce.model;

import java.util.*;
import com.codered.ecomerce.enums.*;

/**
 * This class represents a user account in the e-commerce system.
 */
public class Account {
    int customerId;
    String password;
    String userName;
    String email;
    ArrayList<PayMethod> methods;

    Account(Customer customer, String password, String userName){
        this.customerId = customer.getID();
        this.password = password;
        this.userName = userName;
        this.email = customer.getCustomerEmail();
    }

}
