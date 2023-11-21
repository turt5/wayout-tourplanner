package wayout.files.Dashboard;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class JoinGuide implements Initializable {

    @FXML
    private MFXButton applyBtn;

    @FXML
    private MFXTextField bloodBox;

    @FXML
    private MFXButton cancelBtn;

    @FXML
    private MFXButton upload_photo;

    @FXML
    private MFXTextField chargeBox;

    @FXML
    private MFXTextField cityBox;

    @FXML
    private MFXTextField mailBox;

    @FXML
    private MFXTextField nameBox;

    @FXML
    private MFXTextField nidBox;

    @FXML
    private MFXTextField phoneBox;

    @FXML
    private MFXTextField timeBox;
    byte[] imageData;


    String name,available_area,email,mobile,details,nid_number,hourly_charge,available_time;





    @FXML
    void applyClicked(ActionEvent event) throws IOException {

        name=nameBox.getText();
        available_area=cityBox.getText();
        email=mailBox.getText();
        mobile=phoneBox.getText();
        details=bloodBox.getText();
        nid_number=nidBox.getText();
        hourly_charge=chargeBox.getText();
        available_time=timeBox.getText();


        if(name!=null && available_area!=null && email!=null && mobile!=null && details!=null && nid_number!=null && hourly_charge!=null && available_time!=null){


            Email_Validator emailValidator=new Email_Validator();
            String validation=emailValidator.check_email(email);

                    try{
                        Thread.sleep(200);
                    }catch (Exception e){
                        e.printStackTrace();
                    }



                    if(validation.equals("valid")){



                        String url = "jdbc:mysql://127.0.0.1/wayout";
                        String username = "root";
                        String password = "";

                        System.out.println("Connecting database...");

                        try (Connection connection = DriverManager.getConnection(url, username, password)) {
                            System.out.println("Database connected!");
                        } catch (SQLException e) {
                            throw new IllegalStateException("Cannot connect the database!", e);
                        }

                        Connection con;
                        PreparedStatement pst;

                        try{
                            con = DriverManager.getConnection(url, username, password);
                            pst = con.prepareStatement("INSERT INTO guides_list(Name,City,Email,Mobile,Details,NID,Hourly_Charge,Available_time,Status,image) VALUES(?,?,?,?,?,?,?,?,?,?)");
                            pst.setString(1,name);
                            pst.setString(2,available_area);
                            pst.setString(3,email);
                            pst.setString(4,mobile);
                            pst.setString(5,details);
                            pst.setString(6,nid_number);
                            pst.setString(7,hourly_charge);
                            pst.setString(8,available_time);
                            pst.setString(9,"Pending");
                            pst.setBytes(10, imageData);

                            pst.execute();

                            Alert alert=new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText("Request sent successfully");
                            alert.setContentText("Admin will contact you later on the email you provided, Thanks from wayout!");
                            alert.showAndWait();

                            Parent root= FXMLLoader.load(getClass().getResource("user_dashboard.fxml"));
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();


                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else {

                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Invalid email address provided");
                        alert.setContentText("Please enter a valid email address by which admin can contact you!");
                        alert.showAndWait();
                    }

        }else {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Please fill all the data");
            alert.showAndWait();
        }

    }

    @FXML
    void cancelClicked(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        upload_photo.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(cancelBtn.getScene().getWindow());
            if (selectedFile != null) {
                try {
                    // Read the contents of the file into a byte array
                    FileInputStream inputStream = new FileInputStream(selectedFile);
                    imageData = new byte[(int) selectedFile.length()];
                    inputStream.read(imageData);
                    inputStream.close();


                    ByteArrayInputStream inputStream1=new ByteArrayInputStream(imageData);
                    Image image=new Image(inputStream1);
                    imageview.setImage(image);


//                    // Insert the byte array into the "image" column of the "guide_info" table
//                    Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//                    PreparedStatement statement = connection.prepareStatement("INSERT INTO guide_info (image) VALUES (?)");
//                    statement.setBytes(1, imageData);
//                    statement.executeUpdate();

                    System.out.println("Image loaded.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @FXML
    private ImageView imageview;
}
