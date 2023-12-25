package com.shel.renter.vaadin.rent;

import com.shel.renter.entity.ParkingLot;
import com.shel.renter.entity.RentHistory;
import com.shel.renter.entity.Vehicle;
import com.shel.renter.vaadin.service.ParkingLotService;
import com.shel.renter.vaadin.service.RentHistoryService;
import com.shel.renter.vaadin.service.VehicleService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.DataProvider;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EndRentSelectorDialog {
    private Dialog dialog;

    private Grid<ParkingLot> grid;
    private Button endRentButton;
    private Button cancelButton;
    private List<Consumer<ParkingLot>> listeners = new ArrayList<>();
    private VehicleService vehicleService;
    private ParkingLotService parkingLotService;
    private RentHistoryService rentHistoryService;

    public EndRentSelectorDialog(VehicleService vehicleService,
                                 ParkingLotService parkingLotService,
                                 RentHistoryService rentHistoryService,
                                 RentHistory rentHistory) {
        this.vehicleService = vehicleService;
        this.parkingLotService = parkingLotService;
        this.rentHistoryService = rentHistoryService;

        grid = new Grid<>(ParkingLot.class);
        grid.setColumns("name", "city", "address");
        Grid.Column<ParkingLot> name = grid.getColumnByKey("name");
        name.setHeader("Ім'я паркування");
        Grid.Column<ParkingLot> city = grid.getColumnByKey("city");
        city.setHeader("Місто");
        Grid.Column<ParkingLot> address = grid.getColumnByKey("address");
        address.setHeader("Адреса");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        endRentButton = new Button("Вибрати Паркування");
        cancelButton = new Button("Відмінити");
        this.dialog = new Dialog();
        cancelButton.addClickListener(buttonClickEvent -> {
            this.dialog.close();
        });
        HorizontalLayout hor = new HorizontalLayout(endRentButton, cancelButton);
        dialog.add(grid, hor);
        grid.setDataProvider(DataProvider.ofCollection(parkingLotService.findAll()));
        endRentButton.addClickListener(buttonClickEvent -> {
            if (!grid.getSelectedItems().isEmpty()) {
                ParkingLot parkingLot = grid.getSelectedItems().stream().findFirst().orElse(null);
                rentHistory.setEndLotName(parkingLot.getName() + " " + parkingLot.getAddress());
                rentHistory.setEndRentTime(LocalDateTime.now());
                rentHistoryService.saveRent(rentHistory);
                vehicleService.endVehicleRent(rentHistory.getVehicleId(), parkingLot.getId());
                Notification.show("Оренда закінчена. Дякую що користуєтесь нашим сервісом!", 4000, Notification.Position.TOP_CENTER);
                this.dialog.close();
//                UI.getCurrent().getPage().reload();
            }
        });
    }

    public void setSize(String width, String height) {
        this.dialog.setWidth(width);
        this.dialog.setHeight(height);
    }

    public void addChangeListener(Consumer<ParkingLot> consumer) {
        listeners.add(consumer);
    }

    public void openDialog() {
        dialog.open();
    }
}
