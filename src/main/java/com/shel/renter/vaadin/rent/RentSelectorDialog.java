package com.shel.renter.vaadin.rent;

import com.shel.renter.entity.ParkingLot;
import com.shel.renter.entity.Vehicle;
import com.shel.renter.vaadin.service.ParkingLotService;
import com.shel.renter.vaadin.service.RentHistoryService;
import com.shel.renter.vaadin.service.VehicleService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import jakarta.servlet.http.Cookie;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.shel.renter.vaadin.util.Util.getUserId;

public class RentSelectorDialog {
    private Dialog dialog;
    private Grid<ParkingLot> grid;
    private Button rentButton;
    private Button cancelButton;
    private List<Consumer<ParkingLot>> listeners = new ArrayList<>();
    private RentVehicleSelectorDialog vehicleDialog;

    private ParkingLotService parkingLotService;
    private VehicleService vehicleService;
    private RentHistoryService rentHistoryService;
    private Long selectedLot;

    public RentSelectorDialog(ParkingLotService parkingLotService, VehicleService vehicleService,
                              RentHistoryService rentHistoryService) {
        this.parkingLotService = parkingLotService;
        grid = new Grid<>(ParkingLot.class);
        grid.setColumns("name", "city", "address");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        rentButton = new Button("Select");
        cancelButton = new Button("Cancel");

        this.dialog = new Dialog();
        cancelButton.addClickListener(buttonClickEvent -> {
            this.dialog.close();
        });

        HorizontalLayout hor = new HorizontalLayout(rentButton, cancelButton);
        dialog.add(grid, hor);
        grid.setDataProvider(DataProvider.ofCollection(parkingLotService.findAll()));
        rentButton.addClickListener(buttonClickEvent -> {
            if (grid.getSelectedItems().isEmpty()) {
                return;
            }
            ParkingLot parkingLot = grid.getSelectedItems().stream().findFirst().orElse(null);
            Long lotId = parkingLot.getId();
            List<Vehicle> allByLotId = vehicleService.findAllByLotId(lotId);
            Cookie userId = getUserId();
            this.vehicleDialog = new RentVehicleSelectorDialog(rentHistoryService, vehicleService,
                    allByLotId, parkingLot, Long.valueOf(userId.getValue()));
            vehicleDialog.setSize("80%", "60%");
            vehicleDialog.openDialog();
            selectedLot = grid.getSelectedItems().stream()
                    .findFirst().map(ParkingLot::getId)
                    .orElse(null);
            this.dialog.close();
        });
    }

    public void setSize(String width, String height) {
        this.dialog.setWidth(width);
        this.dialog.setHeight(height);
    }

    public void addChangeListener(Consumer<ParkingLot> consumer) {
        listeners.add(consumer);
    }

    private void setDataProvider(ListDataProvider<ParkingLot> dataProvider) {
        grid.setDataProvider(dataProvider);
    }

    public void openDialog() {
        dialog.open();
    }

    public void closeDialog() {
        dialog.close();
    }
}