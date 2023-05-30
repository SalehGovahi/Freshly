package com.example.freshly;

import java.util.LinkedList;
import java.util.Random;

public class Customer extends Person{
    private LinkedList<Integer> cart=new LinkedList<Integer>();
    private int wallet;
    private LinkedList<String> discountCode;

    public LinkedList<Integer> getCart() {
        return cart;
    }
    public void setCart(LinkedList<Integer> cart) {
        this.cart = cart;
    }

    public int getWallet() {
        return wallet;
    }

    public LinkedList<String> getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(LinkedList<String> discountCode) {
        this.discountCode = discountCode;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }
    public void addTOCart(int productId){
        cart.add(productId);
    }
    public void makeDiscountCode(){
    Random random=new Random();
        this.discountCode=new LinkedList<String>();
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
        for (Integer a:
             cart) {
            System.out.println(a);
        }
    }
    public Customer(String username, String password, String firstname, String lastname, String phoneNumber, String emailAddress, LinkedList<Integer> cart, int wallet,LinkedList<String>discountCode) {
        super(username, password, firstname, lastname, phoneNumber, emailAddress, "customer");
        this.cart = cart;
        this.wallet = wallet;
        this.discountCode=discountCode;
    }
    public Customer(String username, String password, String firstname, String lastname, String phoneNumber, String emailAddress) {
        super(username, password, firstname, lastname, phoneNumber, emailAddress, "customer");
        makeDiscountCode();
    }

    public static void main(String[] args) {
        Customer c=new Customer("a","123","ali","mamad","123","a");
        c.printDiscountCode();
    }
}
