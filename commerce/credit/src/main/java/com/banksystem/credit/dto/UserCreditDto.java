package com.banksystem.credit.dto;

import com.banksystem.credit.model.Credit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreditDto {
    public String title;
    public String description;
    public Long userId;
    public Double remainingSum;

    public static UserCreditDto createUserCreditDto(Credit credit, double remainingSum) {
        return new UserCreditDto(credit.getTitle(), credit.getDescription(), credit.getUserId(), remainingSum);
    }
}
