package org.thisway.vehicle.log.application;

import org.thisway.vehicle.domain.Vehicle;
import org.thisway.vehicle.log.domain.GpsLogData;
import org.thisway.vehicle.log.domain.LogCommand;
import org.thisway.vehicle.log.interfaces.GeofenceLogRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface LogService {
    void savePowerLog(LogCommand.PowerLogSaveInput powerLogSaveInput, Vehicle vehicle);

    void saveGeofenceLog(GeofenceLogRequest request);

    List<GpsLogData> findGpsLogs(Long Id, LocalDateTime start, LocalDateTime end);

    GpsLogData getCurrentGpsLog(Long Id, LocalDateTime start);
}
