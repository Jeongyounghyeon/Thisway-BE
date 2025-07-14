package org.thisway.vehicle.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thisway.vehicle.domain.TemporaryVehicleStore;
import org.thisway.vehicle.domain.Vehicle;

@Slf4j
@Component
@RequiredArgsConstructor
// Todo : 추후 삭제될 임시 클래스 VehicleStoreImpl과 병합 예정.
public class TemporaryVehicleStoreImpl implements TemporaryVehicleStore {
    private final VehicleRepository vehicleRepository;

    @Override
    public void saveVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }
}
