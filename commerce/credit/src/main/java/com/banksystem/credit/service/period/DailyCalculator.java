package com.banksystem.credit.service.period;

import com.banksystem.credit.dto.CreditDto;
import com.banksystem.credit.model.PaymentPeriod;
import com.banksystem.credit.model.Status;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.LongFunction;
import java.util.stream.LongStream;

@Service
public class DailyCalculator implements PeriodCalculator {

    @Override
    public List<PaymentPeriod> calculate(CreditDto creditDto) {
        LocalDate endDate = creditDto.start.plus(Period.of(creditDto.duration.years, creditDto.duration.months, creditDto.duration.days));
        long days = ChronoUnit.DAYS.between(creditDto.start, endDate);
        List<Double> sumsPerMonth = creditDto.rateType.calculateAmount(days, creditDto.sum);
        return LongStream.range(0, days)
                .mapToObj(createPaymentPeriods(creditDto, sumsPerMonth))
                .toList();
    }

    private LongFunction<PaymentPeriod> createPaymentPeriods(CreditDto creditDto, List<Double> sumsPerMonth) {
        return day -> PaymentPeriod.builder()
                .sum(sumsPerMonth.get(Math.toIntExact(day)))
                .startDate(creditDto.start.plusDays(day))
                .endDate(creditDto.start.plusDays(day + 1))
                .status(Status.FUTURE_PAYMENT)
                .build();
    }
}
