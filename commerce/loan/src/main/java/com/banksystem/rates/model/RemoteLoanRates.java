package com.banksystem.rates.model;

import com.banksystem.rates.service.RateServiceClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RemoteLoanRates implements LoanRateProvider{

    private final RateServiceClient rateServiceClient;

    @Override
    public LoanRates rates() {
        return rateServiceClient.requestRates();
    }
}
