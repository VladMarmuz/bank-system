package com.banksystem.rates.model;

public class LoanRates {
    public final Double rateFixed;
    public final Double rateFloating;

    public LoanRates(Double rateFixed, Double rateFloating) {
        this.rateFixed = rateFixed;
        this.rateFloating = rateFloating;
    }
}