package com.example.freshly;

import java.sql.*;

public class database {

    public static Connection connectDB() {

        try {

            Class.forName("com.mysql.jdbc.Driver");


            Connection connect =  DriverManager.getConnection("jdbc:mysql://localhost:3306/Freshly", "root", ""); // LINK YOUR DATABASE
            return connect;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
