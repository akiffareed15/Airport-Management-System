package com.airport.repository;

import com.airport.model.Booking;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository {
    private final List<Booking> bookings = new ArrayList<>();
    public void add(Booking b)             { bookings.add(b); }
    public List<Booking> getAll()          { return bookings; }
}
