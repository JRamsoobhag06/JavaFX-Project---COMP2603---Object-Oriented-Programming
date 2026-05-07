package com.flightbooking.ui;

import com.flightbooking.model.Booking;
import com.flightbooking.model.Passenger;
import com.flightbooking.service.BookingService;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Tab 3 — Passenger Management Panel.
 *
 * PROVIDED shell — students implement all TODO sections.
 *
 * LAB TASK 1: Wire "Register Passenger" button -> service.registerPassenger()
 * LAB TASK 2: Selecting a passenger loads their bookings into bookingTable
 * LAB TASK 3: Wire "Cancel Selected Booking" -> service.cancelBooking()
 * LAB TASK 4: Wire "Print Receipt" button -> passenger.getReceipt() in a dialog
 * LAB TASK 5 (Extension): Add a "Check In" button -> booking.checkIn()
 */
public class PassengerPanel extends VBox {

    private final BookingService service;

    // ── Controls ──────────────────────────────────────────────────────────────
    private final ListView<Passenger>  passengerList   = new ListView<>();
    private final TableView<Booking>   bookingTable    = new TableView<>();
    private final TextField            nameField       = new TextField();
    private final TextField            emailField      = new TextField();
    private final TextField            passportField   = new TextField();
    private final Button               registerBtn     = new Button("Register");
    private final Button               cancelBookBtn   = new Button("Cancel Booking");
    private final Button               receiptBtn      = new Button("Print Receipt");
    private final Label                statusLabel     = new Label("");

    // ── Constructor ───────────────────────────────────────────────────────────

    public PassengerPanel(BookingService service) {
        this.service = service;
        setSpacing(12);
        setPadding(new Insets(16));

        buildHeader();
        buildMainArea();
        buildStatusBar();

        wireEvents();
        refreshPassengerList();
    }

    // ── Event Wiring ──────────────────────────────────────────────────────────

    private void wireEvents() {

        // LAB TASK 1 — Register a new passenger
        registerBtn.setOnAction(e -> {
            String name     = nameField.getText().trim();
            String email    = emailField.getText().trim();
            String passport = passportField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || passport.isEmpty()) {
                statusLabel.setText("All fields are required.");
                return;
            }

            // TODO: Call service.registerPassenger(name, email, passport)
            //       Clear the fields, refresh passengerList, show confirmation
            statusLabel.setText("TODO: implement registerPassenger()");
        });

        // LAB TASK 2 — Load bookings when a passenger is selected
        passengerList.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> {
                if (newVal != null) {
                    // TODO: Load newVal's bookings into bookingTable
                    //       Hint: service.getBookingsForPassenger(newVal.getPassengerId())
                    statusLabel.setText("Selected: " + newVal.getFullName() + " — TODO: load bookings");
                }
            }
        );

        // LAB TASK 3 — Cancel selected booking
        cancelBookBtn.setOnAction(e -> {
            Booking selected = bookingTable.getSelectionModel().getSelectedItem();
            if (selected == null) {
                statusLabel.setText("Please select a booking to cancel.");
                return;
            }
            // TODO: Confirm with an Alert, then call service.cancelBooking(selected.getBookingId())
            //       Refresh bookingTable and passengerList on success
            statusLabel.setText("TODO: implement cancelBooking()");
        });

        // LAB TASK 4 — Print receipt
        receiptBtn.setOnAction(e -> {
            Passenger p = passengerList.getSelectionModel().getSelectedItem();
            if (p == null) {
                statusLabel.setText("Please select a passenger.");
                return;
            }
            // TODO: Show p.getReceipt() in a scrollable Alert or Dialog
            statusLabel.setText("TODO: implement getReceipt()");
        });
    }

    // ── Data Refresh ──────────────────────────────────────────────────────────

    /**
     * TODO: Reload all passengers from service.getAllPassengers()
     * and update the passengerList ListView.
     */
    public void refreshPassengerList() {
        // TODO
    }

    // ── UI Builders ───────────────────────────────────────────────────────────

    private void buildHeader() {
        Label title = new Label("Passenger Management");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        getChildren().add(title);
    }

    private void buildMainArea() {
        // ── Left: passenger list + registration form ───────────────────────────
        Label listTitle = new Label("Registered Passengers");
        listTitle.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));

        passengerList.setPrefWidth(280);
        passengerList.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Passenger p, boolean empty) {
                super.updateItem(p, empty);
                setText(empty || p == null ? null : p.toString());
            }
        });

        // Registration form
        nameField.setPromptText("Full Name");
        emailField.setPromptText("Email");
        passportField.setPromptText("Passport No.");
        registerBtn.getStyleClass().add("primary-btn");

        GridPane form = new GridPane();
        form.setHgap(8); form.setVgap(6);
        form.addRow(0, new Label("Name:"),     nameField);
        form.addRow(1, new Label("Email:"),    emailField);
        form.addRow(2, new Label("Passport:"), passportField);
        form.add(registerBtn, 1, 3);

        VBox leftPane = new VBox(8, listTitle, passengerList, new Separator(), new Label("Register New Passenger"), form);
        leftPane.setPrefWidth(300);

        // ── Right: bookings table ─────────────────────────────────────────────
        Label bookTitle = new Label("Bookings");
        bookTitle.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));

        buildBookingTable();

        cancelBookBtn.getStyleClass().add("danger-btn");
        receiptBtn.getStyleClass().add("secondary-btn");
        HBox actions = new HBox(10, cancelBookBtn, receiptBtn);
        actions.setAlignment(Pos.CENTER_RIGHT);

        VBox rightPane = new VBox(8, bookTitle, bookingTable, actions);
        VBox.setVgrow(bookingTable, Priority.ALWAYS);
        HBox.setHgrow(rightPane, Priority.ALWAYS);

        HBox mainArea = new HBox(16, leftPane, rightPane);
        VBox.setVgrow(mainArea, Priority.ALWAYS);
        getChildren().add(mainArea);
    }

    @SuppressWarnings("unchecked")
    private void buildBookingTable() {
        TableColumn<Booking, String> idCol     = new TableColumn<>("Booking ID");
        TableColumn<Booking, String> flightCol = new TableColumn<>("Flight");
        TableColumn<Booking, String> seatCol   = new TableColumn<>("Seat");
        TableColumn<Booking, String> classCol  = new TableColumn<>("Class");
        TableColumn<Booking, String> priceCol  = new TableColumn<>("Price (£)");
        TableColumn<Booking, String> statusCol = new TableColumn<>("Status");

        // TODO: Wire each column to the right Booking property using setCellValueFactory
        //       Use SimpleStringProperty wrappers where needed, e.g.:
        //       idCol.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getBookingId()));

        idCol.setCellValueFactory(cd ->
            new javafx.beans.property.SimpleStringProperty("TODO"));
        flightCol.setCellValueFactory(cd ->
            new javafx.beans.property.SimpleStringProperty("TODO"));
        seatCol.setCellValueFactory(cd ->
            new javafx.beans.property.SimpleStringProperty("TODO"));
        classCol.setCellValueFactory(cd ->
            new javafx.beans.property.SimpleStringProperty("TODO"));
        priceCol.setCellValueFactory(cd ->
            new javafx.beans.property.SimpleStringProperty("TODO"));
        statusCol.setCellValueFactory(cd ->
            new javafx.beans.property.SimpleStringProperty("TODO"));

        bookingTable.getColumns().addAll(idCol, flightCol, seatCol, classCol, priceCol, statusCol);
        bookingTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        bookingTable.setPlaceholder(new Label("Select a passenger to view their bookings."));
        VBox.setVgrow(bookingTable, Priority.ALWAYS);
    }

    private void buildStatusBar() {
        statusLabel.getStyleClass().add("status-label");
        getChildren().add(statusLabel);
    }
}
