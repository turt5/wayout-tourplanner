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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class VerifyAccount extends TemporaryData {

    @FXML
    private MFXButton confirm;

    @FXML
    private MFXTextField verificationCode;

    @FXML
    void confirmClicked(ActionEvent event) {

        String code = verificationCode.getText();

        // Database connection:

        Connection con;
        PreparedStatement pst;

        String url = "jdbc:mysql://127.0.0.1:3306/wayout";
        String username = "root";
        String password = "";

        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }

        // ending of connection of database

        String mailAuth = "@";


        String a1 = first_name;
        String a2 = last_name;
        String a3 = email_z;
        String a4 = date_of_birth;
        String a5 = gender_T;
        String a6 = password_T;
        String a7 = c_password;

        System.out.println("Given code: "+code);
        System.out.println("Generated code: "+code);

        try {
            if (code.equals(generated_code)) {
                try {
                    con = DriverManager.getConnection(url, username, password);

                    String user_first_name = a1;
                    String user_last_name = a2;
                    String user_email = a3;
                    String user_date_of_birth = a4;
                    String user_gender = a5;
                    String user_password = a6;


                    String query11 = "select count(*) from guide_info";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query11);
                    rs.next();
                    int count = rs.getInt(1);
                    System.out.println(count + 1);

                    // auto generate username

                    String lower_Firstname=user_first_name.toLowerCase();
                    char first_name_firstChar = lower_Firstname.charAt(0);


                    String[] lastnameX = user_last_name.split("\\s+");
                    String last = lastnameX[lastnameX.length - 1];

                    String lowerLastname = last.toLowerCase();

                    String generated_username = first_name_firstChar + lowerLastname + (count + 1);

                    String user_FullName = user_first_name + " " + user_last_name;

                    System.out.println(generated_username);


                    pst = con.prepareStatement("INSERT INTO accountinfo(firstName,lastName,fullName,email,dob,gender,username,password) VALUES(?,?,?,?,?,?,?,?)");
                    pst.setString(1, user_first_name);
                    pst.setString(2, user_last_name);
                    pst.setString(3, user_FullName);
                    pst.setString(4, user_email);
                    pst.setString(5, user_date_of_birth);
                    pst.setString(6, user_gender);
                    pst.setString(7, generated_username);
                    pst.setString(8, user_password);
                    pst.execute();

                    System.out.println("Insert successful");

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Welcome");
                    alert.setHeaderText("SignUP Successful");
                    alert.setContentText("Automatically Generated Username: " + generated_username + "\nUse this username to login into your account");
                    alert.showAndWait();

                    //create a table with the username:

                    String createTableSQL = "CREATE TABLE IF NOT EXISTS " + generated_username + " (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY," +
                            "from_ VARCHAR(255) NOT NULL," +
                            "to_ VARCHAR(255) NOT NULL," +
                            "message TEXT NOT NULL," +
                            "sending_time TEXT NOT NULL"+
                            ")";

                    try (PreparedStatement preparedStatement = con.prepareStatement(createTableSQL)) {
                        preparedStatement.executeUpdate();
                        System.out.println("Table " + username + " created successfully.");
                    }

                    try {
                        Parent root2 = FXMLLoader.load(getClass().getResource("login.fxml"));
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene2 = new Scene(root2);
                        stage.setScene(scene2);
                        stage.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                } catch (Exception e) {
                    e.getStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Wrong code");
                alert.setContentText("Please enter code correctly");
                alert.showAndWait();
            }

        } catch (Exception e) {
            e.getStackTrace();
        }


    }

}
