package org.thisway.vehicle.log.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thisway.emulator.domain.TemporaryEmulatorService;
import org.thisway.vehicle.domain.Vehicle;
import org.thisway.vehicle.log.domain.LogCommand;
import org.thisway.vehicle.log.interfaces.LogDto;
import org.thisway.vehicle.triplog.domain.TripLogCommand;
import org.thisway.vehicle.triplog.domain.TripLogService;

@Slf4j
@Service
@RequiredArgsConstructor
// Todo: 병합 후 삭제 예정인 클래스입니다. LogFacade와 합쳐질 예정.
public class TemporaryLogFacade {
    private final LogService logService;
    private final TripLogService tripLogService;
    private final TemporaryEmulatorService emulatorService;

    public final void receivePowerLog(LogUseCase.PowerLogInputUseCase request) {
        log.info("시동 정보 로그 수신: MDN={}, onTime={}, offTime={}",
                request.getMdn(), request.getOnTime(), request.getOffTime());

        Vehicle vehicle = emulatorService.getVehicleByMdn(request.getMdn());
        logService.savePowerLog(new LogCommand.PowerLogSaveInput(request, vehicle.getId()), vehicle);
        tripLogService.saveTripLog(new TripLogCommand.TripLogSaveInput(vehicle, request));
    }
}
