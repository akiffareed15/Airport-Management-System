package com.airport.model;

public class Booking {
    private final Flight flight;
    private final Passenger passenger;

    public Booking(Flight flight, Passenger passenger) 
    {
        this.flight    = flight;
        this.passenger = passenger;
    }

    public Flight getFlight()        
    {
    	return flight;
    }
    public Passenger getPassenger() 
    {
    	return passenger; 
    }
}
