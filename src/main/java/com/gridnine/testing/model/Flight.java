package com.gridnine.testing.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Flight {
    private final List<Segment> segments;

    public Flight(final List<Segment> segs) {
        segments = segs;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public Duration getLandTime() {
        Duration landTime = Duration.ZERO;

        if (segments.size() > 1) {
            LocalDateTime lastArrivalTime = segments.get(0).getArrivalDate();
            for (Segment segment : segments.stream().skip(1).collect(Collectors.toList())) {
                Duration landTimePart = Duration.between(lastArrivalTime, segment.getDepartureDate());
                landTime = landTime.plus(landTimePart);
                lastArrivalTime = segment.getArrivalDate();
            }
        }

        return landTime;
    }

    @Override
    public String toString() {
        return segments.stream().map(Object::toString)
                .collect(Collectors.joining(" "));
    }
}
