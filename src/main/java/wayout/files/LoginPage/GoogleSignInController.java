package wayout.files.LoginPage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class GoogleSignInController extends TemporaryData {

    @FXML
    private JFXTextArea box;

    @FXML
    private JFXButton submit;

    @FXML
    void submitClicked(ActionEvent event) {
        authcode=box.getText();
    }

}
