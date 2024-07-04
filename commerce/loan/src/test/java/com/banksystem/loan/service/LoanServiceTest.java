package com.banksystem.loan.service;

import com.banksystem.loan.dto.DurationDto;
import com.banksystem.loan.dto.LoanDto;
import com.banksystem.loan.dto.UserLoanDto;
import com.banksystem.loan.model.Loan;
import com.banksystem.loan.model.PaymentPeriod;
import com.banksystem.loan.model.Status;
import com.banksystem.loan.repository.LoanRepository;
import com.banksystem.loan.service.period.PeriodCalculatorFactory;
import com.banksystem.loan.service.period.PeriodType;
import com.banksystem.loan.service.period.RateType;
import com.banksystem.tech.exception.AlreadyPayedException;
import com.banksystem.tech.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;
    @Mock
    private PeriodCalculatorFactory periodCalculatorFactory;
    @InjectMocks
    private LoanService loanService;

    @Test
    public void addLoan() {
//        Given
        final PeriodType periodType = PeriodType.MONTHLY;
        final LoanDto loanDto = loanDto(periodType);
        final List<PaymentPeriod> paymentPeriods = paymentPeriods();
        final Loan loan = new Loan(loanDto.title, loanDto.description, loanDto.userId, paymentPeriods);
        when(periodCalculatorFactory.calculator(periodType)).thenReturn(new Calculator(paymentPeriods));
//        When
        loanService.addLoan(loanDto);
//        Then
        verify(loanRepository, times(1)).save(loan);
        verify(periodCalculatorFactory, times(1)).calculator(periodType);
        verifyNoMoreInteractions(loanRepository, periodCalculatorFactory);
    }

    @Test
    public void loanByUserId() {
//        Given
        final Long userId = 110L;
        final List<PaymentPeriod> periods = paymentPeriods();
        periods.get(0).setStatus(Status.PAYED);
        final Double remainingSum = periods
                .stream()
                .filter(period -> !period.getStatus().payed())
                .mapToDouble(PaymentPeriod::getSum)
                .sum();
        final Loan loan = new Loan("loanDto.title", "loanDto.description",
                userId, periods);
        when(loanRepository.findByUserId(userId)).thenReturn(singletonList(loan));
//        When
        List<UserLoanDto> userLoanDtos = loanService.getLoansByUserId(userId);
//        Then
        assertFalse(userLoanDtos.isEmpty());
        assertEquals(remainingSum, userLoanDtos.get(0).remainingSum);
        assertEquals("loanDto.title", userLoanDtos.get(0).title);
        assertEquals("loanDto.description", userLoanDtos.get(0).description);
        assertEquals(userId, userLoanDtos.get(0).userId);
        verify(loanRepository, times(1)).findByUserId(userId);
        verifyNoMoreInteractions(loanRepository);
    }

    @Test
    public void payNextPeriod() {
//        Given
        final Long loanId = 101L;
        final List<PaymentPeriod> periods = paymentPeriods();
        periods.get(0).setStatus(Status.PAYED);
        final Loan loan = new Loan("loanDto.title", "loanDto.description",
                101L, periods);
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));
//        When
        loanService.payNextPeriod(loanId);
//        Then
        assertEquals(Status.PAYED, periods.get(1).getStatus());
        verify(loanRepository, times(1)).findById(loanId);
        verify(loanRepository, times(1)).save(loan);
        verifyNoMoreInteractions(loanRepository);
    }

    @Test
    public void payNextPeriodNotFound() {
//        Given
        Long loanId = 101L;
        when(loanRepository.findById(loanId)).thenReturn(Optional.empty());
//        When
        assertThrows(EntityNotFoundException.class,
                () -> loanService.payNextPeriod(loanId),
                "Expected loanService.payNextPeriod to throw EntityNotFoundException");
    }

    @Test
    public void payNextPeriodAlreadyPayed() {
//        Given
        Long loanId = 101L;
        List<PaymentPeriod> periods = paymentPeriods();
        periods.forEach(period -> period.setStatus(Status.PAYED));
        Loan loan = new Loan("loanDto.title", "loanDto.description",
                101L, periods);
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));
//        When/Then
        assertThrows(AlreadyPayedException.class,
                () -> loanService.payNextPeriod(loanId),
                "Expected loanService.payNextPeriod to throw AlreadyPayedException");

    }

    private LoanDto loanDto(final PeriodType periodType) {
        DurationDto durationDto = new DurationDto(1, 1, 1);
        return new LoanDto("LoanTitle", "LoanDescription",
                101L, durationDto, 100d, RateType.FIXED,
                periodType, LocalDate.now());
    }

    private List<PaymentPeriod> paymentPeriods() {
        return List.of(new PaymentPeriod(100d, LocalDate.now(), LocalDate.now().plusMonths(1), Status.FUTURE_PAYMENT),
                new PaymentPeriod(100d, LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(2), Status.FUTURE_PAYMENT),
                new PaymentPeriod(125d, LocalDate.now().plusMonths(2), LocalDate.now().plusMonths(3), Status.FUTURE_PAYMENT),
                new PaymentPeriod(150d, LocalDate.now().plusMonths(3), LocalDate.now().plusMonths(4), Status.FUTURE_PAYMENT));
    }
}
