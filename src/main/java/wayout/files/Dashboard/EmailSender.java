package wayout.files.Dashboard;

import javax.mail.*;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {
    public void send_mail(String user_email, String subject, String mail_body){
        // send mail


        String email = "applicationwayout@gmail.com";
        String password = "ejntpurrhwoplqcq";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "*");


        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        try {
            javax.mail.Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(email));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user_email));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(mail_body);

            Transport.send(mimeMessage);

            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
