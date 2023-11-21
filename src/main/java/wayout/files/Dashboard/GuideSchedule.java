package wayout.files.Dashboard;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class GuideSchedule implements Initializable {

    @FXML
    private MFXButton cancelBtn;

    @FXML
    private MFXButton confirmBtn;

    @FXML
    private MFXTextField durationBox;

    @FXML
    private VBox guide_card_vbox;

    @FXML
    private MFXTextField timeBox;

    @FXML
    private MFXDatePicker tourDate;


    private void addGuide(Image image, String guideName, String days, String availArea, String guideDetails, String costHour) {
        HBox hBox = new HBox();
        hBox.setPrefHeight(300);
        hBox.setPrefWidth(1050);
//        hBox.setStyle("-fx-background-color: red");

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(1050);
        anchorPane.setPrefHeight(300);

        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(250);
        imageView.setFitWidth(350);

        imageView.setLayoutX(10);
        imageView.setLayoutY(20);


        javafx.scene.control.Label label = new javafx.scene.control.Label(guideName);
        label.setLayoutX(400);
        label.setLayoutY(20);
        label.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-size: 20px;" +
                "");


        javafx.scene.control.Label duration = new javafx.scene.control.Label("Time: "+days);
        duration.setLayoutX(400);
        duration.setLayoutY(55);
      //  duration.setMaxWidth(85);
//        duration.setWrapText(true);


        duration.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-padding: 5px;" +
                        "-fx-background-color: #0066ff;" +
                        "-fx-text-fill: white" +
                        "");


        javafx.scene.control.Label feat = new javafx.scene.control.Label("Area: "+availArea);
        feat.setLayoutX(600);
        feat.setLayoutY(55);
        feat.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-padding: 5px;" +
                        "-fx-background-color: #ffc600;" +
                        "-fx-text-fill: black;" +
                        "");


        javafx.scene.control.Label detailS_lbl = new javafx.scene.control.Label(guideDetails);
        detailS_lbl.setLayoutX(400);
        detailS_lbl.setLayoutY(95);
        detailS_lbl.setMaxWidth(500);
//        detailS_lbl.setMaxHeight(150);
        detailS_lbl.setWrapText(true);

        detailS_lbl.setStyle("-fx-font-size: 14px;");

        javafx.scene.control.Label cost_package = new Label(costHour + " TK/Hour");
        cost_package.setLayoutX(400);
        cost_package.setLayoutY(240);
        cost_package.setStyle("-fx-background-color: #00ab71;" +
                "-fx-padding: 5px;" +
                "-fx-font-size: 14px;" +
                "-fx-text-fill: white");


        MFXButton viewDetailsButtn = new MFXButton("View details");
        viewDetailsButtn.setStyle("-fx-font-size: 14px;" +
                "-fx-padding: 5 10 5 10px;" +
                "-fx-text-fill: white;" +
                "-fx-background-color: teal;" +
                "-fx-border-radius: 0px;" +
                "-fx-background-radius: 0px");
        viewDetailsButtn.setLayoutY(240);
        viewDetailsButtn.setLayoutX(505);

//        MFXButton hireNowButton = new MFXButton("Hire now");
//        hireNowButton.setStyle("-fx-font-size: 14px;" +
//                "-fx-padding: 5 10 5 10px;" +
//                "-fx-text-fill: white;" +
//                "-fx-background-color: #004b4b;" +
//                "-fx-border-radius: 0px;" +
//                "-fx-background-radius: 0px");
//        hireNowButton.setLayoutY(240);
//        hireNowButton.setLayoutX(510);


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    anchorPane.getChildren().add(imageView);
                    anchorPane.getChildren().add(label);
                    anchorPane.getChildren().add(duration);
                    anchorPane.getChildren().add(feat);
                    anchorPane.getChildren().add(detailS_lbl);
                    anchorPane.getChildren().add(cost_package);
                    // anchorPane.getChildren().add(viewDetailsButtn);
//                    anchorPane.getChildren().add(hireNowButton);

                    hBox.getChildren().add(anchorPane);
                    guide_card_vbox.getChildren().add(hBox);
                });
            }
        });
        t.start();
    }

    @FXML
    void cancelClicked(ActionEvent event) {

    }

    @FXML
    void confirmClicked(ActionEvent event) throws IOException {


        File file = new File("src/main/resources/wayout/files/Dashboard/guide_order_info.txt");
        if(file.exists()){
            BufferedWriter writer=new BufferedWriter(new FileWriter(file));
            writer.write(Guide_name+"@"+tourDate.getText()+"@"+durationBox.getText()+"@"+timeBox.getText()+"@"+guide_charge_per_hour);
            writer.close();
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(200);
                }catch (Exception e){
                    e.printStackTrace();
                }


                Platform.runLater(()->{
                    try{
                        Parent root=FXMLLoader.load(getClass().getResource("guide_payment.fxml"));
                        main_panel.getChildren().add(root);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
            }
        }).start();
    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String text;
        try{
            File file = new File("src/main/resources/wayout/files/Dashboard/guide_name.txt");
            if(file.exists()){
                BufferedReader reader=new BufferedReader(new FileReader(file));
                text=reader.readLine();

                String[] extract=text.split("@");
                Guide_name=extract[0];
                guide_charge_per_hour=extract[1];
            }
        }catch (Exception e){
            e.printStackTrace();
        }


//        Alert alert=new Alert(Alert.AlertType.INFORMATION);
//        alert.setHeaderText(name);
//        alert.showAndWait();

        try{
            String url = "jdbc:mysql://127.0.0.1/wayout";
            String username = "root";
            String password = "";


            Connection conn = DriverManager.getConnection(url, username, password);


            String sql = "SELECT * FROM guides_list WHERE Name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, Guide_name);

            ResultSet rs = stmt.executeQuery();

            String name,city,email,mobile,details,nid,hourlyCharge,availableTime;
            InputStream image;

            while (rs.next()){
                name = rs.getString("Name");
                city = rs.getString("City");
                email = rs.getString("Email");
                 mobile = rs.getString("Mobile");
                details = rs.getString("Details");
//                int nid = rs.getInt("NID");
                hourlyCharge = String.valueOf(rs.getDouble("Hourly_Charge"));
                availableTime = rs.getString("Available_time");
//                String status = rs.getString("Status");
                image = rs.getBinaryStream("image");

                Image img=new Image(image);


                addGuide(img,name,availableTime,city,details,hourlyCharge);

            }
            rs.close();
            stmt.close();
            conn.close();

        }catch (Exception e){
            e.printStackTrace();
        }




    }

    @FXML
    private AnchorPane main_panel;
    String Guide_name="";
    String guide_charge_per_hour="";
}
