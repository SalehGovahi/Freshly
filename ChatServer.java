package com.example.freshly;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    public static ArrayList<ClientHandler> clients = new ArrayList<>(); // لیستی برای ذخیره کلاینت‌هایی که به سرور متصل شده‌اند

    public static void main(String[] args) throws Exception {
        // ایجاد سوکت سرور برای پذیرش اتصال کلاینت‌ها
        ServerSocket serverSocket = new ServerSocket(1234); // پورت سرور را بر روی ۱۲۳۴ تنظیم می کنیم
        System.out.println("Server started");

        while (true) {
            // منتظر وصل شدن کلاینت ها به سرور می‌مانیم
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket);

            // ایجاد یک thread جدید برای هر کلاینت
            ClientHandler clientHandler = new ClientHandler(clientSocket);
            clients.add(clientHandler); // اضافه کردن کلاینت جدید به لیست کلاینت‌ها
            clientHandler.start();
        }
    }

    // ارسال پیام به تمامی کلاینت‌ها
    public static void sendMessageToAllClients(String message , String username) {
        for (ClientHandler client : clients) {
            if (!client.username.equals(username)) {
                client.out.println(message);
            }
        }
    }

    // ارسال پیام خصوصی به یک کلاینت مشخص
    public static void sendPrivateMessage(String message, String sender, String recipient) {
        for (ClientHandler client : clients) {
            if (client.username.equals(recipient)) {
                client.out.println(sender + " : " + message);
            }
        }
    }
}

// یک thread جدید برای هر کلاینت
class ClientHandler extends Thread {
    private Socket clientSocket;
    private BufferedReader in;
    PrintWriter out;
    public String username = "";

    // سازنده کلاس ClientHandler
    public ClientHandler(Socket socket) throws IOException {
        this.clientSocket = socket;
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        //out.println("WELCOME " + socket);
        System.out.println("LLLLL "+username);
        //out.println("Please enter your username: "); // پیام درخواست وارد کردن نام کاربری به کلاینت ارسال می‌شود
        // this.username = in.readLine(); // نام کاربری از کلاینت گرفته می‌شود
        //this.out.println("hello "+this.username);
        //System.out.println(this.username);
        //out.println("Welcome to the chat room, " + this.username + "!"); // پیام خوش‌آمدگویی به کلاینت ارسال می‌شود
    }

    // پیاده‌سازی تابع run برای اجرای کد هر thread
    public void run() {
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Message received from " + username + ": " + inputLine);

                if (inputLine.startsWith("!@#$Username :")){
                    int doNoghtehIndex = inputLine.indexOf(":");
                    String Username = inputLine.substring(doNoghtehIndex + 1);// نام کاربری
                    System.out.println(Username);
                    this.username = Username;
                    //out.println("hello : "+ this.username);
                }
                System.out.println(username);

                    if (!username.equals("admin")) {
                        ChatServer.sendPrivateMessage(inputLine, username, "admin");
                        System.out.println(username + " sent "+ inputLine + " to admin");
                    } else {
                        if (inputLine.startsWith("@")) { // اگر پیام خصوصی باشد
                            int spaceIndex = inputLine.indexOf(" ");
                            if (spaceIndex != -1) {
                                String recipient = inputLine.substring(1, spaceIndex); // نام کاربری دریافت کننده پیام
                                String message = inputLine.substring(spaceIndex + 1); // متن پیام خصوصی
                                System.out.println(message +" "+ recipient + " sent by admin");
                                ChatServer.sendPrivateMessage(message, username, recipient); // ارسال پیام خصوصی به دریافت کننده
                            }
                        }
                    }





                /*if (inputLine.startsWith("@")) { // اگر پیام خصوصی باشد
                    int spaceIndex = inputLine.indexOf(" ");
                    if (spaceIndex != -1) {
                        String recipient = inputLine.substring(1, spaceIndex); // نام کاربری دریافت کننده پیام
                        String message = inputLine.substring(spaceIndex + 1); // متن پیام خصوصی
                        ChatServer.sendPrivateMessage(message, username, recipient); // ارسال پیام خصوصی به دریافت کننده
                    }
                } else { // اگر پیام عمومی باشد
                    ChatServer.sendMessageToAllClients(username + ": " + inputLine,username); // ارسال پیام به تمامی کلاینت‌ها
                }*///salam khobi?
            }
        } catch (IOException e) {
            System.out.println("Error handling client: " + e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing client socket: " + e);
            }
            System.out.println("Client disconnected: " + clientSocket);
            ChatServer.clients.remove(this); // حذف کلاینت از لیست کلاینت‌ها
        }
    }
}
