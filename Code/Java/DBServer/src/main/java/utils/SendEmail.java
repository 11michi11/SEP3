package utils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmail {

    public static void generateAndSendEmail(String to,String subject, String emailBody) throws MessagingException {
        // Step1 - prepare mail server properties
        // System.out.println("\n 1st ===> setup Mail Server Properties..");
        Properties mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.host", "smtp.gmail.com");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        //  System.out.println("Mail Server Properties have been setup successfully..");

        // Step2 - create message
        System.out.println("\n\n 2nd ===> get Mail Session..");
        Session getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        MimeMessage generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        generateMailMessage.setSubject(subject);
        generateMailMessage.setContent(emailBody, "text/html");
        //  System.out.println("Mail Session has been created successfully..");

        // Step3 - get session
        //  System.out.println("\n\n 3rd ===> Get Session and Send mail");
        Transport transport = getMailSession.getTransport("smtp");

        // Step 4 - connect and send message
        transport.connect("smtp.gmail.com", "enteemailservice@gmail.com", "enteAdmin1");
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }

    public static void sendBookReturnEmail(String email, String bookTitle) {
        String body = "Book: " + bookTitle + " has been returned to the library." +
                "<br>Regards, team Read With Panda";
        System.out.println("Trying to send email");
        try {
            System.out.println("Sending change password email to: " + email);
            generateAndSendEmail(email, "Your book has been returned", body);
        } catch (MessagingException e) {
            System.out.println("Email not send " + e.getMessage());
            // e.printStackTrace();
        }
    }

    public static void sendOrderConfirmedEmail(String email, String bookTitle) {
        String body = "Your order for the book: " + bookTitle + " has been confirmed and your book has been shipped." +
                "<br>Regards, team Read With Panda";
        System.out.println("Trying to send email");
        try {
            System.out.println("Sending change password email to: " + email);
            generateAndSendEmail(email, "Your book order has been confirmed", body);
        } catch (MessagingException e) {
            System.out.println("Email not send " + e.getMessage());
            // e.printStackTrace();
        }
    }

}
