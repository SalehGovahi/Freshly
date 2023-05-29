package com.example.freshly;

import java.sql.*;

public class connection {
    private static Connection connect;
    private static Statement statement;
    private static ResultSet result;

    public static void main(String[] args) {
        connect = database.connectDB();
        try {
            if (connect != null) {
                statement = connect.createStatement();
            }
            statement.executeUpdate("DELETE FROM costumer WHERE Username='a'");
            //statement.executeUpdate("UPDATE costumer SET Password = 'mohammad4683' WHERE Username = 'admin01'");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
