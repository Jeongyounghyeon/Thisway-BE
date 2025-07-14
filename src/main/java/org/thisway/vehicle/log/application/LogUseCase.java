package org.thisway.vehicle.log.application;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

public class LogUseCase {

    @Value
    public static class PowerLogInputUseCase {
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
    }
}
