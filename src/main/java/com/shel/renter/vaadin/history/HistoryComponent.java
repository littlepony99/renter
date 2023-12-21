package com.shel.renter.vaadin.history;

import com.shel.renter.entity.RentHistory;
import com.shel.renter.service.RentHistoryService;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("/history")
public class HistoryComponent extends Component {
    //    private final CurrentUserService userService;

    private final RentHistoryService rentHistoryService;
    private final Grid<RentHistory> grid;
    @Autowired
    public HistoryComponent(RentHistoryService rentHistoryService) {
//                            CurrentUserService userService) {
        this.rentHistoryService = rentHistoryService;
//        this.userService = userService;
        grid = new Grid<>(RentHistory.class);
        grid.setColumns("vehicleName", "startLotId", "startTime", "endLot", "endTime");
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        grid.setDataProvider(DataProvider.ofCollection(rentHistoryService.findAllByUserId(1L)));
    }
// rid.setColumns("Start Parking Lot Name", "Start Rent Time", "End Parking Lot Name", "End Rent Time");
}
