package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FlightFilteringService {
    private List<Predicate<Flight>> rules = new ArrayList<>();

    public FlightFilteringService addRule(Predicate<Flight> rule) {
        this.rules.add(rule);
        return this;
    }

    public void setRules(List<Predicate<Flight>> rules) {
        this.rules = rules;
    }

    public void clearRules() {
        rules.clear();
    }

    public List<Flight> filterFlights(List<Flight> flights) {
        return flights.parallelStream()
                .filter(this::checkFlight)
                .collect(Collectors.toList());
    }

    private boolean checkFlight(Flight flight) {
        return rules.parallelStream()
                .map(rule -> rule.test(flight))
                .reduce((x, y) -> x && y)
                .orElse(Boolean.FALSE);
    }
}
