package com.banksystem.loan.service.period;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PeriodCalculatorFactory {

    private final PeriodCalculator monthlyCalculator;
    private final PeriodCalculator dailyCalculator;

    public PeriodCalculator calculator(PeriodType periodType) {
        return switch (periodType) {
            case MONTHLY -> monthlyCalculator;
            case DAILY -> dailyCalculator;
            default -> throw new UnsupportedOperationException("Unexpected periodType: " + periodType);
        };
    }

}
