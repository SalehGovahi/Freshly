package com.example.freshly;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AdminChat {
    public String[] allChats() throws SQLException {
        String sql = "SELECT sender FROM messages WHERE recipient=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,"admin");
        ResultSet result = statement.executeQuery();
        ArrayList<String> messages = new ArrayList<String>();
        ArrayList<String> messagesSorted = new ArrayList<String>();
        while (result.next()) {
            String sender = result.getString("sender");
            String s="You have a chat with :"+ sender;
                messages.add(s);
                //System.out.println(s);
            for (int i = 0; i< messages.size(); i++) {
                if(!messagesSorted.contains(messages.get(i)))
                messagesSorted.add(messages.get(i));
            }

        }

        return messagesSorted.toArray(new String[0]);
    }
    public String[] newMassages() throws SQLException {
        String sql = "SELECT sender FROM messages WHERE recipient=? AND new='yes'";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,"admin");
        ResultSet result = statement.executeQuery();
        List<String> messages = new ArrayList<>();
        while (result.next()) {
            String sender = result.getString("sender");
            messages.add("You have a new message from :"+ sender);
        }
        return messages.toArray(new String[0]);

    }
    private Connection connection=database.connectDB();
    public void sendMessage(String text,String recipient) throws SQLException {
        String sql = "INSERT INTO messages (sender,recipient ,text,new) VALUES (?, ?,?,'yes')";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "admin");
        statement.setString(2,recipient );
        statement.setString(3, text);
        statement.executeUpdate();
    }

    public String[] getMessages(String recipient) throws SQLException {
        String sql = "SELECT sender, text FROM messages WHERE recipient=? OR sender=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, recipient);
        statement.setString(2,recipient);
        ResultSet result = statement.executeQuery();
        List<String> messages = new ArrayList<>();
        while (result.next()) {
            String sender = result.getString("sender");
            String text = result.getString("text");
            messages.add(sender + ": " + text);
        }
        for (String s:
             messages) {
            System.out.println(s);
        }
        //PreparedStatement stmt = connection.prepareStatement("UPDATE messages SET new=? WHERE recipient=?");
        String sql1="UPDATE messages SET new='no' WHERE sender= ?and new ='yes'";
        PreparedStatement preparedStatement1=connection.prepareStatement(sql1);
        preparedStatement1.setString(1,recipient);
        int rowsAffected = preparedStatement1.executeUpdate();
        return messages.toArray(new String[0]);
    }

    public void close() throws SQLException {
        connection.close();
    }

    public static void main(String[] args) throws SQLException {
        AdminChat adminChat = new AdminChat();
        String[] strings = adminChat.newMassages();
        for (String s :
                strings) {
            System.out.println(s);
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter the username of person you want to chat or enter all to see all chats");
        String user = scanner.next();
        if (user.equals("all")) {
            String[]s=adminChat.allChats();

            for (int i = s.length-1; i >=0; i--) {
                System.out.println(s[i]);
            }
            String user1=scanner.next();
            adminChat.getMessages(user1);
        } else {
            adminChat.getMessages(user);
            String message = scanner.next();
            adminChat.sendMessage(message, user);
        }
    }
}

