package com.banksystem.rates.service;

import com.banksystem.rates.model.LoanRates;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient
public interface RateServiceClient {
    @GetMapping(path = "api/v1/rate/loan",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    LoanRates requestRates();
}
