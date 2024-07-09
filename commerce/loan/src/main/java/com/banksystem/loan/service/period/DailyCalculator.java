package com.banksystem.loan.service.period;

import com.banksystem.loan.dto.LoanDto;
import com.banksystem.loan.model.PaymentPeriod;
import com.banksystem.loan.model.Status;
import com.banksystem.rates.model.LoanRateProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.LongFunction;
import java.util.stream.LongStream;

@Service
@RequiredArgsConstructor
public class DailyCalculator implements PeriodCalculator {

    private final LoanRateProvider loanRateProvider;

    @Override
    public List<PaymentPeriod> calculate(LoanDto loanDto) {
        LocalDate endDate = loanDto.start.plus(Period.of(loanDto.duration.years, loanDto.duration.months, loanDto.duration.days));
        long days = ChronoUnit.DAYS.between(loanDto.start, endDate);
        List<Double> sumsPerMonth = loanDto.rateType.calculateAmount(days, loanDto.sum, loanRateProvider.rates());
        return LongStream.range(0, days)
                .mapToObj(createPaymentPeriods(loanDto, sumsPerMonth))
                .toList();
    }

    private LongFunction<PaymentPeriod> createPaymentPeriods(LoanDto loanDto, List<Double> sumsPerMonth) {
        return day -> PaymentPeriod.builder()
                .sum(sumsPerMonth.get(Math.toIntExact(day)))
                .startDate(loanDto.start.plusDays(day))
                .endDate(loanDto.start.plusDays(day + 1))
                .status(Status.FUTURE_PAYMENT)
                .build();
    }
}
