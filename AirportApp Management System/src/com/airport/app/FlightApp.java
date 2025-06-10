package com.airport.app;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.airport.model.Booking;
import com.airport.model.Flight;
import com.airport.model.Passenger;
import com.airport.repository.BookingRepository;
import com.airport.repository.FlightRepository;
import com.airport.repository.PassengerRepository;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;	
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FlightApp extends Application {

    private final Map<String, String> users = Map.of(
        "@akif-dev", "akif#dev"
    );
    private String currentRole;
    // Repositories
    private final FlightRepository flightRepo      = new FlightRepository();
    private final PassengerRepository passengerRepo = new PassengerRepository();
    private final BookingRepository bookingRepo    = new BookingRepository();

    // Observable data lists
    private final ObservableList<Flight>   flightData  = FXCollections.observableArrayList();
    private final ObservableList<Booking>  bookingData = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        // Initialize primary stage properties first
        primaryStage.initStyle(StageStyle.UNDECORATED);
        showSplash(primaryStage);
    }

    private void showSplash(Stage stage) {
        try {
            Stage splash = new Stage(StageStyle.TRANSPARENT);
            VBox splashContent = new VBox(20);
            splashContent.setAlignment(Pos.CENTER);
            
            Label title = new Label("✈️");
            title.setStyle("-fx-font-size: 48px;");
            
            Label subtitle = new Label("Airport Management System");
            subtitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
            
            ProgressBar progress = new ProgressBar();
            progress.setPrefWidth(200);
            
            Label loading = new Label("Loading...");
            loading.setStyle("-fx-font-size: 14px;");
            
            splashContent.getChildren().addAll(title, subtitle, progress, loading);
            splashContent.getStyleClass().add("splash-pane");
            
            Scene splashScene = new Scene(splashContent);
            splashScene.setFill(null);
            
            // Load stylesheet
            String cssPath = getClass().getResource("/style.css").toExternalForm();
            System.out.println("Loading splash CSS from: " + cssPath);
            splashScene.getStylesheets().add(cssPath);
            
            splash.setScene(splashScene);
            splash.initStyle(StageStyle.TRANSPARENT);
            splash.show();
            
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(e -> {
                splash.close();
                showLogin(stage);
            });
            delay.play();
        } catch (Exception e) {
            System.err.println("Error showing splash screen: " + e.getMessage());
            e.printStackTrace();
            showLogin(stage);
        }
    }

    private void showLogin(Stage stage) {
        try {
            System.out.println("=== Starting Login Screen Setup ===");
            
            // Create login content
            VBox loginContent = new VBox(20);
            loginContent.setAlignment(Pos.CENTER);
            loginContent.getStyleClass().add("login-pane");
            loginContent.setPadding(new Insets(30));
            
            // Create a semi-transparent background pane
            StackPane backgroundPane = new StackPane();
            backgroundPane.getStyleClass().add("login-background-pane");
            backgroundPane.setPadding(new Insets(20));
            
            Label title = new Label("✈️");
            title.setStyle("-fx-font-size: 48px;");
            
            Label subtitle = new Label("Yoo! Welcome");
            subtitle.getStyleClass().add("login-title");

            // Add team members section
            Label teamLabel = new Label("akif-dev:");
            teamLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1a237e;");

            Label teamMembers = new Label("PRESENTS");
            teamMembers.setStyle(
                "-fx-font-size: 14px; " +
                "-fx-font-family: 'Segoe UI', Arial; " +
                "-fx-text-fill: #303F9F; " +
                "-fx-font-weight: semi-bold; " +
                "-fx-text-alignment: center;"
            );
            teamMembers.setTextAlignment(TextAlignment.CENTER);

            VBox teamBox = new VBox(5, teamLabel, teamMembers);
            teamBox.setAlignment(Pos.CENTER);
            teamBox.setPadding(new Insets(10, 0, 20, 0));

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setAlignment(Pos.CENTER);
            
            Label userLabel = new Label("Username:");
            userLabel.setStyle("-fx-text-fill: #1a237e;");
            TextField userField = new TextField();
            userField.setPromptText("Enter username");
            userField.setPrefWidth(250);
            
            Label passLabel = new Label("Password:");
            passLabel.setStyle("-fx-text-fill: #1a237e;");
            PasswordField passField = new PasswordField();
            passField.setPromptText("Enter password");
            passField.setPrefWidth(250);
            
            Button loginBtn = new Button("Login");
            loginBtn.setPrefWidth(250);
            
            Label msgLbl = new Label();
            msgLbl.getStyleClass().add("error-label");
            msgLbl.setWrapText(true);
            msgLbl.setMaxWidth(250);
            msgLbl.setTextAlignment(TextAlignment.CENTER);
            
            grid.add(userLabel, 0, 0);
            grid.add(userField, 1, 0);
            grid.add(passLabel, 0, 1);
            grid.add(passField, 1, 1);
            
            loginContent.getChildren().addAll(title, subtitle, teamBox, grid, loginBtn, msgLbl);
            backgroundPane.getChildren().add(loginContent);
            
            // Create main container with background
            StackPane root = new StackPane();
            root.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, " +
                "#1a237e, #0277bd, #0288d1, #039be5);" +
                "-fx-background-radius: 0;"
            );
            
            root.getChildren().add(backgroundPane);
            
            // Create and setup scene
            Scene scene = new Scene(root);
            scene.setFill(null);
            
            // Load stylesheet
            String cssPath = getClass().getResource("/style.css").toExternalForm();
            System.out.println("Loading login CSS from: " + cssPath);
            scene.getStylesheets().add(cssPath);
            
            // Setup event handlers with animations
            loginBtn.setOnAction(e -> {
                // Disable the button during login attempt
                loginBtn.setDisable(true);
                
                // Add error label animation
                msgLbl.getStyleClass().remove("visible");
                
                // Attempt login after a short delay for button animation
                PauseTransition pause = new PauseTransition(Duration.millis(150));
                pause.setOnFinished(event -> {
                    attemptLogin(userField.getText(), passField.getText(), stage, msgLbl);
                    loginBtn.setDisable(false);
                    
                    // Show error message with animation if login fails
                    if (!msgLbl.getText().isEmpty()) {
                        msgLbl.getStyleClass().add("visible");
                    }
                });
                pause.play();
            });
            
            // Add enter key support with animations
            EventHandler<KeyEvent> enterHandler = evt -> {
                if (evt.getCode() == KeyCode.ENTER && !loginBtn.isDisabled()) {
                    loginBtn.fire();
                }
            };
            userField.setOnKeyPressed(enterHandler);
            passField.setOnKeyPressed(enterHandler);
            
            // Make the window draggable
            final Delta dragDelta = new Delta();
            root.setOnMousePressed(mouseEvent -> {
                dragDelta.x = stage.getX() - mouseEvent.getScreenX();
                dragDelta.y = stage.getY() - mouseEvent.getScreenY();
            });
            root.setOnMouseDragged(mouseEvent -> {
                stage.setX(mouseEvent.getScreenX() + dragDelta.x);
                stage.setY(mouseEvent.getScreenY() + dragDelta.y);
            });
            
            // Set stage properties
            stage.setMinWidth(400);
            stage.setMinHeight(300);
            stage.setScene(scene);
            stage.setTitle("Airport Management System - Login");
            
            // Center on screen
            stage.centerOnScreen();
            
            // Show the stage with fade-in animation
            stage.setOpacity(0);
            Platform.runLater(() -> {
                try {
                    stage.show();
                    
                    // Fade in animation
                    FadeTransition fadeIn = new FadeTransition(Duration.millis(500), root);
                    fadeIn.setFromValue(0);
                    fadeIn.setToValue(1);
                    fadeIn.play();
                    
                    stage.setOpacity(1);
                    userField.requestFocus();
                } catch (Exception e) {
                    System.err.println("Error showing login stage: " + e.getMessage());
                    e.printStackTrace();
                }
            });
            
            System.out.println("=== Login Screen Setup Complete ===");
            
        } catch (Exception e) {
            System.err.println("=== Error in Login Screen Setup ===");
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error showing login screen: " + e.getMessage());
        }
    }

    private void attemptLogin(String user, String pass, Stage stage, Label msgLbl) {
        try {
            System.out.println("=== Login Attempt Debug ===");
            System.out.println("Username entered: '" + user + "'");
            System.out.println("Password length: " + (pass != null ? pass.length() : "null"));
            
            // Input validation
            if (user == null || user.trim().isEmpty()) {
                msgLbl.setText("Username is required");
                return;
            }
            if (pass == null || pass.trim().isEmpty()) {
                msgLbl.setText("Password is required");
                return;
            }

            // Trim inputs
            String trimmedUser = user.trim();
            String trimmedPass = pass.trim();

            System.out.println("Checking credentials...");
            System.out.println("Available users: " + users.keySet());
            System.out.println("Contains user?: " + users.containsKey(trimmedUser));
            
            if (users.containsKey(trimmedUser)) {
                String storedPass = users.get(trimmedUser);
                System.out.println("Password match?: " + storedPass.equals(trimmedPass));
                
                if (storedPass.equals(trimmedPass)) {
                    System.out.println("Login successful!");
                    currentRole = "ADMIN";
                    System.out.println("Role assigned: " + currentRole);
                    
                    Platform.runLater(() -> {
                        try {
                            System.out.println("Building main interface...");
                            buildMain(stage);
                            System.out.println("Main interface built successfully");
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.err.println("Error building main interface: " + e.getMessage());
                            msgLbl.setText("Error loading main interface");
                            showAlert(Alert.AlertType.ERROR, "Error loading main interface: " + e.getMessage());
                        }
                    });
                } else {
                    System.out.println("Password mismatch");
                    msgLbl.setText("Invalid password");
                }
            } else {
                System.out.println("User not found");
                msgLbl.setText("Username not found");
            }
        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            e.printStackTrace();
            msgLbl.setText("Login error occurred: " + e.getMessage());
        }
        System.out.println("=== End Login Attempt ===");
    }

    private void buildMain(Stage stage) {
        try {
            System.out.println("=== Building Main Interface ===");
            
            // Create a new stage for the main interface
            Stage mainStage = new Stage(StageStyle.UNDECORATED);
            mainStage.setMinWidth(800);
            mainStage.setMinHeight(600);
            
            // Close the login stage
            stage.close();
            
            // Create root container with modern gradient background
            StackPane root = new StackPane();
            root.setPadding(new Insets(10));
            root.setStyle("-fx-background-color: linear-gradient(to bottom right, #1a237e, #0277bd);");
            
            // Seed flights on startup
            flightRepo.addFlight(new Flight("PK101", "Islamabad", "Karachi"));
            flightRepo.addFlight(new Flight("PK102", "Lahore", "Quetta"));
            flightData.setAll(flightRepo.getAllFlights());
            System.out.println("Flights seeded successfully");

            // Create header with window controls
            Label titleLabel = new Label("✈️ Airport Management System");
            titleLabel.getStyleClass().add("section-header");

            // Window control buttons
            Button minimizeBtn = new Button("—");
            minimizeBtn.getStyleClass().add("window-control");
            minimizeBtn.setOnAction(e -> mainStage.setIconified(true));

            Button maximizeBtn = new Button("□");
            maximizeBtn.getStyleClass().add("window-control");
            maximizeBtn.setOnAction(e -> {
                mainStage.setMaximized(!mainStage.isMaximized());
                maximizeBtn.setText(mainStage.isMaximized() ? "❐" : "□");
            });

            Button closeBtn = new Button("✕");
            closeBtn.getStyleClass().addAll("window-control", "close-button");
            closeBtn.setOnAction(e -> {
                if (confirm("Are you sure you want to exit?")) {
                    Platform.exit();
                }
            });

            HBox windowControls = new HBox(5, minimizeBtn, maximizeBtn, closeBtn);
            windowControls.setAlignment(Pos.CENTER_RIGHT);
            windowControls.getStyleClass().add("window-controls");

            // Create header with title and controls
            HBox headerBox = new HBox();
            headerBox.getChildren().addAll(titleLabel, new Region(), windowControls);
            HBox.setHgrow(titleLabel, Priority.ALWAYS);
            headerBox.setAlignment(Pos.CENTER_LEFT);
            headerBox.setPadding(new Insets(10, 20, 10, 20));
            headerBox.getStyleClass().add("header-container");

            System.out.println("Header created successfully");

            TabPane tabs = new TabPane();
            tabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
            
            // Create tabs with text-based icons
            Tab flightsTab = new Tab("✈️ Flights", buildFlightsPane());
            Tab bookingsTab = new Tab("📅 Bookings", buildBookingsPane());
            Tab checkinTab = new Tab("✅ Check-In", buildCheckinPane());
            Tab reportsTab = new Tab("📊 Reports", buildReportsPane());

            // Add all tabs since only admin access remains
            tabs.getTabs().addAll(flightsTab, bookingsTab, checkinTab, reportsTab);

            // Create main content container with padding
            VBox mainContent = new VBox(10);
            mainContent.getChildren().addAll(headerBox, tabs);
            mainContent.setPadding(new Insets(20));
            VBox.setVgrow(tabs, Priority.ALWAYS);

            // Add status bar
            Label statusLabel = new Label("Logged in as: ADMIN");
            statusLabel.getStyleClass().add("status-label");
            
            HBox statusBar = new HBox(10);
            statusBar.getChildren().addAll(statusLabel);
            statusBar.getStyleClass().add("status-bar");
            statusBar.setPadding(new Insets(5, 10, 5, 10));
            
            // Add everything to the main container
            VBox mainContainer = new VBox(mainContent, statusBar);
            mainContainer.getStyleClass().add("main-container");
            mainContainer.setPadding(new Insets(10));
            
            // Add main container to root
            root.getChildren().add(mainContainer);

            // Create scene with transparent background
            Scene scene = new Scene(root);
            String cssPath = getClass().getResource("/style.css").toExternalForm();
            System.out.println("Loading main CSS from: " + cssPath);
            scene.getStylesheets().add(cssPath);

            // Make the window draggable by the header
            final Delta dragDelta = new Delta();
            headerBox.setOnMousePressed(mouseEvent -> {
                dragDelta.x = mainStage.getX() - mouseEvent.getScreenX();
                dragDelta.y = mainStage.getY() - mouseEvent.getScreenY();
            });
            headerBox.setOnMouseDragged(mouseEvent -> {
                if (!mainStage.isMaximized()) {
                    mainStage.setX(mouseEvent.getScreenX() + dragDelta.x);
                    mainStage.setY(mouseEvent.getScreenY() + dragDelta.y);
                }
            });
            headerBox.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2) {
                    maximizeBtn.fire();
                }
            });

            // Set scene and show stage
            mainStage.setScene(scene);
            mainStage.setTitle("Airport Management - " + currentRole);
            
            // Center on screen before showing
            mainStage.centerOnScreen();
            
            // Show the stage
            Platform.runLater(() -> {
                try {
                    mainStage.show();
                    System.out.println("Main interface shown successfully");
                } catch (Exception e) {
                    System.err.println("Error showing main interface: " + e.getMessage());
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Error showing main interface: " + e.getMessage());
                }
            });

            System.out.println("=== Main Interface Built Successfully ===");

        } catch (Exception e) {
            System.err.println("=== Error Building Main Interface ===");
            System.err.println("Error details: " + e.getMessage());
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error building main interface: " + e.getMessage());
        }
    }

    private BorderPane buildFlightsPane() {
        // Create a container with flight management styling
        VBox contentCard = new VBox(15);
        contentCard.getStyleClass().add("flight-management-container");

        // Header
        Label sectionTitle = new Label("✈️ Flight Management \t(Dev By: M.Akif Fareed)");
        sectionTitle.getStyleClass().add("flight-header");

        // Search section
        TextField search = new TextField();
        search.setPromptText("Search by flight number, origin, or destination...");
        search.getStyleClass().add("flight-search-field");
        
        HBox searchBox = new HBox(10);
        searchBox.getStyleClass().add("flight-search-container");
        searchBox.getChildren().addAll(search);

        // Table setup
        FilteredList<Flight> filtered = new FilteredList<>(flightData, f -> true);
        TableView<Flight> table = new TableView<>(filtered);
        table.getStyleClass().add("flight-table");
        table.setPlaceholder(new Label("No flights available"));
        
        TableColumn<Flight,String> c1 = new TableColumn<>("Flight #");
        TableColumn<Flight,String> c2 = new TableColumn<>("Origin");
        TableColumn<Flight,String> c3 = new TableColumn<>("Destination");
        
        c1.setCellValueFactory(f -> new ReadOnlyStringWrapper(f.getValue().getFlightNumber()));
        c2.setCellValueFactory(f -> new ReadOnlyStringWrapper(f.getValue().getOrigin()));
        c3.setCellValueFactory(f -> new ReadOnlyStringWrapper(f.getValue().getDestination()));
        
        table.getColumns().setAll(c1, c2, c3);

        // Search functionality
        search.textProperty().addListener((obs, old, nw) ->
            filtered.setPredicate(f -> nw.isEmpty() || 
                f.getFlightNumber().toLowerCase().contains(nw.toLowerCase()) ||
                f.getOrigin().toLowerCase().contains(nw.toLowerCase()) ||
                f.getDestination().toLowerCase().contains(nw.toLowerCase()))
        );

        // Input fields
        TextField tfNo = new TextField();
        tfNo.setPromptText("Flight #");
        tfNo.getStyleClass().add("flight-input-field");
        
        TextField tfOri = new TextField();
        tfOri.setPromptText("Origin");
        tfOri.getStyleClass().add("flight-input-field");
        
        TextField tfDes = new TextField();
        tfDes.setPromptText("Destination");
        tfDes.getStyleClass().add("flight-input-field");
        
        // Buttons
        Button btnAdd = new Button("Add Flight");
        btnAdd.getStyleClass().addAll("flight-button", "add-flight-button");
        
        Button btnDel = new Button("Delete Flight");
        btnDel.getStyleClass().addAll("flight-button", "delete-flight-button");
        
        // Button actions
        btnAdd.setOnAction(e -> {
            if (tfNo.getText().isEmpty() || tfOri.getText().isEmpty() || tfDes.getText().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "All fields are required");
                return;
            }
            Flight f = new Flight(tfNo.getText(), tfOri.getText(), tfDes.getText());
            flightRepo.addFlight(f);
            flightData.add(f);
            tfNo.clear();
            tfOri.clear();
            tfDes.clear();
            showAlert(Alert.AlertType.INFORMATION, "Flight added successfully");
        });
        
        btnDel.setOnAction(e -> {
            Flight sel = table.getSelectionModel().getSelectedItem();
            if (sel == null) {
                showAlert(Alert.AlertType.WARNING, "Please select a flight to delete");
            } else if (confirm("Are you sure you want to delete flight " + sel.getFlightNumber() + "?")) {
                flightData.remove(sel);
                showAlert(Alert.AlertType.INFORMATION, "Flight deleted successfully");
            }
        });

        // Controls container
        HBox controls = new HBox(10);
        controls.getStyleClass().add("flight-controls");
        controls.getChildren().addAll(tfNo, tfOri, tfDes, btnAdd, btnDel);
        controls.setAlignment(Pos.CENTER_LEFT);

        // Add all components to the main container
        contentCard.getChildren().addAll(sectionTitle, searchBox, controls, table);
        
        // Create and return the final layout
        BorderPane pane = new BorderPane(contentCard);
        pane.setPadding(new Insets(10));
        return pane;
    }

    private VBox buildBookingsPane() {
        FilteredList<Booking> filtered = new FilteredList<>(bookingData, b->true);
        TableView<Booking> table = new TableView<>(filtered);
        TableColumn<Booking,String> c1 = new TableColumn<>("Flight #");
        TableColumn<Booking,String> c2 = new TableColumn<>("Passenger");
        TableColumn<Booking,String> c3 = new TableColumn<>("Contact");
        TableColumn<Booking,String> c4 = new TableColumn<>("Seat");
        TableColumn<Booking,String> c5 = new TableColumn<>("Passport #");
        TableColumn<Booking,String> c6 = new TableColumn<>("Special Req.");
        
        c1.setCellValueFactory(b->new ReadOnlyStringWrapper(b.getValue().getFlight().getFlightNumber()));
        c2.setCellValueFactory(b->new ReadOnlyStringWrapper(b.getValue().getPassenger().getName()));
        c3.setCellValueFactory(b->new ReadOnlyStringWrapper(b.getValue().getPassenger().getContact()));
        c4.setCellValueFactory(b->new ReadOnlyStringWrapper(b.getValue().getPassenger().getSeatNumber()));
        c5.setCellValueFactory(b->new ReadOnlyStringWrapper(b.getValue().getPassenger().getPassportNumber()));
        c6.setCellValueFactory(b->new ReadOnlyStringWrapper(b.getValue().getPassenger().getSpecialRequirements()));
        
        table.getColumns().setAll(c1,c2,c3,c4,c5,c6);

        TextField search=new TextField(); search.setPromptText("Search Bookings...");
        search.textProperty().addListener((obs,old,nw) ->
            filtered.setPredicate(b->nw.isEmpty()
                || b.getFlight().getFlightNumber().toLowerCase().contains(nw.toLowerCase())
                || b.getPassenger().getName().toLowerCase().contains(nw.toLowerCase())
                || b.getPassenger().getSeatNumber() != null && b.getPassenger().getSeatNumber().toLowerCase().contains(nw.toLowerCase())));
        HBox searchBox=new HBox(10,new Label("Search:"),search);
        searchBox.setPadding(new Insets(10));

        ComboBox<Flight> cbFlights=new ComboBox<>(flightData);
        cbFlights.setPromptText("Select Flight");
        cbFlights.setPrefWidth(200);
        TextField tfName=new TextField(); tfName.setPromptText("Name");
        TextField tfContact=new TextField(); tfContact.setPromptText("Contact");
        TextField tfPassport=new TextField(); tfPassport.setPromptText("Passport Number");
        TextField tfSeat=new TextField(); tfSeat.setPromptText("Seat Number");
        TextArea taSpecialReq=new TextArea(); 
        taSpecialReq.setPromptText("Special Requirements (Optional)");
        taSpecialReq.setPrefRowCount(1);
        taSpecialReq.setPrefWidth(300);
        taSpecialReq.setWrapText(true);
        taSpecialReq.setMaxHeight(50);
        
        Button btnBook=new Button("✈️ Book");
        btnBook.setPrefWidth(120);
        Button btnDel=new Button("🗑️ Cancel");
        btnDel.setPrefWidth(120);
        
        // Style the buttons
        btnBook.getStyleClass().add("action-button");
        btnDel.getStyleClass().add("cancel-button");
        
        // Create form layout with better organization
        HBox row1 = new HBox(10, cbFlights, tfName, tfContact);
        row1.setAlignment(Pos.CENTER_LEFT);
        
        HBox row2 = new HBox(10, tfPassport, tfSeat, taSpecialReq);
        row2.setAlignment(Pos.CENTER_LEFT);
        
        HBox buttonRow = new HBox(10, btnBook, btnDel);
        buttonRow.setAlignment(Pos.CENTER_LEFT);
        
        VBox formInputs = new VBox(10, row1, row2, buttonRow);
        formInputs.setPadding(new Insets(10));
        formInputs.getStyleClass().add("booking-form");

        btnBook.setOnAction(e->{
            Flight sel=cbFlights.getValue();
            if(sel==null) { new Alert(Alert.AlertType.WARNING,"Select a flight").showAndWait(); return; }
            if(tfName.getText().isEmpty() || tfContact.getText().isEmpty() || tfPassport.getText().isEmpty()){
                new Alert(Alert.AlertType.WARNING,"Enter name, contact and passport number").showAndWait(); return; }
            
            Passenger p = new Passenger(tfName.getText(), tfContact.getText(), tfPassport.getText());
            if(!tfSeat.getText().isEmpty()) {
                p.setSeatNumber(tfSeat.getText());
            }
            if(!taSpecialReq.getText().isEmpty()) {
                p.setSpecialRequirements(taSpecialReq.getText());
            }
            
            bookingRepo.add(new Booking(sel,p)); 
            bookingData.add(new Booking(sel,p));
            
            tfName.clear(); tfContact.clear(); tfPassport.clear(); 
            tfSeat.clear(); taSpecialReq.clear();
        });
        
        btnDel.setOnAction(e->{
            Booking sel=table.getSelectionModel().getSelectedItem();
            if(sel==null) new Alert(Alert.AlertType.WARNING,"Select booking to cancel").showAndWait();
            else bookingData.remove(sel);
        });
        
        return new VBox(10, searchBox, formInputs, table);
    }

    private VBox buildCheckinPane() {
        TableView<Booking> table=new TableView<>(bookingData);
        TableColumn<Booking,String> c1=new TableColumn<>("Flight #");
        TableColumn<Booking,String> c2=new TableColumn<>("Passenger");
        TableColumn<Booking,String> c3=new TableColumn<>("Seat");
        TableColumn<Booking,String> c4=new TableColumn<>("Passport #");
        c1.setCellValueFactory(b->new ReadOnlyStringWrapper(b.getValue().getFlight().getFlightNumber()));
        c2.setCellValueFactory(b->new ReadOnlyStringWrapper(b.getValue().getPassenger().getName()));
        c3.setCellValueFactory(b->new ReadOnlyStringWrapper(b.getValue().getPassenger().getSeatNumber()));
        c4.setCellValueFactory(b->new ReadOnlyStringWrapper(b.getValue().getPassenger().getPassportNumber()));
        table.getColumns().setAll(c1,c2,c3,c4);
        Button btn=new Button("✅ Check-In");
        btn.setOnAction(e->{
            Booking sel=table.getSelectionModel().getSelectedItem();
            if(sel==null){ new Alert(Alert.AlertType.WARNING,"Select a booking").showAndWait(); return;} 
            new Alert(Alert.AlertType.INFORMATION,
                "Boarding Pass:\nFlight: " + sel.getFlight().getFlightNumber() +
                "\nPassenger: " + sel.getPassenger().getName() +
                "\nSeat: " + (sel.getPassenger().getSeatNumber() != null ? sel.getPassenger().getSeatNumber() : "Not Assigned") +
                "\nPassport: " + sel.getPassenger().getPassportNumber() +
                (sel.getPassenger().getSpecialRequirements() != null && !sel.getPassenger().getSpecialRequirements().isEmpty() ? 
                "\nSpecial Requirements: " + sel.getPassenger().getSpecialRequirements() : "")).showAndWait();
        });
        return new VBox(10,table,btn);
    }

    private BorderPane buildReportsPane() {
        CategoryAxis x=new CategoryAxis();
        NumberAxis y=new NumberAxis();
        BarChart<String,Number> chart=new BarChart<>(x,y);
        chart.setTitle("Bookings per Flight");
        XYChart.Series<String,Number> series=new XYChart.Series<>();
        series.setName("Bookings");
        chart.getData().add(series);
        bookingData.addListener((ListChangeListener<Booking>)c->updateChart(series));
        updateChart(series);
        return new BorderPane(chart);
    }

    private void updateChart(XYChart.Series<String,Number> s) {
        s.getData().clear();
        bookingData.stream()
            .collect(Collectors.groupingBy(
                b->b.getFlight().getFlightNumber(), Collectors.counting()))
            .forEach((f,c)->s.getData().add(new XYChart.Data<>(f,c)));
    }

    private boolean confirm(String msg) {
        Alert a=new Alert(Alert.AlertType.CONFIRMATION,msg,ButtonType.OK,ButtonType.CANCEL);
        Optional<ButtonType> res=a.showAndWait();
        return res.isPresent() && res.get()==ButtonType.OK;
    }

    private void showAlert(Alert.AlertType type, String message) {
        Platform.runLater(() -> {
            try {
                Alert alert = new Alert(type, message);
                alert.initStyle(StageStyle.UNIFIED);
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error showing alert: " + message);
            }
        });
    }

    public static void main(String[] args) { launch(args); }

    // Helper class for window dragging
    private static class Delta {
        double x, y;
    }
}
