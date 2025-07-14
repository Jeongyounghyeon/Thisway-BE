package org.thisway.vehicle.triplog.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thisway.vehicle.triplog.domain.*;
import org.thisway.vehicle.triplog.util.ReverseGeocodeResult;
import org.thisway.vehicle.triplog.util.ReverseGeocodingConverter;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class TripLogStoreImpl implements TripLogStore {

    private final TripLogRepository tripLogRepository;
    private final ReverseGeocodingConverter reverseGeocodingConverter;

    @Override
    public void storeTripLog(Boolean status, TripLogCommand.TripLogSaveInput input) {
        TripLog tripLog;
        ReverseGeocodeResult address = reverseGeocodingConverter.convertToAddress(input.getLatitude(), input.getLongitude());

        tripLog = TripLog.builder()
                .vehicle(input.getVehicle())
                .startTime(input.getOnTime())
                .totalTripMeter(input.getTotalTripMeter())
                .onLatitude(input.getLatitude())
                .onLongitude(input.getLongitude())
                .onAddress(address.addr())
                .onAddrDetail(address.addrDetail())
                .active(status)
                .build();
        tripLogRepository.save(tripLog);

        log.info("운행 기록 저장 : MDN={}, onTime={}, offTime={}", input.getMdn(), input.getOnTime(), input.getOffTime());
    }

    @Override
    public void updateTripLog(TripLog tripLog, TripLogCommand.TripLogSaveInput input) {
        ReverseGeocodeResult address = reverseGeocodingConverter.convertToAddress(input.getLatitude(), input.getLongitude());

        tripLog.finishTrip(
                input.getOffTime(),
                input.getTotalTripMeter(),
                input.getLatitude(),
                input.getLongitude(),
                address.addr(),
                address.addrDetail()
        );

        tripLogRepository.save(tripLog);
    }
}
