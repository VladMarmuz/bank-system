package com.banksystem.credit.controller;

import com.banksystem.credit.dto.CreditDto;
import com.banksystem.credit.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/credit",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class CreditController {

    private final CreditService creditService;

    @PostMapping
    public void addCredit(final @RequestBody CreditDto creditDto) {
        creditService.addCredit(creditDto);
    }


}
