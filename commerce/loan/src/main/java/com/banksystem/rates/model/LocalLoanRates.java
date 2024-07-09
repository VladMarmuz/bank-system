package com.banksystem.rates.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LocalLoanRates implements LoanRateProvider{

    private final Double rateFixed;
    private final Double rateFloating;

    @Override
    public LoanRates rates() {
        return new LoanRates(rateFixed, rateFloating);
    }
}
