package com.shel.renter.repository;

import com.shel.renter.entity.Vehicle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends CrudRepository<Vehicle, Long> {
    List<Vehicle> findAllByLotId(Long lotId);
    Optional<Vehicle> findFirstById(Long lotId);
}
