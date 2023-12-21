package com.shel.renter.service;

import com.shel.renter.entity.CarParking;
import com.shel.renter.entity.ParkingLot;
import com.shel.renter.entity.Vehicle;
import com.shel.renter.repository.CarParkingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarParkingService {
    private final CarParkingRepository carParkingRepository;

    public List<Vehicle> getVehicleListByParkingLotId(Long parkingLotId) {
        List<CarParking> allByParkingLot = carParkingRepository.findAllByParkingLot(ParkingLot.builder().id(parkingLotId).build());
        return allByParkingLot.stream()
                .map(CarParking::getVehicle)
                .collect(Collectors.toList());
    }


}
