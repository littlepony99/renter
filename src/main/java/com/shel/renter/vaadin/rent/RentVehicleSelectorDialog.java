package com.shel.renter.vaadin.rent;

import com.shel.renter.entity.ParkingLot;
import com.shel.renter.entity.RentHistory;
import com.shel.renter.entity.Vehicle;
import com.shel.renter.vaadin.service.RentHistoryService;
import com.shel.renter.vaadin.service.VehicleService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class RentVehicleSelectorDialog {
    private Dialog dialog;
    private Grid<Vehicle> grid;
    private Button rentButton;
    private List<Consumer<Vehicle>> listeners = new ArrayList<>();
    private RentHistoryService rentHistoryService;
    private VehicleService vehicleService;

    public RentVehicleSelectorDialog(RentHistoryService rentHistoryService,
                                     VehicleService vehicleService,
                                     List<Vehicle> vehicles, ParkingLot parkingLot, Long userId) {
        this.rentHistoryService = rentHistoryService;
        this.vehicleService = vehicleService;
        grid = new Grid<>(Vehicle.class);
        grid.setMinHeight("200px");
        grid.setWidthFull();
        grid.setColumns("make", "model", "prodYear", "color", "gearType", "fuelPercentage");
        // Set single select mode
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        rentButton = new Button("Select");
        this.dialog = new Dialog();
        dialog.add(grid, rentButton);
        grid.setDataProvider(DataProvider.ofCollection(vehicles));
        rentButton.addClickListener(buttonClickEvent -> {
            if (grid.getSelectedItems().isEmpty()) {
                return;
            }
            Vehicle rentVehicle = grid.getSelectedItems().stream().findFirst().orElse(null);
            if (Objects.nonNull(rentVehicle)) {
                startRent(rentVehicle, parkingLot, userId);
            }
            String value = "Car rented successfully, your car waiting for you at " + parkingLot.getAddress();
            var n = Notification.show(value, 4000, Notification.Position.TOP_CENTER);
//            try {
//                Thread.sleep(4000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            this.dialog.close();
            UI.getCurrent().getPage().reload();
        });
    }

    private void startRent(Vehicle vehicle, ParkingLot parkingLot, Long userId) {
        RentHistory history = RentHistory.builder()
                .vehicleId(vehicle.getId())
                .vehicleName(vehicle.getMake() + " " + vehicle.getModel())
                .startRentTime(LocalDateTime.now())
                .startLotName(parkingLot.getName() + " " + parkingLot.getAddress())
                .customerId(userId)
                .build();
        rentHistoryService.saveRent(history);
        vehicleService.startVehicleRent(vehicle);
    }

    private void finishRent(RentHistory rentHistory, ParkingLot parkingLot){
        rentHistory.setEndLotName(parkingLot.getName() + " " + parkingLot.getAddress());
        rentHistory.setEndRentTime(LocalDateTime.now());

        rentHistoryService.saveRent(rentHistory);
        vehicleService.endRent(parkingLot.getId(), rentHistory.getVehicleId());
    }

    public void setSize(String width, String height) {
        this.dialog.setWidth(width);
        this.dialog.setHeight(height);
    }

    public void addChangeListener(Consumer<Vehicle> consumer) {
        listeners.add(consumer);
    }

    private void setDataProvider(ListDataProvider<Vehicle> dataProvider) {
        grid.setDataProvider(dataProvider);
    }

    public void openDialog() {
        dialog.open();
    }

    public void closeDialog() {
        dialog.close();
    }

}