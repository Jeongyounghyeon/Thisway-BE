package org.thisway.vehicle.log.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thisway.emulator.domain.Emulator;
import org.thisway.emulator.infrastructure.EmulatorRepository;
import org.thisway.vehicle.log.domain.LogCommand;
import org.thisway.vehicle.log.interfaces.LogDto;
import org.thisway.vehicle.log.util.LogDataConverter;
import org.thisway.vehicle.log.domain.GeofenceLogData;
import org.thisway.vehicle.log.interfaces.GeofenceLogRequest;
import org.thisway.vehicle.log.infrastructure.LogRepository;
import org.thisway.vehicle.triplog.domain.TripLogCommand;
import org.thisway.vehicle.triplog.domain.TripLogServiceImpl;
import org.thisway.vehicle.domain.Vehicle;
import org.thisway.vehicle.application.VehicleService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LogServiceTest {

    private static final String VALID_MDN = "01234567890";
    private static final Long VEHICLE_ID = 1L;

    @Mock
    private LogRepository logRepository;
    @Mock
    private LogDataConverter converter;
    @Mock
    private Emulator emulator;
    @Mock
    private Vehicle vehicle;
    @Mock
    private EmulatorRepository emulatorRepository;
    @Mock
    private VehicleService vehicleService;
    @Mock
    private TripLogServiceImpl tripLogService;
    @InjectMocks
    private LogServiceImpl logService;

    private void setupMocks() {
        when(emulatorRepository.findByMdn(VALID_MDN)).thenReturn(Optional.of(emulator));
        when(emulator.getVehicle()).thenReturn(vehicle);
        when(vehicle.getId()).thenReturn(VEHICLE_ID);
    }

    private LogUseCase.PowerLogInputUseCase createValidPowerLogRequest() {
        return new LogUseCase.PowerLogInputUseCase(
                VALID_MDN,
                "A001",
                "6",
                "5",
                "1",
                "20210901092000",
                "",
                "A",
                "4140338",
                "217403",
                "270",
                "0",
                "10000");
    }

    private LogUseCase.PowerLogInputUseCase createValidPowerLogRequestWithOffTime() {
        return new LogUseCase.PowerLogInputUseCase(
                VALID_MDN,
                "A001",
                "6",
                "5",
                "1",
                "20210901092000",
                "20210901102000",
                "A",
                "4140338",
                "217403",
                "270",
                "0",
                "10000");
    }

    private GeofenceLogRequest createValidGeofenceLogRequest() {
        return new GeofenceLogRequest(
                VALID_MDN,
                "A001",
                "6",
                "5",
                "1",
                "20210901174045",
                "1",
                "1",
                "1",
                "A",
                "4140338",
                "217403",
                "200",
                "0",
                "11000");
    }

    @Nested
    @DisplayName("시동 정보 저장 테스트")
    class SavePowerLogTest {

        @Test
        @DisplayName("유효한 요청 시 시동 로그 저장 성공 테스트")
        void 유효한_요청이라면_시동로그를_저장해야한다() {
            LogUseCase.PowerLogInputUseCase request = createValidPowerLogRequest();
            LogCommand.PowerLogSaveInput input = new LogCommand.PowerLogSaveInput(request, vehicle.getId());
            setupMocks();
            LocalDateTime powerTime = LocalDateTime.of(2021, 9, 1, 9, 20, 0);
            when(converter.convertDateTimeWithSec(anyString())).thenReturn(powerTime);
            when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(vehicle);

            logService.savePowerLog(input, vehicle);
            verify(logRepository).savePowerLog(any(LogCommand.PowerLogSaveInput.class));
            verify(tripLogService).saveTripLog(any(TripLogCommand.TripLogSaveInput.class));
        }

        @Test
        @DisplayName("시동 ON로그 저장시 Vehicle 시동 상태 true 변경테스트")
        void 시동_ON시_Vehicle_powerState가_true로_변경되어야한다() {
            LogUseCase.PowerLogInputUseCase request = createValidPowerLogRequest();
            LogCommand.PowerLogSaveInput input = new LogCommand.PowerLogSaveInput(request, vehicle.getId());
            setupMocks();
            LocalDateTime powerTime = LocalDateTime.of(2021, 9, 1, 9, 20, 0);
            when(converter.convertDateTimeWithSec(anyString())).thenReturn(powerTime);
            when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(vehicle);
            when(vehicle.isPowerOn()).thenReturn(true);

            logService.savePowerLog(input, vehicle);

            verify(vehicle).updatePowerOn(true);
            verify(vehicleService).saveVehicle(vehicle);
            assertThat(vehicle.isPowerOn()).isTrue();
        }

        @Test
        @DisplayName("시동 OFF로그 저장시 Vehicle 시동 상태 false 변경테스트")
        void 시동_OFF시_Vehicle_powerState가_false로_변경되어야한다() {
            LogUseCase.PowerLogInputUseCase request = createValidPowerLogRequestWithOffTime();
            LogCommand.PowerLogSaveInput input = new LogCommand.PowerLogSaveInput(request, vehicle.getId());
            setupMocks();
            LocalDateTime powerTime = LocalDateTime.of(2021, 9, 1, 10, 20, 0);
            when(converter.convertDateTimeWithSec(anyString())).thenReturn(powerTime);
            when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(vehicle);
            when(vehicle.isPowerOn()).thenReturn(false);

            logService.savePowerLog(input, vehicle);

            verify(vehicle).updatePowerOn(false);
            verify(vehicleService).saveVehicle(vehicle);
            assertThat(vehicle.isPowerOn()).isFalse();
        }
    }

    @Nested
    @DisplayName("지오펜스 정보 저장 테스트")
    class SaveGeofenceLogTest {
        @Test
        @DisplayName("유효한 요청 시 지오펜스 로그 저장 성공 테스트")
        void 유효한_요청이라면_지오펜스로그를_저장해야한다() {
            GeofenceLogRequest request = createValidGeofenceLogRequest();
            setupMocks();
            LocalDateTime occurredTime = LocalDateTime.of(2021, 9, 1, 17, 40, 45);
            when(converter.convertDateTimeWithSec(anyString())).thenReturn(occurredTime);

            logService.saveGeofenceLog(request);

            verify(logRepository).saveGeofenceLog(any(GeofenceLogData.class));
        }
    }
}
