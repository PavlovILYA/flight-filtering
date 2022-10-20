package com.gridnine.testing;

import com.gridnine.testing.builder.FlightBuilder;
import com.gridnine.testing.builder.RuleBuilder;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.service.FlightFilteringService;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FlightFilteringService filteringService = new FlightFilteringService();

        List<Flight> flights = FlightBuilder.createFlights();

        System.out.println("All flights:");
        flights.forEach(System.out::println);
        System.out.println("---");

        System.out.println("1. Flights departing before the current time ("
                + LocalDateTime.now() + ") are excluded");
        filteringService.addRule(RuleBuilder.DepartureTimeAfterNowPredicate());
        filteringService.filterFlights(flights).forEach(System.out::println);
        filteringService.clearRules();

        System.out.println("2. Flights that have segments with an arrival date earlier " +
                "than the departure date are excluded:");
        filteringService.addRule(RuleBuilder.ArriveNotBeforeDeparturePredicate());
        filteringService.filterFlights(flights).forEach(System.out::println);
        filteringService.clearRules();

        System.out.println("3. Flights with a total time on the ground exceeding two hours are excluded:");
        filteringService.addRule(RuleBuilder.LandTimeLess2HoursPredicate());
        filteringService.filterFlights(flights).forEach(System.out::println);
        filteringService.clearRules();
    }
}
