package org.thisway.vehicle.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thisway.vehicle.domain.VehicleLogReader;

@Slf4j
@Component
@RequiredArgsConstructor
public class VehicleLogReaderImpl implements VehicleLogReader {
    private final VehicleRepository vehicleRepository;


}
