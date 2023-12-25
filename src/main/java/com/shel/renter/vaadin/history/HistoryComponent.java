package com.shel.renter.vaadin.history;

import com.shel.renter.entity.RentHistory;
import com.shel.renter.vaadin.service.RentHistoryService;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("/history")
@Tag("history")
public class HistoryComponent extends VerticalLayout {
    private final Grid<RentHistory> grid;
    private Button backButton;

    @Autowired
    private RentHistoryService rentHistoryService;

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public HistoryComponent() {
        Cookie userCookieId = getUserId();
        Long userId = Objects.nonNull(userCookieId) ? Long.valueOf(userCookieId.getValue()) : null;
        if (Objects.isNull(userId)) {
            UI.getCurrent().getPage().executeJs("window.location.href = 'http://localhost:8080/login'");
        }

        this.backButton = new Button("< На головну");
        this.backButton.addClickListener(event -> {
            UI.getCurrent().navigate("/rent");
        });
        var btnLayout = new HorizontalLayout(backButton);
        grid = new Grid<>(RentHistory.class);
        grid.setColumns("vehicleName", "startLotName", "startRentTime", "endLotName", "endRentTime");

        Grid.Column<RentHistory> vehicleName = grid.getColumnByKey("vehicleName");
        vehicleName.setHeader("Автомобіль");

        Grid.Column<RentHistory> startLot = grid.getColumnByKey("startLotName");
        startLot.setHeader("Початкова точка оренди");
        Grid.Column<RentHistory> endLot = grid.getColumnByKey("endLotName");
        endLot.setHeader("Кінцева точка оренди");
        Grid.Column<RentHistory> startLotName = grid.getColumnByKey("startRentTime");
        startLotName.setRenderer(new ComponentRenderer<>(e -> {
            if (Objects.nonNull(e.getStartRentTime())) {
                return new Span(dateTimeFormatter.format(e.getStartRentTime()));
            }
            return new Span("");
        }));
        startLotName.setHeader("Початковий час оренди");

        Grid.Column<RentHistory> endLotTime = grid.getColumnByKey("endRentTime");
        endLotTime.setHeader("Кінцевий час оренди");
        endLotTime.setRenderer(new ComponentRenderer<>(e -> {
            if (Objects.nonNull(e.getEndRentTime())) {
                return new Span(dateTimeFormatter.format(e.getEndRentTime()));
            }
            return new Span("");
        }));
        this.add(btnLayout, grid);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        Cookie userIdValue = getUserId();
        if (Objects.nonNull(userIdValue)) {
            grid.setDataProvider(DataProvider.ofCollection(rentHistoryService.findAllByUserId(Long.valueOf(userIdValue.getValue()))));
        }
    }
}
