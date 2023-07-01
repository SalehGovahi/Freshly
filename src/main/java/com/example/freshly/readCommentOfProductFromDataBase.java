package com.example.freshly;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class readCommentOfProductFromDataBase {
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    public static ArrayList<String> commentOfProduct(String productid){
        ArrayList<String> comments = new ArrayList<>();
        connection = database.connectDB();
        String sql = "SELECT * FROM comments WHERE productid='"+productid+"'";
        try{
            if (connection != null) {
                statement = connection.createStatement();
            }
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                comments.add(resultSet.getString("Text"));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return comments;
    }

    public static void main(String[] args) {
        ArrayList<String > comments = commentOfProduct("3");
        for (int i = 0; i < comments.size(); i++) {
            System.out.println(comments.get(i));
        }
    }
}
