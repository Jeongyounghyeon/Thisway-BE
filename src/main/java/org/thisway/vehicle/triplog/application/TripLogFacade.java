package org.thisway.vehicle.triplog.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.thisway.vehicle.triplog.domain.StreamCoordinatesService;
import org.thisway.vehicle.triplog.domain.TripLogService;
import org.thisway.vehicle.triplog.interfaces.TripLogDetailResponse;
import org.thisway.vehicle.triplog.interfaces.TripLogDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class TripLogFacade {
    private final TripLogService tripLogService;
    private final StreamCoordinatesService streamCoordinatesService;

    public TripLogDto.TripLogResponse findTripLogs(Long companyId, Pageable pageable) {
        return null;
    }

    public TripLogDetailResponse getTripLogDetails(Long tripId){
        return null;
    }

    public SseEmitter createStreamForTripLog(Long vehicleId) {
        return null;
    }

}
