package com.banksystem.overdue.service;

import com.banksystem.loan.service.LoanService;
import com.banksystem.overdue.dto.OverdueDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OverdueChecker {
    private final LoanService loanService;
    private final String kafkaTopic;
    private final KafkaTemplate<String, OverdueDto> kafkaTemplate;

    public OverdueChecker(final LoanService loanService,
                          final @Value("${topics.overdue.name}") String kafkaTopic,
                          final KafkaTemplate<String, OverdueDto> kafkaTemplate) {
        this.loanService = loanService;
        this.kafkaTopic = kafkaTopic;
        this.kafkaTemplate = kafkaTemplate;
    }
    @Scheduled(fixedRate = 10000)
    public void checkOverdue(){
        List<OverdueDto> overdueDtos = loanService.checkOverdue();
        overdueDtos.forEach(overdueDto -> kafkaTemplate.send(kafkaTopic, overdueDto));
    }
}
