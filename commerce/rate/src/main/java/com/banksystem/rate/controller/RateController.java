package com.banksystem.rate.controller;


import com.banksystem.rate.dto.LoanRatesDto;
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

    @GetMapping(path="/loan")
    public LoanRatesDto loanRates(){
        return new LoanRatesDto(0.489, 0.13);
    }
}
