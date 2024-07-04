package com.banksystem.loan.service.period;

import com.banksystem.loan.dto.LoanDto;
import com.banksystem.loan.model.PaymentPeriod;
import com.banksystem.loan.model.Status;
import com.banksystem.rates.model.LoanRates;
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
public class MonthlyCalculator implements PeriodCalculator {

    private final LoanRates loanRates;

    @Override
    public List<PaymentPeriod> calculate(LoanDto loanDto) {
        LocalDate endDate = loanDto.start.plus(Period.of(loanDto.duration.years, loanDto.duration.months, loanDto.duration.days));
        long month = ChronoUnit.MONTHS.between(loanDto.start, endDate);
        List<Double> sumsPerMonth = loanDto.rateType.calculateAmount(month, loanDto.sum, loanRates);
        return LongStream.range(0, month)
                .mapToObj(createPaymentPeriods(loanDto, sumsPerMonth))
                .toList();
    }

    private LongFunction<PaymentPeriod> createPaymentPeriods(LoanDto loanDto, List<Double> sumsPerMonth) {
        return month -> PaymentPeriod.builder()
                .sum(sumsPerMonth.get(Math.toIntExact(month)))
                .startDate(loanDto.start.plusDays(month))
                .endDate(loanDto.start.plusDays(month + 1))
                .status(Status.FUTURE_PAYMENT)
                .build();
    }
}
