package com.gridnine.testing.service;

import com.gridnine.testing.builder.FlightBuilder;
import com.gridnine.testing.builder.RuleBuilder;
import com.gridnine.testing.model.Flight;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlightFilteringServiceTest {
    private final FlightFilteringService filteringService = new FlightFilteringService();
    private final List<Flight> flights = FlightBuilder.createFlights();

    @AfterEach
    public void afterEach() {
        filteringService.clearRules();
    }

    @Test
    public void checkDepartureTimeAfterNowPredicate() {
        filteringService.addRule(RuleBuilder.DepartureTimeAfterNowPredicate());
        List<Flight> filteredFlights = filteringService.filterFlights(flights);

        assertEquals(5, filteredFlights.size());
    }

    @Test
    public void checkArriveNotBeforeDeparturePredicate() {
        filteringService.addRule(RuleBuilder.ArriveNotBeforeDeparturePredicate());
        List<Flight> filteredFlights = filteringService.filterFlights(flights);

        assertEquals(5, filteredFlights.size());
    }

    @Test
    public void checkLandTimeLess2HoursPredicate() {
        filteringService.addRule(RuleBuilder.LandTimeLess2HoursPredicate());
        List<Flight> filteredFlights = filteringService.filterFlights(flights);

        assertEquals(4, filteredFlights.size());
    }

    @Test
    public void checkAllPredicates() {
        filteringService
                .addRule(RuleBuilder.DepartureTimeAfterNowPredicate())
                .addRule(RuleBuilder.ArriveNotBeforeDeparturePredicate())
                .addRule(RuleBuilder.LandTimeLess2HoursPredicate());
        List<Flight> filteredFlights = filteringService.filterFlights(flights);

        assertEquals(2, filteredFlights.size());
    }
}
