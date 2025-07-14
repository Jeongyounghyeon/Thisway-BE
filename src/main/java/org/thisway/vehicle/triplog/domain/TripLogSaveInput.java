package org.thisway.vehicle.triplog.domain;

import org.thisway.vehicle.domain.Vehicle;
import org.thisway.vehicle.log.interfaces.LogDto;
import org.thisway.vehicle.log.util.LogDataConverter;

import java.time.LocalDateTime;


public record TripLogSaveInput(
        Vehicle vehicle,
        String mdn,
        LocalDateTime onTime,
        LocalDateTime offTime,
        Double latitude,
        Double longitude,
        Integer totalTripMeter
) {
    public static TripLogSaveInput from(
            Vehicle vehicle,
            LogDto.PowerLogRequest request,
            LogDataConverter converter
    ) {
        return new TripLogSaveInput(
                vehicle,
                request.getMdn(),
                converter.convertDateTimeWithSec(request.getOnTime()),
                converter.convertDateTimeWithSec(request.getOffTime()),
                converter.convertCoordinate(request.getLat()),
                converter.convertCoordinate(request.getLon()),
                converter.convertToInteger(request.getSum())
        );
    }
}
