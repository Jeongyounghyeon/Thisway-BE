package org.thisway.vehicle.triplog.domain;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.Optional;

public class TripLogInfo {

    @Value
    public static class TripLogBriefInfo {
        Long Id;
        Long vehicleId;
        String carNumber;
        LocalDateTime startTime;
        LocalDateTime endTime;
        Integer tripMeter;
        String address;

        public TripLogBriefInfo(TripLog tripLog) {
            this.Id = tripLog.getId();
            this.vehicleId = tripLog.getVehicle().getId();
            this.carNumber = tripLog.getVehicle().getCarNumber();
            this.startTime = tripLog.getStartTime();
            this.endTime = tripLog.getEndTime();
            this.tripMeter = tripLog.getTotalTripMeter();
            this.address = Optional.ofNullable(tripLog.getOnAddr()).orElse("")
                    + Optional.ofNullable(tripLog.getOnAddrDetail()).orElse("");
        }
    }


}
