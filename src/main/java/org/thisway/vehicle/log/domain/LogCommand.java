package org.thisway.vehicle.log.domain;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.thisway.vehicle.log.application.LogUseCase;
import org.thisway.vehicle.log.util.LogDataConverter;

import java.time.LocalDateTime;

public class LogCommand {

    private static final LogDataConverter converter = new LogDataConverter();

    @Value
    @AllArgsConstructor
    public static class PowerLogSaveInput {
        String mdn;
        Long vehicleId;
        boolean powerStatus;
        LocalDateTime powerTime;
        GpsStatus gpsStatus;
        Double latitude;
        Double longitude;
        Integer totalTripMeter;

        public PowerLogSaveInput(LogUseCase.PowerLogInputUseCase request, Long vehicleId) {
            this.mdn = request.getMdn();
            this.vehicleId = vehicleId;
            this.powerStatus = IsOn(request);
            this.powerTime = converter.convertDateTimeWithSec(
                    powerStatus ? request.getOnTime() : request.getOffTime()
            );
            this.gpsStatus = converter.convertToGpsStatus(request.getGcd());
            this.latitude = converter.convertCoordinate(request.getLat());
            this.longitude = converter.convertCoordinate(request.getLon());
            this.totalTripMeter = converter.convertToInteger(request.getSum());
        }

        private Boolean IsOn(LogUseCase.PowerLogInputUseCase request) {
            if (request.getOffTime() != null && !request.getOffTime().isEmpty()) {
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        }
    }
}
