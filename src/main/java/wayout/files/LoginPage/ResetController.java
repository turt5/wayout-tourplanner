package wayout.files.LoginPage;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Random;

public class ResetController extends TemporaryData {

    @FXML
    private MFXButton CancelButton;

    @FXML
    private Hyperlink ContactLink;

    @FXML
    private MFXTextField EmailBox;

    @FXML
    private MFXButton ExploreButton;

    @FXML
    private Hyperlink SignupLink;

    @FXML
    private MFXButton submitButton;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    void cancelClicked(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("login.fxml"));
        stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void contactClicked(ActionEvent event) {

    }

    @FXML
    void exploreClicked(ActionEvent event) {

    }

    @FXML
    void signupClicked(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("signUP.fxml"));
        stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void submitClicked(ActionEvent event) {
        emal_2=EmailBox.getText();
        String mail=EmailBox.getText();

        String url = "jdbc:mysql://127.0.0.1/wayout";
        String username = "root";
        String password = "";

        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }

        try{

            String chk1="Select * from accountinfo where email=?";
            Connection con;
            PreparedStatement pst1,pst2;

            con= DriverManager.getConnection(url, username, password);

            pst1=con.prepareStatement(chk1);


            pst1.setString(1,mail);


            ResultSet rs1;
            rs1=pst1.executeQuery();


            if(rs1.next()){

                // at first go to the next page to enter a new
                Parent root= FXMLLoader.load(getClass().getResource("resetPassVerification.fxml"));
                stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
                scene=new Scene(root);
                stage.setScene(scene);
                stage.show();

                // then send a verification code

                Random random=new Random();

                int sixLengthRandomNumber = random.nextInt(100000, 1000000);
                System.out.println(sixLengthRandomNumber);
                generated_code2=""+sixLengthRandomNumber;

                // send mail


                String email = "applicationwayout@gmail.com";
                String password2 = "ejntpurrhwoplqcq";
                String to = EmailBox.getText();
                String subject = "Verify your email";
                String message = "Here is your verification code: \n\n" + generated_code2 + "\n\nPlease enter this verification code to reset your old password\n";

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
                        return new PasswordAuthentication(email, password2);
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




            } else{
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Wrong email");
                alert.setContentText("No account found with this account!");
                alert.showAndWait();
            }

        }catch (Exception e){
            System.out.println(e);
        }

    }

}
