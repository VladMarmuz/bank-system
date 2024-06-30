package com.banksystem.overdue.dto;

import java.time.LocalDate;

public record OverdueDto(Double remainingSum, LocalDate overdueDate,Long userId) {

}
