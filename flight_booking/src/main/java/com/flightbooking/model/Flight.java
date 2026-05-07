package com.flightbooking.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Flight {

    // ── Fields ────────────────────────────────────────────────────────────────

    private final String        flightNumber;  // e.g. "BA0117"
    private final String        origin;        // IATA code, e.g. "LHR"
    private final String        destination;   // IATA code, e.g. "JFK"
    private       LocalDateTime departureTime;
    private       LocalDateTime arrivalTime;


    private final Map<String, Seat> seatMap = new HashMap<>();

    // ── Constructor ───────────────────────────────────────────────────────────

    public Flight(String flightNumber, String origin, String destination,
                  LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.flightNumber  = flightNumber;
        this.origin        = origin;
        this.destination   = destination;
        this.departureTime = departureTime;
        this.arrivalTime   = arrivalTime;
        initialiseSeatMap();
    }

    // ── Seat Map Setup ────────────────────────────────────────────────────────

    private void initialiseSeatMap() {
        String[] fcCols = {"A", "B", "C", "D"};
        for (int row = 1; row <= 2; row++) {
            for (String col : fcCols) {
                String num = row + col;
                seatMap.put(num, new FirstClassSeat(num));
            }
        }
        for (int row = 3; row <= 5; row++) {
            for (String col : fcCols) {
                String num = row + col;
                seatMap.put(num, new BusinessSeat(num));
            }
        }
        for (int row = 6; row <=15; row++) {
            for (String col : fcCols) {
                String num = row + col;
                seatMap.put(num, new EconomySeat(num));
            }
        }
    }

    // ── Seat Operations ───────────────────────────────────────────────────────

    public Seat getSeat(String seatNumber) {
        return seatMap.get(seatNumber);
    }

    public List<Seat> getAvailableSeats() {
        return seatMap.values().stream().filter(s-> s.isOccupied()).toList();
    }

    public List<Seat> getAvailableSeatsByClass(String seatClass) {
        return getAvailableSeats().stream().filter(s->s.getSeatClass().equalsIgnoreCase(seatClass)).toList();
    }
    
    public Map<String, Long> getAvailabilityByClass() {
        return getAvailableSeats().stream().collect(Collectors.groupingBy(
                Seat::getSeatClass, Collectors.counting()
        ));
    }

    public boolean hasAvailability() {
        return !getAvailableSeats().isEmpty();
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public String        getFlightNumber()  { return flightNumber; }
    public String        getOrigin()        { return origin; }
    public String        getDestination()   { return destination; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public LocalDateTime getArrivalTime()   { return arrivalTime; }
    public Map<String, Seat> getSeatMap()   { return seatMap; }

    @Override
    public String toString() {
        return flightNumber + " | "+origin+" -> "+destination+" | Dep: "+departureTime;
    }
}
