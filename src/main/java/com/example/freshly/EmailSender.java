package com.example.freshly;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.*;
import javax.mail.internet.*;
public class EmailSender {
    public static int sendEmail(String GmailAddress, String pass) throws MessagingException {
        String to = GmailAddress; // آدرس ایمیل دریافت کننده
        String from = "mohammadjavad4683@gmail.com"; // آدرس ایمیل فرستنده
        String password = "rhmuipnavvtxqedd"; // رمز عبور ایمیل فرستنده

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com"); // نام سرور SMTP شما
        properties.put("mail.smtp.port", "465"); // پورت SMTP شما
        properties.put("mail.smtp.auth", "true"); // تایید هویت مورد نیاز است
        properties.put("mail.smtp.starttls.enable", "true"); // استفاده از TLS

        properties.put("mail.smtp.socketFactory.port", "465"); // پورت امن برای استفاده از SSL
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // استفاده از SSL

        // ایجاد session با تنظیمات SMTP
        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        // ساخت پیام ایمیل
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("Freshly"); // موضوع ایمیل
        message.setText("This is A Test From Freshly Team" +
                "Your Password is : "+pass+""); // متن ایمیل

        // ارسال ایمیل
        Transport.send(message);

        //System.out.println("Email sent successfully.");
        return 1;
    }
}
