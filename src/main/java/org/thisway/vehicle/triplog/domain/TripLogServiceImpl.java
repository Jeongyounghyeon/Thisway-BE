package org.thisway.vehicle.triplog.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thisway.support.common.CustomException;
import org.thisway.support.common.ErrorCode;
import org.thisway.vehicle.application.VehicleService;
import org.thisway.vehicle.interfaces.VehicleResponse;
import org.thisway.vehicle.log.application.LogService;
import org.thisway.vehicle.log.domain.GpsLogData;
import org.thisway.vehicle.triplog.infrastructure.TripLogRepository;
import org.thisway.vehicle.triplog.interfaces.CurrentTripLogResponse;
import org.thisway.vehicle.triplog.interfaces.TripLogDetailResponse;
import org.thisway.vehicle.triplog.interfaces.TripLogDto;
import org.thisway.vehicle.triplog.interfaces.VehicleDetailResponse;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class TripLogServiceImpl implements TripLogService {

    private final VehicleService vehicleService;
    private final LogService logService;
    private final TripLogRepository tripLogRepository;

    private final TripLogStore tripLogStore;
    private final TripLogReader tripLogReader;

    public TripLogServiceImpl(
            VehicleService vehicleService,
            @Lazy LogService logService,
            TripLogRepository tripLogRepository,
            TripLogStore tripLogStore,
            TripLogReader tripLogReader
    ) {
        this.vehicleService = vehicleService;
        this.logService = logService;
        this.tripLogRepository = tripLogRepository;
        this.tripLogStore = tripLogStore;
        this.tripLogReader = tripLogReader;
    }

    @Override
    public VehicleDetailResponse getVehicleDetails(Long vehicleId) {
        // 1. vehicleId로 vehicle에 대한 정보 가져오기
        // 2. vehicleId로 현재 운행 정보와
        VehicleResponse vehicleResponse = vehicleService.getVehicleDetail(vehicleId);
        List<TripLog> tripLogs = tripLogRepository.findTop6ByVehicleIdOrderByStartTimeDesc(vehicleId);
        CurrentDrivingInfo currentDrivingInfo = null;

        if (!tripLogs.isEmpty() && vehicleResponse.powerOn()) {
            currentDrivingInfo = CurrentDrivingInfo.from(
                    tripLogs.getFirst(),
                    logService.getCurrentGpsLog(vehicleId, tripLogs.getFirst().getStartTime())
            );

            tripLogs.removeFirst();
        }

        return VehicleDetailResponse.from(
                vehicleService.getVehicleDetail(vehicleId),
                currentDrivingInfo,
                tripLogs
        );
    }

    @Override
    public CurrentTripLogResponse getCurrentGpsLogs(Long vehicleId, LocalDateTime time) {
        if (vehicleService.getVehiclePowerState(vehicleId)) {
            List<GpsLogData> gpsLogs = logService.findGpsLogs(vehicleId, time, LocalDateTime.now(ZoneId.of("Asia/Seoul")));

            if (!gpsLogs.isEmpty()) {
                return CurrentTripLogResponse.from(gpsLogs);
            } else {
                return null;
            }
        } else {
            throw new CustomException(ErrorCode.VEHICLE_POWER_OFF);
        }
    }

    @Override
    public TripLogDto.TripLogResponse findTripLogs(Long companyId, Pageable pageable) {
        Page<TripLog> TripLogs = tripLogRepository.findAllByCompanyAndActiveTrueOrderByStartTimeDesc(companyId, pageable);

        return new TripLogDto.TripLogResponse(TripLogs);
    }

    @Override
    public TripLogDetailResponse getTripLogDetails(Long tripId) {
        TripLog tripLog = tripLogRepository.findById(tripId).orElseThrow(() -> new CustomException(ErrorCode.TRIP_LOG_NOT_FOUND));
        List<GpsLogData> gpsLogs = logService.findGpsLogs(tripLog.getVehicle().getId(), tripLog.getStartTime(), tripLog.getEndTime());

        return TripLogDetailResponse.from(
                tripLog.getVehicle(),
                tripLog,
                gpsLogs.stream().mapToInt(GpsLogData::speed).average().orElse(0)
        );
    }

    @Override
    public LocalDateTime getLastStartTimeByVehicle(Long vehicleId) {
        return tripLogRepository.findTop1StartTimeByVehicleId(vehicleId);
    }

    @Override
    public List<CoordinatesInfo> getGpsLogsInTripLog(Long tripId) {
        TripLog tripLog = tripLogRepository.findById(tripId)
                .orElseThrow(() -> new CustomException(ErrorCode.TRIP_LOG_NOT_FOUND));
        return logService.findGpsLogs(
                tripLog.getVehicle().getId(),
                tripLog.getStartTime(),
                tripLog.getEndTime()
        ).stream().map(CoordinatesInfo::from).toList();
    }

    @Override
    public void saveTripLog(TripLogCommand.TripLogSaveInput input) {
        if (input.getOffTime() == null) {
            tripLogStore.storeTripLog(Boolean.FALSE, input);
        } else {
            TripLog tripLog = tripLogReader.findTripLogByOnTime(input.getVehicle().getId(), input.getOnTime());

            if (tripLog == null) {
                tripLogStore.storeTripLog(Boolean.TRUE, input);
                return;
            }

            tripLogStore.updateTripLog(tripLog, input);
        }
    }

}
