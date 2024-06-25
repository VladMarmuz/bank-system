package com.banksystem.credit.controller;

import com.banksystem.credit.dto.CreditDto;
import com.banksystem.credit.dto.UserCreditDto;
import com.banksystem.credit.service.CreditService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/credit",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class CreditController {

    private final CreditService creditService;

    @PostMapping
    public void addCredit(final @Valid @RequestBody CreditDto creditDto) {
        creditService.addCredit(creditDto);
    }

    @GetMapping(path = "/user/{userId}")
    public List<UserCreditDto> getUserCredits(final @PathVariable Long userId){
        return creditService.getCreditByUserId(userId);
    }

    @PutMapping(path = "/{creditId}")
    public void payNextPeriod(final @PathVariable Long creditId) {
        creditService.payNextPeriod(creditId);
    }

}
