package wayout.files.Dashboard;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ManageOrdersAdmin implements Initializable {
    public VBox packages_vbox;
    public AnchorPane main_anchorPane;

    private void showOrders(int id, String user, String email, String phone, String note, String Package_title, String Package_duration, String total_cost, String PaymentNumber, String transactionID){
        AnchorPane cards=new AnchorPane();
//        cards.setPrefHeight(100);
//        cards.setBackground(Background.fill(Color.rgb(27,113,250)));
        cards.setStyle("-fx-background-radius: 10px; -fx-background-color: rgb(38,107,255);-fx-padding: 10px 0 10px 0");

        Label index=new Label("Order number: "+currentIndex);
        index.setPrefWidth(1020);
        index.setLayoutY(5);
        index.setStyle("-fx-text-alignment: center; -fx-font-family: 'Poppins SemiBold'; -fx-font-size: 14px; -fx-alignment: center; -fx-text-fill: white");

        Label user_name=new Label("Ordered by: "+user);
        user_name.setLayoutY(35);
        user_name.setLayoutX(100);
        user_name.setStyle("-fx-text-alignment: center; -fx-font-family: 'Poppins SemiBold'; -fx-font-size: 14px; -fx-alignment: center; -fx-text-fill: white");


        Label user_email=new Label("User email: "+email);
        user_email.setLayoutX(100);
        user_email.setLayoutY(65);
        user_email.setStyle("-fx-text-alignment: center; -fx-font-family: 'Poppins'; -fx-font-size: 14px; -fx-alignment: center; -fx-text-fill: white");


        Label user_phone=new Label("User phone: "+phone);
        user_phone.setLayoutX(100);
        user_phone.setLayoutY(95);
        user_phone.setStyle("-fx-text-alignment: center; -fx-font-family: 'Poppins'; -fx-font-size: 14px; -fx-alignment: center; -fx-text-fill: white");


        Label user_note=new Label("Order note: "+note);
        user_note.setLayoutX(100);
        user_note.setLayoutY(125);
        user_note.setStyle("-fx-text-alignment: center; -fx-font-family: 'Poppins'; -fx-font-size: 14px; -fx-alignment: center; -fx-text-fill: white");

        Label packageName=new Label("Package name: "+Package_title);
        packageName.setLayoutX(100);
        packageName.setLayoutY(155);
        packageName.setStyle("-fx-text-alignment: center; -fx-font-family: 'Poppins SemiBold'; -fx-font-size: 14px; -fx-alignment: center; -fx-text-fill: white");

        Label packageDuration=new Label("Package duration: "+Package_duration);
        packageDuration.setLayoutX(100);
        packageDuration.setLayoutY(185);
        packageDuration.setStyle("-fx-text-alignment: center; -fx-font-family: 'Poppins'; -fx-font-size: 14px; -fx-alignment: center; -fx-text-fill: white");

        Label cost=new Label("Total cost: "+email);
        cost.setLayoutX(100);
        cost.setLayoutY(215);
        cost.setStyle("-fx-text-alignment: center; -fx-font-family: 'Poppins'; -fx-font-size: 14px; -fx-alignment: center; -fx-text-fill: white");

        Label payment=new Label("Payment: "+((PaymentNumber.isBlank() || PaymentNumber.isEmpty()) && (transactionID.isBlank() || transactionID.isEmpty())?"Not Paid":"Paid"));
        payment.setLayoutX(100);
        payment.setLayoutY(245);
        payment.setStyle("-fx-text-alignment: center; -fx-font-family: 'Poppins'; -fx-font-size: 14px; -fx-alignment: center; -fx-text-fill: white");


        MFXButton button=new MFXButton("Finish tour");
        button.setLayoutY(97);
        button.setLayoutX(850);
        button.setPrefHeight(50);
        button.setPrefWidth(100);
        button.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
                    String updateQuery = "UPDATE ordered_package SET Status = ? WHERE ID = ?";

                    try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                        preparedStatement.setString(1, "finished");
                        preparedStatement.setInt(2, id);

                        int rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            System.out.println("Status updated successfully.");
                        } else {
                            System.out.println("No rows updated. ID not found.");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        cards.getChildren().add(button);

        cards.getChildren().add(payment);
        cards.getChildren().add(cost);
        cards.getChildren().add(packageDuration);
        cards.getChildren().add(packageName);
        cards.getChildren().add(user_note);
        cards.getChildren().add(user_phone);
        cards.getChildren().add(user_email);
        cards.getChildren().add(user_name);
        cards.getChildren().add(index);

        packages_vbox.getChildren().add(cards);
        AnchorPane space=new AnchorPane();
        space.setPrefHeight(5);
        packages_vbox.getChildren().add(space);
        currentIndex++;
    }

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/wayout";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            try (Connection connection = getConnection()) {
                String query="SELECT * FROM ordered_package WHERE ID > ? ORDER BY ID";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, lastIndex);
                    ResultSet resultSet=preparedStatement.executeQuery();

                    while (resultSet.next()){
                        int id= resultSet.getInt("ID");
                        String name=resultSet.getString("User_Name");
                        String email=resultSet.getString("User_Email");
                        String phone=resultSet.getString("User_Phone");
                        String note=resultSet.getString("User_Note");
                        String package_title=resultSet.getString("Package_Title");
                        String duration=resultSet.getString("Package_Duration_Days");
                        String cost=resultSet.getString("Total_Cost");
                        String paymentNumber=resultSet.getString("Payment_Number");
                        String transactionID=resultSet.getString("Payment_Transaction_ID");
                        String status=resultSet.getString("Status");

                        if(status.equals("running")){
                            Platform.runLater(()->{
                                showOrders(id,name,email,phone,note,package_title,duration,cost,paymentNumber,transactionID);
                            });
                        }

                        lastIndex=id;
                    }
                }catch (Exception ex){

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);


//        if(lastIndex==0){
//            packages_vbox.getChildren().clear();
//
//
//            AnchorPane anchorPane=new AnchorPane();
//            anchorPane.setPrefHeight(650);
//
//            Label label=new Label("No orders!");
//            label.setPrefWidth(1020);
//            label.setLayoutY(300);
//            label.setStyle("-fx-alignment: center;-fx-text-alignment: center;-fx-font-family: 'Poppins SemiBold';-fx-font-size: 14px");
//            anchorPane.getChildren().add(label);
//            packages_vbox.getChildren().add(anchorPane);
//        }else{
//
//        }

    }
    int currentIndex=1;
    private int lastIndex=0;
    int countOrders=0;
}

/*
*
*
ID
User_Name
User_Email
User_Phone
User_Note
Package_Title
Package_Duration_Days
Total_Cost
Payment_Number
Payment_Transaction_ID
*
*
* */