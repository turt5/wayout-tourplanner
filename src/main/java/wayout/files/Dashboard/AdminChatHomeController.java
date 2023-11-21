package wayout.files.Dashboard;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AdminChatHomeController implements Initializable {

    public MFXButton refreshButton;
    @FXML
    private VBox mainVbox;

    private boolean isRead = false;

    private void showUser(Image image, String userName, String text, String time, boolean isGuide) {
        AnchorPane mainAnchorpane = new AnchorPane();
        mainAnchorpane.setPrefHeight(80);
        mainAnchorpane.setMaxHeight(80);
        mainAnchorpane.setStyle("-fx-background-color: white");

        Circle profilePicture = new Circle();
        profilePicture.setFill(new ImagePattern(image));
        profilePicture.setRadius(30);
        profilePicture.setLayoutX(120);
        profilePicture.setLayoutY(40);
        profilePicture.setSmooth(true);

        String designation = isGuide ? "(Guide)" : "(Traveller)";

        Label name = new Label(userName);
        name.setLayoutX(170);
        name.setLayoutY(13);
        name.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 15px;-fx-font-weight: bold");

        Label message = new Label(text.isBlank() || text.isEmpty() ? "No message yet!":text.trim());
        message.setLayoutX(170);
        message.setLayoutY(45);
        message.setStyle("-fx-font-family: 'Poppins';-fx-font-size: 13px;-fx-max-height: 35;-fx-wrap-text: false");
        message.setMaxWidth(600);


        String formattedTime = formatWithoutSeconds(time.isBlank()||time.isEmpty()?"Unavailable":time);


        Label messageTime = new Label(formattedTime);
        messageTime.setLayoutY(45);
        messageTime.setLayoutX(780);
        messageTime.setStyle("-fx-font-family: 'Poppins';-fx-font-size: 13px; -fx-text-fill: #0088ff;");

        Label dot = new Label("·");
        dot.setLayoutY(5);
        dot.setLayoutX(900);

        updateDotColor(dot);


        mainAnchorpane.getChildren().add(dot);
        mainAnchorpane.setOnMouseClicked(event -> handler(event, userName));
        mainAnchorpane.getChildren().add(messageTime);
        mainAnchorpane.getChildren().add(message);
        mainAnchorpane.getChildren().add(name);
        mainAnchorpane.getChildren().add(profilePicture);


//        Separator separator = new Separator();
//        separator.setLayoutY(80);
//        separator.setPrefWidth(1020); // Set the width to match the anchor pane
//        separator.prefHeight(2);
//        separator.setBackground(Background.fill(Color.BLACK));
//        mainAnchorpane.getChildren().add(separator);


        mainVbox.getChildren().add(mainAnchorpane);
    }

    private void updateDotColor(Label dot) {
        if (isRead) {
            dot.setStyle("-fx-font-weight: bold;-fx-font-size: 50px; -fx-text-fill: black");
        } else {
            dot.setStyle("-fx-font-weight: bold;-fx-font-size: 50px; -fx-text-fill: #00b164");
        }
    }

    private void handler(javafx.scene.input.MouseEvent mouseEvent, String name) {
        isRead = true;
        AnchorPane sourcePane = (AnchorPane) mouseEvent.getSource();
        Label dot = (Label) sourcePane.getChildren().get(0);
        updateDotColor(dot);

        openUserDetailsScene(name);
    }


    private static String formatWithoutSeconds(String timeWithSeconds) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("hh:mm a");

        try {
            LocalTime time = LocalTime.parse(timeWithSeconds, inputFormatter);
            return time.format(outputFormatter);
        } catch (Exception e) {
            // Handle parsing errors, return the original string in case of failure
            System.out.println("error");
            return timeWithSeconds;
        }
    }



    private String currentTime() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        return currentTime.format(formatter);
    }

    private void openUserDetailsScene(String userName) {
        try (Connection connection = getConnection()) {
            String selectLastMessageSQL = "SELECT id, message, sending_time FROM " + userName + " ORDER BY id DESC LIMIT 1";

            try (PreparedStatement preparedStatement1 = connection.prepareStatement(selectLastMessageSQL)) {
                ResultSet resultSet1 = preparedStatement1.executeQuery();

                if (resultSet1.next()) {
                    int lastID = resultSet1.getInt("id");
                    String lastMessage = resultSet1.getString("message");
                    String lastMessageTime = resultSet1.getString("sending_time");

                    FXMLLoader loader = new FXMLLoader(AdminChatHomeController.class.getResource("Admin_chat_inbox.fxml"));
                    Parent root = loader.load();

                    AdminChatInboxController userDetailsController = loader.getController();

                    userDetailsController.setUserName(userName);
                    userDetailsController.setLastMessage(lastMessage);
                    userDetailsController.setLastMessageTime(lastMessageTime);

                    FXMLLoader loader2 = new FXMLLoader(Admin_Dashboard.class.getResource("admin_dashboard.fxml"));
                    Parent root2 = loader2.load();
                    Admin_Dashboard adminDashboard = loader2.getController();

                    adminDashboard.mainPanel.getChildren().clear();
                    adminDashboard.mainPanel.getChildren().add(root);
//                    adminDashboard.changeAllRemaining(adminDashboard.inbox);
                    adminDashboard.inbox.setStyle("-fx-text-fill: #00d38b; -fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 15px;" +
                            "-fx-transition: background-color 1s pointer;" +
                            "-fx-cursor: pointer");
                    adminDashboard.whitelistNodes();
                    Scene userDetailsScene = new Scene(root2);
                    Stage currentStage = (Stage) mainVbox.getScene().getWindow();
                    currentStage.setScene(userDetailsScene);
                    currentStage.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void updateUI() {

        Comparator<UserMessage> comparator = Comparator.comparing(UserMessage::getLastMessageTime).reversed();
        Collections.sort(users, comparator);
        for (UserMessage um : users) {
            System.out.println(um.getLastMessageTime());
        }

        Platform.runLater(() -> mainVbox.getChildren().clear());
        for (UserMessage user : users) {
            Platform.runLater(() -> showUser(image, user.getUserName(), user.getMessage(), user.getLastMessageTime(), false));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            try (Connection connection = getConnection()) {
                String selectMessageSQL = "SELECT * FROM accountinfo" + " WHERE id > ? ORDER BY id";

                try (PreparedStatement preparedStatement = connection.prepareStatement(selectMessageSQL)) {
                    preparedStatement.setInt(1, lastMessageId);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        int userID = resultSet.getInt("id");
                        String userName = resultSet.getString("username");

                        System.out.println(userName);

                        String selectLastMessageSQL = "SELECT id, message, sending_time FROM " + userName + " ORDER BY id DESC LIMIT 1";

                        String last_message = "";
                        String last_message_time = "";

                        try (PreparedStatement preparedStatement1 = connection.prepareStatement(selectLastMessageSQL)) {
                            ResultSet resultSet1 = preparedStatement1.executeQuery();

                            if (resultSet1.next()) {
                                last_message_time = resultSet1.getString("sending_time");
                                last_message = resultSet1.getString("message");
                            }
                        }

                        String finalLast_message = last_message;
                        String finalLast_message_time = last_message_time;
                        Platform.runLater(() -> {
                            showUser(image, userName, finalLast_message, finalLast_message_time, false);
                        });

                        lastMessageId = userID;

                        UserMessage um = new UserMessage(userName, last_message_time, last_message);
                        users.add(um);
                    }
                    updateUI();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);


//        refreshButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                try {
////                    updateUI();
//                    reload();
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });



    }

    private void reload() throws IOException {
        // Load the new content
        Parent newContent = FXMLLoader.load(getClass().getResource("Admin_chat_home.fxml"));

        // Load the existing Admin_Dashboard
        FXMLLoader loader = new FXMLLoader(Admin_Dashboard.class.getResource("admin_dashboard.fxml"));
        Parent root = loader.load();

        // Get the main stage:
        Admin_Dashboard adminDashboard = loader.getController();

        // Get the controller associated with the new content
        FXMLLoader newContentLoader = new FXMLLoader(getClass().getResource("Admin_chat_home.fxml"));
        Parent newContentRoot = newContentLoader.load();
        UserDashboardController userDashboardController = newContentLoader.getController();

        // Call the inboxCLicked method on the controller associated with the new content
        adminDashboard.inboxCLicked();

        // Clear the existing content in mainPanel and add the new content
        adminDashboard.mainPanel.getChildren().clear();
        adminDashboard.mainPanel.getChildren().add(newContentRoot);

    }




    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/wayout";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    private int lastMessageId = 0;
    Image image = new Image(getClass().getResourceAsStream("/wayout/files/Dashboard/user.png"));
    private List<UserMessage> users=new ArrayList<>();
}
