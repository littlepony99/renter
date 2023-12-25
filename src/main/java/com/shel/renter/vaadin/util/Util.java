package com.shel.renter.vaadin.util;

import com.vaadin.flow.server.VaadinService;
import jakarta.servlet.http.Cookie;

import java.util.Arrays;

import static com.shel.renter.vaadin.util.Const.USER_CODE;

public class Util {
    public static Cookie getUserId() {
        return Arrays.stream(VaadinService.getCurrentRequest().getCookies())
                .filter(c -> USER_CODE.equals(c.getName()) && !"invalid".equals(c.getValue()))
                .findFirst()
                .orElse(null);
    }
}
