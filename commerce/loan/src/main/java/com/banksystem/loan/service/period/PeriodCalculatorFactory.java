package com.banksystem.loan.service.period;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PeriodCalculatorFactory {

    private final PeriodCalculator monthlyCalculator;
    private final PeriodCalculator dailyCalculator;

    public PeriodCalculator calculator(PeriodType periodType) {
        switch (periodType) {
            case DAILY -> {
                return dailyCalculator;
            }
            case MONTHLY -> {
                return monthlyCalculator;
            }
            default -> throw new UnsupportedOperationException("Period type is not supported");
        }
    }

}
