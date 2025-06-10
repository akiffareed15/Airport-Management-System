package com.airport.repository;

import com.airport.model.Flight;
import java.util.ArrayList;
import java.util.List;

public class FlightRepository {
    private final List<Flight> flights = new ArrayList<>();

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public List<Flight> getAllFlights() {
        return flights;
    }
}
