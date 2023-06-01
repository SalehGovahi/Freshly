package com.example.demo67;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class kiri {
    static Connection connection;
    static Statement statement;
    static ResultSet resultSet;

    public static void main(String[] args) {
        connection = database.connectDB();
        String first_image="F:\\محمد جواد\\OP.png";
        String first_image_path=ImageCopyExample.copyfile(first_image,"456");
        System.out.println(first_image_path);
        try {
            statement= connection.createStatement();

            statement.executeUpdate("INSERT INTO Person (name , lastname , id , Image) VALUES ('mamali','mamali',12356,'"+first_image_path+"')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
