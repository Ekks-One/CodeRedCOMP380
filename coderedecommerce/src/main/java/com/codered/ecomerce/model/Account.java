package com.codered.ecomerce.model;

import java.util.*;

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
