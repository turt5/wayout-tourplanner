package wayout.files.Homepage;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import wayout.files.LoginPage.LoginController;
import wayout.files.LoginPage.SignUpController;

import java.io.IOException;

public class HomeController {

    @FXML
    private Label car;

    @FXML
    private ImageView carHire;

    @FXML
    private ImageView hotel;

    @FXML
    private Label hotels;

    @FXML
    private MFXButton register;

    @FXML
    private MFXTextField searchBox;

    @FXML
    private MFXButton searchButton;

    @FXML
    private MFXButton signIn;

    @FXML
    private ImageView trips;

    @FXML
    void carHireClicked(MouseEvent event) {

    }

    @FXML
    void hotelClicked(MouseEvent event) {

    }

    @FXML
    void registerClicked(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(SignUpController.class.getResource("signUP.fxml"));
        stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    Stage stage;
    Scene scene;


    @FXML
    void searchClicked(ActionEvent event) {

    }

    @FXML
    void signInClicked(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(LoginController.class.getResource("login.fxml"));
        stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void tripsClicked(MouseEvent event) {

    }

//    @FXML
//    void searchHover(MouseDragEvent event) {
//        FadeTransition fade = new FadeTransition(Duration.millis(200), searchButton);
//        fade.setToValue(0.8);
//        fade.play();
//    }
//
//    @FXML
//    void searchHoverE(MouseDragEvent event) {
//        FadeTransition fade = new FadeTransition(Duration.millis(200), searchButton);
//        fade.setToValue(1.0);
//        fade.play();
//    }


}
