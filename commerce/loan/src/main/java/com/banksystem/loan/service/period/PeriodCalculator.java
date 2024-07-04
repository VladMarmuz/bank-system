package com.banksystem.loan.service.period;

import com.banksystem.loan.dto.LoanDto;
import com.banksystem.loan.model.PaymentPeriod;

import java.util.List;

public interface PeriodCalculator {
    List<PaymentPeriod> calculate(LoanDto loanDto);
}
