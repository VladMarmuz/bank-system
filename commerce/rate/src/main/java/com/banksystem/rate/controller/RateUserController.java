package com.banksystem.rate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.banksystem.rate.RateApplication.API_VERSION;

@RestController
@RequestMapping(API_VERSION + "rate/")
public class RateUserController {

    @GetMapping("/user")
    public String user(Principal principal) {
        return "Response from rate-service, userId: " + principal.getName();
    }
}
