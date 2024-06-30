package com.banksystem.overdue.service;

import com.banksystem.credit.service.CreditService;
import com.banksystem.overdue.dto.OverdueDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class OverdueChecker {
    private final Logger LOGGER = LoggerFactory.getLogger(OverdueChecker.class);

    private final String kafkaTopic;
    private final KafkaTemplate<String, OverdueDto> kafkaTemplate;

    @Scheduled(fixedRate = 10000)
    public void checkOverdue(){
        OverdueDto overdueDto = new OverdueDto(2000D, LocalDate.now(), 10L);
        kafkaTemplate.send(kafkaTopic, overdueDto);
    }
}
