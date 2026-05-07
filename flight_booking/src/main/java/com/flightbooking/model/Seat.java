package com.flightbooking.model;

/**
 * Abstract base class representing a seat on a flight.
 *
 * LAB TASK — Complete all TODO sections.
 *
 * Subclasses to create:
 *   - EconomySeat
 *   - BusinessSeat
 *   - FirstClassSeat
 *
 * OOP Concepts: Abstraction, Inheritance, Polymorphism, Encapsulation
 */
public abstract class Seat {

    // ── Fields ────────────────────────────────────────────────────────────────

    private final String seatNumber;   // e.g. "12A"
    private boolean      isOccupied;
    private String       passengerName; // null if unoccupied

    // ── Constructor ───────────────────────────────────────────────────────────

    public Seat(String seatNumber) {
        this.seatNumber    = seatNumber;
        this.isOccupied    = false;
        this.passengerName = null;
    }

    // ── Abstract Methods ──────────────────────────────────────────────────────

    /**
     * TODO: Return the base price of this seat type in GBP.
     * Each subclass defines its own pricing.
     */
    public abstract double getBasePrice();

    /**
     * TODO: Return the seat class label, e.g. "Economy", "Business", "First Class".
     */
    public abstract String getSeatClass();

    /**
     * TODO: Return a list of perks for this seat class as a formatted string,
     * e.g. "20kg baggage, meal included, lounge access".
     * Each subclass must override this.
     */
    public abstract String getPerks();

    // ── Concrete Methods ──────────────────────────────────────────────────────

    /**
     * Assigns a passenger to this seat.
     * TODO: Throw an IllegalStateException if the seat is already occupied.
     *
     * @param name the passenger's full name
     */
    public void occupy(String name) {
        if (this.passengerName != null){
            throw new IllegalStateException("Seat already occupied");
        }
        this.passengerName = name;
    }

    /**
     * Frees the seat (cancellation / check-out).
     * TODO: Reset isOccupied and passengerName.
     */
    public void vacate() {
        this.passengerName = null;
    }

    /**
     * TODO: Override toString() to return a useful summary, e.g.:
     *   "[12A] Business — £349.00 (Occupied: John Smith)"
     *   "[14C] Economy  — £129.00 (Available)"
     */
    @Override
    public String toString() {
        // TODO
        return "";
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────

    public String getSeatNumber()    { return seatNumber; }
    public boolean isOccupied()      { return isOccupied; }
    public String getPassengerName() { return passengerName; }

    // TODO: Add any additional getters / setters you need.
    //       Keep ALL fields private — do not change access modifiers above.
}
