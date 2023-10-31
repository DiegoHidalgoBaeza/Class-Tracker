package com.example.classtracker;

import android.os.AsyncTask;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    public static class SendEmailTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            String username = "classtrackersoporte@gmail.com"; // Cambia esto por tu dirección de correo
            String password = "cqja rklg uych afbr"; // Cambia esto por tu contraseña
            String recipient = params[0];
            String subject = "Recuperación de contraseña";
            String newPassword = params[1]; // Obtén la nueva contraseña desde los parámetros
            String messageText = "Tu nueva contraseña es: " + newPassword;

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com"); // Servidor SMTP de Gmail
            props.put("mail.smtp.port", "587"); // Puerto SMTP de Gmail

            Session session = Session.getInstance(props, new Authenticator(username, password));

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
                message.setSubject(subject);
                message.setText(messageText);

                Transport.send(message);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        private class Authenticator extends javax.mail.Authenticator {
            private String username;
            private String password;

            public Authenticator(String username, String password) {
                this.username = username;
                this.password = password;
            }

            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(username, password);
            }
        }
    }

    public static void sendEmail(String recipient, String newPassword) {
        SendEmailTask sendEmailTask = new SendEmailTask();
        sendEmailTask.execute(recipient, newPassword); // Pasa la nueva contraseña como segundo parámetro
    }
}
