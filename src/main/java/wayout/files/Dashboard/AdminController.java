package wayout.files.Dashboard;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private MFXTextField message_inputBox;

    @FXML
    private AnchorPane messages_anchorpane;

    @FXML
    private MFXScrollPane messages_scrollPane;

    @FXML
    private VBox messages_vbox;

    @FXML
    private MFXButton sendBTN;

    @FXML
    private Label top_Messages;

    @FXML
    private VBox user_card_VBOX;

    private void sendMessage(String message, Boolean isServer) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (isServer) {
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    hBox.setPadding(new Insets(10, 20, 0, 20));

                    Text text = new Text(message);
                    text.setFill(Color.BLACK);

                    TextFlow textFlow = new TextFlow(text);
                    textFlow.setStyle("-fx-background-color: #e8e8e8;" +
                            "-fx-text-fill: White;" +
                            "-fx-padding: 5 10 5 10px;" +
                            "-fx-font-size: 14px;" +
                            "-fx-text-fill: black;" +
                            "-fx-background-radius: 20px;" +
                            "-fx-max-width: 400px");

                    textFlow.setPadding(new Insets(10, 10, 0, 10));


                    hBox.getChildren().add(textFlow);

                    Platform.runLater(() -> {
                        messages_vbox.getChildren().add(hBox);
                    });
                } else {
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(10, 20, 0, 20));

                    Text text = new Text(message);
                    text.setFill(Color.rgb(255, 255, 255));

                    TextFlow textFlow = new TextFlow(text);
                    textFlow.setStyle("-fx-background-color: #0066ff;" +
                            "-fx-text-fill: White;" +
                            "-fx-padding: 5 10 5 10px;" +
                            "-fx-font-size: 14px;" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 15px;" +
                            "-fx-max-width: 400px");

                    textFlow.setPadding(new Insets(10, 10, 0, 10));


                    hBox.getChildren().add(textFlow);

                    Platform.runLater(() -> {
                        messages_vbox.getChildren().add(hBox);
                    });
                }

            }
        }).start();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
