package com.swd392.funfundbe.controller;

import com.swd392.funfundbe.controller.api.WelcomeController;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeControllerImpl implements WelcomeController {

    @Override
    public String welcomeUser(String username) {
        return "Welcome " + username + " to Krowd Investment Project named: FunFund";
    }
}
