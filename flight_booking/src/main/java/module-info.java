module com.flightbooking.flight_booking {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.flightbooking.flight_booking to javafx.fxml;
    exports com.flightbooking.ui;
}