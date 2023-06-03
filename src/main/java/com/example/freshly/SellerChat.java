package com.example.freshly;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.util.Scanner;

public class SellerChat {
    String username;
    private Connection connection=database.connectDB();
        public SellerChat(String username) {
            this.username=username;
        }

        public void sendMessage(String text) throws SQLException {
            String sql = "INSERT INTO messages (sender,recipient ,text,new) VALUES (?, ?,?,'yes')";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, "admin");
            statement.setString(3, text);
            statement.executeUpdate();
        }

        public String[] getMessages() throws SQLException {
            String sql = "SELECT sender, text FROM messages WHERE recipient=? OR sender=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2,username);
            ResultSet result = statement.executeQuery();
            List<String> messages = new ArrayList<>();
            while (result.next()) {
                String sender = result.getString("sender");
                String text = result.getString("text");
                messages.add(sender + ": " + text);
            }
            //statement.executeUpdate("UPDATE messages SET new='no' WHERE new='yes'");
            //statement.setString(1,recipient);
            String sql1="UPDATE messages SET new='no' WHERE recipient= ?and new ='yes'";
            PreparedStatement preparedStatement1=connection.prepareStatement(sql1);
            preparedStatement1.setString(1,username);
            int rowsAffected = preparedStatement1.executeUpdate();
            return messages.toArray(new String[0]);
        }

        public void close() throws SQLException {
            connection.close();
        }

        public static void main(String[] args) throws SQLException {
            Scanner scanner=new Scanner(System.in);
            System.out.println("enter your username");
            String user=scanner.next();
            SellerChat client=new SellerChat(user);
            String[] messages = client.getMessages();
            for (String message1 : messages) {
                System.out.println(message1);
            }
            System.out.println("enter new message:");
            String message=scanner.next();
            client.sendMessage(message);
            client.close();
        }
    }

