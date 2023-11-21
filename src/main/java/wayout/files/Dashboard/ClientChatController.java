package wayout.files.Dashboard;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import wayout.files.Dashboard.util.NetworkUtil;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClientChatController implements Initializable {

    @FXML
    private AnchorPane main_panel;
    @FXML
    private VBox messageBodyVbox;

    @FXML
    private MFXTextField messageBox;

    @FXML
    private MFXButton sendBtn;

    @FXML
    private Label clientName;

    private String name;
    private NetworkUtil networkUtil;
    private NetworkInformation networkInformation = new NetworkInformation();


//    @FXML
//    void sendButtonClicked(ActionEvent event) {
//
//    }

    private void sendMessage(String message,String from,String to, Boolean isServer) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (isServer) {
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    hBox.setPadding(new Insets(10, 20, 0, 20));

                    VBox vBox=new VBox();
                    AnchorPane anchorPane=new AnchorPane();

                    Label message_from=new Label(from+"->");
                    message_from.setStyle("-fx-font-size: 11px;" +
                            "-fx-font-weight: bold");
                    message_from.setLayoutY(2);
                    message_from.setLayoutX(10);



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
                    textFlow.setLayoutY(16);


                    anchorPane.getChildren().add(message_from);
                    vBox.getChildren().add(anchorPane);
                    vBox.getChildren().add(textFlow);
                    hBox.getChildren().add(vBox);

                    Platform.runLater(() -> {
                        messageBodyVbox.getChildren().add(hBox);
                    });
                } else {
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(10, 20, 0, 20));

                    VBox vBox=new VBox();
                    AnchorPane anchorPane=new AnchorPane();

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


                    Label message_to=new Label("->"+to);
                    message_to.setStyle("-fx-font-size: 11px;" +
                            "-fx-font-weight: bold");
                    message_to.setLayoutY(5);
                    message_to.setLayoutX(15);



                    vBox.getChildren().add(textFlow);
                    vBox.getChildren().add(anchorPane);
                    anchorPane.getChildren().add(message_to);
                    hBox.getChildren().add(vBox);

                    Platform.runLater(() -> {
                        messageBodyVbox.getChildren().add(hBox);
                    });
                }

            }
        }).start();
    }


    public void printInboxMessages() {
        for (int i = 0; i < networkInformation.inbox.size(); i++) {
            System.out.println(networkInformation.inbox.get(i));
        }
    }

    private class ReadThreadClient implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Object receivedObject = networkUtil.read();
                    if (receivedObject instanceof Message) {

                        /*
                        receivedObject instanceof Message means that the variable receivedObject
                        is being tested to see if it is an instance of the Message class or one of its subclasses
                         */
                        Message message = (Message) receivedObject;
                        networkInformation.setMessages("From: " + message.getFrom() + " Message: " + message.getText());
                        System.out.println("From: " + message.getFrom() + " Message: " + message.getText());
                        sendMessage(message.getText(),message.getFrom(), message.getTo(),true);

                    }
                } catch (Exception e) {
                    System.out.println(e);
                    break;
                }
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        sendBtn.setOnAction(event -> {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String Non_extracted_message = messageBox.getText();
//                    sendMessage(message,false);

                    String[] extracted_message = Non_extracted_message.split(",");
                    String to = extracted_message[0];
                    String message = extracted_message[1];


                    Message msg = new Message();

                    msg.setFrom(name);
                    msg.setTo(to);
                    msg.setText(message);
                    System.out.println(msg);
                    try {
                        networkUtil.write(msg);
                        sendMessage(message,msg.getFrom(), msg.getTo(),false);
                        messageBox.clear();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();

        });

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Input Dialog");
        dialog.setHeaderText("Enter your name:");
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            name = result.get();
            try{
                networkUtil=new NetworkUtil("127.0.0.1",33331);
                networkUtil.write(name);

                clientName.setText(name);
                new Thread(new ReadThreadClient()).start();


            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            System.exit(0);
        }

    }
}
