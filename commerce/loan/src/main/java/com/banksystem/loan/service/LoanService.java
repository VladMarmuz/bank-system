package com.banksystem.loan.service;

import com.banksystem.loan.dto.LoanDto;
import com.banksystem.loan.dto.UserLoanDto;
import com.banksystem.loan.model.Loan;
import com.banksystem.loan.repository.LoanRepository;
import com.banksystem.overdue.dto.OverdueDto;
import com.banksystem.overdue.model.OverdueProjection;
import com.banksystem.tech.exception.AlreadyPayedException;
import com.banksystem.tech.exception.EntityNotFoundException;
import com.banksystem.loan.model.PaymentPeriod;
import com.banksystem.loan.model.Status;
import com.banksystem.loan.service.period.PeriodCalculator;
import com.banksystem.loan.service.period.PeriodCalculatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanService {
    public static final String LOAN_NOT_FOUND = "Loan not found";
    private final LoanRepository loanRepository;
    private final PeriodCalculatorFactory periodCalculatorFactory;

    public void addLoan(LoanDto loanDto) {
        PeriodCalculator calculator = periodCalculatorFactory.calculator(loanDto.periodType);
        List<PaymentPeriod> periods = calculator.calculate(loanDto);
        Loan loan = Loan.builder()
                .title(loanDto.title)
                .description(loanDto.description)
                .paymentPeriods(periods)
                .userId(loanDto.userId)
                .build();
        loanRepository.save(loan);
    }

    public List<UserLoanDto> getLoansByUserId(Long userId) {
        return loanRepository.findByUserId(userId).stream()
                .map(this::mapToUserLoanDto)
                .toList();
    }

    private UserLoanDto mapToUserLoanDto(Loan loan) {
        double remainingSum = loan.getPaymentPeriods().stream()
                .filter(this::notPayed)
                .mapToDouble(PaymentPeriod::getSum)
                .sum();
        return UserLoanDto.createUserLoanDto(loan, remainingSum);
    }

    private boolean notPayed(PaymentPeriod paymentPeriod) {
        return !paymentPeriod.getStatus().payed();
    }

    public void payNextPeriod(Long loanId) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new EntityNotFoundException(LOAN_NOT_FOUND));
        PaymentPeriod nextPaymentPeriod = loan.getPaymentPeriods().stream()
                .filter(this::notPayed)
                .min(Comparator.comparing(PaymentPeriod::getStartDate))
                .orElseThrow(AlreadyPayedException::new);
        nextPaymentPeriod.setStatus(Status.PAYED);
        loanRepository.save(loan);
    }

    public List<OverdueDto> checkOverdue() {
        return loanRepository.findOverdueHql(LocalDate.now())
                .stream()
                .map(this::mapOverdue)
                .collect(Collectors.toList());
    }

    private OverdueDto mapOverdue(OverdueProjection overdueProjection) {
        return new OverdueDto(overdueProjection.getRemainingSum(), overdueProjection.getOverdueDate(), overdueProjection.getUserId());
    }
}
