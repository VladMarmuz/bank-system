package com.banksystem.rates.service;

import com.banksystem.rates.model.LoanRates;
import org.springframework.stereotype.Component;

@Component
public class RateServiceClientFallback implements RateServiceClient {

    @Override
    public LoanRates requestRates() {
        return new LoanRates(2D, 2D);
    }
}
