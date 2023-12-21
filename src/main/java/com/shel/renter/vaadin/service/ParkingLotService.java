package com.shel.renter.vaadin.service;

import com.shel.renter.entity.ParkingLot;
import com.shel.renter.repository.ParkingLotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingLotService {
    private final ParkingLotRepository parkingLotRepository;

    public List<ParkingLot> findAllByCity(String city) {
        return parkingLotRepository.findAllByCityAndIsActiveTrue(city);
    }

    public List<ParkingLot> findAll() {
        List<ParkingLot> result = new ArrayList<>();

        Iterable<ParkingLot> allLots = parkingLotRepository.findAll();
        allLots.forEach(result::add);
        return result;
    }
}
