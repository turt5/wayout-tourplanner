package wayout.files.Dashboard;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.enums.FloatMode;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BookGuideController implements Initializable {

    @FXML
    private TextArea additional_message;

    @FXML
    private VBox booking_vbox;

    @FXML
    private MFXTextField email;

    @FXML
    private MFXTextField full_name;

    @FXML
    private MFXTextField phn_num;

    public void add_order_info_table(String guideName, String duration, Double cost) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(1020);
//        anchorPane.setPrefHeight(200);
        anchorPane.setLayoutY(100);
//        anchorPane.setStyle("-fx-background-color: red;");
        anchorPane.setPadding(new Insets(0, 0, 50, 0));

        Separator separator2 = new Separator();
        separator2.setPrefWidth(980);

        separator2.setLayoutX(20);
        separator2.setLayoutY(20);
        separator2.setOrientation(Orientation.HORIZONTAL);

        Label packageT = new Label("Guide Name: ");
        packageT.setLayoutX(20);
        packageT.setLayoutY(30);
        packageT.setStyle("-fx-font-size: 15px;" +
                "-fx-font-family: 'Arial Rounded MT Bold'");

        Label packageN = new Label(guideName);
        packageN.setLayoutX(600);
        packageN.setLayoutY(30);
        packageN.setPrefWidth(400);
        packageN.setAlignment(Pos.CENTER_RIGHT);
        packageN.setStyle("-fx-font-size: 15px;" +
                "-fx-font-family: 'Arial Rounded MT Bold';");

        Separator separator = new Separator();
        separator.setPrefWidth(980);

        separator.setLayoutX(20);
        separator.setLayoutY(60);
        separator.setOrientation(Orientation.HORIZONTAL);

        //second row

//        Separator separator3=new Separator();
//        separator3.setPrefWidth(980);
//
//        separator3.setLayoutX(20);
//        separator3.setLayoutY(20);
//        separator3.setOrientation(Orientation.HORIZONTAL);

        Label packageT2 = new Label("Time Duration: ");
        packageT2.setLayoutX(20);
        packageT2.setLayoutY(70);
        packageT2.setStyle("-fx-font-size: 15px;" +
                "-fx-font-family: 'Arial Rounded MT Bold'");

        Label packageN2 = new Label(duration+" Hour/Hours");
        packageN2.setLayoutX(600);
        packageN2.setLayoutY(70);
        packageN2.setPrefWidth(400);
        packageN2.setAlignment(Pos.CENTER_RIGHT);
        packageN2.setStyle("-fx-font-size: 15px;" +
                "-fx-font-family: 'Arial Rounded MT Bold';");

        Separator separator_1 = new Separator();
        separator_1.setPrefWidth(980);

        separator_1.setLayoutX(20);
        separator_1.setLayoutY(100);
        separator_1.setOrientation(Orientation.HORIZONTAL);


        //3rd row
        Label packageT3 = new Label("Total cost: ");
        packageT3.setLayoutX(20);
        packageT3.setLayoutY(110);
        packageT3.setStyle("-fx-font-size: 15px;" +
                "-fx-font-family: 'Arial Rounded MT Bold'");

        Label packageN3 = new Label(cost + " TK (BDT)");
        packageN3.setLayoutX(600);
        packageN3.setLayoutY(110);
        packageN3.setPrefWidth(400);
        packageN3.setAlignment(Pos.CENTER_RIGHT);
        packageN3.setStyle("-fx-font-size: 15px;" +
                "-fx-font-family: 'Arial Rounded MT Bold';");

        Separator separator_2 = new Separator();
        separator_2.setPrefWidth(980);

        separator_2.setLayoutX(20);
        separator_2.setLayoutY(140);
        separator_2.setOrientation(Orientation.HORIZONTAL);


        anchorPane.getChildren().add(separator_2);
        anchorPane.getChildren().add(packageT3);
        anchorPane.getChildren().add(packageN3);

        anchorPane.getChildren().add(separator_1);
        anchorPane.getChildren().add(packageT2);
        anchorPane.getChildren().add(packageN2);

        anchorPane.getChildren().add(separator);
        anchorPane.getChildren().add(separator2);
        anchorPane.getChildren().add(packageT);
        anchorPane.getChildren().add(packageN);
        booking_vbox.getChildren().add(anchorPane);
    }


    public void payment_method(double cost) throws IOException {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(1020);
        anchorPane.setPrefHeight(400);
        anchorPane.setLayoutY(100);
//        anchorPane.setStyle("-fx-background-color: red");

//        Label bkash=new Label("bKash");
//        bkash.setLayoutX(20);
//        bkash.setLayoutY(10);
//        bkash.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
//                "-fx-font-size: 15px");
        MFXRadioButton radioButton = new MFXRadioButton("bKash");
        radioButton.setLayoutY(10);
        radioButton.setLayoutX(20);
        radioButton.setStyle("-fx-font-size: 15px;" +
                "-fx-font-family: 'Arial Rounded MT Bold'");


        ImageView imageView = new ImageView(new Image(getClass().getResource("bkash.png").openStream()));
        imageView.setFitWidth(50);
        imageView.setFitHeight(30);
        imageView.setLayoutY(4);
        imageView.setLayoutX(110);

        double total_cost = cost + (cost * 0.0185);

        Text text = new Text("প্রথমে উল্লেখিত নাম্বারে টাকা পাঠিয়ে ফর্মটি পূরণ করুন। Also note that 1.85% bKash \"SEND MONEY\" cost will be added with net price. Total amount you need to send us at " + total_cost + " tk.");
//        text.maxWidth(980);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-font-size: 14px;" +
                "-fx-wrap-text: true;" +
                "-fx-max-width: 980");
        textFlow.setLayoutX(20);
        textFlow.setLayoutY(45);

        Separator separator = new Separator();
        separator.setPrefWidth(980);

        separator.setLayoutX(20);
        separator.setLayoutY(100);
        separator.setOrientation(Orientation.HORIZONTAL);

        Label bkshNumber_wayout = new Label("bKash personal number: +8801738439423");
        bkshNumber_wayout.setLayoutX(20);
        bkshNumber_wayout.setLayoutY(110);
        bkshNumber_wayout.setStyle("-fx-font-size: 14px");

        Separator separator2 = new Separator();
        separator2.setPrefWidth(980);

        separator2.setLayoutX(20);
        separator2.setLayoutY(140);
        separator2.setOrientation(Orientation.HORIZONTAL);


        Label user_bkash = new Label("Your bKash payment number: ");
        user_bkash.setLayoutX(20);
        user_bkash.setLayoutY(170);
        user_bkash.setStyle("-fx-font-size: 14px");


        MFXTextField bkshNumberofUser = new MFXTextField();
        bkshNumberofUser.setLayoutX(600);
//        bkshNumberofUser.setLayoutX(120);
        bkshNumberofUser.setLayoutY(165);
        bkshNumberofUser.setStyle("-fx-pref-height: 30;" +
                "-fx-pref-width: 300;" +
                "-fx-padding: 0 0 0 10px");
        bkshNumberofUser.setFloatMode(FloatMode.DISABLED);
        bkshNumberofUser.setPromptText("bKash payment number");

        Separator separator3 = new Separator();
        separator3.setPrefWidth(980);

        separator3.setLayoutX(20);
        separator3.setLayoutY(225);
        separator3.setOrientation(Orientation.HORIZONTAL);


        //transc
        Label transc = new Label("Your bKash payment Transaction id: ");
        transc.setLayoutX(20);
        transc.setLayoutY(255);
        transc.setStyle("-fx-font-size: 14px");


        MFXTextField paymentTransaction = new MFXTextField();
        paymentTransaction.setLayoutX(600);
//        bkshNumberofUser.setLayoutX(120);
        paymentTransaction.setLayoutY(250);
        paymentTransaction.setStyle("-fx-pref-height: 30;" +
                "-fx-pref-width: 300;" +
                "-fx-padding: 0 0 0 10px");
        paymentTransaction.setFloatMode(FloatMode.DISABLED);
        paymentTransaction.setPromptText("transaction id");


        Separator separator4 = new Separator();
        separator4.setPrefWidth(980);

        separator4.setLayoutX(20);
        separator4.setLayoutY(305);
        separator4.setOrientation(Orientation.HORIZONTAL);


        //

        Text text2 = new Text("Your personal data will be used to process your order, support your experience throughout this website, and for other purposes described in our privacy policy.");
//        text.maxWidth(980);
        TextFlow textFlow2 = new TextFlow(text2);
        textFlow2.setStyle("-fx-font-size: 14px;" +
                "-fx-wrap-text: true;" +
                "-fx-max-width: 980;");
        textFlow2.setLayoutX(20);
        textFlow2.setLayoutY(320);


        MFXCheckbox mfxCheckbox = new MFXCheckbox("I have read and agree to the terms and conditions *");
        mfxCheckbox.setLayoutX(20);
        mfxCheckbox.setStyle("-fx-font-size: 14px");
        mfxCheckbox.setLayoutY(380);


        MFXButton bookNow = new MFXButton("Book Guide");
        bookNow.setLayoutY(440);
        bookNow.setLayoutX(800);
        bookNow.setStyle("-fx-font-size: 14px;" +
                "-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-padding: 0 10 0 10px;" +
                "-fx-border-color: #0066ff;" +
                "-fx-background-color: #ecbf2a;"+
                "-fx-border-width: 2px");
        bookNow.setPrefHeight(50);
        bookNow.setPrefWidth(150);


        anchorPane.getChildren().add(bookNow);
        anchorPane.getChildren().add(mfxCheckbox);
        anchorPane.getChildren().add(textFlow2);
        anchorPane.getChildren().add(transc);
        anchorPane.getChildren().add(paymentTransaction);
        anchorPane.getChildren().add(separator4);
        anchorPane.getChildren().add(separator3);
        anchorPane.getChildren().add(bkshNumberofUser);
        anchorPane.getChildren().add(user_bkash);
        anchorPane.getChildren().add(separator2);
        anchorPane.getChildren().add(separator);
        anchorPane.getChildren().add(bkshNumber_wayout);
        anchorPane.getChildren().add(textFlow);
        anchorPane.getChildren().add(radioButton);
        anchorPane.getChildren().add(imageView);
        booking_vbox.getChildren().add(anchorPane);


        bookNow.setOnAction(event -> {

            String user_fullname = full_name.getText();

            String user_email = email.getText();
            String user_mobile = phn_num.getText();
            String order_info = additional_message.getText();
            String bkash_Num = bkshNumberofUser.getText();
            String bk_transaction = paymentTransaction.getText();






            if(order_info==null || order_info.equals("")){
                order_info="Not specified";
            }

            System.out.println(user_fullname);
            System.out.println(user_email);
            System.out.println(user_mobile);
            System.out.println(order_info);
            System.out.println(bkash_Num);
            System.out.println(bk_transaction);
            System.out.println(radioButton.isSelected());
            System.out.println(mfxCheckbox.isSelected());

            if (radioButton.isSelected() && mfxCheckbox.isSelected() && user_fullname != null && user_email != null && user_mobile != null && bkash_Num != null && bk_transaction != null && bkash_Num.length()>=11 && bk_transaction.length()>10) {


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
                    pst = con.prepareStatement("INSERT INTO guide_hire_list(Guide_Name,Hours_Hired,Total_Cost,Hire_Date,Starting_Time,User_Who_Hired,User_Email,User_Phone,Booking_Notes,Payment_Bkash_Number,Payment_Bkash_Transaction_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?)");
                    pst.setString(1, guide_name);
                    pst.setString(2,hours);
                    pst.setString(3, String.valueOf(total_cost));
                    pst.setString(4,hire_date);
                    pst.setString(5,starting_time);
                    pst.setString(6,user_fullname);
                    pst.setString(7,user_email);
                    pst.setString(8,user_mobile);
                    pst.setString(9,order_info);
                    pst.setString(10,bkash_Num);
                    pst.setString(11,bk_transaction);
                    pst.execute();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Request sent successfully");
                    alert.setContentText("Guide will contact you later on the email you provided, Thanks from wayout!");
                    alert.showAndWait();

                    try {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                synchronized (this) {
                                    Platform.runLater(() -> {
                                        try {
                                            Parent root = FXMLLoader.load(getClass().getResource("user_dashboard.fxml"));
                                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                            Scene scene = new Scene(root);
                                            stage.setScene(scene);
                                            stage.show();
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }

                                    });
                                }


                            }
                        }).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Please fill all the informations correctly");
                alert.showAndWait();
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String all_info = "";
        try {
            File file = new File("src/main/resources/wayout/files/Dashboard/guide_order_info.txt");

            if (file.exists()) {

                Path filePath = Paths.get("src/main/resources/wayout/files/Dashboard/guide_order_info.txt");
                try {
                    byte[] fileBytes = Files.readAllBytes(filePath);
                    all_info = new String(fileBytes);

                } catch (IOException e) {
                    System.out.println("Error reading file: " + e.getMessage());
                }

                System.out.println(all_info);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        String[] extract = all_info.split("@");
        guide_name = extract[0];
        hire_date = extract[1];
        hours = extract[2];
        starting_time = extract[3];
        cost = extract[4];

        double charge = Double.parseDouble(cost);
        System.out.println(charge);
        int hour = Integer.parseInt(hours);
        System.out.println(hour);

        double totalCost = charge * hour;
        System.out.println(totalCost);

        add_order_info_table(guide_name, hours, totalCost);
        try {
            payment_method(totalCost);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String guide_name;
    private String hire_date;
    private String hours;
    private String starting_time;
    private String cost;
}