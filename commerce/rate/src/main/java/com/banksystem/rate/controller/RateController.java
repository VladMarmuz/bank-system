package com.banksystem.rate.controller;


import com.banksystem.rate.dto.CreditRatesDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.banksystem.rate.RateApplication.API_VERSION;

@RestController
@RequestMapping(path = API_VERSION + "rate/",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class RateController {

    @GetMapping(path="/credit")
    public CreditRatesDto creditRates(){
        return new CreditRatesDto(0.489, 0.13);
    }
}
