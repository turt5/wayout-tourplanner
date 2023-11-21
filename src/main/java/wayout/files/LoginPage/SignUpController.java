package wayout.files.LoginPage;

import io.github.palexdev.materialfx.beans.NumberRange;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import wayout.files.Homepage.HomePage_2nd_Controller;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import java.io.IOException;

public class SignUpController extends TemporaryData implements Initializable{

    @FXML
    private MFXButton backButton;

    @FXML
    private MFXPasswordField cPasswordBox;

    @FXML
    private MFXDatePicker dateofBirth;

    @FXML
     MFXTextField emailBox;

    @FXML
    private MFXTextField fnameBox;

    @FXML
    private MFXComboBox<String> gender;

    @FXML
    private MFXTextField lNameBox;

    @FXML
    private MFXPasswordField passwordBox;

    @FXML
    private MFXButton signUP;
    private Stage stage;
    private Scene scene, scene2;
    private Parent root,root2;


    @FXML
    void backButtonClicked(ActionEvent event) throws Exception {
        root= FXMLLoader.load(HomePage_2nd_Controller.class.getResource("home3.fxml"));
        stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void signUPClicked(ActionEvent event) throws IOException {


        String a1=fnameBox.getText();
        String a2=lNameBox.getText();
        String a3=emailBox.getText();
        String a4=dateofBirth.getCurrentDate().toString();
        String a5=gender.getValue();
        String a6=passwordBox.getText();
        String a7=cPasswordBox.getText();


        if ((a1 != null) && (a2 != null) && (a3 != null) && (a5 != null) && (a6 != null && a6.equals(a7))){
            first_name=a1;
            last_name=a2;
            email_z=a3;
            date_of_birth=a4;
            gender_T=a5;
            password_T=a6;
            c_password=a7;

            Random random=new Random();

            int sixLengthRandomNumber = random.nextInt(100000, 1000000);
            System.out.println(sixLengthRandomNumber);
            generated_code=""+sixLengthRandomNumber;

            EmailVerifier emailVerifier=new EmailVerifier();
            emailVerifier.verifyEmail(a3,event);
        }else {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Please enter all the data correctly");
            alert.showAndWait();
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gender.getItems().addAll(
                "Male",
                "Female",
                "Custom"
        );
        dateofBirth.setYearsRange(NumberRange.of(1970,2023)); // limitations for future dates
    }
}
