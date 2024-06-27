package com.banksystem.credit.service.period;


import com.banksystem.rates.model.CreditRates;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

public enum RateType {
    FIXED {
        @Override
        public List<Double> calculateAmount(final Long units, final Double sum, final CreditRates creditRates) {
            final Double finalSum = sum * (1 + creditRates.rateFixed);
            return LongStream.range(0, units)
                    .mapToDouble(month -> finalSum / units)
                    .boxed()
                    .toList();
        }
    },
    FLOATING {
        @Override
        public List<Double> calculateAmount(final Long units, final Double sum, final CreditRates creditRates) {
            double floatingRate = creditRates.rateFloating;
            final double sumPerMonth = sum / units;
            List<Double> amounts = new ArrayList<>();
            for (int i = 0; i < units; i++) {
                Double floatingAmount = sumPerMonth * (1 + floatingRate);
                floatingRate += floatingRate;
                amounts.add(floatingAmount);
            }
            return amounts;
        }
    };

    public abstract List<Double> calculateAmount(Long units, Double sum, CreditRates creditRates);
}
