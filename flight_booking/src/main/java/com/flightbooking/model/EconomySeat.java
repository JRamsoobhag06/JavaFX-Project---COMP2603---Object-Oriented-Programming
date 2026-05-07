package com.flightbooking.model;

/**
 * Represents an Economy class seat.
 *
 * LAB TASK — Extend Seat and implement all abstract methods.
 *
 * Pricing guide (you may adjust):
 *   Base price : £99.00
 *   Perks      : 1 carry-on bag, complimentary snack
 *
 * OOP Concepts: Inheritance, Polymorphism
 */
public class EconomySeat extends Seat{
    // ── Constructor ───────────────────────────────────────────────────────────

    public EconomySeat(String seatNumber) {
        super(seatNumber);
        // TODO: Initialise any extra fields
    }

    @Override
    public double getBasePrice(){
        return 99.00;
    }

    @Override
    public String getSeatClass(){
        return "Economy";
    }

    @Override
    public String getPerks(){
        return "1 carry-on, complimentary snack";
    }

    @Override
    public String toString() {
        return getSeatNumber() + " | " + getSeatClass() + " | $" + getBasePrice();
    }
}

