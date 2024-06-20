package com.banksystem.credit.service;

import com.banksystem.credit.dto.CreditDto;
import com.banksystem.credit.repository.CreditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditService {

    private final CreditRepository creditRepository;

    public void addCredit(CreditDto creditDto) {
    }
}
