package com.shel.renter.repository;

import com.shel.renter.entity.CarParking;
import com.shel.renter.entity.ParkingLot;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarParkingRepository extends CrudRepository<CarParking, Long> {
    List<CarParking> findAllByParkingLot(ParkingLot parkingLotId);
}
