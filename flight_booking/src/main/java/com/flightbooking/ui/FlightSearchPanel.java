package com.flightbooking.ui;

import com.flightbooking.model.Flight;
import com.flightbooking.service.BookingService;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

/**
 * Tab 1 — Flight Search Panel.
 *
 * PROVIDED (mostly working). Students must:
 *   LAB TASK 1: Wire the Search button to BookingService.searchFlights()
 *   LAB TASK 2: Wire the "Show All" button to BookingService.getAllFlights()
 *   LAB TASK 3: Double-clicking a flight row should load that flight in SeatMapPanel
 *   LAB TASK 4 (Extension): Add a departure date filter using a DatePicker
 */
public class FlightSearchPanel extends VBox {

    private final BookingService service;

    // ── Controls ──────────────────────────────────────────────────────────────
    private final TextField          originField      = new TextField();
    private final TextField          destField        = new TextField();
    private final Button             searchBtn        = new Button("Search");
    private final Button             showAllBtn       = new Button("Show All Flights");
    private final Label              statusLabel      = new Label("");
    private final TableView<Flight>  resultsTable     = new TableView<>();

    // ── Constructor ───────────────────────────────────────────────────────────

    public FlightSearchPanel(BookingService service) {
        this.service = service;
        setSpacing(12);
        setPadding(new Insets(16));

        buildSearchBar();
        buildResultsTable();
        buildStatusBar();

        // ── LAB TASK 1 — Wire searchBtn ActionEvent ───────────────────────────
        searchBtn.setOnAction(event -> {
            String origin = originField.getText().trim().toUpperCase();
            String dest   = destField.getText().trim().toUpperCase();

            if (origin.isEmpty() || dest.isEmpty()) {
                statusLabel.setText("Please enter both an origin and destination.");
                return;
            }

            // TODO: Call service.searchFlights(origin, dest)
            //       Populate resultsTable with the results
            //       Update statusLabel: "Found N flight(s)" or "No flights found."
            statusLabel.setText("TODO: implement search");
        });

        // ── LAB TASK 2 — Wire showAllBtn ActionEvent ──────────────────────────
        showAllBtn.setOnAction(event -> {
            // TODO: Call service.getAllFlights() and populate resultsTable
            statusLabel.setText("TODO: implement show all");
        });
    }

    // ── UI Builders ───────────────────────────────────────────────────────────

    private void buildSearchBar() {
        Label title = new Label("Search Flights");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        originField.setPromptText("Origin (e.g. LHR)");
        destField.setPromptText("Destination (e.g. JFK)");
        originField.setPrefWidth(140);
        destField.setPrefWidth(140);

        searchBtn.getStyleClass().add("primary-btn");
        showAllBtn.getStyleClass().add("secondary-btn");

        HBox searchBar = new HBox(10,
                new Label("From:"), originField,
                new Label("To:"),   destField,
                searchBtn, showAllBtn);
        searchBar.setAlignment(Pos.CENTER_LEFT);

        getChildren().addAll(title, searchBar);
    }

    @SuppressWarnings("unchecked")
    private void buildResultsTable() {
        TableColumn<Flight, String> numCol  = new TableColumn<>("Flight No.");
        TableColumn<Flight, String> origCol = new TableColumn<>("Origin");
        TableColumn<Flight, String> destCol = new TableColumn<>("Destination");
        TableColumn<Flight, String> depCol  = new TableColumn<>("Departure");
        TableColumn<Flight, String> arrCol  = new TableColumn<>("Arrival");
        TableColumn<Flight, String> avCol   = new TableColumn<>("Availability");

        numCol.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        origCol.setCellValueFactory(new PropertyValueFactory<>("origin"));
        destCol.setCellValueFactory(new PropertyValueFactory<>("destination"));

        // TODO (LAB TASK 4): Format departure/arrival using DateTimeFormatter
        depCol.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        arrCol.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));

        // TODO: avCol should show e.g. "E:48  B:10  F:6" using getAvailabilityByClass()
        avCol.setCellValueFactory(cd -> {
            // TODO
            return new javafx.beans.property.SimpleStringProperty("TODO");
        });

        numCol.setPrefWidth(100);
        origCol.setPrefWidth(80);
        destCol.setPrefWidth(120);
        depCol.setPrefWidth(160);
        arrCol.setPrefWidth(160);
        avCol.setPrefWidth(160);

        resultsTable.getColumns().addAll(numCol, origCol, destCol, depCol, arrCol, avCol);
        resultsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        resultsTable.setPlaceholder(new Label("Search for flights above, or click \"Show All Flights\""));
        VBox.setVgrow(resultsTable, Priority.ALWAYS);

        // ── LAB TASK 3 — Double-click to load in SeatMapPanel ─────────────────
        resultsTable.setRowFactory(tv -> {
            TableRow<Flight> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Flight selected = row.getItem();
                    // TODO: Communicate selected flight to SeatMapPanel
                    //       Hint: Store a shared reference or use a callback/listener
                    statusLabel.setText("Selected: " + selected.getFlightNumber() + " (TODO: open in Seat Map tab)");
                }
            });
            return row;
        });

        getChildren().add(resultsTable);
    }

    private void buildStatusBar() {
        statusLabel.getStyleClass().add("status-label");
        getChildren().add(statusLabel);
    }
}
