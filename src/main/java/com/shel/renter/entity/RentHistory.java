package com.shel.renter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String vehicleName;
    private String startLotName;
    private String endLotName;
    private LocalDateTime startRentTime;
    private LocalDateTime endRentTime;
    private Long customerId;
}
