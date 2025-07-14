package org.thisway.vehicle.triplog.domain;

import lombok.Value;
import org.thisway.vehicle.domain.Vehicle;
import org.thisway.vehicle.log.application.LogUseCase;
import org.thisway.vehicle.log.interfaces.LogDto;
import org.thisway.vehicle.log.util.LogDataConverter;

import java.time.LocalDateTime;

public class TripLogCommand {
    private static final LogDataConverter converter = new LogDataConverter();

    @Value
    public static class TripLogSaveInput {
        Vehicle vehicle;
        String mdn;
        LocalDateTime onTime;
        LocalDateTime offTime;
        Double latitude;
        Double longitude;
        Integer totalTripMeter;

        public TripLogSaveInput(Vehicle vehicle, LogUseCase.PowerLogInputUseCase request) {
            this.vehicle = vehicle;
            this.mdn = request.getMdn();
            this.onTime = converter.convertDateTimeWithSec(request.getOnTime());
            this.offTime = converter.convertDateTimeWithSec(request.getOffTime());
            this.latitude = converter.convertCoordinate(request.getLat());
            this.longitude = converter.convertCoordinate(request.getLon());
            this.totalTripMeter = converter.convertToInteger(request.getSum());
        }
    }
}
