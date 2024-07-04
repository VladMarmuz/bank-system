package com.banksystem.loan.service;

import com.banksystem.loan.dto.LoanDto;
import com.banksystem.loan.model.PaymentPeriod;
import com.banksystem.loan.service.period.PeriodCalculator;

import java.util.List;

public class Calculator implements PeriodCalculator {
    private final List<PaymentPeriod> paymentPeriods;

    public Calculator(List<PaymentPeriod> paymentPeriods) {
        this.paymentPeriods = paymentPeriods;
    }


    public List<PaymentPeriod> calculate(LoanDto loanDto) {
        return paymentPeriods;
    }
}
