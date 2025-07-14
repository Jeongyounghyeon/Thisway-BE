package org.thisway.emulator.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thisway.emulator.infrastructure.EmulatorRepository;
import org.thisway.support.common.CustomException;
import org.thisway.support.common.ErrorCode;
import org.thisway.vehicle.domain.Vehicle;

@Slf4j
@Service
@RequiredArgsConstructor
// Todo: 병합 후 삭제 예정인 클래스입니다. EmulatorService와 합쳐질 예정.
public class TemporaryEmulatorService {
    private final EmulatorRepository emulatorRepository;

    public Vehicle getVehicleByMdn(String mdn){
        Emulator emulator = emulatorRepository.findByMdn(mdn)
                .orElseThrow(() -> new CustomException(ErrorCode.EMULATOR_NOT_FOUND));
        return emulator.getVehicle();
    }

}
