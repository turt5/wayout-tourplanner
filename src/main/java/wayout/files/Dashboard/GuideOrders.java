package wayout.files.Dashboard;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;

import javax.mail.*;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

public class GuideOrders implements Initializable {

    @FXML
    private AnchorPane main_anchorPane;

    @FXML
    private VBox packages_vbox;

    public void add_orders(String hired_by, String hire_date, String hire_hours, String starting_time, String email, String phone, String notes) {
        HBox hBox = new HBox();
        hBox.setPrefWidth(1020);
        hBox.setPrefHeight(260);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(1020);
        anchorPane.setPrefHeight(240);
        anchorPane.setStyle("-fx-background-color: #ccdcd5;");

        AnchorPane anchorPane1 = new AnchorPane();
        anchorPane1.setPrefWidth(1020);
        anchorPane1.setPrefHeight(20);

        Label user = new Label("Traveller Name: " + hired_by);
        user.setLayoutX(40);
        user.setLayoutY(30);
        user.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold");

        Label date = new Label("Hired date: " + hire_date);
        date.setLayoutX(40);
        date.setLayoutY(60);
        date.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold");

        Label hours = new Label("Hired for: " + hire_hours + " hours, From "+starting_time  );
        hours.setLayoutX(40);
        hours.setLayoutY(90);
        hours.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold");

        Label u_num = new Label("Phone: " + phone );
        u_num.setLayoutX(40);
        u_num.setLayoutY(120);
        u_num.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold");


        Label u_email = new Label("Email:" + email );
        u_email.setLayoutX(40);
        u_email.setLayoutY(150);
        u_email.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;" +
                "-fx-vgap: 10px");

        Label special_notes = new Label("Order notes: " + notes);
        special_notes.setLayoutX(40);
        special_notes.setLayoutY(180);
        special_notes.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold");


        MFXButton accept = new MFXButton("Accept order");
        accept.setLayoutX(700);
        accept.setLayoutY(210);
        accept.setStyle("-fx-font-size: 13px;" +
                "-fx-padding: 7 15 7 15px;" +
                "-fx-background-color: #008f61;" +
                "-fx-text-fill: white");


        MFXButton reject = new MFXButton("Reject order");
        reject.setLayoutX(815);
        reject.setLayoutY(210);
        reject.setStyle("-fx-font-size: 13px;" +
                "-fx-padding: 7 15 7 15px;" +
                "-fx-background-color: #8c0000;" +
                "-fx-text-fill: white");


        anchorPane.getChildren().add(reject);
        anchorPane.getChildren().add(accept);
        anchorPane.getChildren().add(special_notes);
        anchorPane.getChildren().add(u_email);
        anchorPane.getChildren().add(u_num);
        anchorPane.getChildren().add(hours);
        anchorPane.getChildren().add(date);
        anchorPane.getChildren().add(user);
        hBox.getChildren().add(anchorPane);
        packages_vbox.getChildren().add(hBox);
        packages_vbox.getChildren().add(anchorPane1);

        accept.setOnAction(event -> {
            try {
                String admin_email = "applicationwayout@gmail.com";
                String admin_password = "ejntpurrhwoplqcq";
                String to = email;
                String subject = "Regarding Guide Hire";
                String message = "Dear " + hired_by + "," +
                        "Your guide hire was successful.\n" +
                        "Thanks for staying with wayout!";

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
                        return new PasswordAuthentication(admin_email, admin_password);
                    }
                });

                try {
                    javax.mail.Message mimeMessage = new MimeMessage(session);
                    mimeMessage.setFrom(new InternetAddress(admin_email));
                    mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                    mimeMessage.setSubject(subject);
                    mimeMessage.setText(message);

                    Transport.send(mimeMessage);

                    System.out.println("Email sent successfully!");
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }catch (Exception e){
             e.printStackTrace();
            }
        });


        reject.setOnAction(event -> {

            packages_vbox.getChildren().remove(hBox);
            packages_vbox.getChildren().remove(anchorPane1);
            try {

                String url = "jdbc:mysql://127.0.0.1/wayout";
                String username = "root";
                String password = "";
                System.out.println(hired_by);
                Connection conn = DriverManager.getConnection(url, username, password);
                String sql = "DELETE FROM guide_hire_list WHERE User_Who_Hired = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, hired_by);

                // Execute delete statement
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected == 1) {
                    System.out.println("Row deleted successfully.");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Order removed");
                    alert.showAndWait();
                    packages_vbox.getChildren().remove(hBox);
                } else {
                    System.out.println("Error: Row not found or multiple rows affected.");
                }

                // Close database connection
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


            String admin_email = "applicationwayout@gmail.com";
            String admin_password = "ejntpurrhwoplqcq";
            String to = email;
            String subject = "Regarding Guide Hire";
            String message = "Sorry, the guide is not available right now!\nPlease contact: 01754281079 to refund your paid money!";

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
                    return new PasswordAuthentication(admin_email, admin_password);
                }
            });

            try {
                javax.mail.Message mimeMessage = new MimeMessage(session);
                mimeMessage.setFrom(new InternetAddress(admin_email));
                mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                mimeMessage.setSubject(subject);
                mimeMessage.setText(message);

                Transport.send(mimeMessage);

                System.out.println("Email sent successfully!");
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        add_orders("Kaium Al Limon", "18 May 2023", "5", "12:00 PM", "rvivy41@gmail.com", "01738439423", "Hey there?");
//        add_orders("Kaium Al Limon", "18 May 2023", "5", "12:00 PM", "rvivy41@gmail.com", "01738439423", "Hey there?");
//        add_orders("Kaium Al Limon", "18 May 2023", "5", "12:00 PM", "rvivy41@gmail.com", "01738439423", "Hey there?");
//        add_orders("Kaium Al Limon", "18 May 2023", "5", "12:00 PM", "rvivy41@gmail.com", "01738439423", "Hey there?");


        String url = "jdbc:mysql://127.0.0.1/wayout";
        String username = "root";
        String password = "";

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM guide_hire_list");


            while (rs.next()) {
                String name = rs.getString("User_Who_Hired");
                String hire_date = rs.getString("Hire_Date");
                String hoursHired = rs.getString("Hours_Hired");
                String starting_time = rs.getString("Starting_Time");
                String userEmail = rs.getString("User_Email");
                String userPhone = rs.getString("User_Phone");
                String booking_note = rs.getString("Booking_Notes");
                String guideName = rs.getString("Guide_Name");

                String G_name = "";
                File file = new File("src/main/resources/wayout/files/Dashboard/username.txt");
                if (file.exists()) {
                    String word = "";
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {

                        G_name = br.readLine();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else System.out.println("File not found");

                System.out.println(G_name);

                if (G_name.equals(guideName)) {
                    add_orders(name, hire_date, hoursHired, starting_time, userEmail, userPhone, booking_note);
                }
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving data: " + e.getMessage());
        }
    }
}
