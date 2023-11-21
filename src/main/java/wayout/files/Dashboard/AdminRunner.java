package wayout.files.Dashboard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminRunner extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root= FXMLLoader.load(getClass().getResource("tour_packages.fxml"));
//        Parent root= FXMLLoader.load(getClass().getResource("client_chat.fxml"));
        Parent root= FXMLLoader.load(getClass().getResource("admin_dashboard.fxml"));
        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
