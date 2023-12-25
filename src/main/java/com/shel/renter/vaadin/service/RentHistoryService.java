package com.shel.renter.vaadin.service;

import com.shel.renter.entity.ParkingLot;
import com.shel.renter.entity.RentHistory;
import com.shel.renter.repository.RentHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentHistoryService {
    @Autowired
    private RentHistoryRepository rentHistoryRepository;

    public List<RentHistory> findAllByUserId(Long userId) {
        return rentHistoryRepository.findAllByCustomerId(userId);
    }

    public RentHistory findActiveRent(Long userId) {
        return rentHistoryRepository.findFirstByCustomerIdAndEndLotNameIsNull(userId);
    }

    public RentHistory saveRent(RentHistory rentHistory) {
        return rentHistoryRepository.save(rentHistory);
    }
}
