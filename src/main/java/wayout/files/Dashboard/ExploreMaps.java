package wayout.files.Dashboard;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ExploreMaps implements Initializable {

    @FXML
    private MFXTextField locationNameBox;

    @FXML
    private Label locationNameSearched;

    @FXML
    private MFXButton searchButton;

    @FXML
    private WebView webpage;

    public static String generateMapLink(String location) throws UnsupportedEncodingException {
        String encodedLocation = URLEncoder.encode(location, "UTF-8");
        String mapLink = "https://www.google.com/maps?q=" + encodedLocation + "&hl=en&theme=dark";
        return mapLink;
    }

    public void writeFile(String search) throws IOException {
        File file = new File("src/main/resources/wayout/files/Dashboard/maps_data.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));

        bw.write(search);
        bw.close();
    }

    public String ReadFile() throws IOException {
        File file = new File("src/main/resources/wayout/files/Dashboard/maps_data.txt");
        String line = "";

        if (file.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            line = br.readLine();

        } else {
            System.out.println("File not found");


        }
        return line;

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try{
//            autocompleteValues.addAll("dhaka", "sylhet", "chittagong", "cox's Bazar", "sundarbans", "sajek-valley")
            String filePath="src/main/resources/wayout/files/Dashboard/locations.txt";
            File f=new File(filePath);
            if(f.exists()){
                byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
                String fileContentString = new String(fileContent, "UTF-8");

                String[] splitLocations=fileContentString.split("@");

                for(int i=0;i<splitLocations.length;i++){
                    if(!autocompleteValues.contains(splitLocations[i].toLowerCase())){
                        autocompleteValues.add(splitLocations[i].toLowerCase());
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }



        searchButton.setOnAction((event -> {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        File file = new File("src/main/resources/wayout/files/Dashboard/locations.txt");
                        String filePath="src/main/resources/wayout/files/Dashboard/locations.txt";
                        if(file.exists()){

                            byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
                            String fileContentString = new String(fileContent, "UTF-8");

                            if(!fileContentString.contains(locationNameBox.getText().toLowerCase())){
                                PrintWriter pw=new PrintWriter(new FileWriter(file,true));
                                pw.print(locationNameBox.getText().toLowerCase()+"@");
                                pw.close();
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }).start();



            listView.setVisible(false);
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        WebEngine engine = webpage.getEngine();
                        String address = generateMapLink(locationNameBox.getText());
                        engine.setJavaScriptEnabled(true);
                        Platform.runLater(() -> {
                            if (address != null) {

                                engine.executeScript("document.documentElement.lang='en';");
//                                engine.setUserStyleSheetLocation(getClass().getResource("webview-style.css").toString());
                                locationNameSearched.setText("Search result for: " + locationNameBox.getText().trim());
                                System.out.println(locationNameBox.getText());
                                engine.load(address);
//                                locationNameBox.clear();

                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setHeaderText("Location cannot be empty.");
                                alert.setContentText("Please enter a location to continue!");
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();

        }));


        try {
            Thread autoCompleteSearch=new Thread(new Runnable() {
                @Override
                public void run() {

                    listView = new ListView<>();
                    listView.setPrefWidth(locationNameBox.getPrefWidth());
                    listView.setVisible(false);
                    listView.setLayoutX(locationNameBox.getLayoutX());
                    listView.setLayoutY(locationNameBox.getLayoutY()+locationNameBox.getPrefHeight());
                    listView.setPrefHeight(autocompleteValues.size()*24);

                    locationNameBox.textProperty().addListener((observable, oldValue, newValue) -> {
                        List<String> filteredValues = autocompleteValues.stream()
                                .filter(value -> value.toLowerCase().startsWith(newValue.toLowerCase()))
                                .collect(Collectors.toList());
                        if (!filteredValues.isEmpty()) {
                            listView.setItems(FXCollections.observableArrayList(filteredValues));
                            listView.setVisible(true);
                        } else {
                            listView.getItems().clear();
                            listView.setVisible(false);
                        }
                    });

                    listView.setOnMouseClicked(event -> {
                        String selectedValue = listView.getSelectionModel().getSelectedItem();
                        locationNameBox.setText(selectedValue);
                        listView.setVisible(false);
                    });


                   Platform.runLater(()->{
                       parent.getChildren().addAll(listView);
                   });
                }
            });
            autoCompleteSearch.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private ListView<String> listView;
    @FXML
    private AnchorPane parent;

    private List<String> autocompleteValues = new ArrayList<>();
}
