package com.banksystem.rates.config;

import com.banksystem.rates.model.LoanRates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Configuration
public class RateProperties {

    @Bean
    @Profile("dev")
    public LoanRates devRates(final @Value("${loan.rate.fixed}") Double rateFixed,
                              final @Value("${loan.rate.floating}") Double rateFloating){
        return new LoanRates(rateFixed, rateFloating);
    }

    @Bean
    @Profile("prod")
    public LoanRates prodRates(final RestTemplateBuilder restTemplateBuilder,
                               final @Value("${loan.rateProvider}") String rateProvider){
        final RestTemplate restTemplate = restTemplateBuilder
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON.toString())
                .rootUri(rateProvider)
                .build();
        return restTemplate.getForEntity("/loan", LoanRates.class).getBody();
    }


}