package com.shel.renter.vaadin.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class LoginComponent extends VerticalLayout {
    private final TextField loginTxt;
    private final TextField passwordTxt;
    private final Button loginButton;
    private final Button cancelButton;

    public LoginComponent() {
        this.loginTxt = new TextField("Login");
        this.passwordTxt = new TextField("Password");
        this.loginButton = new Button("Login");
        this.cancelButton = new Button("Cancel");
        var buttonLayout = new HorizontalLayout(loginButton, cancelButton);
        initButtonsListeners();
        this.add(loginTxt, passwordTxt, buttonLayout);
    }

    private void initButtonsListeners() {
        loginButton.addClickListener(onClick -> {

        });
    }

}
