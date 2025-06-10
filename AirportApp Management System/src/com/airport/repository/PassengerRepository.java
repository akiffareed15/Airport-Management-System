package com.airport.repository;

import com.airport.model.Passenger;
import java.util.ArrayList;
import java.util.List;

public class PassengerRepository {
    private final List<Passenger> passengers = new ArrayList<>();
    public void add(Passenger p)           { passengers.add(p); }
    public List<Passenger> getAll()        { return passengers; }
}
