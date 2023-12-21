package com.shel.renter.vaadin.rent;

import com.shel.renter.entity.ParkingLot;
import com.shel.renter.vaadin.service.ParkingLotService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RentComponentDialog {
    private Dialog dialog;
    private Grid<ParkingLot> grid;
    private Button okButton;
    private List<Consumer<ParkingLot>> listeners = new ArrayList<>();

    public RentComponentDialog(ParkingLotService service) {
        grid = new Grid<>(ParkingLot.class);
        grid.setColumns("name", "city", "address");
        // Set single select mode
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        okButton = new Button("Rent");
        // Create a dialog and add the grid to it
        this.dialog = new Dialog();
        dialog.add(grid);
        grid.setDataProvider(DataProvider.ofCollection(service.findAll()));
        okButton.addClickListener(buttonClickEvent -> {
            grid.addSelectionListener(event -> {
                if (event.getAllSelectedItems().isEmpty()) {
                    return;
                }
                listeners.forEach(v -> event.getAllSelectedItems().stream().findFirst().orElse(null));
            });
        });
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