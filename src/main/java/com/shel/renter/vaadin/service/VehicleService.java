package com.shel.renter.vaadin.service;

import com.shel.renter.entity.Vehicle;
import com.shel.renter.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public List<Vehicle> findAllByLotId(Long lotId) {
        return vehicleRepository.findAllByLotId(lotId);
    }

    public void startVehicleRent(Vehicle vehicle) {
        vehicle.setLotId(null);
        vehicleRepository.save(vehicle);
    }

    public void endVehicleRent(Long vehicleId, Long parkingLotId) {
        vehicleRepository.findFirstById(vehicleId).ifPresent(vehicle -> {
            vehicle.setLotId(parkingLotId);
            vehicleRepository.save(vehicle);
        });
    }

    public void endRent(Long vehicleId, Long lotId) {
        Vehicle vehicle = vehicleRepository.findFirstById(vehicleId).orElse(null);
        if (Objects.nonNull(vehicle)) {
            vehicle.setLotId(lotId);
            vehicleRepository.save(vehicle);
        }
    }

}
