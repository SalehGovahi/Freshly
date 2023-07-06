package com.example.freshly;

public interface CreatingCartOfCostumerAndUpdateDatabase {
    String createCart(String prodId,int count);
    void addToDataBase(String cart);
}
