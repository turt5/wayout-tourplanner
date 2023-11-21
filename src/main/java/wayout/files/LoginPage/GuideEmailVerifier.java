package wayout.files.LoginPage;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.json.JSONObject;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.Properties;

public class GuideEmailVerifier extends TemporaryData {
    private static final String API_KEY = "516ab40faaec419288e3d2390d74ccf4";
    private static final String VERIFY_API_URL = "https://api.zerobounce.net/v2/validate?api_key=" + API_KEY;


    public void verifyEmail(String U_email, Event event) {
        String verifyUrl = VERIFY_API_URL + "&email=" + U_email;

        try {
            URL url = new URL(verifyUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            String status = jsonResponse.getString("status");
            System.out.println("" + status);
            Parent root2;
            Stage stage;
            Scene scene2;

            if (status.equals("valid")) {

                if(checkMail(email_z)){
                    root2 = FXMLLoader.load(getClass().getResource("guide_verify_account.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene2 = new Scene(root2);
                    stage.setScene(scene2);
                    stage.show();

                    // send mail


                    String email = "applicationwayout@gmail.com";
                    String password = "ejntpurrhwoplqcq";
                    String to = email_z;
                    String subject = "Verify your email";
                    String message = "Here is your verification code: \n\n" + generated_code + "\n\nPlease enter this verification code to confirm your account as a guide\n";

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
                        Message mimeMessage = new MimeMessage(session);
                        mimeMessage.setFrom(new InternetAddress(email));
                        mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                        mimeMessage.setSubject(subject);
                        mimeMessage.setText(message);

                        Transport.send(mimeMessage);

                        System.out.println("Email sent successfully!");
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Cannot create account with this email!");
                    alert.setContentText("This email account already exists");
                    alert.showAndWait();

                }


            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Email address is not valid.");
                alert.showAndWait();
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to verify email address.");
            alert.showAndWait();
        }
    }

    // this function actually checks if the given email is already existing in the database or not
    public boolean checkMail (String email){


        String url = "jdbc:mysql://127.0.0.1/wayout";
        String username = "root";
        String password = "";

        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }

        try {

            String chk1 = "Select * from guide_info where email=?";

            Connection con;
            PreparedStatement pst1, pst2;

            con = DriverManager.getConnection(url, username, password);

            pst1 = con.prepareStatement(chk1);

            pst1.setString(1, email);


            ResultSet rs1, rs2;
            rs1 = pst1.executeQuery();


            if (rs1.next()) {
                return false;
            }
            else return true;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

}
