package Core;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SMTP {
    public boolean sendMail(int OTP, String Recipient) {
        Properties properties = new Properties();

        // Google SMTP Server
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String senderEMail = "YOUR_EMAIL";
        String senderPassword = "YOUR_PASSWORD";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEMail, senderPassword);
            }
        });

        Message message = prepareMessage(session, senderEMail, Recipient, OTP);

        try {
            assert message != null;
            Transport.send(message);
            System.out.println("Check your Mail for OTP");
            return true;
        } catch (MessagingException e) {
            System.out.println("Exception while sending mail: " + e);
        }

        return false;
    }

    private Message prepareMessage(Session session, String senderEmail, String Recipient, int OTP) {
        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(Recipient));

            message.setSubject("PassManager OTP");

            message.setText("Hey! Here's your OTP to perform master login. \nOTP: " + OTP);

            return message;
        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e);
        }

        return null;
    }
}
