package com.shel.renter.vaadin.parking;

import com.shel.renter.entity.ParkingLot;
import com.shel.renter.vaadin.service.ParkingLotService;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Tag("parking")
@Route("/parking")
public class ParkingLotComponent extends VerticalLayout {
    private List<Consumer<ParkingLot>> listeners = new ArrayList<>();
    private Button historyBtn;
    private Button logOutBtn;
    private Button rentBtn;
    private ParkingLotService parkingLotService;
}
