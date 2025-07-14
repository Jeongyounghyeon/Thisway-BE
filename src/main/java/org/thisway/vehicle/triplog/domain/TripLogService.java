package org.thisway.vehicle.triplog.domain;

import org.springframework.data.domain.Pageable;
import org.thisway.vehicle.triplog.interfaces.CurrentTripLogResponse;
import org.thisway.vehicle.triplog.interfaces.TripLogDetailResponse;
import org.thisway.vehicle.triplog.interfaces.TripLogDto;
import org.thisway.vehicle.triplog.interfaces.VehicleDetailResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface TripLogService {

    VehicleDetailResponse getVehicleDetails(Long vehicleId);

    CurrentTripLogResponse getCurrentGpsLogs(Long vehicleId, LocalDateTime time);

    TripLogDto.TripLogResponse findTripLogs(Long companyId, Pageable pageable);

    TripLogDetailResponse getTripLogDetails(Long tripId);

    LocalDateTime getLastStartTimeByVehicle(Long vehicleId);

    List<CoordinatesInfo> getGpsLogsInTripLog(Long tripId);

    void saveTripLog(TripLogCommand.TripLogSaveInput input);
}
