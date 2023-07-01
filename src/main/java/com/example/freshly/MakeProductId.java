package com.example.freshly;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MakeProductId {
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    private static ArrayList<Integer> prodIds = new ArrayList<>();
    public static int productIdMaker(){
        int id = 0;
        connection = database.connectDB();
        try {
            statement = connection.createStatement();
            resultSet=statement.executeQuery("SELECT * FROM product");
            while (resultSet.next()){
                prodIds.add(Integer.parseInt(resultSet.getString("productid")));
            }
            int max = -1;
            for (int i = 0; i < prodIds.size(); i++) {
                if (max < prodIds.get(i)){
                    max=prodIds.get(i);
                }
            }
            id = max + 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }
}
