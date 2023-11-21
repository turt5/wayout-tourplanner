package wayout.files.Homepage;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import wayout.files.LoginPage.LoginController;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Home3Controller implements Initializable {
    public MFXButton loginBtn;
    @FXML
    MediaView bgVideo;

    @FXML
    AnchorPane anchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file=new File("nature4.mp4");
        Media media=new Media(file.toURI().toString());
        MediaPlayer mediaPlayer=new MediaPlayer(media);
        bgVideo.setMediaPlayer(mediaPlayer);
        mediaPlayer.setVolume(0);

        double initialScale = 1.3;

        bgVideo.setScaleX(initialScale);
        bgVideo.setScaleY(initialScale);

        mediaPlayer.play();
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        loginBtn.setOnAction(e->{
            try{
                Parent root= FXMLLoader.load(LoginController.class.getResource("login.fxml"));
                Stage stage= (Stage) ((Node)e.getSource()).getScene().getWindow();
                Scene scene=new Scene(root);
                stage.setScene(scene);
                stage.show();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });
    }

}
