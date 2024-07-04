package com.banksystem.credit.dto;

import com.banksystem.credit.service.period.PeriodType;
import com.banksystem.credit.service.period.RateType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
public class CreditDto {

    @NotEmpty(message = "{credit.title.empty}")
    @Size(max = 25, message = "{credit.title.size}")
    public String title;
    @NotEmpty(message = "{credit.description.empty}")
    @Size(max = 250, message = "{credit.description.size}")
    public String description;
    @NotNull(message = "{credit.userid.notnull}")
    public Long userId;
    @Valid
    @NotNull(message = "{credit.duration.notnull}")
    public DurationDto duration;
    @NotNull(message = "{credit.sum.notnull}")
    @Positive(message = "{credit.sum.positive}")
    public Double sum;
    @NotNull(message = "{credit.rateType.notnull}")
    public RateType rateType;
    @NotNull(message = "{credit.periodType.notnull}")
    public PeriodType periodType;
    @NotNull(message = "{credit.startDate.notnull}")
    @FutureOrPresent(message = "{credit.startDate.future}")
    public LocalDate start;
}
