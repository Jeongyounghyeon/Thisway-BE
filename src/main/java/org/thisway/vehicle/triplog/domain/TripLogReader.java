package org.thisway.vehicle.triplog.domain;

import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TripLogReader {
    TripLog getTripLog();

    Page<TripLog> findTripLogs();

    TripLog findRecentTripLogByVehicle(Long VehicleId);

    TripLog findTripLogByOnTime(Long vehicleId, LocalDateTime onTime);
}
