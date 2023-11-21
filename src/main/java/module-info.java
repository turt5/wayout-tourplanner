module wayout.files {
    requires javafx.fxml;
    requires MaterialFX;
    requires com.jfoenix;
    requires java.sql;
    requires org.json;
    requires mail;
    requires google.api.client;
    requires com.google.api.client.json.gson;
    requires google.api.services.oauth2.v2.rev157;
    requires java.desktop;
    requires com.google.api.client;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires org.jsoup;
    requires javafx.media;
    opens wayout.files to javafx.fxml;
    exports wayout.files;

    opens wayout.files.LoginPage to javafx.fxml;
    exports wayout.files.LoginPage;

    opens wayout.files.Homepage to javafx.fxml;
    exports wayout.files.Homepage;

    opens wayout.files.Dashboard to javafx.fxml;
    exports wayout.files.Dashboard;

//    opens wayout.files.Dashboard.Networking to javafx.fxml;
//    exports wayout.files.Dashboard.Networking;

    exports wayout.files.Dashboard.util;
    opens wayout.files.Dashboard.util to javafx.fxml;


}