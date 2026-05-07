package com.flightbooking.model;
import java.time.LocalDateTime;

/**
 * Represents a single seat booking linking a Passenger to a Seat on a Flight.
 *
 * LAB TASK — Implement Payable and complete all TODO sections.
 *
 * OOP Concepts: Encapsulation, Implementing Interfaces, Composition
 */
public class Booking implements Payable {

    // ── Fields ────────────────────────────────────────────────────────────────

    private final String        bookingId;
    private final Passenger     passenger;
    private final Flight        flight;
    private final Seat          seat;
    private final LocalDateTime bookingTime;
    private       BookingStatus status;

    // ── Booking Status Enum ───────────────────────────────────────────────────

    /**
     * TODO: Add a WAITLISTED status and handle it in BookingService.
     */
    public enum BookingStatus {
        CONFIRMED,
        CANCELLED,
        CHECKED_IN
        // TODO: WAITLISTED
    }

    // ── Constructor ───────────────────────────────────────────────────────────

    public Booking(String bookingId, Passenger passenger, Flight flight, Seat seat) {
        this.bookingId   = bookingId;
        this.passenger   = passenger;
        this.flight      = flight;
        this.seat        = seat;
        this.bookingTime = LocalDateTime.now();
        this.status      = BookingStatus.CONFIRMED;
    }

    // ── Payable Implementation ────────────────────────────────────────────────

    /**
     * TODO: Return the seat's base price.
     * Extension: Apply a 10% discount if the booking is made more than 60 days before departure.
     */
    @Override
    public double calculateTotal() {
        double price = seat.getBasePrice();
        return price;
    }

    /**
     * TODO: Return a single receipt line, e.g.:
     *   "Booking #BK001 | LHR → JFK | Economy [12A] | £99.00 | CONFIRMED"
     */
    @Override
    public String getReceipt(){
        return "Booking #"+bookingId+" | "+flight.getOrigin() + " -> "+flight.getDestination()+" | "
                +seat.getSeatClass()+" ["+seat.getSeatNumber()+"] | $" + String.format("%.2f", calculateTotal())+
                " | " + status;
    }

    // ── Status Management ─────────────────────────────────────────────────────
    public void cancel() {
        if (status == BookingStatus.CANCELLED){
            throw new IllegalStateException("Booking already cancelled");
        }
        status = BookingStatus.CANCELLED;
        seat.vacate();
    }

    public void checkIn() {
        if (status!= BookingStatus.CONFIRMED){
            throw new IllegalStateException("Cannot check in unless CONFIRMED DUMBASS WHORE");
        }
        status = BookingStatus.CHECKED_IN;
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public String        getBookingId()   { return bookingId; }
    public Passenger     getPassenger()   { return passenger; }
    public Flight        getFlight()      { return flight; }
    public Seat          getSeat()        { return seat; }
    public LocalDateTime getBookingTime() { return bookingTime; }
    public BookingStatus getStatus()      { return status; }

    @Override
    public String toString() {
        return getReceipt();
    }
}
