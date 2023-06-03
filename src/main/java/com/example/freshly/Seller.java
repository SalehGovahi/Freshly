package com.example.freshly;

import java.util.LinkedList;

public class Seller extends Person{
<<<<<<< Updated upstream
    private LinkedList<Integer> productsOnSale=new LinkedList<Integer>();
    public void addProduct(int productId){
        productsOnSale.add(productId);
    }
    public void startAnAuction(int productId){

    }
    public Seller(String username, String password, String firstname, String lastname, String phoneNumber, String emailAddress, String role) {
        super(username, password, firstname, lastname, phoneNumber, emailAddress, role);
    }

    public Seller(String username, String password, String firstname, String lastname, String phoneNumber, String emailAddress, String role, LinkedList<Integer> productsOnSale) {
        super(username, password, firstname, lastname, phoneNumber, emailAddress, role);
        this.productsOnSale = productsOnSale;
    }
=======
    private String company;

    public Seller(String username, String password, String firstname, String lastname, String phoneNumber, String emailAddress, String role) {
        super(username, password, firstname, lastname, phoneNumber, emailAddress, role);
    }
>>>>>>> Stashed changes
}
