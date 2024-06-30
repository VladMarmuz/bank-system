package com.banksystem.credit.service;

import com.banksystem.credit.dto.CreditDto;
import com.banksystem.credit.dto.UserCreditDto;
import com.banksystem.overdue.dto.OverdueDto;
import com.banksystem.overdue.model.OverdueProjection;
import com.banksystem.tech.exception.AlreadyPayedException;
import com.banksystem.tech.exception.EntityNotFoundException;
import com.banksystem.credit.model.Credit;
import com.banksystem.credit.model.PaymentPeriod;
import com.banksystem.credit.model.Status;
import com.banksystem.credit.repository.CreditRepository;
import com.banksystem.credit.service.period.PeriodCalculator;
import com.banksystem.credit.service.period.PeriodCalculatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditService {

    private final CreditRepository creditRepository;
    private final PeriodCalculatorFactory periodCalculatorFactory;

    public void addCredit(CreditDto creditDto) {
        PeriodCalculator calculator = periodCalculatorFactory.calculator(creditDto.periodType);
        List<PaymentPeriod> periods = calculator.calculate(creditDto);
        Credit credit = Credit.builder()
                .title(creditDto.title)
                .description(creditDto.description)
                .paymentPeriods(periods)
                .userId(creditDto.userId)
                .build();
        creditRepository.save(credit);
    }

    public List<UserCreditDto> getCreditByUserId(Long userId) {
        return creditRepository.findByUserId(userId).stream()
                .map(this::mapCreditDto)
                .toList();
    }

    private UserCreditDto mapCreditDto(Credit credit) {
        double remainingSum = credit.getPaymentPeriods().stream()
                .filter(this::getPaymentPeriodPredicate)
                .mapToDouble(PaymentPeriod::getSum)
                .sum();
        return UserCreditDto.createUserCreditDto(credit, remainingSum);
    }

    private boolean getPaymentPeriodPredicate(PaymentPeriod paymentPeriod) {
        return !paymentPeriod.getStatus().payed();
    }

    public void payNextPeriod(Long creditId) {
        Credit credit = creditRepository.findById(creditId).orElseThrow(EntityNotFoundException::new);
        PaymentPeriod nextPaymentPeriod = credit.getPaymentPeriods().stream()
                .filter(this::getPaymentPeriodPredicate)
                .min(Comparator.comparing(PaymentPeriod::getStartDate))
                .orElseThrow(AlreadyPayedException::new);
        nextPaymentPeriod.setStatus(Status.PAYED);
        creditRepository.save(credit);
    }

    public List<OverdueDto> checkOverdue() {
        return creditRepository.findOverdueHql(LocalDate.now())
                .stream()
                .map(this::mapOverdue)
                .collect(Collectors.toList());
    }

    private OverdueDto mapOverdue(OverdueProjection overdueProjection) {
        return new OverdueDto(overdueProjection.getRemainingSum(), overdueProjection.getOverdueDate(), overdueProjection.getUserId());
    }
}
