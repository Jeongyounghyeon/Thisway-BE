package org.thisway.vehicle.log.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;
import org.thisway.vehicle.log.application.LogUseCase;

public class LogDto {

    @Value
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PowerLogRequest {
        String mdn;
        String tid;
        String mid;
        String pv;
        String did;
        String onTime;
        String offTime;
        String gcd;
        String lat;
        String lon;
        String ang;
        String spd;
        String sum;

        public LogUseCase.PowerLogInputUseCase toUseCase() {
            return new LogUseCase.PowerLogInputUseCase(
                    mdn, tid, mid, pv, did, onTime, offTime, gcd, lat, lon, ang, spd, sum
            );
        }
    }
}
