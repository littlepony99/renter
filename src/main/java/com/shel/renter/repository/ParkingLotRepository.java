package com.shel.renter.repository;

import com.shel.renter.entity.ParkingLot;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParkingLotRepository extends CrudRepository<ParkingLot, Long> {
    List<ParkingLot> findAllByCityAndIsActiveTrue(String city);
}
