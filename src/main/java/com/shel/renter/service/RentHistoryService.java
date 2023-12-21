package com.shel.renter.service;

import com.shel.renter.entity.RentHistory;
import com.shel.renter.repository.RentHistoryRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.RequiredArgsConstructor;
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

    public void finishRent() {

    }
}
