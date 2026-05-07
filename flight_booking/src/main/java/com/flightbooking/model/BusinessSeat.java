package com.flightbooking.model;

/**
 * Represents a Business class seat.
 *
 * LAB TASK — Extend Seat and implement all abstract methods.
 *
 * Pricing guide (you may adjust):
 *   Base price : £349.00
 *   Perks      : 2 checked bags, hot meal, priority boarding, lounge access
 *
 * OOP Concepts: Inheritance, Polymorphism
 */
public class BusinessSeat extends Seat {

    public BusinessSeat(String seatNumber) {
        super(seatNumber);
    }

    @Override
    public double getBasePrice(){
        return 349.00;
    }

    @Override
    public String getSeatClass(){
        return "Business";
    }

    @Override
    public String getPerks(){
        return "2 checked bags, hot meal, priority boarding, lounge access";
    }

    @Override
    public String toString() {
        return getSeatNumber() + " | " + getSeatClass() + " | $" + getBasePrice();
    }
}
