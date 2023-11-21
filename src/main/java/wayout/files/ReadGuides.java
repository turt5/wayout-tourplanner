package wayout.files;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.InputStream;
import java.sql.*;

public class ReadGuides extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String url = "jdbc:mysql://127.0.0.1/wayout";
        String username = "root";
        String password = "";

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM guides_list");

            while (rs.next()) {
                String name = rs.getString("Name");
                String city = rs.getString("City");
                String email = rs.getString("Email");
                String mobile = rs.getString("Mobile");
                String details = rs.getString("Details");
                String nid = rs.getString("NID");
                double hourlyCharge = rs.getDouble("Hourly_Charge");
                String availableTime = rs.getString("Available_time");
                String Status = rs.getString("Status");
                InputStream image=rs.getBinaryStream("image");


                Image img=new Image(image);
                System.out.println("Name: " + name);
                System.out.println("City: " + city);
                System.out.println("Email: " + email);
                System.out.println("Mobile: " + mobile);
                System.out.println("Details: " + details);
                System.out.println("NID: " + nid);
                System.out.println("Hourly Charge: " + hourlyCharge);
                System.out.println("Available Time: " + availableTime);
                System.out.println();


                AnchorPane anchorPane=new AnchorPane();
                ImageView imageView=new ImageView(img);
                imageView.setFitWidth(300);
                imageView.setFitHeight(400);

                anchorPane.getChildren().add(imageView);
                Scene scene=new Scene(anchorPane);
                primaryStage.setScene(scene);
                primaryStage.show();
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving data: " + e.getMessage());
        }

    }
}
