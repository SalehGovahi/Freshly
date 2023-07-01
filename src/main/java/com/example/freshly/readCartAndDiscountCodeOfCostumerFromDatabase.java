package com.example.freshly;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class readCartAndDiscountCodeOfCostumerFromDatabase {
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    private static String cartOfCostumer_temp_(String Username){
        String Cart = "";
        connection = database.connectDB();
        String sql = "SELECT * FROM costumer WHERE Username='"+Username+"'";
        try{
            if (connection != null) {
                statement = connection.createStatement();
            }
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()){
                Cart=resultSet.getString("cart");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return Cart;
    }
    public static ObservableList<String> cartOfCostumer(String Username){
        ObservableList<String> Cart = FXCollections.observableArrayList();
        String databaseResult = cartOfCostumer_temp_(Username);
        String[] carts = databaseResult.split(separator());
        Cart.addAll(Arrays.asList(carts));
        return Cart;
    }
    private static String separator(){
        return "@";
    }
    private static String discountCodeOfCostumer_temp_(String Username){
        String discountCode="";
        connection = database.connectDB();
        String sql = "SELECT * FROM costumer WHERE Username='"+Username+"'";
        try{
            if (connection != null) {
                statement = connection.createStatement();
            }
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()){
                discountCode=resultSet.getString("discountcode");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return discountCode;
    }
    public static ObservableList<String> discountCodeOfCostumer(String Username){
        ObservableList<String> discountCode = FXCollections.observableArrayList();
        String databaseResult = discountCodeOfCostumer_temp_(Username);
        String[] discountCodes = databaseResult.split(separator());
        discountCode.addAll(Arrays.asList(discountCodes));
        return discountCode;
    }

    public static void main(String[] args) {
        ObservableList<String> cart = readCartAndDiscountCodeOfCostumerFromDatabase.cartOfCostumer("admin03");
        cart=cartOfCostumer("admin03");
        for (String s : cart) {
            System.out.println(s);
        }

    }
}
