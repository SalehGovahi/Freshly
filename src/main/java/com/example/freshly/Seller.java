package com.example.freshly;

import java.util.LinkedList;

public class Seller extends Person{
    private String company;

    public Seller(String username, String password, String firstname, String lastname, String phoneNumber, String emailAddress, String role,String Company) {
        super(username, password, firstname, lastname, phoneNumber, emailAddress, role);
        this.company=Company;
    }
}
