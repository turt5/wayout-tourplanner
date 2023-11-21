package wayout.files.Dashboard;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class SearchPage implements Initializable {

    @FXML
    private WebView webpage;
    public static String generateMapLink(String location) throws UnsupportedEncodingException {
        String encodedLocation = URLEncoder.encode(location, "UTF-8");
        String mapLink = "https://www.google.com/search?q=" + encodedLocation;
        return mapLink;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String filePath="src/main/resources/wayout/files/Dashboard/temp_search.txt";
        try{
            File file=new File(filePath);
            if(file.exists()){
                byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
                String fileContentString = new String(fileContent, "UTF-8");

                WebEngine webEngine=webpage.getEngine();
                System.out.println(generateMapLink(fileContentString));
                webEngine.load(generateMapLink(fileContentString));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
