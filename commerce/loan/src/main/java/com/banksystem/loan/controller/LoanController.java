package com.banksystem.loan.controller;

import com.banksystem.loan.dto.LoanDto;
import com.banksystem.loan.dto.UserLoanDto;
import com.banksystem.loan.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/loan",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    public void addLoan(final @Valid @RequestBody LoanDto loanDto) {
        loanService.addLoan(loanDto);
    }

    @GetMapping(path = "/user/{userId}")
    public List<UserLoanDto> getUserLoans(final @PathVariable Long userId) {
        return loanService.getLoansByUserId(userId);
    }

    @PutMapping(path = "/{loanId}")
    public void payNextPeriod(final @PathVariable Long loanId) {
        loanService.payNextPeriod(loanId);
    }

}
