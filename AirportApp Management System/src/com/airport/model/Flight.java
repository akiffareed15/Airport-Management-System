package com.airport.model;

public class Flight {
    private String flightNumber;
    private String origin;
    private String destination;

    public Flight(String flightNumber, String origin, String destination) {
        this.flightNumber = flightNumber;
        this.origin       = origin;
        this.destination  = destination;
    }

    public String getFlightNumber()
    { 
    	return flightNumber;
    }
    public String getOrigin() 
    {
    	return origin;
    }
    public String getDestination()
    {
    	return destination;
    }

    public void setFlightNumber(String flightNumber)
    {
        this.flightNumber = flightNumber;
    }
    public void setOrigin(String origin)
    {
        this.origin = origin;
    }

    public void setDestination(String destination) 
    {
        this.destination = destination;
    }
}
