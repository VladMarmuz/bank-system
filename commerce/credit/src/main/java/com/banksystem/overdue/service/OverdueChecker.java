package com.banksystem.overdue.service;

import com.banksystem.credit.service.CreditService;
import com.banksystem.overdue.dto.OverdueDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OverdueChecker {
    private final CreditService creditService;
    private final String kafkaTopic;
    private final KafkaTemplate<String, OverdueDto> kafkaTemplate;

    @Scheduled(fixedRate = 10000)
    public void checkOverdue(){
        List<OverdueDto> overdueDtos = creditService.checkOverdue();
        overdueDtos.forEach(overdueDto -> kafkaTemplate.send(kafkaTopic, overdueDto));
    }
}
