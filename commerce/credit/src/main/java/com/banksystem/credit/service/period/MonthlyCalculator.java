package com.banksystem.credit.service.period;

import com.banksystem.credit.dto.CreditDto;
import com.banksystem.credit.model.PaymentPeriod;
import com.banksystem.credit.model.Status;
import com.banksystem.rates.model.CreditRates;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.LongFunction;
import java.util.stream.LongStream;

@Service
public class MonthlyCalculator implements PeriodCalculator {

    private final CreditRates creditRates;

    public MonthlyCalculator(CreditRates creditRates) {
        this.creditRates = creditRates;
    }

    @Override
    public List<PaymentPeriod> calculate(CreditDto creditDto) {
        LocalDate endDate = creditDto.start.plus(Period.of(creditDto.duration.years, creditDto.duration.months, creditDto.duration.days));
        long month = ChronoUnit.MONTHS.between(creditDto.start, endDate);
        List<Double> sumsPerMonth = creditDto.rateType.calculateAmount(month, creditDto.sum, creditRates);
        return LongStream.range(0, month)
                .mapToObj(createPaymentPeriods(creditDto, sumsPerMonth))
                .toList();
    }

    private LongFunction<PaymentPeriod> createPaymentPeriods(CreditDto creditDto, List<Double> sumsPerMonth) {
        return month -> PaymentPeriod.builder()
                .sum(sumsPerMonth.get(Math.toIntExact(month)))
                .startDate(creditDto.start.plusDays(month))
                .endDate(creditDto.start.plusDays(month + 1))
                .status(Status.FUTURE_PAYMENT)
                .build();
    }
}
