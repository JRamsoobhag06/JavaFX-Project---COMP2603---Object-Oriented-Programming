package com.flightbooking.ui;

import com.flightbooking.service.BookingService;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Main entry point for the Flight Booking System.
 *
 * This class wires together the three UI panels into a TabPane.
 * The GUI shell is PROVIDED — do not modify this class.
 *
 * Your work lives in:
 *   - FlightSearchPanel.java    (tab 1)
 *   - SeatMapPanel.java         (tab 2)
 *   - PassengerPanel.java       (tab 3)
 *   - BookingService.java       (service layer)
 *   - model/*                   (data model)
 */
public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        // Initialise the service (singleton — students implement getInstance())
        BookingService service = BookingService.getInstance();

        // ── Build UI ──────────────────────────────────────────────────────────
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab searchTab    = new Tab("✈  Search Flights",  new FlightSearchPanel(service));
        Tab seatTab      = new Tab("💺  Seat Map",         new SeatMapPanel(service));
        Tab passengerTab = new Tab("🧑  Passengers",       new PassengerPanel(service));

        tabPane.getTabs().addAll(searchTab, seatTab, passengerTab);

        BorderPane root = new BorderPane(tabPane);
        root.setPadding(new Insets(8));

        Scene scene = new Scene(root, 1000, 700);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        stage.setTitle("✈  SkyDesk — Flight Booking System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
