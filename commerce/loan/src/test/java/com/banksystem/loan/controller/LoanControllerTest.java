package com.banksystem.loan.controller;


import com.banksystem.loan.dto.DurationDto;
import com.banksystem.loan.dto.LoanDto;
import com.banksystem.loan.dto.UserLoanDto;
import com.banksystem.loan.model.Loan;
import com.banksystem.loan.service.LoanService;
import com.banksystem.loan.service.period.PeriodType;
import com.banksystem.loan.service.period.RateType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("dev")
@SpringBootTest
@AutoConfigureMockMvc
class LoanControllerTest {
    @MockBean
    private LoanService loanService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void userLoans() throws Exception {
//        Given
        Long userId = 101L;
        Loan loan = new Loan("loanDto.title", "loanDto.description",
                userId, emptyList());
        UserLoanDto userLoanDto = UserLoanDto.createUserLoanDto(loan, 1000d);
        when(loanService.getLoansByUserId(userId)).thenReturn(singletonList(userLoanDto));
//        When
        ResultActions result = mockMvc.perform(get("/api/v1/loan/user/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON));
//        Then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(userLoanDto.title)))
                .andExpect(jsonPath("$[0].description", is(userLoanDto.description)))
                .andExpect(jsonPath("$[0].userId", is(userLoanDto.userId.intValue())))
                .andExpect(jsonPath("$[0].remainingSum", is(userLoanDto.remainingSum)));

        verify(loanService, times(1)).getLoansByUserId(userId);
    }

    @Test
    public void payNextPeriod() throws Exception {
//        Given
        Long loanId = 101L;
        doNothing().when(loanService).payNextPeriod(loanId);
//        When
        ResultActions result = mockMvc.perform(put("/api/v1/loan/{loanId}", loanId)
                .contentType(MediaType.APPLICATION_JSON));
//        Then
        result.andExpect(status().isOk());
        verify(loanService, times(1)).payNextPeriod(loanId);
        verifyNoMoreInteractions(loanService);
    }

    @Test
    public void addLoan() throws Exception {
//        Given
        DurationDto durationDto = new DurationDto(1, 1, 1);
        LoanDto loanDto = new LoanDto("LoanTitle", "LoanDescription",
                101L, durationDto, 100d, RateType.FIXED,
                PeriodType.MONTHLY, LocalDate.now());
        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        String content = objectMapper.writeValueAsString(loanDto);
        doNothing().when(loanService).addLoan(any(LoanDto.class));
//        When

        ResultActions result = mockMvc.perform(post("/api/v1/loan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
//        Then
        result.andExpect(status().isOk());
        verify(loanService, times(1)).addLoan(any(LoanDto.class));

    }
}