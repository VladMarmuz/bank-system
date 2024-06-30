package com.banksystem.overdue.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OverdueDto {

    private Double remainingSum;
    private LocalDate overdueDate;
    private Long userId;

    @JsonCreator
    public OverdueDto(@JsonProperty("remainingSum") Double remainingSum,
                      @JsonProperty("overdueDate") LocalDate overdueDate,
                      @JsonProperty("userId") Long userId) {
        this.remainingSum = remainingSum;
        this.overdueDate = overdueDate;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "OverdueDto [" +
               "remainingSum=" + remainingSum +
               ", overdueDate=" + overdueDate +
               ", userId=" + userId +
               ']';
    }

}
