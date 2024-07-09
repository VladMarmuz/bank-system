package com.banksystem.loan.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Range;

@AllArgsConstructor
public class DurationDto {
    @Range(min = 0, max = 31, message = "{duration.days.minmax}")
    public int days;
    @Range(min = 0, max = 12, message = "{duration.months.minmax}")
    public int months;
    @PositiveOrZero
    public int years;

}
