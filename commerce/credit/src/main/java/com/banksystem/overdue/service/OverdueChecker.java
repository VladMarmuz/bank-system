package com.banksystem.overdue.service;

import com.banksystem.credit.service.CreditService;
import com.banksystem.overdue.dto.OverdueDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OverdueChecker {
    private final CreditService creditService;
    private final String kafkaTopic;
    private final KafkaTemplate<String, OverdueDto> kafkaTemplate;

    public OverdueChecker(final CreditService creditService,
                          final @Value("${topics.overdue.name}") String kafkaTopic,
                          final KafkaTemplate<String, OverdueDto> kafkaTemplate) {
        this.creditService = creditService;
        this.kafkaTopic = kafkaTopic;
        this.kafkaTemplate = kafkaTemplate;
    }
    @Scheduled(fixedRate = 10000)
    public void checkOverdue(){
        List<OverdueDto> overdueDtos = creditService.checkOverdue();
        overdueDtos.forEach(overdueDto -> kafkaTemplate.send(kafkaTopic, overdueDto));
    }
}
