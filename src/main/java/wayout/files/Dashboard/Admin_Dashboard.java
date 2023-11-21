package wayout.files.Dashboard;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import wayout.files.LoginPage.LoginController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class Admin_Dashboard implements Initializable {
    public Label manage_orders;
    @FXML
    private Label add_package;
    @FXML
    private Label tour_package_count;

    @FXML
    private Label user_count;
    @FXML
    private Label guides_count;

    @FXML
    private Circle profile_pic;

    @FXML
    private Label manage_packages;

    @FXML
    private Label manage_guides;

    @FXML
    private Label earnings;

    @FXML
    private Label home;

    @FXML
    public Label inbox;

    @FXML
    private Label guide_applications;

    @FXML
    private LineChart<?, ?> linearChart;

    @FXML
    private Label logout;

    @FXML
    private Label manage_user;
    private Vector<Node> nodesVector = new Vector<>();
    private Parent root;
    private Scene scene;
    private Stage stage;
    @FXML
    public AnchorPane mainPanel;


    //  problem chart
    private void setLinearChart() {
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data("January", 10));
        series.getData().add(new XYChart.Data("February", 14));
        series.getData().add(new XYChart.Data("March", 20));
        series.getData().add(new XYChart.Data("April", 12));
        series.getData().add(new XYChart.Data("May", 12));
        series.getData().add(new XYChart.Data("June", 15));
        series.getData().add(new XYChart.Data("July", 18));
        series.getData().add(new XYChart.Data("August", 20));
        series.getData().add(new XYChart.Data("September", 4));
        series.getData().add(new XYChart.Data("October", 15));
        series.getData().add(new XYChart.Data("November", 10));
        series.getData().add(new XYChart.Data("December", 17));
        linearChart.getData().addAll(series);
    }

//    @FXML
//    private PieChart pieChart;
//
//    private void inPieChart() {
//        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
//                new PieChart.Data("Dhaka", 10),
//                new PieChart.Data("Chattogram", 10),
//                new PieChart.Data("Shylet", 10),
//                new PieChart.Data("Khulna", 10),
//                new PieChart.Data("Rajshahi", 10),
//                new PieChart.Data("Cox's Bazar", 10),
//                new PieChart.Data("Bogura", 10)
//        );
//        pieChart.setData(pieChartData);
//    }


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

    public void whitelistNodes(){
        home.setStyle("-fx-text-fill: #ffffff; -fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 13px;" +
                "-fx-transition: background-color 1s pointer;" +
                "-fx-cursor: pointer");
        manage_user.setStyle("-fx-text-fill: #ffffff; -fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 13px;" +
                "-fx-transition: background-color 1s pointer;" +
                "-fx-cursor: pointer");
        manage_guides.setStyle("-fx-text-fill: #ffffff; -fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 13px;" +
                "-fx-transition: background-color 1s pointer;" +
                "-fx-cursor: pointer");
        earnings.setStyle("-fx-text-fill: #ffffff; -fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 13px;" +
                "-fx-transition: background-color 1s pointer;" +
                "-fx-cursor: pointer");
        add_package.setStyle("-fx-text-fill: #ffffff; -fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 13px;" +
                "-fx-transition: background-color 1s pointer;" +
                "-fx-cursor: pointer");
        guide_applications.setStyle("-fx-text-fill: #ffffff; -fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 13px;" +
                "-fx-transition: background-color 1s pointer;" +
                "-fx-cursor: pointer");
        manage_packages.setStyle("-fx-text-fill: #ffffff; -fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 13px;" +
                "-fx-transition: background-color 1s pointer;" +
                "-fx-cursor: pointer");
        manage_orders.setStyle("-fx-text-fill: #ffffff; -fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 13px;" +
                "-fx-transition: background-color 1s pointer;" +
                "-fx-cursor: pointer");
    }

    public void addAllSideNodes() {
        nodesVector.add(home);
        nodesVector.add(manage_user);
        nodesVector.add(earnings);
        nodesVector.add(inbox);
        nodesVector.add(add_package);
        nodesVector.add(guide_applications);
//        nodesVector.add(transport);
//        nodesVector.add(cart);
//        nodesVector.add(chat);
//        nodesVector.add(rewards);
//        nodesVector.add(edit_profile);
    }

    @FXML
    private Label weather;
    @FXML
    private Label location_info;

    public void inboxCLicked(){
        addAllSideNodes();
        changeAllRemaining(inbox);
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    Platform.runLater(() -> {
                        try {
                            root = FXMLLoader.load(UserDashboardController.class.getResource("Admin_chat_home.fxml"));
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
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
                        weather.setText(temperature + "F");
                    } else {
                        weather.setText(temperature + "C");
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



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
                location_info.setText(city + ", " + country);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }




        try {
            profile_pic.setFill(new ImagePattern(new Image(getClass().getResource("software-engineer.png").openStream())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        setLinearChart();
//        inPieChart();



//        home.setOnMouseClicked(event -> {
////            addAllSideNodes();
////            changeAllRemaining(trips);
////
////            try {
////                root = FXMLLoader.load(getClass().getResource("tour_packages.fxml"));
////                mainPanel.getChildren().add(root);
////
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
//        });

        home.setStyle("-fx-text-fill: #00d38b; -fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 15px;" +
                "-fx-transition: background-color 1s pointer;" +
                "-fx-cursor: pointer");


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
                                    root = FXMLLoader.load(getClass().getResource("admin_dashboard.fxml"));
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

        manage_user.setOnMouseClicked(event -> {
            addAllSideNodes();
            changeAllRemaining(manage_user);

            try {
                root = FXMLLoader.load(getClass().getResource("BanUser.fxml"));
                mainPanel.getChildren().add(root);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        earnings.setOnMouseClicked(event -> {
            addAllSideNodes();
            changeAllRemaining(earnings);

            try {
                root = FXMLLoader.load(getClass().getResource("Earn.fxml"));
                mainPanel.getChildren().add(root);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

//        inbox.setOnMouseClicked(event -> {
//
//        });

        add_package.setOnMouseClicked(event -> {
            addAllSideNodes();
            changeAllRemaining(add_package);

            try {
                root = FXMLLoader.load(getClass().getResource("add_Package.fxml"));
                mainPanel.getChildren().add(root);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        guide_applications.setOnMouseClicked(event -> {
            addAllSideNodes();
            changeAllRemaining(guide_applications);

            try {
                root = FXMLLoader.load(getClass().getResource("guide_applications.fxml"));
                mainPanel.getChildren().add(root);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

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




        manage_packages.setOnMouseClicked(event -> {
            addAllSideNodes();
            changeAllRemaining(manage_packages);

            try {
                root = FXMLLoader.load(getClass().getResource("manage_packages.fxml"));
                mainPanel.getChildren().add(root);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });



        manage_guides.setOnMouseClicked(event -> {
            addAllSideNodes();
            changeAllRemaining(manage_guides);

            try {
                root = FXMLLoader.load(getClass().getResource("manage_guides.fxml"));
                mainPanel.getChildren().add(root);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        manage_orders.setOnMouseClicked(event -> {
            addAllSideNodes();
            changeAllRemaining(manage_orders);

            try {
                root = FXMLLoader.load(getClass().getResource("manage_orders.fxml"));
                mainPanel.getChildren().add(root);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        DatabaseUtils database=new DatabaseUtils();


        try {
            user_count.setText(""+database.getRowCount("accountinfo"));
            guides_count.setText(""+database.getRowCount("guide_info"));
            tour_package_count.setText(""+database.getRowCount("packages_list"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
