package com.example.financemanager.Utils;

import android.os.AsyncTask;

import java.security.SecureRandom;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailAPI extends AsyncTask<Void, Void, Boolean> {
    private static final String SENDER_EMAIL = "appqlchitieu@gmail.com"; // Email người gửi
    private static final String SENDER_PASSWORD = "oyot czzs gsxj wven"; // Mật khẩu ứng dụng email
    private static final String SUBJECT_EMAIL = "XÁC THỰC ĐĂNG KÝ TÀI KHOẢN - QUẢN LÝ CHI TIÊU"; //Chủ đề email
    private  final String email;       // Email người nhận
    private final String message;     // Nội dung email


    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            // Cấu hình các thuộc tính
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");

            // Đăng nhập vào email người gửi
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
                }
            });

            // Tạo email
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(SENDER_EMAIL));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            mimeMessage.setSubject(SUBJECT_EMAIL);
            mimeMessage.setText(message);

            // Gửi email
            Transport.send(mimeMessage);

            return true; // Email gửi thành công
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Lỗi khi gửi email
        }
    }
    public JavaMailAPI(String email, String message) {
        this.email = email;
        this.message = message;
    }
}
