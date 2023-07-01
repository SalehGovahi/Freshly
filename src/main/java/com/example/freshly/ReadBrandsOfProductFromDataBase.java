package com.example.freshly;

import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReadBrandsOfProductFromDataBase {
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    public static List<String> Brands(){
        Set<String> brands = new HashSet<>();
        connection = database.connectDB();
        try{
            if (connection != null) {
                statement = connection.createStatement();
            }
            resultSet = statement.executeQuery("SELECT * FROM product");
            while (resultSet.next()){
                brands.add(resultSet.getString("brand"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        List<String> brand = new ArrayList<>(brands);
        return brand;
    }

    /*public static void main(String[] args) {
        List<String> t = ReadBrandsOfProductFromDataBase.Brands();
        for (int i = 0; i < t.size(); i++) {
            System.out.println(t.get(i));
        }
    }*/
}
