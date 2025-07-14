package org.thisway.vehicle.triplog.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.thisway.vehicle.triplog.domain.TripLog;
import org.thisway.vehicle.triplog.domain.TripLogReader;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TripLogReaderImpl implements TripLogReader {

    private final TripLogRepository tripLogRepository;

    @Override
    public TripLog getTripLog() {
        return null;
    }

    @Override
    public Page<TripLog> findTripLogs() {
        return null;
    }

    @Override
    public TripLog findRecentTripLogByVehicle(Long VehicleId) {
        return null;
    }

    @Override
    public TripLog findTripLogByOnTime(Long vehicleId, LocalDateTime onTime) {
        return tripLogRepository.findByVehicleIdAndStartTime(vehicleId, onTime);
    }
}
