package com.banksystem.loan.dto;

import com.banksystem.loan.service.period.PeriodType;
import com.banksystem.loan.service.period.RateType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
public class LoanDto {

    @NotEmpty(message = "{loan.title.empty}")
    @Size(max = 25, message = "{loan.title.size}")
    public String title;
    @NotEmpty(message = "{loan.description.empty}")
    @Size(max = 250, message = "{loan.description.size}")
    public String description;
    @NotNull(message = "{loan.userid.notnull}")
    public Long userId;
    @Valid
    @NotNull(message = "{loan.duration.notnull}")
    public DurationDto duration;
    @NotNull(message = "{loan.sum.notnull}")
    @Positive(message = "{loan.sum.positive}")
    public Double sum;
    @NotNull(message = "{loan.rateType.notnull}")
    public RateType rateType;
    @NotNull(message = "{loan.periodType.notnull}")
    public PeriodType periodType;
    @NotNull(message = "{loan.startDate.notnull}")
    @FutureOrPresent(message = "{loan.startDate.future}")
    public LocalDate start;
}
