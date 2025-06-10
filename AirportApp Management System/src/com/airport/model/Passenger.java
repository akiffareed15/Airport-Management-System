package com.airport.model;

public class Passenger {
    private static int nextId = 1;
    private final int id;
    private final String name;
    private final String contact;
    private String seatNumber;
    private String passportNumber;
    private String specialRequirements;

    public Passenger(String name, String contact, String passportNumber) {
        this.id = nextId++;
        this.name = name;
        this.contact = contact;
        this.passportNumber = passportNumber;
        this.specialRequirements = "";
    }

    public int getId()     
    {
    	return id;
    }
    public String getName()
    {
    	return name; 
    }
    public String getContact()
    {
    	return contact;
    }
    public String getSeatNumber() 
    {
    	return seatNumber;
    }
    public String getPassportNumber() 
    {
    	return passportNumber; 
    }
    public String getSpecialRequirements() 
    {
    	return specialRequirements; 
    }
    public void setSeatNumber(String seatNumber) 
    {
        this.seatNumber = seatNumber;
    }

    public void setSpecialRequirements(String specialRequirements) 
    {
        this.specialRequirements = specialRequirements;
    }

    @Override
    public String toString()
    {
        return name + " (Seat: " + (seatNumber != null ? seatNumber : "Not Assigned") + ")";
    }
}
