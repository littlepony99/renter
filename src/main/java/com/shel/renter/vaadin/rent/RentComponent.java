package com.shel.renter.vaadin.rent;

import com.shel.renter.entity.ParkingLot;
import com.shel.renter.entity.RentHistory;
import com.shel.renter.vaadin.service.ParkingLotService;
import com.shel.renter.vaadin.service.RentHistoryService;
import com.shel.renter.vaadin.service.VehicleService;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.Optional;

import static com.shel.renter.vaadin.util.Util.getUserId;

@Slf4j
@Tag("rent")
@Route("/rent")
public class RentComponent extends VerticalLayout {

    private RentSelectorDialog lotDialog;
    private EndRentSelectorDialog endRentDialog;
    private Div container = new Div("TST!!!");
    private Button historyBtn;
    private Button logOutBtn;
    private Button rentBtn;
    private Button endRentButton;
    private RentHistoryService rentHistoryService;


    @Autowired
    public RentComponent(ParkingLotService parkingLotService,
                         RentHistoryService rentHistoryService,
                         VehicleService vehicleService) {
        this.rentHistoryService = rentHistoryService;
        Cookie userCookieId = getUserId();
        Long userId = Objects.nonNull(userCookieId) ? Long.valueOf(userCookieId.getValue()) : null;
        if (Objects.isNull(userId)) {
            UI.getCurrent().getPage().executeJs("window.location.href = 'http://localhost:8080/login'");
        }

        this.lotDialog = new RentSelectorDialog(parkingLotService, vehicleService, rentHistoryService);
        this.lotDialog.setSize("600px", "500px");
        this.lotDialog.addChangeListener(parkingLot -> {
            this.container.removeAll();
            this.container.add(getRentInfo(parkingLot));
        });
        this.rentBtn = new Button("Орендувати");
        this.rentBtn.addClickListener(event -> {
            this.lotDialog.openDialog();
        });

        this.container.setHeightFull();
        this.container.setWidthFull();
        var verticalLayout = new VerticalLayout();
        verticalLayout.setWidthFull();
        var btnLayout = new HorizontalLayout();
        initializeHeader();
        btnLayout.add(historyBtn, logOutBtn);
        btnLayout.setJustifyContentMode(JustifyContentMode.END);
        btnLayout.setWidthFull();
        verticalLayout.add(btnLayout);

        if (Objects.nonNull(userId)) {
            Optional<RentHistory> optRentHistory = rentHistoryService.findAllByUserId(Long.valueOf(userCookieId.getValue()))
                    .stream().filter(v -> Objects.isNull(v.getEndRentTime()))
                    .findFirst();
            if (optRentHistory.isEmpty()) {
                container.add(rentBtn);
                var rentLabelLayout = new HorizontalLayout(new H3("Знайдені автомобілі біля Вас. Бажаєте орендувати?"));
                rentLabelLayout.setWidthFull();
                rentLabelLayout.setJustifyContentMode(JustifyContentMode.CENTER);
                var rentBtnLayout = new HorizontalLayout(rentBtn);
                rentBtnLayout.setWidthFull();
                rentBtnLayout.setJustifyContentMode(JustifyContentMode.CENTER);
                var rentLayout = new VerticalLayout();
                rentLayout.add(rentLabelLayout, rentBtnLayout);
                rentLayout.setJustifyContentMode(JustifyContentMode.CENTER);
                rentLayout.setWidthFull();
                rentLayout.setHeight("85%");
                this.add(verticalLayout, rentLayout);
            } else {
                var endRentLabelLayout = new HorizontalLayout(new H3("Знайдені активні оренди. Хочете завершити оренду?"));
                endRentLabelLayout.setWidthFull();
                endRentLabelLayout.setJustifyContentMode(JustifyContentMode.CENTER);
                this.endRentButton = new Button("Кінець оренди");
                var endBtnLayout = new HorizontalLayout(endRentButton);
                endBtnLayout.setWidthFull();
                endBtnLayout.setJustifyContentMode(JustifyContentMode.CENTER);
                var endRentLayout = new VerticalLayout();
                endRentLayout.add(endRentLabelLayout, endBtnLayout);
                endRentLayout.setJustifyContentMode(JustifyContentMode.CENTER);
                endRentLayout.setWidthFull();
                endRentLayout.setHeight("85%");
                this.add(verticalLayout, endRentLayout);

                this.endRentDialog = new EndRentSelectorDialog(vehicleService, parkingLotService, rentHistoryService,
                        optRentHistory.get());
                this.endRentDialog.setSize("600px", "500px");
                this.endRentDialog.addChangeListener(parkingLot -> {
                    this.container.removeAll();
                    this.container.add(getRentInfo(parkingLot));
                });

                this.endRentButton.addClickListener(event -> {
                    this.endRentDialog.openDialog();
                });
            }


        }
    }

    private void initializeHeader() {
        this.historyBtn = new Button("Історія");
        this.historyBtn.addClickListener(onClick -> {
            UI.getCurrent().navigate("/history");
        });
        this.logOutBtn = new Button("Вихід");
        this.logOutBtn.addClickListener(event -> {
            Cookie userId = getUserId();
            if(Objects.nonNull(userId)){
                userId.setValue("invalid");
                userId.setMaxAge(0);
                VaadinService.getCurrentResponse().addCookie(userId);
            }
            UI.getCurrent().navigate("/login");
        });
    }

    private Span getRentInfo(ParkingLot parkingLot) {
        //return new Span("Car is waiting for you at: " + parkingLot.getName() + " - " + parkingLot.getAddress() + " - " + parkingLot.getCity());
        return new Span("Авто чекає Вас за адресою: " + parkingLot.getName() + " - " + parkingLot.getAddress() + " - " + parkingLot.getCity());
    }

}