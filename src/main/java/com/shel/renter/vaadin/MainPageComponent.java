package com.shel.renter.vaadin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("/")
public class MainPageComponent extends HorizontalLayout {

    private Button loginBtn;

    public MainPageComponent() {
//        if () {
//            loginBtn.setVisible(false);
//        }
        this.loginBtn = new Button("Login");
        Button rentButton = new Button("Rent");
        rentButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        rentButton.addClickListener(e -> {
            UI.getCurrent().navigate("/rent");
        });

        Button historyButton = new Button("History");
        historyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        historyButton.addClickListener(e -> {
            UI.getCurrent().navigate("/history");
        });

        Button logoutButton = new Button("Logout");
        logoutButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        logoutButton.addClickListener(e -> {
            UI.getCurrent().navigate("/login");
        });

        var layout = new VerticalLayout(loginBtn, rentButton, historyButton, logoutButton);
        layout.setHeightFull();
        layout.setWidthFull();
        layout.setAlignItems(Alignment.CENTER);
        this.setHeightFull();
        this.setWidthFull();
        add(layout);
    }
}
