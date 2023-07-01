package com.example.freshly;

import com.example.freshly.Exception.NoProductInTheDatabase;
import com.example.freshly.Exception.OutOfNumberToDecrease;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class DeleteProductOfDataBase {
    private static String  deletingProdOfDatabase(String Username , String productId , int count) throws NoProductInTheDatabase, OutOfNumberToDecrease {
        //first is productId second is count of product
        HashMap<String,String> cart = new HashMap<>();
        ObservableList<String> data = readCartAndDiscountCodeOfCostumerFromDatabase.cartOfCostumer(Username);
        for (String datum : data) {
            String[] infos = datum.split(separator());
            cart.put(infos[0], infos[1]);
        }
        //System.out.println(cart.get("2"));
        int sw = 0;
        for (String id:
                cart.keySet()) {
            if (id.equals(productId)) {
                int number = Integer.parseInt(cart.get(id));
                if (number-count >= 0) {
                    number-=count;
                }
                else {
                    throw new OutOfNumberToDecrease("You cant decreasing Because : "+String.valueOf(number)+"<"+String.valueOf(count));
                }
                sw = 1;
                if (number == 0) {
                    cart.remove(id);
                    break;
                } else {
                    cart.put(id, String.valueOf(number));
                    break;
                }
            }
        }
        if (sw == 0){
            throw new NoProductInTheDatabase("there is no product by this prodId in cart");
        }
        ArrayList<String> temp = new ArrayList<>();
        for (String id:
             cart.keySet()) {
            temp.add(id+"_"+cart.get(id));
        }
        StringBuilder result = new StringBuilder(temp.get(0));
        for (int i = 1; i < temp.size(); i++) {
            result.append("@").append(temp.get(i));
        }
        //result.append(temp.get(temp.size()-1));
        return result.toString();
    }
    private static String separator(){
        return "_";
    }

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    public static void deleteAProductFromDatabase(String Username , String productId , int count) throws NoProductInTheDatabase, OutOfNumberToDecrease {
        connection = database.connectDB();
        try {
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE costumer SET cart = '"+deletingProdOfDatabase(Username,productId,count)+"' WHERE Username = '"+Username+"'");
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            deleteAProductFromDatabase("admin03","10",1);
            ObservableList<String> newCart = readCartAndDiscountCodeOfCostumerFromDatabase.cartOfCostumer("admin03");
            for (int i = 0; i < newCart.size(); i++) {
                System.out.println(newCart.get(i));
            }
        } catch (NoProductInTheDatabase e) {
            System.out.println(e.getMessage());
        } catch (OutOfNumberToDecrease e) {
            System.out.println(e.getMessage());
        }

    }
}
