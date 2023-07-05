package com.example.freshly;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class conn {
    public static void main(String[] args) {
        Connection connection ;
        Statement statement ;
        ResultSet resultSet ;
        connection=database.connectDB();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT cart FROM costumer  WHERE Username = 'admin02'");
            if (resultSet.next()){
                System.out.println(resultSet.getString(1));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
