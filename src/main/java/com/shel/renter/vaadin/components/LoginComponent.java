package com.shel.renter.vaadin.components;

import com.shel.renter.vaadin.service.CustomerService;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

import static com.shel.renter.vaadin.util.Const.USER_CODE;
import static com.shel.renter.vaadin.util.Util.getUserId;

@Route("/login")
@Tag("login")
public class LoginComponent extends VerticalLayout {
    private final TextField loginTxt;
    private final PasswordField passwordTxt;
    private final Button loginButton;
    private final CustomerService customerService;

    @Autowired
    public LoginComponent(CustomerService customerService) {
        this.customerService = customerService;
        this.loginTxt = new TextField("Логін");
        this.passwordTxt = new PasswordField("Пароль");
        this.loginButton = new Button("Вхід");
        var loginLayout = new HorizontalLayout(loginTxt);
        var passLayout = new HorizontalLayout(passwordTxt);
        var btnLayout = new HorizontalLayout(loginButton);
        loginLayout.setWidthFull();
        loginLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        passLayout.setWidthFull();
        passLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        btnLayout.setWidthFull();
        btnLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        var fieldLayout = new VerticalLayout(loginLayout, passLayout, btnLayout);
        fieldLayout.setHeightFull();
        fieldLayout.setWidthFull();
        fieldLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        this.add(fieldLayout);
        initButtonsListeners();
    }

    private void initButtonsListeners() {
        loginButton.addClickListener(onClick -> {
            Long userId = customerService.login(loginTxt.getValue(), passwordTxt.getValue());
            if (Objects.nonNull(userId)) {
                Cookie oldCookie = getUserId();
                Cookie userIdCookie = Objects.nonNull(oldCookie) ?
                        oldCookie :
                        new Cookie(USER_CODE, String.valueOf(userId));
                userIdCookie.setMaxAge(120);
                VaadinService.getCurrentResponse().addCookie(userIdCookie);
                UI.getCurrent().navigate("/rent");
            } else {
                Notification.show("Невірний логін або пароль!", 3000, Notification.Position.TOP_CENTER);
            }
        });
    }

}
