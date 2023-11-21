package wayout.files.LoginPage;

import com.jfoenix.controls.JFXCheckBox;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;

import java.util.Optional;
import java.util.ResourceBundle;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.services.oauth2.model.Userinfo;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.security.GeneralSecurityException;

import javafx.scene.web.WebView;
import wayout.files.Dashboard.Admin_Dashboard;
import wayout.files.Dashboard.ClientChatController;
import wayout.files.Dashboard.UserDashboardController;
import wayout.files.Dashboard.util.NetworkUtil;
import wayout.files.Homepage.HomePage_2nd_Controller;

import java.util.Collections;

public class LoginController implements Initializable {

    // google api properties
    private static final String CLIENT_ID = "156066230892-j88hek91hj3h39bcqt1aqh1d176i76o5.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";
    private static final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
    private static final String SCOPE = "email profile";
    public ImageView loginImageLogo;
    @FXML
    private MFXTextField EmailorUsernameBox;
    @FXML
    private Hyperlink GuideLogin;
    @FXML
    private MFXButton SignIn;
    @FXML
    private Hyperlink createAcc;
    @FXML
    private Hyperlink forgotPass;
    @FXML
    private MFXPasswordField passwordBox;
    @FXML
    private JFXCheckBox remember;
    @FXML
    private WebView web;
    @FXML
    private MFXButton signinGoogle;

    @FXML
    private AnchorPane eraser;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private GoogleAuthorizationCodeFlow flow;
    @FXML
    private MFXButton backButton;

    @FXML
    void GuideLoginClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("guideLogin.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void backButtonClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(HomePage_2nd_Controller.class.getResource("home3.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void createAccClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("signUP.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void forgotPassClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("resetPass.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void rememberMeSelected(ActionEvent event) {

    }

    @FXML
    void signInClicked(ActionEvent event) {
        String mail = EmailorUsernameBox.getText();
        String pass = passwordBox.getText();

        String url = "jdbc:mysql://127.0.0.1/wayout";
        String username = "root";
        String password = "";

        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }

        try {

            String chk1 = "Select * from accountinfo where email=?";
            String chk2 = "Select * from accountinfo where username=?";
            Connection con;
            PreparedStatement pst1, pst2;

            con = DriverManager.getConnection(url, username, password);

            pst1 = con.prepareStatement(chk1);
            pst2 = con.prepareStatement(chk2);

            pst1.setString(1, mail);
            pst2.setString(1, mail);

            ResultSet rs1, rs2;
            rs1 = pst1.executeQuery();
            rs2 = pst2.executeQuery();

            if (mail.equals("admin") && pass.equals("admin")) {

                eraser.setVisible(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(300);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Platform.runLater(() -> {
                            try {
                                Parent root = FXMLLoader.load(Admin_Dashboard.class.getResource("admin_dashboard.fxml"));
                                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }).start();
            } else if (rs1.next()) {
                String pas = rs1.getString("password");
                if (pas.equals(pass)) {
                    String fullName = rs1.getString("fullName");
                    String em = rs1.getString("email");
                    String datofBirth = rs1.getString("dob");
                    String gend = rs1.getString("gender");
                    String usern = rs1.getString("username");

                    Parent root = FXMLLoader.load(UserDashboardController.class.getResource("user_dashboard.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();


                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Wrong password");
                    alert.setContentText("Please enter your password correctly!");
                    alert.showAndWait();
                }
            } else if (rs2.next()) {
                String pas = rs2.getString("password");
                if (pas.equals(pass)) {
                    String fullName = rs2.getString("fullName");
                    String em = rs2.getString("email");
                    String datofBirth = rs2.getString("dob");
                    String gend = rs2.getString("gender");
                    String usern = rs2.getString("username");


                    File file = new File("src/main/resources/wayout/files/Dashboard/username.txt");
                    File file2 = new File("src/main/resources/wayout/files/Dashboard/account_name.txt");
                    File file3=new File("src/main/resources/wayout/files/Dashboard/username_chat.txt");

                    if(file3.exists()){
                        BufferedWriter bw3=new BufferedWriter(new FileWriter(file3));
                        bw3.write(usern);
                        bw3.close();
                    }

                    if (file.exists()) {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                        bw.write(usern);
                        System.out.println("Written");
                        bw.close();
                    } else System.out.println("File not found");

                    if (file2.exists()) {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                        bw.write(fullName);
                        System.out.println("Written");
                        bw.close();
                    }

                    eraser.setVisible(true);
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                            Platform.runLater(() -> {
                                try {
                                    Parent root = FXMLLoader.load(UserDashboardController.class.getResource("user_dashboard.fxml"));
                                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    scene = new Scene(root);
                                    stage.setScene(scene);
                                    stage.show();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                    });
                    thread.start();


                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Wrong password");
                    alert.setContentText("Please enter your password correctly!");
                    alert.showAndWait();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Wrong email/password");
                alert.setContentText("Please enter your email and password correctly!");
                alert.showAndWait();
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @FXML
    void signinGoogleClicked(ActionEvent event) throws GeneralSecurityException, IOException, URISyntaxException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Generate the Google Authorization URL
                    String authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();

                    // Open the Authorization URL in the user's default browser
                    Desktop.getDesktop().browse(new URI(authorizationUrl));


                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                Platform.runLater(() -> {
                    try {
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setTitle("Authorization code");
                        dialog.setHeaderText("Enter your code:");
                        dialog.setContentText("Code:");

                        Optional<String> result = dialog.showAndWait();
                        String authorizationCode = "";
                        if (result.isPresent()) {
                            authorizationCode = result.get();
                            try {
                                // Exchange the authorization code for an access token and a refresh token
                                GoogleTokenResponse response = flow.newTokenRequest(authorizationCode).setRedirectUri(REDIRECT_URI).execute();

                                // Use the access token to fetch the user's profile data
                                GoogleCredential credential = new GoogleCredential.Builder().setTransport(GoogleNetHttpTransport.newTrustedTransport()).setJsonFactory(new GsonFactory()).setClientSecrets(CLIENT_ID, CLIENT_SECRET).build().setAccessToken(response.getAccessToken()).setRefreshToken(response.getRefreshToken());

                                Oauth2 oauth2 = new Oauth2.Builder(GoogleNetHttpTransport.newTrustedTransport(), new GsonFactory(), credential).build();

//                                String user_password = JOptionPane.showInputDialog("Enter a password: ");

                                TextInputDialog password_box = new TextInputDialog();
                                password_box.setTitle("Password");
                                password_box.setHeaderText("Enter a password to login:");
                                password_box.setContentText("Password:");

                                Optional<String> password_result = password_box.showAndWait();

                                if (password_result.isPresent()) {
                                    Connection con;
                                    PreparedStatement pst;

                                    String url = "jdbc:mysql://127.0.0.1:3306/wayout";
                                    String username = "root";
                                    String password = "";

                                    System.out.println("Connecting database...");

                                    try (Connection connection = DriverManager.getConnection(url, username, password)) {
                                        System.out.println("Database connected!");
                                    } catch (SQLException e) {
                                        throw new IllegalStateException("Cannot connect the database!", e);
                                    }

                                    try {


                                        con = DriverManager.getConnection(url, username, password);


                                        //extract data from json:

                                        Userinfo user = oauth2.userinfo().get().execute();
                                        String user_first_name = user.getGivenName();
                                        String user_FullName = user.getName();
                                        String[] splitName = user_FullName.split(" ");
                                        int words = splitName.length;
                                        int lastwordIndex = words - 1;
                                        String user_last_name = splitName[lastwordIndex];


                                        String query11 = "select count(*) from accountinfo";
                                        Statement stmt = con.createStatement();
                                        ResultSet rs = stmt.executeQuery(query11);
                                        rs.next();
                                        int count = rs.getInt(1);
                                        System.out.println(count + 1);

                                        // auto generate username

                                        String lower_Firstname = user_first_name.toLowerCase();
                                        char first_name_firstChar = lower_Firstname.charAt(0);
                                        String[] lastnameX = user_last_name.split("\\s+");
                                        String last = lastnameX[lastnameX.length - 1];
                                        String lowerLastname = last.toLowerCase();
                                        String generated_username = first_name_firstChar + lowerLastname + (count + 1);
                                        System.out.println(generated_username);


                                        pst = con.prepareStatement("INSERT INTO accountinfo(firstName,lastName,fullName,email,dob,gender,username,password) VALUES(?,?,?,?,?,?,?,?)");

                                        pst.setString(1, user_first_name);
                                        pst.setString(2, user_last_name);
                                        pst.setString(3, user_FullName);
                                        pst.setString(4, " ");
                                        pst.setString(5, " ");
                                        pst.setString(6, " ");
                                        pst.setString(7, generated_username.trim());
                                        pst.setString(8, password_result.get());
                                        pst.execute();

                                        System.out.println("Insert successful");

                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Welcome");
                                        alert.setHeaderText("SignUP Successful");
                                        alert.setContentText("Automatically Generated Username: " + generated_username + "\nUse this username to login into your account");
                                        alert.showAndWait();

                                        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + generated_username + " (" +
                                                "id INT AUTO_INCREMENT PRIMARY KEY," +
                                                "from_ VARCHAR(255) NOT NULL," +
                                                "to_ VARCHAR(255) NOT NULL," +
                                                "message TEXT NOT NULL," +
                                                "sending_time TEXT NOT NULL"+
                                                ")";

                                        try (PreparedStatement preparedStatement = con.prepareStatement(createTableSQL)) {
                                            preparedStatement.executeUpdate();
                                            System.out.println("Table " + username + " created successfully.");
                                        }

                                    } catch (Exception e) {
                                        e.getStackTrace();
                                    }
                                }else {

                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }).start();


        // Prompt the user to enter the authorization code
//        String authorizationCode = JOptionPane.showInputDialog("Enter the authorization code:");


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eraser.setVisible(false);
        try {
            flow = new GoogleAuthorizationCodeFlow.Builder(GoogleNetHttpTransport.newTrustedTransport(), new GsonFactory(), CLIENT_ID, CLIENT_SECRET, Collections.singleton(SCOPE)).setAccessType("offline").build();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // webview property (animated text)
        WebEngine webEngine = web.getEngine();
        webEngine.loadContent("<!DOCTYPE html>\n" + "<html lang=\"en\">\n" + "\n" + "<head>\n" + "    <meta charset=\"UTF-8\">\n" + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + "    <title>Document</title>\n" +

                "</head>\n" + "\n" + "<body>\n" + "    <div id=\"header\">\n" + "        <div class=\"container\">\n" + "            <div class=\"header-text\">\n" + "                <h2><span class=\"auto-input\"></span></h2>\n" + "            </div>\n" + "        </div>\n" + "    </div>\n" + "    <script src=\"https://unpkg.com/typed.js@2.0.132/dist/typed.umd.js\"></script>\n" + "    <script>\n" + "        var typed = new Typed(\".auto-input\", {\n" + "            strings: [\"Welcome to wayout !\"],\n" + "            typeSpeed: 70,\n" + "            backSpeed: 70,\n" + "            loop: true\n" + "        })\n" + "    </script>\n" + "</body>\n" + "\n" + "</html>");
        webEngine.setUserStyleSheetLocation(String.valueOf(getClass().getResource("style.css")));

    }

}
