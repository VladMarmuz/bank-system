package com.banksystem.loan.dto;

import com.banksystem.loan.model.Loan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoanDto {
    public String title;
    public String description;
    public Long userId;
    public Double remainingSum;

    public static UserLoanDto createUserLoanDto(Loan loan, double remainingSum) {
        return new UserLoanDto(loan.getTitle(), loan.getDescription(), loan.getUserId(), remainingSum);
    }
}
