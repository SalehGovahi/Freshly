package com.example.demo67;

import java.sql.*;

public class database {

    public static Connection connectDB() {

        try {

            Class.forName("com.mysql.jdbc.Driver");


            Connection connect =  DriverManager.getConnection("jdbc:mysql://localhost:3307/jdbc", "root", ""); // LINK YOUR DATABASE
            return connect;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
