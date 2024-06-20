package com.banksystem.credit.dto;

import com.banksystem.credit.service.period.PeriodType;
import com.banksystem.credit.service.period.RateType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
public class CreditDto {

    public String title;
    public String description;
    public Long userId;
    public Double sum;
    public LocalDate start;
    public DurationDto duration;
    public RateType rateType;
    public PeriodType periodType;
}
