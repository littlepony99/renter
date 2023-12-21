package com.shel.renter.vaadin.history;

import com.shel.renter.entity.RentHistory;
import com.shel.renter.vaadin.service.RentHistoryService;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("/history")
@Tag("history")
public class HistoryComponent extends VerticalLayout {
    //    private final CurrentUserService userService;
    
    private final Grid<RentHistory> grid;
    @Autowired
    private RentHistoryService rentHistoryService;

    public HistoryComponent() {
//                            CurrentUserService userService) {
//        this.rentHistoryService = rentHistoryService;
//        this.userService = userService;
        grid = new Grid<>(RentHistory.class);
        grid.setColumns("vehicleName", "startLotName", "startRentTime", "endRentTime");
        this.add(grid);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        grid.setDataProvider(DataProvider.ofCollection(rentHistoryService.findAllByUserId(1L)));
    }
// rid.setColumns("Start Parking Lot Name", "Start Rent Time", "End Parking Lot Name", "End Rent Time");
}
