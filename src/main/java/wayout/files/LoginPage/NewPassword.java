package wayout.files.LoginPage;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class NewPassword extends TemporaryData{

    @FXML
    private MFXPasswordField ConfirmNewPass;

    @FXML
    private MFXButton Submit;

    @FXML
    private MFXPasswordField newPass;
    @FXML
    private Hyperlink ContactLink;

    @FXML
    private Hyperlink SignupLink;

    @FXML
    private MFXButton Cancel;

    @FXML
    void CancelClicked(ActionEvent event) {

    }
    @FXML
    void contactClicked(ActionEvent event) {

    }

    @FXML
    void signupClicked(ActionEvent event) {

    }

    @FXML
    void SubmitClicked(ActionEvent event) throws IOException {

        if(newPass.getText()!=null && newPass.getText().equals(ConfirmNewPass.getText())){
            Parent root= FXMLLoader.load(getClass().getResource("login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene=new Scene(root);
            stage.setScene(scene);
            stage.show();

            // now change the password in the database:
            String U_Password=newPass.getText();

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

            try {
                con = DriverManager.getConnection(url, username, password);

                pst = con.prepareStatement("UPDATE accountinfo SET password = ? WHERE email = ?");
                pst.setString(1, U_Password);
                pst.setString(2, emal_2);
                pst.execute();

                System.out.println("Update successful");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Successful");
                alert.setContentText("Password Reset Successful");
                alert.showAndWait();


            } catch (Exception e) {
                e.getStackTrace();
            }

        }

    }

}
