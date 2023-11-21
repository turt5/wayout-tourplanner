package wayout.files.Dashboard;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

public class CartController implements Initializable {

    @FXML
    private AnchorPane main_anchorPane;

    @FXML
    private VBox packages_vbox;

    public void add_orders(String hired_by, String hire_date, String hire_hours, String starting_time, String email, String phone, String notes,String cost,String title,String status) {
        HBox hBox = new HBox();
        hBox.setPrefWidth(1020);
        hBox.setPrefHeight(260);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(1020);
        anchorPane.setStyle("-fx-background-color: #ccdcd5;-fx-padding: 10px 0 20px 0");

        AnchorPane anchorPane1 = new AnchorPane();
        anchorPane1.setPrefWidth(1020);
        anchorPane1.setPrefHeight(20);

        Label packageTitle=new Label("Package: "+title);
        packageTitle.setLayoutX(40);
        packageTitle.setLayoutY(30);
        packageTitle.setStyle("-fx-font-family: 'Poppins SemiBold';" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold");

        Label user = new Label("Traveller Name: " + hired_by);
        user.setLayoutX(40);
        user.setLayoutY(60);
        user.setStyle("-fx-font-family: 'Poppins SemiBold';" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold");

        Label date = new Label("Hired date: " + hire_date);
        date.setLayoutX(40);
        date.setLayoutY(90);
        date.setStyle("-fx-font-family: 'Poppins SemiBold';" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold");

        Label hours = new Label("Duration: "+hire_hours+" Days");
        hours.setLayoutX(40);
        hours.setLayoutY(120);
        hours.setStyle("-fx-font-family: 'Poppins SemiBold';" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold");

        Label u_num = new Label("Email: " + email );
        u_num.setLayoutX(40);
        u_num.setLayoutY(150);
        u_num.setStyle("-fx-font-family: 'Poppins SemiBold';" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold");


        Label u_email = new Label("Phone:" + phone );
        u_email.setLayoutX(40);
        u_email.setLayoutY(180);
        u_email.setStyle("-fx-font-family: 'Poppins SemiBold';" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;" +
                "-fx-vgap: 10px");

        Label special_notes = new Label("Order notes: " + notes);
        special_notes.setLayoutX(40);
        special_notes.setLayoutY(210);
        special_notes.setStyle("-fx-font-family: 'Poppins SemiBold';" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold");

        Label totalCost = new Label("Total cost: " + cost);
        totalCost.setLayoutX(40);
        totalCost.setLayoutY(240);
        totalCost.setStyle("-fx-font-family: 'Poppins SemiBold';" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold");

//        Label status_ = new Label("Total cost: " + cost);
//        status_.setLayoutX(40);
//        status_.setLayoutY(270);
//        status_.setStyle("-fx-font-family: 'Poppins SemiBold';" +
//                "-fx-font-size: 18px;" +
//                "-fx-font-weight: bold");

//        add_orders(user_name,hiringDate,package_duration,hiringDate,user_email,user_phone,user_note,total_cost);

        anchorPane.getChildren().add(special_notes);
//        anchorPane.getChildren().add(status_);
        anchorPane.getChildren().add(packageTitle);
        anchorPane.getChildren().add(totalCost);
        anchorPane.getChildren().add(u_email);
        anchorPane.getChildren().add(u_num);
        anchorPane.getChildren().add(hours);
        anchorPane.getChildren().add(date);
        anchorPane.getChildren().add(user);
        hBox.getChildren().add(anchorPane);
        packages_vbox.getChildren().add(hBox);
        packages_vbox.getChildren().add(anchorPane1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String url = "jdbc:mysql://127.0.0.1/wayout";
        String username = "root";
        String password = "";

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ordered_package");

//            User_Name,User_Email,User_Phone,User_Note,Package_Title,Package_Duration_Days,Total_Cost,Payment_Number,Payment_Transaction_ID
            while (rs.next()) {
                String user_name = rs.getString("User_Name");
                String user_email = rs.getString("User_Email");
                String user_phone = rs.getString("User_Phone");
                String user_note = rs.getString("User_note");
                String package_title = rs.getString("Package_Title");
                String package_duration = rs.getString("Package_Duration_Days");
                String total_cost = rs.getString("Total_Cost");
                String hiringDate=rs.getString("Tour_Starting");
                String status=rs.getString("Status");

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
                System.out.println(user_name);

                if (G_name.trim().equals(user_name.trim())) {
                    //add_orders(String hired_by, String hire_date, String hire_hours, String starting_time, String email, String phone, String notes)
                    if(status.equals("running")){
                        add_orders(user_name,hiringDate,package_duration,hiringDate,user_email,user_phone,user_note,total_cost,package_title,status);
                    }
                }else{
                    System.out.println("Name did not matched");
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
