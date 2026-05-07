package com.flightbooking.model;

/**
 * Represents a First Class seat.
 *
 * LAB TASK — Extend Seat and implement all abstract methods.
 *
 * Pricing guide (you may adjust):
 *   Base price : £799.00
 *   Perks      : 3 checked bags, gourmet dining, flat bed, private suite, chauffeur
 *
 * Extension challenge:
 *   Add a boolean 'privateSuite' field. If true, add a £200 surcharge to getBasePrice().
 *   Update the GUI seat map to visually distinguish suite vs non-suite seats.
 *
 * OOP Concepts: Inheritance, Polymorphism, Encapsulation
 */
public class FirstClassSeat extends Seat {

    public FirstClassSeat(String seatNumber) {
        super(seatNumber);
        // TODO
    }

    @Override
    public double getBasePrice(){
        return 799.00;
    }

    @Override
    public String getSeatClass(){
        return "First Class";
    }

    @Override
    public String getPerks(){
        return "3 checked bags, gourmet dining, flat bed, private suite,\n" +
                "chauffeur";
    }

    @Override
    public String toString() {
        return getSeatNumber() + " | " + getSeatClass() + " | $" + getBasePrice();
    }
}
