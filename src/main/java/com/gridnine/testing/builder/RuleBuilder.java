package com.gridnine.testing.builder;

import com.gridnine.testing.model.Flight;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Predicate;

public class RuleBuilder {
    public static Predicate<Flight> DepartureTimeAfterNowPredicate() {
        return flight -> flight.getSegments().stream()
                .anyMatch(segment ->
                        !segment.getDepartureDate().isBefore(LocalDateTime.now()));
    }

    public static Predicate<Flight> ArriveNotBeforeDeparturePredicate() {
        return flight -> flight.getSegments().stream()
                .anyMatch(segment ->
                        !segment.getArrivalDate().isBefore(segment.getDepartureDate()));
    }

    public static Predicate<Flight> LandTimeLess2HoursPredicate() {
        Duration critical = Duration.ofHours(2);
        return flight -> flight.getLandTime().compareTo(critical) <= 0;
    }
}
