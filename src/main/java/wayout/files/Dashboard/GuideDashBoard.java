package wayout.files.Dashboard;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import wayout.files.LoginPage.LoginController;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.stream.Collectors;

public class GuideDashBoard implements Initializable {
    @FXML
    private HBox card_must_go_Lists;

    @FXML
    private Label order;

    @FXML
    private Label chat;

//    @FXML
//    private Label edit_profile;

//    @FXML
//    private Label guides;

    @FXML
    private Label home;

//    @FXML
//    private Label hotel;

    @FXML
    private Label logout;

    @FXML
    private MFXScrollPane main_scrollPane;

    @FXML
    private Label maps;

    @FXML
    private HBox popular_hotels;

    @FXML
    private Circle profile_Picture;

    @FXML
    private HBox recently_viewed_places;

//    @FXML
//    private Label rewards;

    @FXML
    private MFXTextField searchBox;

    @FXML
    private MFXButton searchBtn;

    @FXML
    private MFXButton see_best_hotel_listBtn;

//    @FXML
//    private Label transport;

//    @FXML
//    private Label trips;

    @FXML
    private Label userLoc;

    @FXML
    private Label username;

    @FXML
    AnchorPane mainPanel;
    @FXML
    private HBox ways_to_tour_Dhaka_city;

    @FXML
    private Label weatherLbl;

    private Vector<Node> nodesVector = new Vector<>();
    private Stage stage;
    private Parent root;
    private Scene scene;
    //    @FXML
    private ListView<String> suggestionListView = new ListView<>();

    private List<String> suggestions = new ArrayList<>();

    private Popup suggestionPopup = new Popup();


    public void sideBarMove(Node node) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                node.setStyle("-fx-translate-x: 25px;-fx-text-fill: #00d38b; -fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 15px;");

            }
        }).start();
    }

    public void changeAllRemaining(Node node) {
        new Thread(new Runnable() {
            @Override
            public void run() {


                for (int i = 0; i < nodesVector.size(); i++) {
                    if (!nodesVector.get(i).equals(node)) {
                        nodesVector.get(i).setStyle("-fx-text-fill: #ffffff; -fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 13px;" +
                                "-fx-transition: background-color 1s pointer;" +
                                "-fx-cursor: pointer");
                    } else {
                        nodesVector.get(i).setStyle("-fx-text-fill: #00d38b; -fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 15px;" +
                                "-fx-transition: background-color 1s pointer;" +
                                "-fx-cursor: pointer");
                    }
                }

            }
        }).start();
    }

    public void addAllSideNodes() {
        nodesVector.add(home);
        nodesVector.add(maps);
        //nodesVector.add(trips);
        //nodesVector.add(guides);
//        nodesVector.add(hotel);
//        nodesVector.add(transport);
        nodesVector.add(order);
        nodesVector.add(chat);
//        nodesVector.add(rewards);
//        nodesVector.add(edit_profile);
    }

    public void must_go_list_card(String loc, Image image) {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    AnchorPane cards = new AnchorPane();
                    cards.setPrefHeight(280);
                    cards.setPrefWidth(300);

                    StackPane cards_stackPane1 = new StackPane();
                    cards_stackPane1.setPrefHeight(280);
                    cards_stackPane1.setPrefWidth(300);
//            cards_stackPane1.setLayoutX(10);
                    cards_stackPane1.setLayoutY(10);
                    cards_stackPane1.setPadding(new Insets(5, 10, 5, 0));


                    ImageView imageView = new ImageView();
                    imageView.setFitWidth(300);
                    imageView.setFitHeight(290);
                    imageView.setImage(image);


                    AnchorPane stackAncor = new AnchorPane();
                    MFXButton placeName = new MFXButton();
//                    stackAncor.setLayoutX(10);
                    placeName.setText(loc);
                    placeName.setLayoutX(10);
                    placeName.setStyle("-fx-font-size: 14px;" +
                            "-fx-font-family: 'Arial Black';" +
                            "-fx-background-color: Black;" +
                            "-fx-padding: 5px;" +
                            "-fx-text-fill: white");


//            Platform.runLater(()->{
//                String css=getClass().getResource("stackAnchor.css").toExternalForm();
//                cards.getStylesheets().add(css);
//
//                if(cards.isHover()){
//                    System.out.println("yes");
//                }else {
//                    System.out.println("NO");
//                }
//            });

//                   placeName.setLayoutX(25);
                    placeName.setLayoutY(240);
                    placeName.setPadding(new Insets(0, 5, 0, 5));
                    placeName.setPrefWidth(280);
                    placeName.setAlignment(Pos.CENTER);
                    stackAncor.getChildren().add(placeName);

                    //placeName button Controller

                    placeName.setOnAction((event -> {
                        System.out.println("YES");
                    }));

                    stackAncor.setOnMouseEntered((event -> {
                        cards.setStyle("-fx-translate-y: -10px;" +
                                "-fx-smooth: true");
                        stackAncor.setStyle("-fx-background-color: rgba(0,0,0,0.23)");
                    }));

                    stackAncor.setOnMouseExited((event -> {
                        cards.setStyle("-fx-translate-y: 0px");
                        stackAncor.setStyle("");
                    }));


                    //individual card clicks
                    stackAncor.setOnMouseClicked((event -> {
                        System.out.println(placeName.getText());
                    }));


                    Platform.runLater(() -> {
                        cards_stackPane1.getChildren().add(imageView);
                        cards_stackPane1.getChildren().add(stackAncor);
                        cards.getChildren().add(cards_stackPane1);
                        card_must_go_Lists.getChildren().add(cards);
                    });

//            if(card_must_go_Lists.getWidth()>=750){
//                MFXButton mfxButton=new MFXButton("Next");
//                stackAncor.getChildren().add(mfxButton);
//                cards_stackPane1.getChildren().add(stackAncor);
//                cards.getChildren().add(cards_stackPane1);
//                card_must_go_Lists.getChildren().add(cards);
//            }
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setHotels(String name, String loc, Image image) {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    AnchorPane cards = new AnchorPane();
                    cards.setPrefHeight(280);
                    cards.setPrefWidth(300);

                    StackPane cards_stackPane1 = new StackPane();
                    cards_stackPane1.setPrefHeight(280);
                    cards_stackPane1.setPrefWidth(300);
//            cards_stackPane1.setLayoutX(10);
                    cards_stackPane1.setLayoutY(10);
                    cards_stackPane1.setPadding(new Insets(5, 10, 5, 0));


                    ImageView imageView = new ImageView();
                    imageView.setFitWidth(300);
                    imageView.setFitHeight(290);
                    imageView.setImage(image);


                    AnchorPane stackAncor = new AnchorPane();
                    MFXButton placeName = new MFXButton();
//                    stackAncor.setLayoutX(10);
                    placeName.setText(loc);
                    placeName.setLayoutX(10);
                    placeName.setStyle("-fx-font-size: 14px;" +
                            "-fx-font-family: 'Arial Black';" +
                            "-fx-background-color: Black;" +
                            "-fx-padding: 5px;" +
                            "-fx-text-fill: white");


//            Platform.runLater(()->{
//                String css=getClass().getResource("stackAnchor.css").toExternalForm();
//                cards.getStylesheets().add(css);
//
//                if(cards.isHover()){
//                    System.out.println("yes");
//                }else {
//                    System.out.println("NO");
//                }
//            });

//                   placeName.setLayoutX(25);
                    placeName.setLayoutY(240);
                    placeName.setPadding(new Insets(0, 5, 0, 5));
                    placeName.setPrefWidth(280);
                    placeName.setAlignment(Pos.CENTER);
                    stackAncor.getChildren().add(placeName);

                    //placeName button Controller

//                    placeName.setOnAction((event -> {
//                        System.out.println("YES");
//                    }));

                    stackAncor.setOnMouseEntered((event -> {
                        cards.setStyle("-fx-translate-y: -10px;" +
                                "-fx-smooth: true");
                        stackAncor.setStyle("-fx-background-color: rgba(0,0,0,0.23)");
                    }));

                    stackAncor.setOnMouseExited((event -> {
                        cards.setStyle("-fx-translate-y: 0px");
                        stackAncor.setStyle("");
                    }));


                    //individual card clicks
                    stackAncor.setOnMouseClicked((event -> {
                        System.out.println(placeName.getText());
                    }));


                    Platform.runLater(() -> {
                        cards_stackPane1.getChildren().add(imageView);
                        cards_stackPane1.getChildren().add(stackAncor);
                        cards.getChildren().add(cards_stackPane1);
                        popular_hotels.getChildren().add(cards);
                    });

//            if(card_must_go_Lists.getWidth()>=750){
//                MFXButton mfxButton=new MFXButton("Next");
//                stackAncor.getChildren().add(mfxButton);
//                cards_stackPane1.getChildren().add(stackAncor);
//                cards.getChildren().add(cards_stackPane1);
//                card_must_go_Lists.getChildren().add(cards);
//            }
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        searchBox.getItems().addAll("Apple","Banana");
//        sideBarMove(home);

        try {
            profile_Picture.setFill(new ImagePattern(new Image(getClass().getResource("user.png").openStream())));
            File file = new File("src/main/resources/wayout/files/Dashboard/username.txt");
            if (file.exists()) {
                String word = "";
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    // Read the first line of the file
                    String line = br.readLine();
                    // Assign the first word to the 'word' variable
                    if (line != null) {
                        word = line.trim().split("\\s+")[0];
                        username.setText(word);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else System.out.println("File not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        home.setStyle("-fx-text-fill: #00d38b; -fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 15px;" +
                "-fx-transition: background-color 1s pointer;" +
                "-fx-cursor: pointer");

        maps.setOnMouseClicked((event) -> {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    addAllSideNodes();
                    changeAllRemaining(maps);

                    Platform.runLater(()->{
                        try {
                            root = FXMLLoader.load(getClass().getResource("explore.fxml"));
                            System.out.println("SSS");
                            mainPanel.getChildren().add(root);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }).start();
        });

        home.setOnMouseClicked((event) -> {
            addAllSideNodes();
            changeAllRemaining(home);

            try {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        synchronized (this) {
                            Platform.runLater(() -> {
                                try {
                                    root = FXMLLoader.load(getClass().getResource("guide_dashboard.fxml"));
                                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    scene = new Scene(root);
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
        });


//        hotel.setOnMouseClicked((event) -> {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    addAllSideNodes();
//                    changeAllRemaining(hotel);
//                }
//            }).start();
//        });
//
//        transport.setOnMouseClicked((event) -> {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    addAllSideNodes();
//                    changeAllRemaining(transport);
//                }
//            }).start();
//        });

        order.setOnMouseClicked((event) -> {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    addAllSideNodes();
                    changeAllRemaining(order);

                    Platform.runLater(() -> {
                        try {
                            root = FXMLLoader.load(UserDashboardController.class.getResource("guide_orders.fxml"));
                            mainPanel.getChildren().add(root);

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    });
                }
            }).start();
        });

        chat.setOnMouseClicked((event) -> {
            addAllSideNodes();
            changeAllRemaining(chat);

            try {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Platform.runLater(() -> {
                            try {
                                root = FXMLLoader.load(UserDashboardController.class.getResource("chat_gui.fxml"));
                                mainPanel.getChildren().add(root);

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        });
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

//        rewards.setOnMouseClicked((event) -> {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    addAllSideNodes();
//                    changeAllRemaining(rewards);
//                }
//            }).start();
//        });
//        edit_profile.setOnMouseClicked((event) -> {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    addAllSideNodes();
//                    changeAllRemaining(edit_profile);
//                    Platform.runLater(()->{
//                        try {
//                            root = FXMLLoader.load(getClass().getResource("editProfile.fxml"));
//                            mainPanel.getChildren().add(root);
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    });
//                }
//            }).start();
//        });

        logout.setOnMouseClicked((event) -> {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Platform.runLater(()->{
                        try {
                            root = FXMLLoader.load(LoginController.class.getResource("login.fxml"));
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
        });

        Thread Clocation = new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    try {
                        String url = "https://mylocation.org/";
                        Document doc = Jsoup.connect(url).get();
                        Element info = doc.selectFirst(".info");

                        String city = "";
                        String country = "";

                        if (info != null) {
                            Elements rows = info.select("table tr");
                            for (Element row : rows) {
                                String label = row.selectFirst("td").text();
                                String value = row.select("td").get(1).text();
                                if (label.equals("City")) {
                                    city = value;
                                } else if (label.equals("Country")) {
                                    country = value;
                                }
                            }
                            userLoc.setText(city + ", " + country);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        });


        Thread weather = new Thread(new Runnable() {
            @Override
            public void run() {
                //weather information
                try {
                    String url = "https://www.tomorrow.io/weather/";
                    Document doc = Jsoup.connect(url).get();

                    Elements weatherElements = doc.select("div.fvkAud span._3fQrr5");

                    for (Element weatherElement : weatherElements) {
                        String temperature = weatherElement.text();


                        String[] split = temperature.split("°");
                        int temp = Integer.parseInt(split[0]);

                        Platform.runLater(() -> {
                            if (temp >= 45) {
                                weatherLbl.setText(temperature + "F");
                            } else {
                                weatherLbl.setText(temperature + "C");
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Clocation.start();
        weather.start();

//        card_must_go_Lists
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Image image = new Image(getClass().getResource("sugandha-beach-cox-s.png").openStream());
                    Image image2 = new Image(getClass().getResource("image1.png").openStream());
                    Image image3 = new Image(getClass().getResource("image2.jpg").openStream());

                    must_go_list_card("Cox's Bazar", image);
                    must_go_list_card("Sundarbans", image2);

                    setHotels("AA", "BB", image3);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}
