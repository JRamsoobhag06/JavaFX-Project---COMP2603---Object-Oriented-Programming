package com.flightbooking.model;

/**
 * LAB TASK — Implement this interface in any class that can generate a payment charge.
 *
 * Students must implement this interface in:
 *   - Booking  (total fare for that booking)
 *   - Passenger (sum of all their bookings)
 *
 * TODO: Do NOT modify this interface.
 */
public interface Payable {

    /**
     * Returns the total amount due in GBP (£).
     * @return amount as a double
     */
    double calculateTotal();

    /**
     * Returns a formatted receipt string, e.g.:
     *   "Booking #BK001 | LHR → JFK | Economy | £249.99"
     * @return human-readable receipt line
     */
    String getReceipt();
}
