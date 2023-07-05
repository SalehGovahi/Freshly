package com.example.freshly;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Customer extends Person{
    private ObservableList<String> cart= FXCollections.observableArrayList();
    private int wallet;
    private ObservableList<String> discountCode;

    public ObservableList<String> getCart() {
        return cart;
    }
    public void setCart(ObservableList<String> cart) {
        this.cart = cart;
    }

    public int getWallet() {
        return wallet;
    }

    public ObservableList<String> getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(ObservableList<String> discountCode) {
        this.discountCode = discountCode;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }
    public void addTOCart(int productId){
        cart.add(String.valueOf(productId));
    }
    public void makeDiscountCode(){
    Random random=new Random();
        this.discountCode=FXCollections.observableArrayList();
        for (int i = 0; i < 5; i++) {
            String discountCode="freshly";
            discountCode+=(random.nextInt(9)+10*i);
            this.discountCode.add(discountCode);
        }
    }
    public void printDiscountCode(){
        for (String a:
                discountCode) {
            System.out.println(a);
        }
    }
    public void removeFromCart(int productId){
        Integer id=productId;
        cart.remove(id);
    }
    public void printCart(){
        for (String a:
             cart) {
            System.out.println(a);
        }
    }
    public Customer(String username, String password, String firstname, String lastname, String phoneNumber, String emailAddress, ObservableList<String> cart, int wallet,ObservableList<String> discountCode) {
        super(username, password, firstname, lastname, phoneNumber, emailAddress, "customer");
        this.cart = cart;
        this.wallet = wallet;
        this.discountCode=discountCode;
    }
    public Customer(String username, String password, String firstname, String lastname, String phoneNumber, String emailAddress) {
        super(username, password, firstname, lastname, phoneNumber, emailAddress, "customer");
        makeDiscountCode();
    }

    /*public static void main(String[] args) {
        Customer c=new Customer("a","123","ali","mamad","123","a");
        c.printDiscountCode();
    }*/
}
