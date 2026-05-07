package com.flightbooking.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Represents a passenger using the booking system.
 *
 * LAB TASK — Implement Payable and complete all TODO sections.
 *
 * OOP Concepts: Encapsulation, Collections (List), Implementing Interfaces
 */
public class Passenger implements Payable {

    // ── Fields ────────────────────────────────────────────────────────────────

    private final String passengerId; // e.g. "PAX-001"
    private String       fullName;
    private String       email;
    private String       passportNumber;

    /**
     * A passenger may hold multiple bookings across different flights.
     * TODO: Use this list in addBooking(), getBookings(), and calculateTotal().
     */
    private final List<Booking> bookings = new ArrayList<>();

    // ── Constructor ───────────────────────────────────────────────────────────

    public Passenger(String passengerId, String fullName, String email, String passportNumber) {
        this.passengerId=passengerId;
        this.fullName=fullName;
        this.email=email;
        this.passportNumber=passportNumber;
    }

    // ── Booking Management ────────────────────────────────────────────────────

    public void addBooking(Booking booking) {
        if (booking==null){
            throw new IllegalArgumentException("Booking cannot be null dumbass");
        }
        bookings.add(booking);
    }

    public boolean cancelBooking(String bookingId) {
        return bookings.removeIf(booking -> booking.getBookingId().equals(bookingId));
    }

    public List<Booking> getBookings() {
        return Collections.unmodifiableList(bookings);
    }

    // ── Payable Implementation ────────────────────────────────────────────────

    @Override
    public double calculateTotal() {
       double total=0.0;
       for (Booking booking : bookings){
           total += booking.calculateTotal();
       }
        return total;
    }

    /**
     * TODO: Return a multi-line receipt listing all bookings and a grand total.
     * Example:
     *   Passenger: Jane Doe (PAX-001)
     *   --------------------------------
     *   Booking #BK001 | LHR → JFK | Economy  | £99.00
     *   Booking #BK003 | LHR → CDG | Business | £349.00
     *   --------------------------------
     *   Grand Total: £448.00
     */
    @Override
    public String getReceipt() {
        String receipt = "";
        receipt += "Passenger: "+fullName+ "("+passengerId+")\n";
        receipt += "----------------------------------------\n";

        for (Booking booking : bookings){
            receipt += booking.getReceipt() + "\n";
        }
        receipt += "---------------------------------------\n";
        receipt += "Grand Total: $" + String.format("%.2f", calculateTotal());

        return receipt;
    }
    // ── Getters & Setters ─────────────────────────────────────────────────────

    public String getPassengerId()    { return passengerId; }
    public String getFullName()       { return fullName; }
    public String getEmail()          { return email; }
    public String getPassportNumber() { return passportNumber; }

    public void setFullName(String fullName){
        this.fullName=fullName;
    }

    public void setEnail(String email){
        if (email == null || !email.contains("@")){
            throw new IllegalArgumentException("Invalid Email Fucking Loser you should KILL YOURSELF FOR IT");
        }
        this.email = email;
    }

    @Override
    public String toString() {
        return fullName + " ("+passengerId+") - " + bookings.size() + " booking(s)";
    }
}
