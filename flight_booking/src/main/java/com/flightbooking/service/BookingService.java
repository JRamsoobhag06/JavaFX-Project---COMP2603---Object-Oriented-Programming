package com.flightbooking.service;

import com.flightbooking.model.*;

import java.time.LocalDateTime;
import java.util.*;


public class BookingService {

    // ── Singleton ─────────────────────────────────────────────────────────────

    private static BookingService instance;

    private BookingService() {
        seedData();
    }

    public static BookingService getInstance() {
        if (instance == null){
            instance = new BookingService();
        }
        return instance;
    }

    // ── Storage ───────────────────────────────────────────────────────────────

    /**
     * Key = flightNumber, Value = Flight object
     * TODO: Use this map in all flight-related methods below.
     */
    private final Map<String, Flight>    flights    = new HashMap<>();

    /**
     * Key = passengerId, Value = Passenger object
     */
    private final Map<String, Passenger> passengers = new HashMap<>();

    /**
     * Key = bookingId, Value = Booking object
     */
    private final Map<String, Booking>   bookings   = new HashMap<>();

    private int bookingCounter = 1;



    // ── Seed Data ─────────────────────────────────────────────────────────────

    /**
     * Pre-loads sample flights and passengers so the GUI has something to show.
     * TODO: Add at least 2 more flights and 3 more passengers of your own.
     */
    private void seedData() {
        // Sample flights
        flights.put("BA0117", new Flight("BA0117", "LHR", "JFK",
                LocalDateTime.of(2025, 6, 1, 10, 30),
                LocalDateTime.of(2025, 6, 1, 13, 45)));

        flights.put("EK0003", new Flight("EK0003", "LHR", "DXB",
                LocalDateTime.of(2025, 6, 3, 21, 15),
                LocalDateTime.of(2025, 6, 4,  7, 20)));

        // Sample passengers
        passengers.put("PAX-001", new Passenger("PAX-001", "Alice Johnson", "alice@email.com", "GB123456"));
        passengers.put("PAX-002", new Passenger("PAX-002", "Bob Smith",     "bob@email.com",   "GB654321"));

        Flight f1 = new Flight(("BA0117", "LHR", "JFK",
                LocalDateTime.now().plusDays(90));

        Flight f2 = new Flight(("EK0003", "LHR", "DXB",
                LocalDateTime.now().plusDays(60));

        Flight f3 = new Flight(("BA0200", "LHR", "CDG",
                LocalDateTime.now().plusDays(30));

        flights.put(f1.getFlightNumber(), f1);
        flights.put(f2.getFlightNumber(), f2);
        flights.put(f3.getFlightNumber(), f3);

            Passenger p1 = new Passenger("PAX-001", "Jane Doe", "jane@email.com", "P123");
            Passenger p2 = new Passenger("PAX-002", "John Smith", "john@email.com", "P456");

            passengers.put(p1.getPassengerId(), p1);
            passengers.put(p2.getPassengerId(), p2);

            // sample booking
            createBooking("PAX-001", "BA0117", "1A");

    }

    // ── Flight Operations ─────────────────────────────────────────────────────

    /** TODO: Return a list of all flights. */
    public List<Flight> getAllFlights() {
        return new ArrayList<>(flights.values());
    }

    /** TODO: Find a flight by flight number. Return null if not found. */
    public Flight getFlightByNumber(String flightNumber) {
        return flights.get(flightNumber);
    }

    /**
     * TODO: Search flights by origin AND destination (case-insensitive).
     * Return an empty list if none found.
     */
    public List<Flight> searchFlights(String origin, String destination) {
        return flights.values().stream()
                .filter(f -> f.getOrigin().equalsIgnoreCase(origin)
                        && f.getDestination().equalsIgnoreCase(destination))
                .toList();
    }

    // ── Passenger Operations ──────────────────────────────────────────────────

    /** TODO: Return a list of all passengers. */
    public List<Passenger> getAllPassengers() {
        return new ArrayList<>(passengers.values());
    }

    /** TODO: Find a passenger by ID. Return null if not found. */
    public Passenger getPassengerById(String passengerId) {
        return passengers.get(passengerId);
    }

    public Passenger registerPassenger(String fullName, String email, String passport) {
        String id = "PAX-" + String.format("%03d", passengers.size() + 1);
        Passenger p = new Passenger(id, fullName, email, passport);
        passengers.put(id, p);
        return p;
    }

    // ── Booking Operations ────────────────────────────────────────────────────

    /**
     * TODO: Create a booking for the given passenger, flight, and seat number.
     *
     * Steps:
     *  1. Validate that the flight, passenger, and seat exist
     *  2. Check the seat is not already occupied (throw IllegalStateException if it is)
     *  3. Occupy the seat with passenger.getFullName()
     *  4. Generate a bookingId: "BK" + String.format("%03d", bookingCounter++)
     *  5. Create a Booking, add to bookings map, add to passenger's booking list
     *  6. Return the new Booking
     */
    public Booking createBooking(String passengerId, String flightNumber, String seatNumber) {
        Passenger passenger = passengers.get(passengerId);
        Flight flight = flights.get(flightNumber);

        if (passenger == null || flight == null) {
            throw new IllegalArgumentException("Invalid passenger or flight");
        }

        Seat seat = flight.getSeat(seatNumber);

        if (seat == null) {
            throw new IllegalArgumentException("Seat not found");
        }

        if (seat.isOccupied()) {
            throw new IllegalStateException("Seat already taken");
        }

        seat.occupy(passenger.getFullName());

        String bookingId = "BK" + String.format("%03d", bookingCounter++);

        Booking booking = new Booking(bookingId, passenger, flight, seat);

        bookings.put(bookingId, booking);

        passenger.addBooking(booking);

        return booking;
    }

    public boolean cancelBooking(String bookingId) {
        Booking booking = bookings.get(bookingId);

        if (booking == null) return false;

        booking.cancel();
        return true;
    }


    public List<Booking> getBookingsForPassenger(String passengerId) {
        return bookings.values().stream()
                .filter(b -> b.getPassenger().getPassengerId().equals(passengerId))
                .toList();
    }

    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookings.values());
    }
}

