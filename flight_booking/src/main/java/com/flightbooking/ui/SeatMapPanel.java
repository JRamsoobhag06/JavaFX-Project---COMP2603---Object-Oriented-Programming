package com.flightbooking.ui;

import com.flightbooking.model.*;
import com.flightbooking.service.BookingService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Map;

/**
 * Tab 2 — Visual Seat Map Panel.
 *
 * A grid of coloured seat buttons is PARTIALLY PROVIDED.
 * Students must complete the booking flow and colouring logic.
 *
 * LAB TASK 1: Implement renderSeatMap() — draw seats as coloured buttons on the grid
 * LAB TASK 2: Clicking an available seat opens a booking dialog (buildBookingDialog)
 * LAB TASK 3: Implement buildBookingDialog() — collect passenger ID and confirm booking
 * LAB TASK 4: After a successful booking, refresh the seat map
 * LAB TASK 5 (Extension): Add a legend and a "Cancel Booking" button
 */
public class SeatMapPanel extends VBox {

    private final BookingService service;

    // ── Controls ──────────────────────────────────────────────────────────────
    private final ComboBox<Flight> flightPicker  = new ComboBox<>();
    private final GridPane         seatGrid      = new GridPane();
    private final Label            statusLabel   = new Label("Select a flight to view its seat map.");
    private final Label            flightInfo    = new Label("");

    // Seat colour legend
    static final String COLOR_FIRST    = "#FFD700";  // gold
    static final String COLOR_BUSINESS = "#4FC3F7";  // blue
    static final String COLOR_ECONOMY  = "#A5D6A7";  // green
    static final String COLOR_OCCUPIED = "#EF9A9A";  // red
    static final String COLOR_SELECTED = "#CE93D8";  // purple

    // ── Constructor ───────────────────────────────────────────────────────────

    public SeatMapPanel(BookingService service) {
        this.service = service;
        setSpacing(12);
        setPadding(new Insets(16));

        buildHeader();
        buildLegend();
        buildGrid();
        buildStatusBar();

        // Wire flight picker
        flightPicker.setOnAction(e -> {
            Flight selected = flightPicker.getValue();
            if (selected != null) {
                flightInfo.setText(selected.toString());
                renderSeatMap(selected);
            }
        });
    }

    // ── Called externally by FlightSearchPanel (LAB TASK 3, Tab 1) ───────────

    /**
     * Load a specific flight into this panel programmatically.
     * TODO: Set flightPicker value and call renderSeatMap().
     */
    public void loadFlight(Flight flight) {
        // TODO
    }

    /**
     * Refresh available flights in the picker.
     * Call this when the panel becomes visible.
     * TODO: Populate flightPicker with service.getAllFlights()
     */
    public void refreshFlights() {
        // TODO
    }

    // ── UI Builders ───────────────────────────────────────────────────────────

    private void buildHeader() {
        Label title = new Label("Seat Map");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        flightPicker.setPromptText("Select a flight...");
        flightPicker.setPrefWidth(260);
        flightInfo.getStyleClass().add("flight-info-label");

        HBox header = new HBox(12, new Label("Flight:"), flightPicker, flightInfo);
        header.setAlignment(Pos.CENTER_LEFT);
        getChildren().addAll(title, header);
    }

    private void buildLegend() {
        HBox legend = new HBox(16,
            legendItem(COLOR_FIRST,    "First Class"),
            legendItem(COLOR_BUSINESS, "Business"),
            legendItem(COLOR_ECONOMY,  "Economy"),
            legendItem(COLOR_OCCUPIED, "Occupied"),
            legendItem(COLOR_SELECTED, "Your Selection")
        );
        legend.setAlignment(Pos.CENTER_LEFT);
        getChildren().add(legend);
    }

    private HBox legendItem(String hex, String label) {
        Rectangle swatch = new Rectangle(16, 16, Color.web(hex));
        swatch.setArcWidth(4); swatch.setArcHeight(4);
        return new HBox(5, swatch, new Label(label));
    }

    private void buildGrid() {
        seatGrid.setHgap(6);
        seatGrid.setVgap(6);
        seatGrid.setPadding(new Insets(12));
        ScrollPane scroll = new ScrollPane(seatGrid);
        scroll.setFitToWidth(true);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        getChildren().add(scroll);
    }

    private void buildStatusBar() {
        statusLabel.getStyleClass().add("status-label");
        getChildren().add(statusLabel);
    }

    // ── Seat Map Rendering ────────────────────────────────────────────────────

    /**
     * LAB TASK 1 — Render the seat map for the given flight.
     *
     * Instructions:
     *  1. Clear seatGrid.getChildren()
     *  2. Add column headers (A B C  D E F) as Labels in row 0
     *  3. Add row number Labels in column 0
     *  4. For each seat in flight.getSeatMap():
     *       - Create a Button with the seat number as text
     *       - Set background colour based on seat class and occupied status
     *         (use COLOR_* constants above)
     *       - If available: setOnAction -> handleSeatClick(flight, seat)
     *       - If occupied: disable the button
     *       - Add to seatGrid at the correct row/column
     *
     * Hint: Parse the row number and column letter from seat.getSeatNumber()
     *       e.g. "12C" -> row=12, col='C'
     */
    private void renderSeatMap(Flight flight) {
        seatGrid.getChildren().clear();
        statusLabel.setText("");

        // TODO: Add column header labels (A, B, C, gap, D, E, F)

        Map<String, Seat> seats = flight.getSeatMap();

        // TODO: Loop over seats and add coloured buttons to seatGrid
        //       Remember rows 1-2 = First, 3-5 = Business, 6-15 = Economy

        statusLabel.setText("Showing seat map for " + flight.getFlightNumber()
                + " — TODO: implement renderSeatMap()");
    }

    /**
     * LAB TASK 2 — Called when a passenger clicks an available seat.
     *
     * Open buildBookingDialog() and if confirmed, call service.createBooking().
     * On success: show confirmation alert and call renderSeatMap() to refresh.
     * On failure: show error alert with the exception message.
     */
    private void handleSeatClick(Flight flight, Seat seat) {
        // TODO
        statusLabel.setText("Clicked: " + seat.getSeatNumber()
                + " (" + seat.getSeatClass() + ") — £"
                + String.format("%.2f", seat.getBasePrice())
                + " — TODO: open booking dialog");
    }

    /**
     * LAB TASK 3 — Build and show a booking confirmation dialog.
     *
     * The dialog should show:
     *   - Flight details
     *   - Seat number, class, price
     *   - A TextField for the passenger ID (or a ComboBox of passengers)
     *   - Confirm / Cancel buttons
     *
     * Return the passenger ID string if confirmed, null if cancelled.
     *
     * Hint: Use a javafx.scene.control.Dialog<String> or Alert with custom buttons.
     */
    private String buildBookingDialog(Flight flight, Seat seat) {
        // TODO
        return null;
    }
}
