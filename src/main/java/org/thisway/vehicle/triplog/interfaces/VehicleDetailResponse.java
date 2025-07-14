package org.thisway.vehicle.triplog.interfaces;

import org.thisway.vehicle.interfaces.VehicleResponse;
import org.thisway.vehicle.triplog.domain.CurrentDrivingInfo;
import org.thisway.vehicle.triplog.domain.TripLog;
import org.thisway.vehicle.triplog.domain.TripLogInfo;

import java.util.List;

public record VehicleDetailResponse(
        VehicleResponse vehicleResponse,
        CurrentDrivingInfo currentDrivingInfo,
        List<TripLogInfo.TripLogBriefInfo> tripLogBriefInfos
) {

    public static VehicleDetailResponse from(
            VehicleResponse vehicleResponse,
            CurrentDrivingInfo currentDrivingInfo,
            List<TripLog> tripLogs
    ) {
        return new VehicleDetailResponse(
                vehicleResponse,
                currentDrivingInfo,
                tripLogs.stream()
                        .map(TripLogInfo.TripLogBriefInfo::new)
                        .toList()
        );
    }

}
