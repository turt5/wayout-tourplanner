package wayout.files.Dashboard;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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

public class AddPackage implements Initializable {

    @FXML
    private MFXButton addBtn;

    @FXML
    private MFXButton cancelBtn;

    @FXML
    private MFXTextField cityBox;

    @FXML
    private TextArea descriptionBox;

    @FXML
    private MFXTextField durationBox;

    @FXML
    private ImageView imgview;

    @FXML
    private MFXTextField nameBox;

    @FXML
    private MFXButton photoBox;

    @FXML
    private MFXTextField priceBox;

    byte[] imageData;

    @FXML
    void addClicked(ActionEvent event) {

        String package_name=nameBox.getText();
        String city=cityBox.getText();
        String price=priceBox.getText().trim();
        String duration=durationBox.getText();
        String description=descriptionBox.getText();



        if(package_name!=null && city!=null && price!=null && duration!=null && description!=null && imageData.length>=1){
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

            try {
                con = DriverManager.getConnection(url, username, password);
                pst = con.prepareStatement("INSERT INTO packages_list(Package_Name,City,Price,Package_Duration,Package_Description,Image) VALUES(?,?,?,?,?,?)");
                pst.setString(1, package_name);
                pst.setString(2,city);
                pst.setString(3,price);
                pst.setString(4,duration);
                pst.setString(5,description);
                pst.setBytes(6, imageData);
                pst.execute();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("New package added.");
                alert.setContentText("Your new package is added to the user dashboard, Thanks from Wayout!");
                alert.showAndWait();


                try {
                    Parent root = FXMLLoader.load(getClass().getResource("admin_dashboard.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

        @FXML
        void cancelClicked (ActionEvent event){

        }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        photoBox.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(cancelBtn.getScene().getWindow());
            if (selectedFile != null) {
                try {
                    // Read the contents of the file into a byte array
                    FileInputStream inputStream = new FileInputStream(selectedFile);
                    imageData = new byte[(int) selectedFile.length()];
                    inputStream.read(imageData);
                    inputStream.close();

//                    Image image=new Image(inputStream);
//                    System.out.println(image);
//                    imgview.setImage(image);

                    ByteArrayInputStream the_image = new ByteArrayInputStream(imageData);
                    Image img=new Image(the_image);
                    imgview.setImage(img);

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
}
