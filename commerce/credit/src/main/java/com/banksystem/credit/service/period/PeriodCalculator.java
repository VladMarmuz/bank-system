package com.banksystem.credit.service.period;

import com.banksystem.credit.dto.CreditDto;
import com.banksystem.credit.model.PaymentPeriod;

import java.util.List;

public interface PeriodCalculator {
    List<PaymentPeriod> calculate(CreditDto creditDto);
}
