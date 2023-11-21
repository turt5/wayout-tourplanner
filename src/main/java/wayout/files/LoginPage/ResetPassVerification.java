package wayout.files.LoginPage;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

import java.io.IOException;

public class ResetPassVerification extends TemporaryData {

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

    @FXML
    void cancelClicked(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
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
    void signupClicked(ActionEvent event) {

    }

    @FXML
    void submitClicked(ActionEvent event) throws IOException {
        if(generated_code2.equals(EmailBox.getText())){
            Parent root= FXMLLoader.load(getClass().getResource("NewPassword.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

}