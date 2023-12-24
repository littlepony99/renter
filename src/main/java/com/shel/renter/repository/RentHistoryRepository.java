package com.shel.renter.repository;

import com.shel.renter.entity.RentHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentHistoryRepository extends CrudRepository<RentHistory, Long> {
    List<RentHistory> findAllByCustomerId(Long customer);
    RentHistory findFirstByCustomerIdAndEndLotNameIsNull(Long customerId);
}
