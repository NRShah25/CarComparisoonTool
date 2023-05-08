package list.carcomparisontool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class PrimaryController {

    @FXML
    private Label makeLabel;
    @FXML
    private Label modelLabel;
    @FXML
    private Label bodyStyleLabel;
    @FXML
    private Label doorsLabel;
    @FXML
    private Label passengerCapacityLabel;
    @FXML
    private Label mpgLabel;
    @FXML
    private Label costLabel;
    @FXML
    private ComboBox<String> makeComboBox;
    @FXML
    private ComboBox<String> modelComboBox;
    @FXML
    private Label makeLabel1;
    @FXML
    private Label modelLabel1;
    @FXML
    private Label bodyStyleLabel1;
    @FXML
    private Label doorsLabel1;
    @FXML
    private Label passengerCapacityLabel1;
    @FXML
    private Label mpgLabel1;
    @FXML
    private Label costLabel1;
    @FXML
    private ComboBox<String> makeComboBox1;
    @FXML
    private ComboBox<String> modelComboBox1;
    @FXML
    private ComboBox<String> bodyStyleComboBox;
    @FXML
    private ComboBox<String> bodyStyleComboBox1;
    @FXML
    private ImageView carImageView;
    @FXML
    private ImageView carImageView1;

    static ArrayList<Car> InventoryList;

    @FXML
    /**
     * Starting method calls all relevant methods so when user starts program
     * all data is loaded.
     */
    public void initialize() throws FileNotFoundException {
        Thread t = new Thread(() -> {
            try {
                loadDB();
                createCarList();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
         Platform.runLater(() -> {
             guiDisplay();
         });
          });
         t.start();
    }

    /**
     * This method creates an array list from the database from which all car
     * objects will be populated and stored from
     */
    public static void createCarList() {
        ArrayList<Car> carList = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            // create the database connection
            String databaseURL = "jdbc:ucanaccess://.//Vehicles.accdb";
            conn = DriverManager.getConnection(databaseURL);

            // read the car data from the Inventory table and add them to the carList
            String query = "SELECT * FROM Inventory";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                Car car = new Car();
                car.setMakeName(rs.getString("makeName"));
                car.setModelName(rs.getString("modelName"));
                car.setBodyStyle(rs.getString("bodyStyle"));
                car.setDoors(rs.getInt("doors"));
                car.setPassengerCapacity(rs.getInt("passengerCapacity"));
                car.setMpg(rs.getInt("mpg"));
                car.setCost(rs.getDouble("cost"));
                carList.add(car);
            }
        } catch (SQLException ex) {
        } //Block to close all connections despite if method was successful or not
        finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                }
            }
        }
        // print the carList to console to check if it's working correctly
        InventoryList = carList;
         for (Car car : InventoryList) {
            System.out.println(car);
        }

    }
    /**
     * Method that runs all methods related to populating gui display.
     */
    private void guiDisplay(){
        filterBodyStyleComboBox();
        initBodyStyleComboBox1();
        imageModelComboBox();
        imageModelComboBox1();
        comboBox();
        comboBox1();
    }

    /**
     * This method has a listener that checks the make and model. From there the
     * appropriate image is loaded with fade animation
     */
    private void imageModelComboBox() {
        modelComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String selectedMake = makeComboBox.getSelectionModel().getSelectedItem();
            String selectedModel = newValue;

            // Load the image based on the selected make and model
            String imagePath = "Images/" + selectedMake + "_" + selectedModel + ".jpg";
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                Image image = new Image(imageFile.toURI().toString());
                FadeTransition fadeOut = new FadeTransition(Duration.millis(200), carImageView);
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);
                fadeOut.setOnFinished(event -> {
                    carImageView.setImage(image);
                    FadeTransition fadeIn = new FadeTransition(Duration.millis(200), carImageView);
                    fadeIn.setFromValue(0.0);
                    fadeIn.setToValue(1.0);
                    fadeIn.play();
                });
                fadeOut.play();
            } else {
                carImageView.setImage(null);
            }
        });
    }

    /**
     * duplicate method to imageModelComboBox, just applies steps to second set
     * of imageView
     */
    private void imageModelComboBox1() {
        modelComboBox1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String selectedMake = makeComboBox1.getSelectionModel().getSelectedItem();
            String selectedModel = newValue;

            // Load the image based on the selected make and model
            String imagePath = "Images/" + selectedMake + "_" + selectedModel + ".jpg";
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                Image image = new Image(imageFile.toURI().toString());
                FadeTransition fadeOut = new FadeTransition(Duration.millis(200), carImageView1);
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);
                fadeOut.setOnFinished(event -> {
                    carImageView1.setImage(image);
                    FadeTransition fadeIn = new FadeTransition(Duration.millis(200), carImageView1);
                    fadeIn.setFromValue(0.0);
                    fadeIn.setToValue(1.0);
                    fadeIn.play();
                });
                fadeOut.play();
            } else {
                carImageView1.setImage(null);
            }
        });
    }

    /**
     * This method creates the functionality of a filter by body style first a
     * listener is added to the body style filter. Once selected a second
     * listener is implemented to wait for a make selection so appropriate
     * models are displayed.
     */
    private void filterBodyStyleComboBox() {
        // add the body styles to the combo box
        bodyStyleComboBox.getItems().addAll("All", "SUV", "Sedan", "Coupe");

        // set the default value to "All"
        bodyStyleComboBox.setValue("All");

        // add a listener to the body style combo box
        bodyStyleComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // clear the model combo box
            modelComboBox.getItems().clear();

            // get the selected body style and make
            String selectedBodyStyle = bodyStyleComboBox.getSelectionModel().getSelectedItem();
            String selectedMake = makeComboBox.getSelectionModel().getSelectedItem();

            // filter the car list based on the selected body style and make, if not "All"
            List<String> filteredModels;
            if (selectedMake == null) {
                System.out.println("Waiting for make");
                filteredModels = Collections.emptyList();
            } else {
                filteredModels = InventoryList.stream()
                        .filter(car -> (selectedBodyStyle.equals("All") || car.getBodyStyle().equals(selectedBodyStyle))
                        && (selectedMake.equals("All") || car.getMakeName().equals(selectedMake)))
                        .map(Car::getModelName)
                        .collect(Collectors.toList());
            }

            // add the filtered models to the model combo box
            modelComboBox.getItems().addAll(filteredModels);

            // set the default value to the first item
            if (!filteredModels.isEmpty()) {
                modelComboBox.setValue(filteredModels.get(0));
            }
        });

        // add a listener to the make combo box
        makeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // clear the model combo box
            modelComboBox.getItems().clear();

            // get the selected body style and make
            String selectedBodyStyle = bodyStyleComboBox.getSelectionModel().getSelectedItem();
            String selectedMake = makeComboBox.getSelectionModel().getSelectedItem();

            // filter the car list based on the selected body style and make, if not "All"
            List<String> filteredModels = InventoryList.stream()
                    .filter(car -> (selectedBodyStyle.equals("All") || car.getBodyStyle().equals(selectedBodyStyle))
                    && (selectedMake.equals("All") || car.getMakeName().equals(selectedMake)))
                    .map(Car::getModelName)
                    .collect(Collectors.toList());

            // add the filtered models to the model combo box
            modelComboBox.getItems().addAll(filteredModels);

            // set the default value to the first item
            if (!filteredModels.isEmpty()) {
                modelComboBox.setValue(filteredModels.get(0));
            }
        });
    }

    /**
     * Duplicate method to filterBodyStyleComboBox
     */
    private void initBodyStyleComboBox1() {
        // add the body styles to the combo box
        bodyStyleComboBox1.getItems().addAll("All", "SUV", "Sedan", "Coupe");

        // set the default value to "All"
        bodyStyleComboBox1.setValue("All");

        // add a listener to the body style combo box
        bodyStyleComboBox1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // clear the model combo box
            modelComboBox1.getItems().clear();

            // get the selected body style and make
            String selectedBodyStyle = bodyStyleComboBox1.getSelectionModel().getSelectedItem();
            String selectedMake = makeComboBox1.getSelectionModel().getSelectedItem();

            // filter the car list based on the selected body style and make, if not "All"
            List<String> filteredModels;
            if (selectedMake == null) {
                System.out.println("Waiting for make");
                filteredModels = Collections.emptyList();
            } else {
                filteredModels = InventoryList.stream()
                        .filter(car -> (selectedBodyStyle.equals("All") || car.getBodyStyle().equals(selectedBodyStyle))
                        && (selectedMake.equals("All") || car.getMakeName().equals(selectedMake)))
                        .map(Car::getModelName)
                        .collect(Collectors.toList());
            }

            // add the filtered models to the model combo box
            modelComboBox1.getItems().addAll(filteredModels);

            // set the default value to the first item
            if (!filteredModels.isEmpty()) {
                modelComboBox1.setValue(filteredModels.get(0));
            }
        });

        // add a listener to the make combo box
        makeComboBox1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // clear the model combo box
            modelComboBox1.getItems().clear();

            // get the selected body style and make
            String selectedBodyStyle = bodyStyleComboBox1.getSelectionModel().getSelectedItem();
            String selectedMake = makeComboBox1.getSelectionModel().getSelectedItem();

            // filter the car list based on the selected body style and make, if not "All"
            List<String> filteredModels = InventoryList.stream()
                    .filter(car -> (selectedBodyStyle.equals("All") || car.getBodyStyle().equals(selectedBodyStyle))
                    && (selectedMake.equals("All") || car.getMakeName().equals(selectedMake)))
                    .map(Car::getModelName)
                    .collect(Collectors.toList());

            // add the filtered models to the model combo box
            modelComboBox1.getItems().addAll(filteredModels);

            // set the default value to the first item
            if (!filteredModels.isEmpty()) {
                modelComboBox1.setValue(filteredModels.get(0));
            }
        });
    }

    /**
     * Method to populate ComboBoxes based on Inventory array list.
     */
    public void comboBox() {
        // read the distinct make names from the InventoryList and add them to an ArrayList
        ArrayList<String> makeList = new ArrayList<>();
        for (Car car : InventoryList) {
            if (!makeList.contains(car.getMakeName())) {
                makeList.add(car.getMakeName());
            }
        }

        // set the makeComboBox items
        ObservableList<String> makeObservableList = FXCollections.observableArrayList(makeList);
        makeComboBox.setItems(makeObservableList);
        modelComboBox.setDisable(true);

        // handle makeComboBox selection changes
        makeComboBox.setOnAction(event -> {
            String selectedMake = makeComboBox.getSelectionModel().getSelectedItem();
            handleMakeSelection(selectedMake);
        });
    }

    /**
     * Duplicate method to comboBox.
     */
    public void comboBox1() {
        // read the distinct make names from the InventoryList and add them to an ArrayList
        ArrayList<String> makeList = new ArrayList<>();
        for (Car car : InventoryList) {
            if (!makeList.contains(car.getMakeName())) {
                makeList.add(car.getMakeName());
            }
        }

        // set the makeComboBox items
        ObservableList<String> makeObservableList = FXCollections.observableArrayList(makeList);
        makeComboBox1.setItems(makeObservableList);
        modelComboBox1.setDisable(true);

        // handle makeComboBox selection changes
        makeComboBox1.setOnAction(event -> {
            String selectedMake = makeComboBox1.getSelectionModel().getSelectedItem();
            handleMakeSelection1(selectedMake);
        });
    }

    /**
     * method responsible for allowing a model to be selected based on the
     * chosen make
     *
     * @param selectedMake
     */
    private void handleMakeSelection(String selectedMake) {
        if (selectedMake != null) {
            ObservableList<String> modelList = FXCollections.observableArrayList();
            for (Car car : InventoryList) {
                if (car.getMakeName().equals(selectedMake)) {
                    modelList.add(car.getModelName());
                }
            }
            modelComboBox.setItems(modelList);
            modelComboBox.setDisable(false);
            // handle modelComboBox selection changes
            modelComboBox.setOnAction(event -> {
                String selectedModel = modelComboBox.getSelectionModel().getSelectedItem();
                displayCarInformation(selectedMake, selectedModel); // call displayCarInformation method with selected make and model
            });
        } else {
            modelComboBox.setItems(null);
            modelComboBox.setDisable(true);
        }
    }

    /**
     * Duplicate method to handleMakeSelection
     *
     * @param selectedMake1
     */
    private void handleMakeSelection1(String selectedMake1) {
        if (selectedMake1 != null) {
            ObservableList<String> modelList1 = FXCollections.observableArrayList();
            for (Car car : InventoryList) {
                if (car.getMakeName().equals(selectedMake1)) {
                    modelList1.add(car.getModelName());
                }
            }
            modelComboBox1.setItems(modelList1);
            modelComboBox1.setDisable(false);
            // handle modelComboBox selection changes
            modelComboBox1.setOnAction(event -> {
                String selectedModel = modelComboBox1.getSelectionModel().getSelectedItem();
                displayCarInformation1(selectedMake1, selectedModel); // call displayCarInformation method with selected make and model
            });
        } else {
            modelComboBox1.setItems(null);
            modelComboBox1.setDisable(true);
        }
    }

    /**
     * method to display car information based on selected model updates label
     * based on selected model and make
     *
     * @param selectedMake
     * @param selectedModel
     */
    private void displayCarInformation(String selectedMake, String selectedModel) {
        Car car = null;
        for (Car c : InventoryList) {
            if (c.getMakeName().equals(selectedMake) && c.getModelName().equals(selectedModel)) {
                car = c;
                break;
            }
        }

        if (car != null) {
            makeLabel.setText("Make: " + car.getMakeName());
            modelLabel.setText("Model: " + car.getModelName());
            bodyStyleLabel.setText("Body Style: " + car.getBodyStyle());
            doorsLabel.setText("Doors: " + car.getDoors());
            passengerCapacityLabel.setText("Passenger Capacity: " + car.getPassengerCapacity());
            mpgLabel.setText("MPG: " + car.getMpg());
            costLabel.setText("Cost: $" + car.getCost() + "0");

            // display the car information
            System.out.println("");
            System.out.println("Make: " + car.getMakeName());
            System.out.println("Model: " + car.getModelName());
            System.out.println("Body Style: " + car.getBodyStyle());
            System.out.println("Doors: " + car.getDoors());
            System.out.println("Passenger Capacity: " + car.getPassengerCapacity());
            System.out.println("MPG: " + car.getMpg());
            System.out.println("Cost: $" + car.getCost());
        }
    }

    /**
     * duplicate method to displayCarInformation
     *
     * @param selectedMake
     * @param selectedModel
     */
    private void displayCarInformation1(String selectedMake, String selectedModel) {
        Car car = null;
        for (Car c : InventoryList) {
            if (c.getMakeName().equals(selectedMake) && c.getModelName().equals(selectedModel)) {
                car = c;
                break;
            }
        }

        if (car != null) {
            makeLabel1.setText("Make: " + car.getMakeName());
            modelLabel1.setText("Model: " + car.getModelName());
            bodyStyleLabel1.setText("Body Style: " + car.getBodyStyle());
            doorsLabel1.setText("Doors: " + car.getDoors());
            passengerCapacityLabel1.setText("Passenger Capacity: " + car.getPassengerCapacity());
            mpgLabel1.setText("MPG: " + car.getMpg());
            costLabel1.setText("Cost: $" + car.getCost() + "0");

            // display the car information
            System.out.println("");
            System.out.println("Make: " + car.getMakeName());
            System.out.println("Model: " + car.getModelName());
            System.out.println("Body Style: " + car.getBodyStyle());
            System.out.println("Doors: " + car.getDoors());
            System.out.println("Passenger Capacity: " + car.getPassengerCapacity());
            System.out.println("MPG: " + car.getMpg());
            System.out.println("Cost: $" + car.getCost());
        }
    }

    /**
     * Clears database
     */
    public void clearDB() {
        String databaseURL = "";
        Connection conn = null;
        try {                                                                    // attempts connection to database
            databaseURL = "jdbc:ucanaccess://.//Vehicles.accdb";
            conn = DriverManager.getConnection(databaseURL);
            System.out.println("load success");
        } catch (SQLException ex) {
            System.out.println("File not Found");
        }
        try {                                                                   // attempts to delete from table using sql command
            String sql = "DELETE FROM Inventory";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            int rowsDeleted = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Delete Failed");
        }
    }

    /**
     * Loads database with information from custom JSON file.
     *
     * @throws FileNotFoundException
     */
    public void loadDB() throws FileNotFoundException {
        String databaseURL = "";
        Connection conn = null;
        try {
            // attempts connection to database
            databaseURL = "jdbc:ucanaccess://.//Vehicles.accdb";
            conn = DriverManager.getConnection(databaseURL);
            System.out.println("Load success");
        } catch (SQLException ex) {
            System.out.println("File not found");
        }
        clearDB(); // call to clear database

        GsonBuilder builder = new GsonBuilder(); // creates necessary objects to read JSON file
        Gson gson = builder.create();
        FileReader fr = new FileReader("cars.json");
        Car[] cars = gson.fromJson(fr, Car[].class);
        for (int i = 0; i < cars.length; i++) { // use of a loop ensures every line in file is read and appropriate data is extracted
            String makeName = cars[i].getMakeName();
            String modelName = cars[i].getModelName();
            String bodyStyle = cars[i].getBodyStyle();
            int doors = cars[i].getDoors();
            int passengerCapacity = cars[i].getPassengerCapacity();
            int mpg = cars[i].getMpg();
            double cost = cars[i].getCost();

            try { // final block attempts to insert JSON data from prepared statements into access database
                String sql = "INSERT INTO Inventory (MakeName, ModelName, BodyStyle, Doors, PassengerCapacity, Mpg, Cost) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, makeName);
                preparedStatement.setString(2, modelName);
                preparedStatement.setString(3, bodyStyle);
                preparedStatement.setInt(4, doors);
                preparedStatement.setInt(5, passengerCapacity);
                preparedStatement.setInt(6, mpg);
                preparedStatement.setDouble(7, cost);
                int row = preparedStatement.executeUpdate();
                /*  if (row > 0) {
                    System.out.println("Row inserted");
                }*/
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
